package com.chelchowskidawidjan;

import static com.chelchowskidawidjan.generated.Tables.*;

import com.chelchowskidawidjan.generated.enums.Permissions;
import com.chelchowskidawidjan.generated.tables.Filepermissions;
import com.chelchowskidawidjan.generated.tables.records.FilecommentsRecord;
import com.chelchowskidawidjan.generated.tables.records.FilepermissionsRecord;
import com.chelchowskidawidjan.generated.tables.records.FilesRecord;
import com.chelchowskidawidjan.generated.tables.records.UsersRecord;
import com.chelchowskidawidjan.generated.enums.Filetype;
import org.jooq.*;
import org.jooq.exception.DataAccessException;
import org.jooq.impl.DSL;

import java.sql.*;
import java.sql.DriverManager;
import java.util.Optional;
import java.time.LocalDateTime;
import java.util.List;

public class DatabaseUserRepository implements UserRepository {

    private final String url = "jdbc:postgresql://localhost:5432/postgres";
    private final String dbUser = "postgres";
    private final String dbPassword = "postgres";

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
    public boolean persistUserUpdate(String[] objectName, String[] UUID, String[] passwordHash, String[] passwordSalt) {
        return false;
    }

    @Override
    public Optional<User> fetchUserByUUID(String UUID) {
        return Optional.empty();
    }

    @Override
    public Iterable<User> fetchAllUsers() {
        return null;
    }

    @Override
    public Iterable<User> fetchAllAdmins() {
        return null;
    }

    @Override
    public Iterable<User> fetchUsersWithAccessToFile(String fileUUID) {
        return null;
    }
}
