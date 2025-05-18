package com.chelchowskidawidjan;

import static com.chelchowskidawidjan.generated.Tables.*;

import com.chelchowskidawidjan.generated.tables.records.UsersRecord;
import org.jooq.*;
import org.jooq.exception.DataAccessException;
import org.jooq.impl.DSL;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.sql.DriverManager;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class DatabaseUserRepository implements UserRepository {

    private final String url = "jdbc:postgresql://localhost:5432/postgres";
    private final String dbUser = "postgres";
    private final String dbPassword = "postgres";
    private UserAdapter adapter;

    public DatabaseUserRepository() {
    }

    @Override
    public boolean login(String username, String password) {
        try(Connection con = DriverManager.getConnection(url, dbUser, dbPassword)){
            DSLContext ctx = DSL.using(con, SQLDialect.POSTGRES);

            String[] tempUsername = {username};

            Record2<String[], String[]> result = ctx
                    .select(USERS.PASSWORDHASH, USERS.PASSWORDSALT)
                    .from(USERS)
                    .where(USERS.OBJECTNAME.eq(tempUsername))
                    .fetchOne();

            if(result == null){
                return false;
            }

            String[] storedHash = result.get(USERS.PASSWORDHASH);
            byte[] storedSalt = Arrays.toString(result.get(USERS.PASSWORDSALT)).getBytes();

            MessageDigest digest = MessageDigest.getInstance("SHA-512");
            digest.update(storedSalt);
            String[] computedHash = new String[]{Arrays.toString(digest.digest(password.getBytes(StandardCharsets.UTF_8)))};

            con.close();
            return Arrays.equals(storedHash, computedHash);
        }
        catch(java.sql.SQLException e){
            System.err.println("Error while establishing connection to database:\n" + e.getMessage());
            return false;
        }
        catch(DataAccessException e) {
            System.err.println("Error while writing data to the database:\n" + e.getMessage());
            return false;
        }
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean persistUserCreation(User user, String passwordHash, String salt) {

        JooqUserObject pluginUser = adapter.domainToPlugin(user, JooqUserObject.class);
        String[] passwordHashArray = {passwordHash};
        String[] saltArray = {salt};

        try(Connection con = DriverManager.getConnection(url, dbUser, dbPassword)){
            DSLContext ctx = DSL.using(con, SQLDialect.POSTGRES);
            InsertValuesStep5<UsersRecord, String[], String[], String[], String[], Boolean> step =
                    ctx.insertInto(USERS, USERS.UUID, USERS.OBJECTNAME, USERS.PASSWORDHASH, USERS.PASSWORDSALT, USERS.ISADMIN)
                            .values(pluginUser.getUUIDArray(), pluginUser.getObjectNameArray(), passwordHashArray, saltArray, false);
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
    public boolean persistUserDeletion(UUID UUID) {

        String[] uuidArray = {UUID.toString()};

        try(Connection con = DriverManager.getConnection(url, dbUser, dbPassword)){
            DSLContext ctx = DSL.using(con, SQLDialect.POSTGRES);
            ctx.deleteFrom(USERS)
                    .where(USERS.UUID.eq(uuidArray))
                    .execute();

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
    public boolean persistUserUpdate(User user) {

        JooqUserObject pluginUser = adapter.domainToPlugin(user, JooqUserObject.class);

        try(Connection con = DriverManager.getConnection(url, dbUser, dbPassword)){
            DSLContext ctx = DSL.using(con, SQLDialect.POSTGRES);

            ctx.update(USERS)
                    .set(USERS.OBJECTNAME, pluginUser.getObjectNameArray())
                    .set(USERS.ISADMIN, pluginUser.isAdmin())
                    .where(USERS.UUID.eq(pluginUser.getUUIDArray()))
                    .execute();

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
    public boolean persistUserPasswordUpdate(UUID UUID, String passwordHash, String salt){
        String[] passwordHashArray = {passwordHash};
        String[] saltArray = {salt};
        String[] uuidArray = {UUID.toString()};

        try(Connection con = DriverManager.getConnection(url, dbUser, dbPassword)){
            DSLContext ctx = DSL.using(con, SQLDialect.POSTGRES);

            ctx.update(USERS)
                    .set(USERS.PASSWORDHASH, passwordHashArray)
                    .set(USERS.PASSWORDSALT, saltArray)
                    .where(USERS.UUID.eq(uuidArray))
                    .execute();

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
    public User fetchUserByUUID(UUID UUID) {
        String[] uuidArray = {UUID.toString()};

        try(Connection con = DriverManager.getConnection(url, dbUser, dbPassword)){
            DSLContext ctx = DSL.using(con, SQLDialect.POSTGRES);

            UsersRecord userRecord = ctx.selectFrom(USERS)
                    .where(USERS.UUID.eq(uuidArray))
                    .fetchOne();

            JooqUserObject user = new JooqUserObject(Arrays.toString(userRecord.get(USERS.UUID)), Arrays.toString(userRecord.get(USERS.OBJECTNAME)), userRecord.get(USERS.ISADMIN));
            con.close();
            return adapter.pluginToDomain(user);
        }
        catch(SQLException e){
            System.err.println("Error while establishing connection to database:\n" + e.getMessage());
            return null;
        }
        catch(DataAccessException e) {
            System.err.println("Error while writing data to the database:\n" + e.getMessage());
            return null;
        }
        catch(NullPointerException e){
            System.err.println("User not found\n" + e.getMessage());
            return null;
        }
    }

    @Override
    public List<User> fetchAllUsers() {
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
    public List<User> fetchAllAdmins() {
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
}
