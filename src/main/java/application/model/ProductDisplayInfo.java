package application.model;

public class ProductDisplayInfo {

    private String productName;
    private String subtype;
    private String price;
    private String imageURL;
    private boolean inStock;

    public ProductDisplayInfo(String productName, String subtype, String price, String imageURL,boolean inStock) {
        this.productName = productName;
        this.subtype = subtype;
        this.price = price;
        this.imageURL = imageURL;
        this.inStock = inStock;
    }

    public String getProductName() {
        return productName;
    }

    public String getSubtype() {
        return subtype;
    }

    public String getPrice() {
        return price;
    }
    public boolean getStockInfo() {
        return inStock;
    }

    public String getImageURL() {
        return imageURL;
    }
}
