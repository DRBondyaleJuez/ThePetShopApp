package persistence.database.dbConnection;

import persistence.database.dbConnection.dbTablesEnums.TableNameEnums;
import persistence.database.dbConnection.dbTablesEnums.UsersTableColumnNameEnums;

public class QueryTranslator {

    public QueryTranslator() {
    }

    public String buildSelectQuery(TableNameEnums tableName, UsersTableColumnNameEnums refColumn, String reference){

        String queryRefColumn = translateEnum(refColumn);
        String queryTableName = translateEnum(tableName);

        String sqlQuery = "SELECT * " +
                "FROM " + queryTableName + " " +
                "WHERE " + queryRefColumn + " = '" + reference + "'";

        return sqlQuery;
    }

    public String translateEnum(TableNameEnums currentENUM){
        String translation = "";
        switch(currentENUM){
            case USERS:
                translation = "users";
                break;
            case PRODUCTS:
                translation = "products";
                break;
            case PRODUCT_SALES:
                translation = "product_sales";
                break;
            default:
                break;
        }
        return translation;
    }

    public String translateEnum(UsersTableColumnNameEnums currentENUM){
        String translation = "";
        switch(currentENUM){
            case USER_UUID:
                translation = "user_id";
                break;
            case USERNAME:
                translation = "username";
                break;
            case USER_PASSWORD:
                translation = "password";
                break;
            case USER_EMAIL:
                translation = "email";
                break;
            default:
                break;
        }
        return translation;
    }

}
