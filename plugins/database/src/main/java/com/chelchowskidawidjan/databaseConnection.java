package com.chelchowskidawidjan;

import static org.jooq.impl.DSL.*;
import static com.chelchowskidawidjan.generated.Tables.*;

import java.sql.*;
import java.time.LocalDateTime;

import com.chelchowskidawidjan.generated.tables.records.FilesRecord;
import com.chelchowskidawidjan.generated.tables.records.UsersRecord;
import com.chelchowskidawidjan.generated.enums.Filetype;
import org.jooq.DSLContext;
import org.jooq.InsertValuesStep5;
import org.jooq.InsertValuesStep6;
import org.jooq.SQLDialect;
import org.jooq.exception.DataAccessException;
import org.jooq.impl.DSL;

public class databaseConnection {
    private final String url = "jdbc:postgresql://localhost:5432/postgres";
    private final String dbUser = "postgres";
    private final String dbPassword = "postgres";
    private User invokingUser = null;

    public databaseConnection() {
    }

    public databaseConnection(User invokingUser) {
        this.invokingUser = invokingUser;
    }

    private boolean persistUserCreation(String[] objectName, String[] UUID, String[] passwordHash, String[] passwordSalt) {
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

    public boolean persistFileUpload(String[] fileUUID, String[] objectName, Filetype fileType, String[] uploaderUUID, LocalDateTime creationDate) {
        try(Connection con = DriverManager.getConnection(url, dbUser, dbPassword)){
            DSLContext ctx = DSL.using(con, SQLDialect.POSTGRES);
            InsertValuesStep6<FilesRecord, String[], String[], String[], LocalDateTime, LocalDateTime, Filetype> step =
                    ctx.insertInto(FILES, FILES.UUID, FILES.OBJECTNAME, FILES.UPLOADER, FILES.CREATIONDATE, FILES.MODIFICATIONDATE, FILES.FILETYPE)
                            .values(fileUUID, objectName, uploaderUUID, creationDate, creationDate, fileType);
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
}
