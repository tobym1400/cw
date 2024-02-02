package jaxrs;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.io.*;
import java.sql.*;
import java.util.*;

@Path("/read")
public class ReadDB {
    /**
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

    public String list(Connection database)
            throws SQLException {
        ArrayList<String> outputArray = new ArrayList<>();
        Statement statement = database.createStatement();
        ResultSet results = statement.executeQuery(
                "SELECT * FROM details");
        while (results.next()) {
            String forename = results.getString("forename");
            String surname = results.getString("surname");
            long booksSold = results.getLong("booksSold");
            String nationality = results.getString("nationality");
            int authorId = results.getInt("author_id");
            outputArray.add(forename + " " + surname + " " + booksSold + " " + nationality + " " + authorId);
        }
        statement.close();
        String output = String.join("\n",outputArray);
        return output;
    }

    @GET
    @Path("/")
    @Produces(MediaType.TEXT_PLAIN)
    public String main() {
        Connection connection = null;

        try {
            connection = getConnection();
            return(list(connection));
        } catch (Exception error) {
            error.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (Exception error) {
                }
            }
        }
        return "Fail";
    }
}
