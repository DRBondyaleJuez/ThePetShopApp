package application.web;

import application.persistence.assets.FileSystemAssetTalker;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.URISyntaxException;

public class ImageCollectorClient {

    private final Requester requester;
    private static Logger logger = LogManager.getLogger(ImageCollectorClient.class);

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
            // ---- LOG ----
            StringBuilder errorStackTrace = new StringBuilder();
            for (StackTraceElement ste:e.getStackTrace()) {
                errorStackTrace.append("        ").append(ste).append("\n");
            }
            logger.warn("The online image in url (" + imageURL + ") could not be loaded. ERROR:\n " + e + "\n" + "STACK TRACE:\n" + errorStackTrace );
            return null;
        }

        return jsonBody;
    }
}


