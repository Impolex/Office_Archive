package com.chelchowskidawidjan;

import static org.jooq.impl.DSL.*;
import static com.chelchowskidawidjan.generated.Tables.*;

import java.sql.*;

import com.chelchowskidawidjan.generated.tables.records.UsersRecord;
import org.jooq.DSLContext;
import org.jooq.InsertValuesStep5;
import org.jooq.SQLDialect;
import org.jooq.exception.DataAccessException;
import org.jooq.impl.DSL;

public class databaseConnection {
    private String url = "jdbc:postgresql://localhost:5432/postgres";
    private String dbUser = "postgres";
    private String dbPassword = "postgres";
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
}
