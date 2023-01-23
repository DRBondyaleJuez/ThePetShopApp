package persistence.database.dbConnection;

import model.UserPurchaseRecord;
import persistence.database.dbConnection.dbTablesEnums.TableNameEnums;
import persistence.database.dbConnection.dbTablesEnums.UsersTableColumnNameEnums;

import java.io.ByteArrayInputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.UUID;

public class DBConnection {

    private String database;
    private final String url;
    private final String user = "postgres";
    private final String password;
    private final Connection currentConnection;

    public DBConnection(String currentDatabase) {

        database = currentDatabase;
        url = "jdbc:postgresql://localhost/" + database;
        password = new DBPasswordHandler().getPassword();
        currentConnection = connect();
    }

    /**
     * Connect to the PostgreSQL database
     *
     * @return a Connection object
     */
    public Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, user, password);
            System.out.println("Connected to the PostgreSQL server successfully.");
        } catch (SQLException e) {
            System.out.println("SQL ERROR MESSAGE: " + e.getMessage());
        }

        return conn;
    }

    public Connection getConnection() {
        return currentConnection;
    }

    public SQLErrorMessageEnums addNewUserToDatabase(UUID newUserUUID, String newUsername, byte[] newUserPassword, String newUserEmail, Timestamp newUserCreationTimeStamp){

        String sql = "INSERT INTO users (user_id,username,password,email,date_created) " +
                "VALUES (?::uuid , ? , ? , ? , ?) " +
                "RETURNING *";
        try {
            PreparedStatement preparedStatement = currentConnection.prepareStatement(sql);
            preparedStatement.setString(1,newUserUUID.toString());
            preparedStatement.setString(2,newUsername);
            preparedStatement.setBinaryStream(3,new ByteArrayInputStream(newUserPassword));
            preparedStatement.setString(4,newUserEmail);
            preparedStatement.setTimestamp(5,newUserCreationTimeStamp);

            ResultSet resultSet = preparedStatement.executeQuery();

            //TODO: This is not working properly. Investigate the returned resultset when inserting----------------------------------
            System.out.println(resultSet);
            while(resultSet.next()) {
                String returnedUsername = resultSet.getString("username");
                System.out.println(returnedUsername);

                if (newUsername.equals(returnedUsername)) {
                    System.out.println("EVERYTHING WAS CORRECT. New user inserted");
                } else {
                    System.out.println("SOMETHING WAS WRONG. Not sure user inserted");
                }
            }

        } catch (SQLException e) {
            System.out.println("SQL ERROR MESSAGE: " + e.getMessage());
            return interpretSQLErrorMessage(e.getMessage());
        }
        return SQLErrorMessageEnums.NO_ERROR;
    }

    private SQLErrorMessageEnums interpretSQLErrorMessage(String sqlErrorMessage){

        if(sqlErrorMessage.contains("users_username_key")){
            return SQLErrorMessageEnums.USERNAME;
        }
        if(sqlErrorMessage.contains("users_email_key")){
            return SQLErrorMessageEnums.USER_EMAIL;
        }
        if(sqlErrorMessage.contains("users_user_id_key")){
            return SQLErrorMessageEnums.UUID;
        }
        return SQLErrorMessageEnums.UNKNOWN_ERROR;

    }

    public String getRecordFromTable(TableNameEnums tableName, UsersTableColumnNameEnums refColumn, String reference, UsersTableColumnNameEnums columnOfInterest){

        String uuidCaseModifier = " ";
        if(refColumn == UsersTableColumnNameEnums.USER_UUID) {
            uuidCaseModifier = "::uuid ";
        }

        String sql = "SELECT * " +
                "FROM " + tableName.toString() + " " +
                "WHERE " + refColumn.toString() + " = ? " + uuidCaseModifier;

        String returnedRecord = "";

        try {
            PreparedStatement preparedStatement = currentConnection.prepareStatement(sql);
            preparedStatement.setString(1,reference);

            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                returnedRecord = resultSet.getString(columnOfInterest.toString());
            }

        } catch (SQLException e) {
            System.out.println("SQL ERROR MESSAGE during record retrieval: " + e.getMessage());
        }
        return returnedRecord;
    }

    public byte[] getPasswordFromTable(UsersTableColumnNameEnums refColumn,String reference){

        String sql = "SELECT * " +
                "FROM " + TableNameEnums.USERS.toString() + " " +
                "WHERE " + refColumn.toString() + " = ? ";

        ArrayList<Byte> returnedEncryptedByteList = new ArrayList<>();

        try {
            PreparedStatement preparedStatement = currentConnection.prepareStatement(sql);
            preparedStatement.setString(1,reference);

            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()) {
                byte[] returnedPasswordByteArray = resultSet.getBytes(UsersTableColumnNameEnums.USER_PASSWORD.toString());

                for (int i = 0; i < returnedPasswordByteArray.length; i++) {
                    returnedEncryptedByteList.add(returnedPasswordByteArray[i]);
                }
            }

        } catch (SQLException e) {
            System.out.println("SQL ERROR MESSAGE during record retrieval: " + e.getMessage());
        }
        Byte[] returnedPasswordByteArray = new Byte[returnedEncryptedByteList.size()];
        returnedEncryptedByteList.toArray(returnedPasswordByteArray);
        byte[] primitiveReturnedPasswordByteArray = new byte[returnedPasswordByteArray.length];

        for (int i = 0; i < returnedPasswordByteArray.length; i++) {
            primitiveReturnedPasswordByteArray[i] = returnedPasswordByteArray[i];
        }

        return primitiveReturnedPasswordByteArray;
    }

