package model;

public class UserPurchaseRecord {

    private String productType;
    private String productCompleteName;

    private int purchasedQuantity;
    private double purchasePrice;
    private String purchaseDate;

    public UserPurchaseRecord(String [] currentPurchasedItemInfo) {

        productType = currentPurchasedItemInfo[0];
        productCompleteName = currentPurchasedItemInfo[1] + " (" + currentPurchasedItemInfo[2] + ")";
        purchasedQuantity = Integer.parseInt(currentPurchasedItemInfo[3]);
        String formattedPrice = currentPurchasedItemInfo[4].replace(",",".").replace(" €","");
        purchasePrice =  purchasedQuantity * Double.parseDouble(formattedPrice);
        purchaseDate = currentPurchasedItemInfo[5];

    }

    public String toString(){
        return productCompleteName + " " + purchasedQuantity + purchasePrice + " €" + " " + purchaseDate;
    }

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
        return purchaseDate;
    }
}
