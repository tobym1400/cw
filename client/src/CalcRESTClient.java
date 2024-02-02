
//package com.tutorial.jaxrs.calc.client;

import javax.print.DocFlavor;
import javax.ws.rs.core.MediaType;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

import java.util.Scanner;

public class CalcRESTClient {
    static final String service1Link = "http://localhost:9998/service1/main";
    static final String service2Link = "http://localhost:9999/service2/top3/returntop3";
    static final String externalLink = "http://localhost:9997/service3/";
 
    public static void main(String[] args) {

        String sName = args[0];

        ClientConfig config = new DefaultClientConfig();
        Client client = Client.create(config);

        WebResource service1 = client.resource(service1Link);
        WebResource search1 = service1.path("'" + sName + "'");
        System.out.println("Response: " + getResponse(search1));
        String output = getOutputAsText(search1);
        String foreignKeyStr = output.substring(output.length()-1);
        System.out.println("Output as Text: " + getOutputAsText(search1));

        WebResource service2 = client.resource(service2Link);
        WebResource search2 = service2.path(foreignKeyStr);
        System.out.println("Response: " + getResponse(search2));
        System.out.println("Output as Text: " + getOutputAsText(search2));


        System.out.println("Enter book name: ");
        Scanner scan = new Scanner(System.in);
        String bull = scan.nextLine();
        WebResource serviceE = client.resource(externalLink);
        WebResource searchE = serviceE.path(bull);
        System.out.println("Response: " + getResponse(searchE));
        System.out.println("Output as Text: " + getOutputAsText(searchE));

 
    }
 
    private static String getResponse(WebResource service) {
        return service.accept(MediaType.TEXT_XML).get(ClientResponse.class).toString();
    }
 
//    private static String getOutputAsXML(WebResource service) {
//        return service.accept(MediaType.TEXT_XML).get(String.class);
//    }
 
    private static String getOutputAsText(WebResource service) {
        return service.accept(MediaType.TEXT_PLAIN).get(String.class);
    }
}
