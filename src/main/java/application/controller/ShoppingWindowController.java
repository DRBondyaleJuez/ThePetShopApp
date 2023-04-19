package application.controller;

import application.model.ProductDisplayInfo;
import application.persistence.database.DatabaseManager;
import application.web.ImageCollectorClient;

import java.sql.Timestamp;
import java.util.UUID;

public class ShoppingWindowController {

    private ImageCollectorClient imageCollectorClient;
    private DatabaseManager databaseManager;

    public ShoppingWindowController() {

        imageCollectorClient = new ImageCollectorClient();
        databaseManager = DatabaseManager.getInstance();
    }

    public byte[] getOnlineImageToSetProductImageView(String imageURL){
        return imageCollectorClient.requestImageData(imageURL);
    }

    public boolean insertNewPurchaseInfo(int productId, UUID userId,int quantity){
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());

        NewPurchaseInfo newPurchaseInfo = new NewPurchaseInfo(productId,userId,currentTime,quantity);

        return databaseManager.insertNewPurchaseInfo(newPurchaseInfo);
    }

    public class NewPurchaseInfo {
        private int productId;
        private UUID userId;
        private Timestamp currentTime;
        private int quantity;

        public NewPurchaseInfo(int productId, UUID userId, Timestamp currentTime, int quantity) {
            this.productId = productId;
            this.userId = userId;
            this.currentTime = currentTime;
            this.quantity = quantity;
        }

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

}
