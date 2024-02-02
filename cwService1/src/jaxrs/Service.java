package jaxrs;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Properties;

@Path("/main")
public class Service {

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

    @GET
    @Path("/")
    @Produces(MediaType.TEXT_PLAIN)
    public String searchBySurname(@QueryParam("sName") String sName) {
        try (Connection connection = getConnection()) {
            ArrayList<String> outputArray = new ArrayList<>();
            Statement statement = connection.createStatement();
            ResultSet results = statement.executeQuery(
                    "SELECT * FROM details WHERE surname = " + sName);
            while (results.next()) {
                String forename = results.getString("forename");
                String surname = results.getString("surname");
                long booksSold = results.getLong("booksSold");
                String nationality = results.getString("nationality");
                int authorId = results.getInt("author_id");
                outputArray.add("Name: " + forename + " " + surname + ", Books Sold: " +
                        booksSold + ", Nationality: " + nationality + ", Foreign Key: " + authorId);
            }
            statement.close();
            String output = String.join("\n", outputArray);
            return output;
        } catch (SQLException | IOException s) {
            return "Fail";
        }


    }
}