public boolean updateRecord(TableNameEnums tableName, UsersTableColumnNameEnums refColumn, String reference,UsersTableColumnNameEnums columnToUpdate,String updatedContent){

    String timeStampCaseModifier = " ";
    if(columnToUpdate == UsersTableColumnNameEnums.USER_LAST_LOGIN) {
        timeStampCaseModifier = "::timestamp ";
    }
    String sql = "UPDATE " + tableName.toString() + " " +
            "SET " + columnToUpdate.toString() + " = ? " + timeStampCaseModifier +
            "WHERE " + refColumn.toString() + " = ?::uuid " +
            "RETURNING *";

    String returnedRecord = "";
    try {
        PreparedStatement preparedStatement = currentConnection.prepareStatement(sql);
        preparedStatement.setString(1,updatedContent);
        preparedStatement.setString(2,reference);

        ResultSet resultSet = preparedStatement.executeQuery();
        while(resultSet.next()) {
            returnedRecord = resultSet.getString(columnToUpdate.toString());
        }

        return returnedRecord.equals(updatedContent);

    } catch (SQLException e) {
        System.out.println("SQL ERROR MESSAGE during record update:" + e.getMessage());
        return false;
    }
}

    public UserPurchaseRecord[] getUserPurchaseRecordInfo(UUID currentUserUUID){

        ArrayList<UserPurchaseRecord> purchaseInfoList = new ArrayList<>();
        String[] currentPurchaseInfo = new String[6];

        String sql = "SELECT product_type, product_name, subtype, quantity, price, sale_date " +
                "FROM product_sales " +
                "INNER JOIN products USING (product_id) " +
                "WHERE buyer_id = ?::uuid " +
                "ORDER BY sale_date";
        try {
            PreparedStatement preparedStatement = currentConnection.prepareStatement(sql);
            preparedStatement.setString(1, currentUserUUID.toString());

            ResultSet resultSet = preparedStatement.executeQuery();

            System.out.println("This corresponds to the result set from the purchase info retrieval: "+ resultSet);
            while (resultSet.next()) {
                String currentPurchaseType = resultSet.getString("product_type");
                String currentName = resultSet.getString("product_name");
                String currentSubtype = resultSet.getString("subtype");
                int currentQuantity = resultSet.getInt("quantity");
                String currentPrice = resultSet.getString("price");
                String currentSaleDate = resultSet.getString("sale_date");

                UserPurchaseRecord currentPurchaseRecord = new UserPurchaseRecord(currentPurchaseType,currentName,currentSubtype,currentQuantity,currentPrice,currentSaleDate);
                purchaseInfoList.add(currentPurchaseRecord);
            }
        } catch (SQLException e) {
            System.out.println("SQL ERROR MESSAGE of the purchase info retrieval: " + e.getMessage());
            return null;
        }

        UserPurchaseRecord[] purchaseInfoArray = new UserPurchaseRecord[purchaseInfoList.size()];
        purchaseInfoArray = purchaseInfoList.toArray(purchaseInfoArray);

        return purchaseInfoArray;
    }

    public String properCase(String s) {
        String result = s;
        try (
             CallableStatement properCase = currentConnection.prepareCall("{ ? = call initcap( ? ) }")) {
            properCase.registerOutParameter(1, Types.VARCHAR);
            properCase.setString(2, s);
            properCase.execute();
            result = properCase.getString(1);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return result;
    }

    public void getAllColumnFormActorsTable(){
        String sql = "SELECT * FROM actor";
        try (
            PreparedStatement preparedStatement = currentConnection.prepareStatement(sql)) {

            ResultSet resultSet = preparedStatement.executeQuery();

            System.out.println("Amount of actors: " + resultSet.getFetchSize());

            while (resultSet.next()) {
                int actorId = resultSet.getInt("actor_id");
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                Date lastUpdate = resultSet.getDate("last_update");

                System.out.println(actorId + " " + firstName + " " + lastName + " " + lastUpdate);

            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void getFilms(String pattern, int releaseYear) {
        String SQL = "SELECT * FROM get_film (?, ?)";
        try (
            PreparedStatement pstmt = currentConnection.prepareStatement(SQL)) {

            pstmt.setString(1,pattern);
            pstmt.setInt(2,releaseYear);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                System.out.println(String.format("%s %d",
                        rs.getString("film_title"),
                        rs.getInt("film_release_year")));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }



}
