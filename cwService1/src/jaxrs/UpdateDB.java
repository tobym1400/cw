package jaxrs;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Properties;

@Path("/update")
public class UpdateDB {

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
    public String updateById(@QueryParam("index") String indexStr, @QueryParam("sName") String surname,
                             @QueryParam("fName") String forename, @QueryParam("books") String booksSoldStr,
                             @QueryParam("nation") String nationality, @QueryParam("fk") String foreignKeyStr) {
        try (Connection connection = getConnection()) {
            indexStr = indexStr.substring(1,indexStr.length()-1);
            int index = Integer.parseInt(indexStr);
            booksSoldStr = booksSoldStr.substring(1,booksSoldStr.length()-1);
            long booksSold = Long.parseLong(booksSoldStr);
            foreignKeyStr = foreignKeyStr.substring(1,foreignKeyStr.length()-1);
            int foreignKey = Integer.parseInt(foreignKeyStr);
            System.out.println("surname = " + surname + ", forename = " + forename +
                    ", booksSold = " + booksSold + ", nationality= " + nationality +
                            ", author_id = " + foreignKey + " WHERE details_id = " + index);
            Statement statement = connection.createStatement();
            statement.executeUpdate(
                    "UPDATE details SET surname = " + surname + ", forename = " + forename +
                            ", booksSold = " + booksSold + ", nationality= " + nationality +
                            ", author_id = " + foreignKey + " WHERE details_id = " + index);

            statement.close();
            return "Success";
        } catch (SQLException | IOException s) {
            s.printStackTrace();
            return "Fail";
        }


    }
}
