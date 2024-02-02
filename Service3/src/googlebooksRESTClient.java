/**
 * Created by el20jr on 22/11/22.
 */

import javax.ws.rs.core.MediaType;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

public class googlebooksRESTClient {
    static final String REST_URI = "http://localhost:9997/service3";

    public static void main(String[] args) {

        // Enter book name to test
        String bookName = "And Then There Were None";

        // Configure webservice
        ClientConfig config = new DefaultClientConfig();
        Client client = Client.create(config);
        WebResource service = client.resource(REST_URI);

        // Contact webservice and print response
        WebResource createService = service.path(bookName);
        System.out.println("Sub Response: " + getResponse(createService));
        System.out.println("Add Output as Text: " + getOutputAsText(createService));
        System.out.println("---------------------------------------------------");

    }

    private static String getResponse(WebResource service) {
        return service.accept(MediaType.TEXT_XML).get(ClientResponse.class).toString();
    }

    private static String getOutputAsText(WebResource service) {
        return service.accept(MediaType.TEXT_PLAIN).get(String.class);
    }
}
