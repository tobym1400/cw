/*
 * Created on 10 Sep 2013
 * Revised 22 Oct 2017
 *
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package jaxrs.client;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

import javax.ws.rs.core.MediaType;

public class RESTClient {
    static final String REST_URI = "http://localhost:9994/service1";
    static final String CREATE_PATH = "create";
    static final String DELETE_PATH = "delete";
    static final String READ_PATH = "read";
    static final String UPDATE_PATH = "update/";
    static final String MAIN_PATH = "main";
 
    public static void main(String[] args) {

        String index = '"'+args[0]+'"';
        String sName = '"'+args[1]+'"';
        String fName = '"'+args[2]+'"';
        String booksSold = '"'+args[3]+'"';
        String nationality = '"'+args[4]+'"';
        String foreignKey = '"'+args[5]+'"';
 
        ClientConfig config = new DefaultClientConfig();
        Client client = Client.create(config);
        WebResource service = client.resource(REST_URI);
 
        WebResource createService = service.path(CREATE_PATH);
        WebResource deleteService = service.path(DELETE_PATH);
        WebResource readService = service.path(READ_PATH);
        WebResource mainService = service.path(MAIN_PATH).queryParam("sName",'"'+"Tolstoy"+'"');
        WebResource updateService = service.path(UPDATE_PATH).queryParam("index",index)
                .queryParam("sName",sName).queryParam("fName",fName).queryParam("books",booksSold)
                .queryParam("nation",nationality).queryParam("fk",foreignKey);

        System.out.println("Create Response: " + getResponse(createService));
        System.out.println("Create Output as Text: " + getOutputAsText(createService));
        System.out.println("---------------------------------------------------");

        System.out.println("Read Response: " + getResponse(readService));
        System.out.println("Read Output as Text: " + getOutputAsText(readService));
        System.out.println("---------------------------------------------------");

        System.out.println("Service Response: " + getResponse(mainService));
        System.out.println("Service Output as Text: " + getOutputAsText(mainService));
        System.out.println("---------------------------------------------------");

        System.out.println("Update Response: " + getResponse(updateService));
        System.out.println("Update Output as Text: " + getOutputAsText(updateService));
        System.out.println("---------------------------------------------------");

        System.out.println("Read Response: " + getResponse(readService));
        System.out.println("Read Output as Text: " + getOutputAsText(readService));
        System.out.println("---------------------------------------------------");

        System.out.println("Delete Response: " + getResponse(deleteService));
        System.out.println("Delete Output as Text: " + getOutputAsText(deleteService));
        System.out.println("---------------------------------------------------");

        System.out.println("Read Response: " + getResponse(readService));
        System.out.println("Read Output as Text: " + getOutputAsText(readService));
        System.out.println("---------------------------------------------------");
    }
 
    private static String getResponse(WebResource service) {
        return service.accept(MediaType.TEXT_XML).get(ClientResponse.class).toString();
    }
 
    private static String getOutputAsText(WebResource service) {
        return service.accept(MediaType.TEXT_PLAIN).get(String.class);
    }
}
