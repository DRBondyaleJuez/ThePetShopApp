package model;

public class UserPurchaseRecord {

    private String productType;
    private String productCompleteName;

    private int purchasedQuantity;
    private double purchasePrice;
    private String purchaseDate;

    public UserPurchaseRecord(String productType, String productName, String subtype, int quantity, String price, String saleDate) {

        this.productType = productType;
        productCompleteName = productName + " (" + subtype + ")";
        purchasedQuantity = quantity;
        String formattedPrice = price.replace(",",".").replace(" €","");
        purchasePrice =  purchasedQuantity * Double.parseDouble(formattedPrice);
        purchaseDate = saleDate.substring(0,saleDate.length() - 4);
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
