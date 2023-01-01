package persistence.database.dbConnection.dbTablesEnums;

public enum UsersTableColumnNameEnums {
    //USER_UUID,USERNAME,USER_PASSWORD,USER_EMAIL,USER_CREATION_DATE,USER_LAST_LOGIN

    USER_UUID("user_id"), USERNAME("username"),USER_PASSWORD("password"),USER_EMAIL("email"),USER_CREATION_DATE("date_created"),USER_LAST_LOGIN("last_login");

    private final String text;

    UsersTableColumnNameEnums (final String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}
