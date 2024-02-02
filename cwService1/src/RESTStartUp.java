import com.sun.jersey.api.container.httpserver.HttpServerFactory;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;

public class RESTStartUp {

    private static final String BASE_URI = "http://localhost:9994/service1/";

    public static void main(String[] args) {
        try {
            HttpServer server = HttpServerFactory.create(BASE_URI);
            server.start();
            System.out.println("Press Enter to stop the server. ");
            System.in.read();
            server.stop(0);
        } catch (IllegalArgumentException | IOException e) {
            e.printStackTrace();
        }
    }
}
/**
 * @author Karim Djemame
 * @version 1.0 [2022-10-22]
 */
