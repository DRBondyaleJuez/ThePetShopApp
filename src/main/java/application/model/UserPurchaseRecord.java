package application.model;

import java.sql.Timestamp;

/**
 * Provides an encapsulation of the information associated with each purchase record of a user
 */
public class UserPurchaseRecord {

    private String productType;
    private String productCompleteName;

    private int purchasedQuantity;
    private double purchasePrice;
    private Timestamp purchaseDate;

    /**
     * This is the constructor. Its attributes value are based on the parameters retrieved from the database
     * @param productType String The category to which the product belongs to
     * @param productName String the name of the product
     * @param subtype String extended description of the product
     * @param quantity int the number of items purchased at that moment
     * @param price String the price of the product with the currency
     * @param saleDate Timestamp the date and time of the purchase
     */
    public UserPurchaseRecord(String productType, String productName, String subtype, int quantity, String price, Timestamp saleDate) {

        this.productType = productType;
        productCompleteName = productName + " (" + subtype + ")";
        purchasedQuantity = quantity;
        String formattedPrice = price.replace(",",".").replace(" €","");
        purchasePrice =  purchasedQuantity * Double.parseDouble(formattedPrice);
        purchaseDate = saleDate;
    }

    //TO STRING
    public String toString(){
        return productCompleteName + " " + purchasedQuantity + purchasePrice + " €" + " " + purchaseDate.toString().substring(0,purchaseDate.toString().length()-4);
    }

    //GETTER
    public String getProductType() {
        return productType;
    }

    public String getProductCompleteName() {
        return productCompleteName;
    }

    public int getPurchasedQuantity() {
        return purchasedQuantity;
    }

    public double getPurchasePrice() {
        return purchasePrice;
    }

    public String getPurchaseDate() {
        return purchaseDate.toString().substring(0,purchaseDate.toString().length()-4);
    }
}
