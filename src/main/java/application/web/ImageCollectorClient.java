package application.web;

import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
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
     * This method is called to request beer data to the API filtering using the beer name.
     * The requester will format the request properly with the parameters and arguments provided. The response is processed
     * using an objectMapper to manage the JSON provided by the API.
     * @param imageURL the path of the image online
     * @return ArrayList of Beer with the provided name. It can be empty.
     */
    public byte[] requestImageData(String imageURL){

        return processResponse(requester.getMethod(imageURL));
    }

    private byte[] processResponse(HttpResponse response) {

        //ObjectMapper objectMapper = new ObjectMapper();
        //objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false); //This is necessary son non-defined attributes don't trigger an UnrecognizedPropertyException
        byte[] jsonBody;
        try {
            jsonBody = EntityUtils.toByteArray(response.getEntity());
        } catch (IOException e) {
            throw new RuntimeException(e);
            // TODO: As always handle this better
        }

        /*
        try {
            listPunkBeers = objectMapper.readValue(jsonBody, new TypeReference<List<AuxiliaryPunkBeer>>(){});
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        ArrayList<Beer> beerSearched = new ArrayList<>();

        Manufacturer auxiliaryManufacturer = new Manufacturer("PunkApi","External Manufacturer");

        for (AuxiliaryPunkBeer apBeer : listPunkBeers) {
            String tempName = apBeer.getName();
            double tempGraduation = apBeer.getAbv();
            String tempType = apBeer.getTagline();
            String tempDescription = apBeer.getDescription();

            Beer tempBeer = new Beer(tempName,tempGraduation,tempType,tempDescription,auxiliaryManufacturer);
            beerSearched.add(tempBeer);
        }
        */

        return jsonBody;
    }

}


