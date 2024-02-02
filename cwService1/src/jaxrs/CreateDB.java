package jaxrs;

import javax.ws.rs.*;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import java.io.*;
import java.sql.*;
import java.util.*;

@Path("/create")
public class CreateDB {

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

    public static void createTable(Connection database) throws SQLException
    {
        Statement statement = database.createStatement();
        try {
            statement.executeUpdate("DROP TABLE details");
        }
        catch (SQLException error) {
        }

        statement.executeUpdate("CREATE TABLE details ("
                + "details_id int NOT NULL PRIMARY KEY,"
                + "surname VARCHAR(30) NOT NULL,"
                + "forename VARCHAR(20) NOT NULL,"
                + "booksSold long NOT NULL,"
                + "nationality VARCHAR(20) NOT NULL, "
                + "author_id int NOT NULL)");

        statement.close();
    }

    public static void addData(BufferedReader in, Connection database)
            throws IOException, SQLException
    {
        PreparedStatement statement = database.prepareStatement("INSERT INTO details VALUES(?,?,?,?,?,?)");

        while (true) {

            String line = in.readLine();
            if (line == null)
                break;
            StringTokenizer parser = new StringTokenizer(line, ",");
            String detailsId = parser.nextToken();
            String surname = parser.nextToken();
            String forename = parser.nextToken();
            String booksSoldStr = parser.nextToken();
            String nationality = parser.nextToken();
            String authorId = parser.nextToken();
            int index = Integer.parseInt(detailsId);
            long booksSold = Long.parseLong(booksSoldStr);
            int authorIndex = Integer.parseInt(authorId);

            statement.setInt(1, index);
            statement.setString(2, surname);
            statement.setString(3, forename);
            statement.setLong(4,booksSold);
            statement.setString(5, nationality);
            statement.setInt(6,authorIndex);
            statement.executeUpdate();
        }

        statement.close();
        in.close();
    }

    @GET
    @Path("/")
    @Produces(MediaType.TEXT_PLAIN)
    public String main() {
        Connection database = null;

        try {
            database = getConnection();
            createTable(database);
            BufferedReader input = new BufferedReader(new FileReader("/home/csunix/sc20tpm/IdeaProjects/cwService1/src/jaxrs/details.txt"));
            addData(input,database);
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


