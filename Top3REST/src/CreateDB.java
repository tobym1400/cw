import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.awt.*;
import java.io.*;
import java.sql.*;
import java.util.*;

/**
 * Created by el20jr on 21/11/22.
 */

@Path("/createDB")

public class CreateDB {


  public static final String propsFile = "jdbc.properties";

  public static Connection getConnection() throws IOException, SQLException
  {
    // Load properties

    FileInputStream in = new FileInputStream(propsFile);
    Properties props = new Properties();
    props.load(in);

    // Define JDBC driver

    String drivers = props.getProperty("jdbc.drivers");
    if (drivers != null)
      System.setProperty("jdbc.drivers", drivers);
      // Setting standard system property jdbc.drivers
      // is an alternative to loading the driver manually
      // by calling Class.forName()

    // Obtain access parameters and use them to create connection

    String url = props.getProperty("jdbc.url");
    String user = props.getProperty("jdbc.user");
    String password = props.getProperty("jdbc.password");

    return DriverManager.getConnection(url, user, password);
  }

  public static void createTable(Connection database) throws SQLException
  {
    // Create a Statement object with which we can execute SQL commands

    Statement statement = database.createStatement();

    // Drop existing table, if present

    try {
      statement.executeUpdate("DROP TABLE books");
    }
    catch (SQLException error) {
      // Catch and ignore SQLException, as this merely indicates
      // that the table didn't exist in the first place!
    }

    // Create a fresh table

    statement.executeUpdate("CREATE TABLE books ("
                          + "authorID CHAR(8) NOT NULL PRIMARY KEY,"
                          + "book1 VARCHAR(50) NOT NULL,"
                          + "book2 VARCHAR(50) NOT NULL,"
                          + "book3 VARCHAR(50) NOT NULL)");

    statement.close();
  }

  public static void addData(BufferedReader in, Connection database)
   throws IOException, SQLException
  {
    // Prepare statement used to insert data

    PreparedStatement statement =
     database.prepareStatement("INSERT INTO books VALUES(?,?,?,?)");

    // Loop over input data, inserting it into table...
 
    while (true) {

      // Obtain user ID, surname and forename from input file

      String line = in.readLine();
      if (line == null)
        break;
      StringTokenizer parser = new StringTokenizer(line,",");
      String authorID = parser.nextToken();
      String book1 = parser.nextToken();
      String book2 = parser.nextToken();
      String book3 = parser.nextToken();

      // Insert data into table

      statement.setString(1, authorID);
      statement.setString(2, book1);
      statement.setString(3, book2);
      statement.setString(4, book3);
      statement.executeUpdate();

    }

    statement.close();
    in.close();
  }

  @GET
  @Path("/")
  @Produces(MediaType.TEXT_PLAIN)
  public static String main()
  {

    Connection database = null;
 
    try {
      BufferedReader input = new BufferedReader(new FileReader("books.txt"));
      database = getConnection();
      createTable(database);
      addData(input, database);
    }
    catch (Exception error) {
      error.printStackTrace();
      return "ERROR";
    }
    finally {

      if (database != null) {
        try {
          database.close();
        }
        catch (Exception error) {return "ERROR";}
      }
    }
    return "SUCCESS";
  }


}
