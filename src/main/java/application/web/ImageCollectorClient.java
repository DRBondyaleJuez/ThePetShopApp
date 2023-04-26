package application.web;

import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ImageCollectorClient {

    private final Requester requester;

    /**
     * This is the constructor where the protocol and host are defined and requester is instantiated.
     */
    public ImageCollectorClient() {
        requester = new Requester();
    }

    /**
     * This method is called to request an image provided the imageURL.
     * The requester will format the request properly with the parameters and arguments provided. The response is processed
     * extracting the body in the form of byte array.
     * @param imageURL the path of the image online
     * @return byte array corresponding to the image of the provided url
     */
    public byte[] requestImageData(String imageURL){

        return processResponse(imageURL);
    }

    private byte[] processResponse(String imageURL) {

        byte[] jsonBody;
        try {
            HttpResponse response = requester.getMethod(imageURL);
            if(response.getStatusLine().getStatusCode() != 200) return null;
            jsonBody = EntityUtils.toByteArray(response.getEntity());
        } catch (IOException | URISyntaxException e) {
            System.out.println(e);
            return null;
            //throw new RuntimeException(e);
            // TODO: As always handle this better

        }

        return jsonBody;
    }
}


