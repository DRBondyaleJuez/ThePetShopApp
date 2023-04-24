package application.model;

import java.sql.Timestamp;
import java.util.UUID;

/**
 * Nested Class to encapsulate the purchase information that is sent through the persistence to be stored in the database
 * once a purchase takes place.
 */
public class NewPurchaseInfo {
    private int productId;
    private UUID userId;
    private Timestamp currentTime;
    private int quantity;

    /**
     * This is the constructor with all the attributes.
     * @param productId int corresponding to the id of the product purchased
     * @param userId UUID corresponding to the user that performed the purchase
     * @param currentTime Timestamp of the date and time the purchase was performed
     * @param quantity int the number of units of the product purchased
     */
    public NewPurchaseInfo(int productId, UUID userId, Timestamp currentTime, int quantity) {
        this.productId = productId;
        this.userId = userId;
        this.currentTime = currentTime;
        this.quantity = quantity;
    }

    //GETTERS
    public int getProductId() {
        return productId;
    }

    public UUID getUserId() {
        return userId;
    }

    public Timestamp getCurrentTime() {
        return currentTime;
    }

    public int getQuantity() {
        return quantity;
    }
}
