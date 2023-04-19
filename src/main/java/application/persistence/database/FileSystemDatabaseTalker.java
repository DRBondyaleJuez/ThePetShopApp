package application.persistence.database;

import application.controller.ShoppingWindowController;
import application.model.ProductDisplayInfo;
import application.model.UserPurchaseRecord;
import application.persistence.database.dbConnection.DBConnection;
import application.persistence.database.dbConnection.SQLErrorMessageEnums;
import application.persistence.database.dbConnection.dbTablesEnums.TableNameEnums;
import application.persistence.database.dbConnection.dbTablesEnums.UsersTableColumnNameEnums;

import java.sql.Timestamp;
import java.util.UUID;

public class FileSystemDatabaseTalker implements DatabaseTalker{

    private DBConnection currentDBConnection;

    public FileSystemDatabaseTalker() {
        currentDBConnection = new DBConnection("the_pet_shop");
    }

    @Override
    public SQLErrorMessageEnums addNewUserToDatabase(UUID newUserUUID, String newUsername, byte[] newUserPassword, String newUserEmail, Timestamp newUserCreationTimeStamp) {
        if(!checkConnection()){
            System.out.println("Unable to connect to the database");
            return SQLErrorMessageEnums.UNKNOWN_ERROR;
        }

        return currentDBConnection.addNewUserToDatabase(newUserUUID,newUsername,newUserPassword,newUserEmail,newUserCreationTimeStamp);
    }

    @Override
    public String getRecordFromTable(TableNameEnums tableName, UsersTableColumnNameEnums refColumn, String reference, UsersTableColumnNameEnums columnOfInterest) {
        return currentDBConnection.getRecordFromTable(tableName, refColumn, reference, columnOfInterest);
    }

    @Override
    public byte[] getPasswordFromTable(UsersTableColumnNameEnums refColumn, String reference) {
        return currentDBConnection.getPasswordFromTable(refColumn, reference);
    }

    @Override
    public ProductDisplayInfo[] getProductsDisplayInfo() {
        return currentDBConnection.getProductsDisplayInfo();
    }

    @Override
    public boolean insertNewPurchaseInfo(ShoppingWindowController.NewPurchaseInfo newPurchaseInfo) {
        return currentDBConnection.insertNewPurchaseInfo(newPurchaseInfo);
    }

    @Override
    public boolean updateRecord(TableNameEnums tableName, UsersTableColumnNameEnums refColumn, String reference, UsersTableColumnNameEnums columnToUpdate, String updatedContent) {
        return currentDBConnection.updateRecord(tableName,refColumn,reference,columnToUpdate,updatedContent);
    }

    @Override
    public UserPurchaseRecord[] getUserPurchaseRecordInfo (UUID currentUserUUID) {
        return currentDBConnection.getUserPurchaseRecordInfo (currentUserUUID);
    }


    private boolean checkConnection(){
        return currentDBConnection.getConnection() != null;
    }
}
