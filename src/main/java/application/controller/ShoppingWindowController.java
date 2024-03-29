package application.controller;

import application.model.NewPurchaseInfo;
import application.persistence.assets.AssetManager;
import application.persistence.assets.FileSystemAssetTalker;
import application.persistence.database.DatabaseManager;
import application.web.ImageCollectorClient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Timestamp;
import java.util.UUID;

/**
 * Provides an object that acts as an intermediary between the ShoppingWindowView, the persistence and the web client. It performs the
 * actions necessary to fulfill the events of view interactions
 */
public class ShoppingWindowController {

    private static Logger logger = LogManager.getLogger(ShoppingWindowController.class);
    private final ImageCollectorClient imageCollectorClient;
    private final DatabaseManager databaseManager;

    private final AssetManager assetManager;

    /**
     * This is the constructor. Here  an instance of the DatabaseManager class is assigned to
     * the databaseManager. As well as the instantiation of an object of the ImageCollectorClient class.
     */
    public ShoppingWindowController() {

        imageCollectorClient = new ImageCollectorClient();
        databaseManager = DatabaseManager.getInstance();
        assetManager = AssetManager.getInstance();

    }

    /**
     * Retrieve the image found at the provided URL
     * @param imageURL String the URL where the image can be found
     * @return byte Array corresponding to the image at the URL or if unable it displays an asset placeholder
     */
    public byte[] getOnlineImageToSetProductImageView(String imageURL){
        byte[] productImage = imageCollectorClient.requestImageData(imageURL);

        if(productImage == null){

            // ---- LOG ----
            StringBuilder errorStackTrace = new StringBuilder();
            logger.warn("During the shopping window creation. The online image in url (" + imageURL + ") could not be retrieved properly from the url."  );

            productImage = assetManager.getUnavailableImage();
        }

        return productImage;
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
