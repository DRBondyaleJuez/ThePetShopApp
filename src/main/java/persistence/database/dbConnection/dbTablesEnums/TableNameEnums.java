package persistence.database.dbConnection.dbTablesEnums;

public enum TableNameEnums {
    USERS("users"),PRODUCTS("products"),PRODUCT_SALES("product_sales");

    private final String text;

    TableNameEnums (final String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }

}
