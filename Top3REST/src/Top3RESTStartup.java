/**
 * Created by el20jr on 21/11/22.
 * Create database: http://localhost:9999/service2/createDB
 * Get top 3 books using authorID: http://localhost:9999/service2/top3/returntop3/{authorID}
 * Google API key: AIzaSyB-UeHhpvmEABLv1_ItIzWao58ngm91FiA
 */

import java.io.IOException;
import java.sql.*;
import com.sun.jersey.api.container.httpserver.HttpServerFactory;
import com.sun.net.httpserver.HttpServer;

public class Top3RESTStartup {

    static final String BASE_URI = "http://localhost:9999/service2/";

    public static Connection connection;

    public static void main(String[] args) {
        try {
            HttpServer server = HttpServerFactory.create(BASE_URI);
            server.start();

            connection = null;

            try {
                connection = QueryDB.getConnection();
                System.out.println("Press Enter to stop the server. ");
                System.in.read();
            }
            catch (Exception error) {
                error.printStackTrace();
            }
            finally {
                if (connection != null) {
                    try {
                        connection.close();
                    } catch (Exception error) {
                    }
                }
            }
            server.stop(0);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
