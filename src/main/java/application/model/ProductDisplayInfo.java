package application.model;

public class ProductDisplayInfo {

    private String productName;
    private String subtype;
    private String price;
    private byte[] imageBytearray;
    private boolean inStock;

    public ProductDisplayInfo(String productName, String subtype, String price, byte[] imageBytearray,boolean inStock) {
        this.productName = productName;
        this.subtype = subtype;
        this.price = price;
        this.imageBytearray = imageBytearray;
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

    public byte[] getImageBytearray() {
        return imageBytearray;
    }
}
