/**
 * Created by el20jr on 22/11/
 * google API key: AIzaSyB-UeHhpvmEABLv1_ItIzWao58ngm91FiA
 */

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

@Path("/")
public class googlebooksREST {
    static final String REST_URI = "https://www.googleapis.com/books/v1/";
    static final String PREBOOKNAME_PATH = "volumes";
    static final String GOOGLEAPI_KEY = "AIzaSyB-UeHhpvmEABLv1_ItIzWao58ngm91FiA";

    @GET
    @Path("/{bookName}")
    @Produces(MediaType.TEXT_PLAIN)
    public static String main(@PathParam("bookName") String bookName) {

        //String book = "War and Peace";

        ClientConfig config = new DefaultClientConfig();
        Client client = Client.create(config);
        WebResource service = client.resource(REST_URI);

        WebResource booksService = service.path(PREBOOKNAME_PATH).queryParam("q",bookName).queryParam("key",GOOGLEAPI_KEY);
        //System.out.println("Sub Response: " + getResponse(booksService));
        //System.out.println("Add Output as Text: " + getOutputAsText(booksService));
        //System.out.println("Link: " + getLink(booksService));
        //System.out.println("---------------------------------------------------");
        return getLink(booksService);

    }

    private static String getResponse(WebResource service) {
        return service.accept(MediaType.TEXT_XML).get(ClientResponse.class).toString();
    }

    private static String getOutputAsText(WebResource service) {
        return service.accept(MediaType.TEXT_PLAIN).get(String.class);
    }

    private static String getLink(WebResource service) {
        String wholeText = getOutputAsText(service);
        //System.out.printf(wholeText);
        try {
            JSONObject obj = new JSONObject(wholeText);
            JSONArray items = new JSONArray(obj.getString("items"));
            JSONObject item0 = items.getJSONObject(0);
            JSONObject volumeInfo = new JSONObject(item0.getString("volumeInfo"));
            String link = volumeInfo.getString("canonicalVolumeLink");
            return link;
        } catch (JSONException e){
            e.printStackTrace();
            return "Err";
        }
    }


}
