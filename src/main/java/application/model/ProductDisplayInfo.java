package application.model;

/**
 * Provides an encapsulation of the information associated with the product as documented in the database
 */
public class ProductDisplayInfo {

    private final String productName;
    private final String subtype;
    private final String price;
    private final String imageURL;
    private final boolean inStock;
    private final int productId;


    /**
     * This is the constructor. Its attributes value are based on the parameters retrieved from the database
     * @param productId int corresponding to the product id number
     * @param productName String name of the product
     * @param subtype String extended description of the product
     * @param price String the price of the product with its currency symbol
     * @param imageURL String the URL of the image corresponding to the product
     * @param inStock boolean informing if the product is in stock
     */
    public ProductDisplayInfo(int productId, String productName, String subtype, String price, String imageURL,boolean inStock) {
        this.productId = productId;
        this.productName = productName;
        this.subtype = subtype;
        this.price = price;
        this.imageURL = imageURL;
        this.inStock = inStock;
    }

    //GETTERS
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
