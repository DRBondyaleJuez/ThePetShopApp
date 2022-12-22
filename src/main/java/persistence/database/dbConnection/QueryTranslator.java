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
        return currentENUM.toString();
    }

    public String translateEnum(UsersTableColumnNameEnums currentENUM){
        return currentENUM.toString();
    }

    public String buildUpdateQuery(TableNameEnums tableName, UsersTableColumnNameEnums refColumn, String reference, UsersTableColumnNameEnums columnToUpdate, String updatedContent) {

        String queryRefColumn = translateEnum(refColumn);
        String queryColumnToUpdate = translateEnum(columnToUpdate);
        String queryTableName = translateEnum(tableName);

        String sqlQuery = "UPDATE " + queryTableName + " " +
                "SET " + queryColumnToUpdate + " = '" + updatedContent + "' " +
                "WHERE " + queryRefColumn + " = '" + reference + "' " +
                "RETURNING *";

        return sqlQuery;

    }
}
