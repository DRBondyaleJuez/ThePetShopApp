package model;

public class ProductDisplayInfo {

    String productName;
    String subtype;
    String price;
    byte[] imageBytearray;
    boolean inStock;

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

    public byte[] getImageBytearray() {
        return imageBytearray;
    }
}
