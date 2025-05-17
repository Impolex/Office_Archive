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

    @Override
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

    @Override
    public boolean persistCommentRemoval(String[] commentUUID) {
        try(Connection con = DriverManager.getConnection(url, dbUser, dbPassword)){
            DSLContext ctx = DSL.using(con, SQLDialect.POSTGRES);

            ctx.deleteFrom(FILECOMMENTS).where(FILECOMMENTS.UUID.eq(commentUUID)).execute();

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
    public Comment getCommentByUUID(String[] commentUUID) {
        try(Connection con = DriverManager.getConnection(url, dbUser, dbPassword)) {
            DSLContext ctx = DSL.using(con, SQLDialect.POSTGRES);

            Comment jooqComment = ctx.selectFrom(FILECOMMENTS).where(FILECOMMENTS.UUID.eq(commentUUID)).fetchOne().into(Comment.class);

            return new Comment(jooqComment.author, jooqComment.comment, jooqComment.creationDate, jooqComment.file);
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
            return null;
        }
    }

    @Override
    public Iterable<Comment> getCommentsOfFile(String[] fileUUID) {
        try(Connection con = DriverManager.getConnection(url, dbUser, dbPassword)){
            DSLContext ctx = DSL.using(con, SQLDialect.POSTGRES);

            List<Comment> comments = ctx.selectFrom(FILECOMMENTS).where(FILECOMMENTS.FILE.eq(fileUUID)).fetchInto(Comment.class);

            con.close();
            return comments;
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
    public Iterable<Comment> getCommentsOfUser(String[] userUUID) {
        try(Connection con = DriverManager.getConnection(url, dbUser, dbPassword)){
            DSLContext ctx = DSL.using(con, SQLDialect.POSTGRES);

            List<Comment> comments = ctx.selectFrom(FILECOMMENTS).where(FILECOMMENTS.AUTHOR.eq(userUUID)).fetchInto(Comment.class);

            con.close();
            return comments;
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
