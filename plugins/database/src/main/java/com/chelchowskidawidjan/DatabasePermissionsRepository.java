package com.chelchowskidawidjan;

import static com.chelchowskidawidjan.generated.Tables.*;

import java.sql.*;
import java.util.List;
import java.util.UUID;

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
    public boolean persistUserFilePermissions(UUID userUUID, UUID fileUUID, com.chelchowskidawidjan.Permissions permissions) {

        String[] userUUIDArray = { userUUID.toString() };
        String[] fileUUIDArray = { fileUUID.toString() };

        try(Connection con = DriverManager.getConnection(url, dbUser, dbPassword)){
            DSLContext ctx = DSL.using(con, SQLDialect.POSTGRES);

            Permissions perms = Permissions.valueOf(permissions.name());

            InsertValuesStep3<FilepermissionsRecord, String[], String[], Permissions> step =
                    ctx.insertInto(FILEPERMISSIONS, FILEPERMISSIONS.USER, FILEPERMISSIONS.FILE, FILEPERMISSIONS.PERMISSION)
                            .values(userUUIDArray, fileUUIDArray, perms);
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
    public com.chelchowskidawidjan.Permissions fetchUserFilePermissions(UUID userUUID, UUID fileUUID) {

        String[] userUUIDArray = { userUUID.toString() };
        String[] fileUUIDArray = { fileUUID.toString() };

        try(Connection con = DriverManager.getConnection(url, dbUser, dbPassword)) {
            DSLContext ctx = DSL.using(con, SQLDialect.POSTGRES);
            Permissions perm = ctx.select(FILEPERMISSIONS.PERMISSION).from(FILEPERMISSIONS).where(FILEPERMISSIONS.USER.eq(userUUIDArray)).and(FILEPERMISSIONS.FILE.eq(fileUUIDArray)).fetchOne().getValue(FILEPERMISSIONS.PERMISSION, Permissions.class);
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
}
