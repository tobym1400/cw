/**
 * Created by el20jr on 21/11/22.
 */

import javax.ws.rs.core.MediaType;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

public class Top3RESTClient {
    static final String REST_URI = "http://localhost:9999/service2";
    static final String RETURNTOP3_PATH = "top3/returntop3";
    static final String CREATE_PATH = "create";
    static final String READ_PATH = "top3/read";

    public static void main(String[] args) {

        //  Enter authorID to test
        String authorID = "4";

        //  Configure webservice
        ClientConfig config = new DefaultClientConfig();
        Client client = Client.create(config);
        WebResource service = client.resource(REST_URI);

        //  Create database and print response
        WebResource createService = service.path(CREATE_PATH);
        System.out.println("Sub Response: " + getResponse(createService));
        System.out.println("Add Output as Text: " + getOutputAsText(createService));
        System.out.println("---------------------------------------------------");

        //  Read database and print response
        WebResource readService = service.path(READ_PATH);
        System.out.println("Sub Response: " + getResponse(readService));
        System.out.println("Add Output as Text: \n" + getOutputAsText(readService));
        System.out.println("---------------------------------------------------");

        // Read top 3 books for given authorID and print response
        WebResource booksService = service.path(RETURNTOP3_PATH).path(authorID);
        System.out.println("Sub Response: " + getResponse(booksService));
        System.out.println("Add Output as Text: " + getOutputAsText(booksService));
        System.out.println("---------------------------------------------------");

    }

    private static String getResponse(WebResource service) {
        return service.accept(MediaType.TEXT_XML).get(ClientResponse.class).toString();
    }

    private static String getOutputAsText(WebResource service) {
        return service.accept(MediaType.TEXT_PLAIN).get(String.class);
    }
}
