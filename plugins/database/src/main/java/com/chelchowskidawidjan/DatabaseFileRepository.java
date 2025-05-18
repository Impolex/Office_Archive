package com.chelchowskidawidjan;

import static com.chelchowskidawidjan.generated.Tables.*;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.*;

import com.chelchowskidawidjan.generated.enums.Permissions;
import com.chelchowskidawidjan.generated.tables.records.FilepermissionsRecord;
import com.chelchowskidawidjan.generated.tables.records.FilesRecord;
import com.chelchowskidawidjan.generated.enums.Filetype;
import org.jooq.*;
import org.jooq.exception.DataAccessException;
import org.jooq.impl.DSL;

public class DatabaseFileRepository implements FileRepository {
    private final String url = "jdbc:postgresql://localhost:5432/postgres";
    private final String dbUser = "postgres";
    private final String dbPassword = "postgres";
    private FileAdapter adapter;

    private final DatabaseUserRepository databaseUserRepository = new DatabaseUserRepository();

    @Override
    public boolean persistFileUpload(File file) {

        JooqFileObject pluginFile = adapter.domainToPlugin(file, JooqFileObject.class);

        try(Connection con = DriverManager.getConnection(url, dbUser, dbPassword)){
            DSLContext ctx = DSL.using(con, SQLDialect.POSTGRES);

            Filetype jooqFileType = Filetype.valueOf(pluginFile.getType().toString());

            InsertValuesStep7<FilesRecord, String[], String[], String[], LocalDateTime, LocalDateTime, Filetype, byte[]> step =
                    ctx.insertInto(FILES, FILES.UUID, FILES.OBJECTNAME, FILES.UPLOADER, FILES.CREATIONDATE, FILES.MODIFICATIONDATE, FILES.FILETYPE, FILES.CONTENT)
                            .values(pluginFile.getUUIDArray(), pluginFile.getObjectNameArray(), pluginFile.getUploaderUUIDArray(), pluginFile.getCreationDate(), pluginFile.getModificationDate(), jooqFileType, pluginFile.getContent());
            step.execute();
            InsertValuesStep3<FilepermissionsRecord, String[], String[], com.chelchowskidawidjan.generated.enums.Permissions> step2 =
                    ctx.insertInto(FILEPERMISSIONS, FILEPERMISSIONS.FILE, FILEPERMISSIONS.USER, FILEPERMISSIONS.PERMISSION)
                            .values(pluginFile.getUUIDArray(), pluginFile.getUploaderUUIDArray(), Permissions.WRITE);
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
    public boolean persistFileEdit(File file) {

        JooqFileObject pluginFile = adapter.domainToPlugin(file, JooqFileObject.class);

        try(Connection con = DriverManager.getConnection(url, dbUser, dbPassword)){
            DSLContext ctx = DSL.using(con, SQLDialect.POSTGRES);
            ctx.update(FILES)
                    .set(FILES.CONTENT, pluginFile.getContent())
                    .set(FILES.MODIFICATIONDATE, pluginFile.getModificationDate())
                    .where(FILES.UUID.eq(pluginFile.getUUIDArray()))
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
    public boolean persistFileDeletion(UUID UUID) {

        String[] uuidArray = { UUID.toString() };

        try(Connection con = DriverManager.getConnection(url, dbUser, dbPassword)){
            DSLContext ctx = DSL.using(con, SQLDialect.POSTGRES);
            ctx.deleteFrom(FILEPERMISSIONS)
                    .where(FILEPERMISSIONS.FILE.eq(uuidArray))
                    .execute();
            ctx.deleteFrom(FILES)
                    .where(FILES.UUID.eq(uuidArray))
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
    public File fetchFileByUUID(UUID UUID) {

        String[] uuidArray = { UUID.toString() };

        try(Connection con = DriverManager.getConnection(url, dbUser, dbPassword)) {
            DSLContext ctx = DSL.using(con, SQLDialect.POSTGRES);
            FilesRecord file = ctx.selectFrom(FILES)
                    .where(FILES.UUID.eq(uuidArray))
                    .fetchOne();

            JooqFileObject pluginFile = new JooqFileObject(
                    Arrays.toString(file.getUuid()),
                    Arrays.toString(file.getObjectname()),
                    Arrays.toString(file.getUploader()),
                    file.getCreationdate(),
                    file.getModificationdate(),
                    FileType.valueOf(file.getFiletype().name()),
                    file.getContent());

            return adapter.pluginToDomain(pluginFile);
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
            System.err.println("File not found:\n" + e.getMessage());
            return null;
        }
    }
}
