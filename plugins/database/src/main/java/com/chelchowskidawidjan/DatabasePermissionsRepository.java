package com.chelchowskidawidjan;

import static com.chelchowskidawidjan.generated.Tables.*;

import java.sql.*;
import java.util.List;

import com.chelchowskidawidjan.generated.enums.Permissions;
import com.chelchowskidawidjan.generated.tables.records.FilepermissionsRecord;
import org.jooq.*;
import org.jooq.exception.DataAccessException;
import org.jooq.impl.DSL;

public class DatabasePermissionsRepository implements PermissionsRepository {
    private final String url = "jdbc:postgresql://localhost:5432/postgres";
    private final String dbUser = "postgres";
    private final String dbPassword = "postgres";

@Override
    public boolean persistUserFilePermissions(String[] userUUID, String[] fileUUID, com.chelchowskidawidjan.Permissions permissions) {
    try(Connection con = DriverManager.getConnection(url, dbUser, dbPassword)){
        DSLContext ctx = DSL.using(con, SQLDialect.POSTGRES);

        Permissions perms = Permissions.valueOf(permissions.name());

        InsertValuesStep3<FilepermissionsRecord, String[], String[], Permissions> step =
                ctx.insertInto(FILEPERMISSIONS, FILEPERMISSIONS.USER, FILEPERMISSIONS.FILE, FILEPERMISSIONS.PERMISSION)
                        .values(userUUID, fileUUID, perms);
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
    public boolean persistUserFilePermissionsUpdate(String[] userUUID, String[] fileUUID, com.chelchowskidawidjan.Permissions permissions) {
        return false;
    }

    @Override
    public com.chelchowskidawidjan.Permissions fetchUserFilePermissions(String[] userUUID, String[] fileUUID) {
        try(Connection con = DriverManager.getConnection(url, dbUser, dbPassword)) {
            DSLContext ctx = DSL.using(con, SQLDialect.POSTGRES);
            Permissions perm = ctx.select(FILEPERMISSIONS.PERMISSION).from(FILEPERMISSIONS).where(FILEPERMISSIONS.USER.eq(userUUID)).and(FILEPERMISSIONS.FILE.eq(fileUUID)).fetchOne().getValue(FILEPERMISSIONS.PERMISSION, Permissions.class);
            return com.chelchowskidawidjan.Permissions.valueOf(perm.name());
        }
        catch(java.sql.SQLException e){
            System.err.println("Error while establishing connection to database:\n" + e.getMessage());
            return null;
        }
        catch(DataAccessException e) {
            System.err.println("Error while fetching data from the database:\n" + e.getMessage());
            return null;
        }
        catch(NullPointerException e){
            return com.chelchowskidawidjan.Permissions.NOPERMISSION;
        }
    }

    @Override
    public Iterable<com.chelchowskidawidjan.Permissions> fetchAllUserPermissionsForFile(String[] userUUID, String[] fileUUID) {
        try(Connection con = DriverManager.getConnection(url, dbUser, dbPassword)){
            DSLContext ctx = DSL.using(con, SQLDialect.POSTGRES);

            List<com.chelchowskidawidjan.Permissions> users = ctx.selectFrom(FILEPERMISSIONS).where(FILEPERMISSIONS.FILE.eq(fileUUID).and(FILEPERMISSIONS.USER.eq(USERS.UUID))).fetchInto(com.chelchowskidawidjan.Permissions.class);

            con.close();
            return users;
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
