package com.chelchowskidawidjan;

import static com.chelchowskidawidjan.generated.Tables.*;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

public class DatabaseFileRepository implements FileRepository {
    private final String url = "jdbc:postgresql://localhost:5432/postgres";
    private final String dbUser = "postgres";
    private final String dbPassword = "postgres";

    @Override
    public boolean persistFileUpload(String[] fileUUID, String[] objectName, FileType fileType, String[] uploaderUUID, LocalDateTime creationDate, byte[] content) {
        try(Connection con = DriverManager.getConnection(url, dbUser, dbPassword)){
            DSLContext ctx = DSL.using(con, SQLDialect.POSTGRES);

            Filetype jooqFileType = Filetype.valueOf(fileType.name());

            InsertValuesStep7<FilesRecord, String[], String[], String[], LocalDateTime, LocalDateTime, Filetype, byte[]> step =
                    ctx.insertInto(FILES, FILES.UUID, FILES.OBJECTNAME, FILES.UPLOADER, FILES.CREATIONDATE, FILES.MODIFICATIONDATE, FILES.FILETYPE, FILES.CONTENT)
                            .values(fileUUID, objectName, uploaderUUID, creationDate, creationDate, jooqFileType, content);
            step.execute();
            InsertValuesStep3<FilepermissionsRecord, String[], String[], com.chelchowskidawidjan.generated.enums.Permissions> step2 =
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

    @Override
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

    @Override
    public boolean persistFileDeletion(String[] UUID) {
        try(Connection con = DriverManager.getConnection(url, dbUser, dbPassword)){
            DSLContext ctx = DSL.using(con, SQLDialect.POSTGRES);
            ctx.deleteFrom(FILEPERMISSIONS).where(FILEPERMISSIONS.FILE.eq(UUID)).execute();
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

    @Override
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
            System.err.println("Error while fetching data from the database:\n" + e.getMessage());
            return null;
        }
    }

    @Override
    public List<String[]> fetchFilesforUser(String[] userUUID) {
        try(Connection con = DriverManager.getConnection(url, dbUser, dbPassword)) {
            DSLContext ctx = DSL.using(con, SQLDialect.POSTGRES);
            List<String[]> files = ctx.select(FILEPERMISSIONS.FILE).from(FILEPERMISSIONS).where(FILEPERMISSIONS.USER.eq(userUUID)).fetch().getValues(FILEPERMISSIONS.FILE, String[].class);
            return files;
        }
        catch(java.sql.SQLException e){
            System.err.println("Error while establishing connection to database:\n" + e.getMessage());
            return null;
        }
        catch(DataAccessException e) {
            System.err.println("Error while fetching data from the database:\n" + e.getMessage());
            return null;
        }
    }

    @Override
    public Optional<File> fetchFile(String[] UUID) {
        try(Connection con = DriverManager.getConnection(url, dbUser, dbPassword)) {
            DSLContext ctx = DSL.using(con, SQLDialect.POSTGRES);
            FilesRecord file = ctx.selectFrom(FILES).where(FILES.UUID.eq(UUID)).fetchOne();
            return file;
        }
        catch(java.sql.SQLException e){
            System.err.println("Error while establishing connection to database:\n" + e.getMessage());
            return null;
        }
        catch(DataAccessException e) {
            System.err.println("Error while fetching data from the database:\n" + e.getMessage());
            return null;
        }
    }
}
