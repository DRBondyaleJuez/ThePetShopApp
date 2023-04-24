package application.controller;

import application.model.NewPurchaseInfo;
import application.model.ProductDisplayInfo;
import application.persistence.database.DatabaseManager;
import application.web.ImageCollectorClient;

import java.sql.Timestamp;
import java.util.UUID;

/**
 * Provides an object that acts as an intermediary between the ShoppingWindowView, the persistence and the web client. It performs the
 * actions necessary to fulfill the events of view interactions
 */
public class ShoppingWindowController {

    private ImageCollectorClient imageCollectorClient;
    private DatabaseManager databaseManager;

    /**
     * This is the constructor. Here  an instance of the DatabaseManager class is assigned to
     * the databaseManager. As well as the instantiation of an object of the ImageCollectorClient class.
     */
    public ShoppingWindowController() {

        imageCollectorClient = new ImageCollectorClient();
        databaseManager = DatabaseManager.getInstance();
    }

    /**
     * Retrieve the image found at the provided URL
     * @param imageURL String the URL where the image can be found
     * @return byte Array corresponding to the image at the URL
     */
    public byte[] getOnlineImageToSetProductImageView(String imageURL){
        return imageCollectorClient.requestImageData(imageURL);
    }


    /**
     * Insert in the database the purchase information that took place
     * @param productId int The id of the product purchased
     * @param userId UUID The id of the user that purchased the product
     * @param quantity int the number of units of the product purchased
     * @return boolean indicating if the info was inserted correctly
     */
    public boolean insertNewPurchaseInfo(int productId, UUID userId,int quantity){
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());

        NewPurchaseInfo newPurchaseInfo = new NewPurchaseInfo(productId,userId,currentTime,quantity);

        return databaseManager.insertNewPurchaseInfo(newPurchaseInfo);
    }
}
