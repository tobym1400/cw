package jaxrs;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;
import java.util.StringTokenizer;

@Path("/delete")
public class DeleteDB {

    /**
     *
     * @author Karim Djemame and Nick Efford
     * @version 2.2 [2022-09-30]
     */

    public static final String propsFile = "/home/csunix/sc20tpm/IdeaProjects/cwService1/src/jdbc.properties";

    public static Connection getConnection() throws IOException, SQLException {
        FileInputStream in = new FileInputStream(propsFile);
        Properties props = new Properties();
        props.load(in);

        String drivers = props.getProperty("jdbc.drivers");
        if (drivers != null)
            System.setProperty("jdbc.drivers", drivers);

        String url = props.getProperty("jdbc.url");
        String user = props.getProperty("jdbc.user");
        String password = props.getProperty("jdbc.password");

        return DriverManager.getConnection(url, user, password);
    }

    public static void deleteTable(Connection database) throws SQLException
    {
        Statement statement = database.createStatement();
        statement.executeUpdate("DROP TABLE details");
        statement.close();
    }

    @GET
    @Path("/")
    @Produces(MediaType.TEXT_PLAIN)
    public String main() {
        Connection database = null;

        try {
            database = getConnection();
            deleteTable(database);
        } catch (Exception error) {
            error.printStackTrace();
            return "FAIL";
        } finally {
            if (database != null) {
                try {
                    database.close();

                } catch (Exception error) {
                    return "FAIL";
                }
            }
        }
        return "SUCCESS";


    }


}


