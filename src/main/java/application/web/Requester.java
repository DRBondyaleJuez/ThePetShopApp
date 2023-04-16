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
     * @param protocolAndHost the protocol and host of the API "https://exampleHost.com"
     * @param path the path of the API "/examplePath"
     * @param queryParams the query parameters required by the API
     * @return HttpResponse with the corresponding content base on the request
     */
    public HttpResponse getMethod (String protocolAndHost, String path, Map<String, String> queryParams) {
        URI finalEndpoint = createUri(protocolAndHost, path, queryParams);

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

    private URI createUri(String protocolAndHost, String path, Map<String, String> queryParams) {
        try {
            URIBuilder uriBuilder = new URIBuilder(protocolAndHost);
            uriBuilder.setPath(path);
            for (Map.Entry<String, String> entry : queryParams.entrySet()) {
                uriBuilder.setParameter(entry.getKey(), entry.getValue());
            }

            return uriBuilder.build();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
            // TODO: Do this better
        }
    }

}
