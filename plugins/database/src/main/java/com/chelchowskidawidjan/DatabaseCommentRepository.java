package com.chelchowskidawidjan;

import java.util.Optional;
import static com.chelchowskidawidjan.generated.Tables.*;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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

public class DatabaseCommentRepository implements CommentRepository {
    private final String url = "jdbc:postgresql://localhost:5432/postgres";
    private final String dbUser = "postgres";
    private final String dbPassword = "postgres";
    private CommentAdapter adapter;

    @Override
    public boolean persistCommentUpload(Comment comment) {

        JooqCommentObject pluginComment = adapter.domainToPlugin(comment, JooqCommentObject.class);

        try(Connection con = DriverManager.getConnection(url, dbUser, dbPassword)){
            DSLContext ctx = DSL.using(con, SQLDialect.POSTGRES);
            InsertValuesStep4<FilecommentsRecord, String[], String[], String[], String[]> step =
                    ctx.insertInto(FILECOMMENTS, FILECOMMENTS.UUID, FILECOMMENTS.AUTHOR, FILECOMMENTS.FILE, FILECOMMENTS.CONTENT)
                            .values(pluginComment.getCommentUUIDArray(), pluginComment.getAuthorUUIDArray(), pluginComment.getFileUUIDArray(), pluginComment.getContentArray());
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
    public boolean persistCommentRemoval(UUID commentUUID) {

        String[] uuidArray = {commentUUID.toString()};

        try(Connection con = DriverManager.getConnection(url, dbUser, dbPassword)){
            DSLContext ctx = DSL.using(con, SQLDialect.POSTGRES);

            ctx.deleteFrom(FILECOMMENTS)
                    .where(FILECOMMENTS.UUID.eq(uuidArray))
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
}
