package com.chelchowskidawidjan;

import static com.chelchowskidawidjan.generated.Tables.*;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

public class databaseConnection {
    private final String url = "jdbc:postgresql://localhost:5432/postgres";
    private final String dbUser = "postgres";
    private final String dbPassword = "postgres";

    public databaseConnection() {
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

    private boolean persistUserDeletion(String[] UUID) {
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

    public boolean persistFileUpload(String[] fileUUID, String[] objectName, Filetype fileType, String[] uploaderUUID, LocalDateTime creationDate, byte[] content) {
        try(Connection con = DriverManager.getConnection(url, dbUser, dbPassword)){
            DSLContext ctx = DSL.using(con, SQLDialect.POSTGRES);
            InsertValuesStep7<FilesRecord, String[], String[], String[], LocalDateTime, LocalDateTime, Filetype, byte[]> step =
                    ctx.insertInto(FILES, FILES.UUID, FILES.OBJECTNAME, FILES.UPLOADER, FILES.CREATIONDATE, FILES.MODIFICATIONDATE, FILES.FILETYPE, FILES.CONTENT)
                            .values(fileUUID, objectName, uploaderUUID, creationDate, creationDate, fileType, content);
            step.execute();
            InsertValuesStep3<FilepermissionsRecord, String[], String[], Permissions> step2 =
                    ctx.insertInto(FILEPERMISSIONS, FILEPERMISSIONS.FILE, FILEPERMISSIONS.USER, FILEPERMISSIONS.PERMISSION)
                                    .values(fileUUID, uploaderUUID, Permissions.WRITE);
            step2.execute();
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

    public boolean persistFileEdit(String[] fileUUID, LocalDateTime editDate, byte[] content) {
        try(Connection con = DriverManager.getConnection(url, dbUser, dbPassword)){
            DSLContext ctx = DSL.using(con, SQLDialect.POSTGRES);
            ctx.update(FILES).set(FILES.CONTENT, content).set(FILES.MODIFICATIONDATE, editDate).where(FILES.UUID.eq(fileUUID)).execute();
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

    private boolean persistFileDeletion(String[] UUID) {
        try(Connection con = DriverManager.getConnection(url, dbUser, dbPassword)){
            DSLContext ctx = DSL.using(con, SQLDialect.POSTGRES);
            ctx.deleteFrom(FILES).where(FILES.UUID.eq(UUID)).execute();
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

    public boolean persistCommentUpload(String[] commentUUID, String[] userUUID, String[] fileUUID, String[] content) {
        try(Connection con = DriverManager.getConnection(url, dbUser, dbPassword)){
            DSLContext ctx = DSL.using(con, SQLDialect.POSTGRES);
            InsertValuesStep4<FilecommentsRecord, String[], String[], String[], String[]> step =
                    ctx.insertInto(FILECOMMENTS, FILECOMMENTS.UUID, FILECOMMENTS.AUTHOR, FILECOMMENTS.FILE, FILECOMMENTS.CONTENT)
                            .values(commentUUID, userUUID, fileUUID, content);
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

    public boolean persistUserFilePermissions(String[] userUUID, String[] fileUUID, Permissions permissions) {
        try(Connection con = DriverManager.getConnection(url, dbUser, dbPassword)){
            DSLContext ctx = DSL.using(con, SQLDialect.POSTGRES);
            InsertValuesStep3<FilepermissionsRecord, String[], String[], Permissions> step =
                    ctx.insertInto(FILEPERMISSIONS, FILEPERMISSIONS.USER, FILEPERMISSIONS.FILE, FILEPERMISSIONS.PERMISSION)
                            .values(userUUID, fileUUID, permissions);
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

    public byte[] fetchFileContent(String[] UUID) {
        try(Connection con = DriverManager.getConnection(url, dbUser, dbPassword)) {
            DSLContext ctx = DSL.using(con, SQLDialect.POSTGRES);
            FilesRecord file = ctx.selectFrom(FILES).where(FILES.UUID.eq(UUID)).fetchSingle();
            if(file != null) {
                return file.getContent();
            }
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

    public List<String[]> fetchFilesforUser(String[] userUUID) {
        try(Connection con = DriverManager.getConnection(url, dbUser, dbPassword)) {
            DSLContext ctx = DSL.using(con, SQLDialect.POSTGRES);
            List<FilepermissionsRecord> files = ctx.select(FILEPERMISSIONS.FILE).from(FILEPERMISSIONS).where(FILEPERMISSIONS.USER.eq(userUUID)).fetchMany();

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

    //TODO: fetch files for user
    //TODO: fetch file metadata
    //TODO: update deleteFiles for filepermissions table

}
