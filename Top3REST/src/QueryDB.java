import java.io.*;
import java.sql.*;
import java.util.*;

/**
 * Created by el20jr on 21/11/22.
 */

public class QueryDB {


    public static final String propsFile = "jdbc.properties";

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

    public static String returnTop3(String authorID, Connection database)
            throws SQLException {
        String output = "Err";
        Statement statement = database.createStatement();
        ResultSet results = statement.executeQuery(
                "SELECT * FROM books WHERE authorID = '" + authorID + "'");
        while (results.next()) {
            String book1 = results.getString("book1");
            String book2 = results.getString("book2");
            String book3 = results.getString("book3");
            output = book1 + ", " + book2 + ", " + book3 + "\n";
        }
        return output;
    }
}
