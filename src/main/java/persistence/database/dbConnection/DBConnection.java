package persistence.database.dbConnection;

import persistence.database.dbConnection.dbTablesEnums.TableNameEnums;
import persistence.database.dbConnection.dbTablesEnums.UsersTableColumnNameEnums;

import java.sql.*;
import java.util.UUID;

public class DBConnection {

    private String database;
    private final String url;
    private final String user = "postgres";
    private final String password;
    private final Connection currentConnection;
    private final QueryTranslator queryTranslator;

    public DBConnection(String currentDatabase) {

        database = currentDatabase;
        url = "jdbc:postgresql://localhost/" + database;
        password = new DBPasswordHandler().getPassword();
        currentConnection = connect();
        queryTranslator = new QueryTranslator();

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

    public SQLErrorMessageEnums addNewUserToDatabase(UUID newUserUUID, String newUsername, String newUserPassword, String newUserEmail, Timestamp newUserCreationTimeStamp){

        String sql = "INSERT INTO users (user_id,username,password,email,date_created) " +
                     "VALUES ('" + newUserUUID + "','" + newUsername + "','" + newUserPassword + "','" + newUserEmail + "','" + newUserCreationTimeStamp + "') " +
                     "RETURNING *";
        try (
            PreparedStatement preparedStatement = currentConnection.prepareStatement(sql)) {

            ResultSet resultSet = preparedStatement.executeQuery();

            //TODO: This is not working properly. Investigate the returned resultset when inserting----------------------------------
            System.out.println(resultSet);
            while(resultSet.next()) {
                String returnedUsername = resultSet.getString("username");
                System.out.println(returnedUsername);

                if (newUsername == returnedUsername) {
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

    public SQLErrorMessageEnums interpretSQLErrorMessage(String sqlErrorMessage){

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

        String sql = queryTranslator.buildSelectQuery(tableName,refColumn,reference);

        String returnedRecord = "";
        try (
            PreparedStatement preparedStatement = currentConnection.prepareStatement(sql)) {

            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                returnedRecord = resultSet.getString(queryTranslator.translateEnum(columnOfInterest));
                //System.out.println(returnedRecord);
            }

        } catch (SQLException e) {
            System.out.println("SQL ERROR MESSAGE during record retrieval: " + e.getMessage());
        }
        return returnedRecord;
    }

    public boolean updateRecord(TableNameEnums tableName, UsersTableColumnNameEnums refColumn, String reference,UsersTableColumnNameEnums columnToUpdate,String updatedContent){
        String sql = queryTranslator.buildUpdateQuery(tableName,refColumn,reference,columnToUpdate,updatedContent);

        String returnedRecord = "";
        try (
                PreparedStatement preparedStatement = currentConnection.prepareStatement(sql)) {

            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                returnedRecord = resultSet.getString(queryTranslator.translateEnum(columnToUpdate));
            }
            //System.out.println(returnedRecord);
            if(returnedRecord == updatedContent){
                return true;
            } else {
                return false;
            }

        } catch (SQLException e) {
            System.out.println("SQL ERROR MESSAGE during record update:" + e.getMessage());
            return false;
        }
    }

    public int countPurchasesByUser(UUID currentUserUUID){

        int numberOfPurchasesByUser = -1;

        String sql = "SELECT COUNT(*) " +
                "FROM product_sales " +
                "WHERE buyer_id = '" + currentUserUUID + "'";
;
        try (
            PreparedStatement preparedStatement = currentConnection.prepareStatement(sql)) {

            ResultSet resultSet = preparedStatement.executeQuery();

            System.out.println("This corresponds to the result set from the purchase count: " + resultSet);
            while (resultSet.next()) {
                numberOfPurchasesByUser = resultSet.getInt("count");
            }

        } catch (SQLException e) {
            System.out.println("SQL ERROR MESSAGE associated with counting purchases: " + e.getMessage());
            return numberOfPurchasesByUser;
        }
        return numberOfPurchasesByUser;
    }

    public String[] getPurchaseRecordInfo(UUID currentUserUUID,int position){

        String [] purchaseInfo = new String[6];

        String sql = "SELECT product_type, product_name, subtype, quantity, price, sale_date " +
                "FROM product_sales " +
                "INNER JOIN products USING (product_id) " +
                "WHERE buyer_id = '" + currentUserUUID + "' " +
                "ORDER BY sale_date " +
                "LIMIT 1 OFFSET " + position;
        ;
        try (
            PreparedStatement preparedStatement = currentConnection.prepareStatement(sql)) {

            ResultSet resultSet = preparedStatement.executeQuery();

            System.out.println("This corresponds to the result set from the purchase info retrieval: "+ resultSet);
            while (resultSet.next()) {
                for (int i = 0; i < purchaseInfo.length; i++) {
                    purchaseInfo[i] = resultSet.getString(i+1);
                }
            }


        } catch (SQLException e) {
            System.out.println("SQL ERROR MESSAGE of the purchase info retrieval: " + e.getMessage());
            return null;
        }
        return purchaseInfo;
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
