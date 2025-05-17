package com.chelchowskidawidjan;

import static com.chelchowskidawidjan.generated.Tables.*;

import com.chelchowskidawidjan.generated.tables.records.UsersRecord;
import org.jooq.*;
import org.jooq.exception.DataAccessException;
import org.jooq.impl.DSL;

import java.sql.*;
import java.sql.DriverManager;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class DatabaseUserRepository implements UserRepository {

    private final String url = "jdbc:postgresql://localhost:5432/postgres";
    private final String dbUser = "postgres";
    private final String dbPassword = "postgres";

    public DatabaseUserRepository() {
    }

    @Override
    public boolean persistUserCreation(String[] objectName, String[] UUID, String[] passwordHash, String[] passwordSalt) {
        try(Connection con = DriverManager.getConnection(url, dbUser, dbPassword)){
            DSLContext ctx = DSL.using(con, SQLDialect.POSTGRES);
            InsertValuesStep5<UsersRecord, String[], String[], String[], String[], Boolean> step =
                    ctx.insertInto(USERS, USERS.UUID, USERS.OBJECTNAME, USERS.PASSWORDHASH, USERS.PASSWORDSALT, USERS.ISADMIN)
                            .values(UUID, objectName, passwordHash, passwordSalt, false);
            step.execute();
            con.close();
            return true;
        }
        catch(java.sql.SQLException e){
            System.err.println("Error while establishing connection to database:\n" + e.getMessage());
            return false;
        }
        catch(DataAccessException e) {
            System.err.println("Error while writing data to the database:\n" + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean persistUserDeletion(String[] UUID) {
        try(Connection con = DriverManager.getConnection(url, dbUser, dbPassword)){
            DSLContext ctx = DSL.using(con, SQLDialect.POSTGRES);
            ctx.deleteFrom(USERS).where(USERS.UUID.eq(UUID)).execute();
            con.close();
            return true;
        }
        catch(java.sql.SQLException e){
            System.err.println("Error while establishing connection to database:\n" + e.getMessage());
            return false;
        }
        catch(DataAccessException e) {
            System.err.println("Error while writing data to the database:\n" + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean persistUserNameUpdate(String[] UUID, String[] name) {
        try(Connection con = DriverManager.getConnection(url, dbUser, dbPassword)){
            DSLContext ctx = DSL.using(con, SQLDialect.POSTGRES);

            ctx.update(USERS).set(USERS.OBJECTNAME, name).where(USERS.UUID.eq(UUID)).execute();

            con.close();
            return true;
        }
        catch(java.sql.SQLException e){
            System.err.println("Error while establishing connection to database:\n" + e.getMessage());
            return false;
        }
        catch(DataAccessException e) {
            System.err.println("Error while writing data to the database:\n" + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean persistUserPasswordUpdate(String[] UUID, String[] passwordHash, String[] passwordSalt){
        try(Connection con = DriverManager.getConnection(url, dbUser, dbPassword)){
            DSLContext ctx = DSL.using(con, SQLDialect.POSTGRES);

            ctx.update(USERS).set(USERS.PASSWORDHASH, passwordHash).set(USERS.PASSWORDSALT, passwordSalt).where(USERS.UUID.eq(UUID)).execute();

            con.close();
            return true;
        }
        catch(java.sql.SQLException e){
            System.err.println("Error while establishing connection to database:\n" + e.getMessage());
            return false;
        }
        catch(DataAccessException e) {
            System.err.println("Error while writing data to the database:\n" + e.getMessage());
            return false;
        }
    }

    @Override
    public User fetchUserByUUID(String[] UUID) {
        try(Connection con = DriverManager.getConnection(url, dbUser, dbPassword)){
            DSLContext ctx = DSL.using(con, SQLDialect.POSTGRES);

            UsersRecord userRecord = ctx.selectFrom(USERS).where(USERS.UUID.eq(UUID)).fetchOne();
                    
            User user = new User(Arrays.toString(userRecord.get(USERS.UUID)), Arrays.toString(userRecord.get(USERS.OBJECTNAME)), userRecord.get(USERS.ISADMIN));
            con.close();
            return user;
        }
        catch(SQLException e){
            System.err.println("Error while establishing connection to database:\n" + e.getMessage());
            return null;
        }
        catch(DataAccessException e) {
            System.err.println("Error while writing data to the database:\n" + e.getMessage());
            return null;
        }
    }

    @Override
    public Iterable<User> fetchAllUsers() {
        try(Connection con = DriverManager.getConnection(url, dbUser, dbPassword)){
            DSLContext ctx = DSL.using(con, SQLDialect.POSTGRES);

            List<User> userRecord = ctx.selectFrom(USERS).fetchInto(User.class);

            con.close();
            return userRecord;
        }
        catch(java.sql.SQLException e){
            System.err.println("Error while establishing connection to database:\n" + e.getMessage());
            return null;
        }
        catch(DataAccessException e) {
            System.err.println("Error while writing data to the database:\n" + e.getMessage());
            return null;
        }
    }

    @Override
    public Iterable<User> fetchAllAdmins() {
        try(Connection con = DriverManager.getConnection(url, dbUser, dbPassword)){
            DSLContext ctx = DSL.using(con, SQLDialect.POSTGRES);

            List<User> userRecord = ctx.selectFrom(USERS).where(USERS.ISADMIN).fetchInto(User.class);

            con.close();
            return null;
        }
        catch(java.sql.SQLException e){
            System.err.println("Error while establishing connection to database:\n" + e.getMessage());
            return null;
        }
        catch(DataAccessException e) {
            System.err.println("Error while writing data to the database:\n" + e.getMessage());
            return null;
        }
    }

    @Override
    public Iterable<User> fetchUsersWithAccessToFile(String[] fileUUID) {
        try(Connection con = DriverManager.getConnection(url, dbUser, dbPassword)){
            DSLContext ctx = DSL.using(con, SQLDialect.POSTGRES);

            List<User> userRecord = ctx.selectFrom(USERS).where(FILEPERMISSIONS.FILE.eq(fileUUID).and(FILEPERMISSIONS.USER.eq(USERS.UUID))).fetchInto(User.class);

            con.close();
            return userRecord;
        }
        catch(java.sql.SQLException e){
            System.err.println("Error while establishing connection to database:\n" + e.getMessage());
            return null;
        }
        catch(DataAccessException e) {
            System.err.println("Error while writing data to the database:\n" + e.getMessage());
            return null;
        }
    }
}
