package application.model;

public class ProductDisplayInfo {

    private String productName;
    private String subtype;
    private String price;
    private String imageURL;
    private boolean inStock;
    private int productId;

    public ProductDisplayInfo(int productId, String productName, String subtype, String price, String imageURL,boolean inStock) {
        this.productId = productId;
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

    public boolean isInStock() {
        return inStock;
    }

    public int getProductId() {
        return productId;
    }
}
