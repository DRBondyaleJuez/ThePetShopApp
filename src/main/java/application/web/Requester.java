package application.web;

import org.apache.http.HttpResponse;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.fluent.Response;
import org.apache.http.client.utils.URIBuilder;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

/**
 * Provides an object that build properly a request for the API
 */
public class Requester {

    /**
     * This method build the URI and request for a GET method in the provided API
     * @param imageURL the path  of the image online "https://exampleHost.com"
     * @return HttpResponse with the corresponding content base on the request
     */
    public HttpResponse getMethod (String imageURL) {
        URI finalEndpoint = createUri(imageURL);

        String finalEndpointString = finalEndpoint.toString().replaceAll("%2C", ",");
        System.out.println(finalEndpointString);

        try {
            Response response = Request.Get(finalEndpointString).execute();

            /*
            // IF this need body
            Request request = Request.Post("");
            request.body
             */

            return response.returnResponse();
        } catch (IOException e) {
            throw new RuntimeException(e);
            // TODO: Log this and/or do this better
        }

    }

    private URI createUri(String imageURL) {
        try {
            URIBuilder uriBuilder = new URIBuilder(imageURL);

            return uriBuilder.build();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
            // TODO: Do this better
        }
    }

}