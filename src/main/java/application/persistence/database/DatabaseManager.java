package application.persistence.database;

import application.controller.ShoppingWindowController;
import application.model.ProductDisplayInfo;
import application.model.UserPurchaseRecord;
import application.persistence.database.dbConnection.SQLErrorMessageEnums;
import application.persistence.database.dbConnection.dbTablesEnums.TableNameEnums;
import application.persistence.database.dbConnection.dbTablesEnums.UsersTableColumnNameEnums;

import java.sql.Timestamp;
import java.util.UUID;

public class DatabaseManager {

    private final DatabaseTalker databaseTalker;
    private static DatabaseManager instance;

    private DatabaseManager(){
        databaseTalker = new FileSystemDatabaseTalker();
        instance = null;
    }

    public static DatabaseManager getInstance() {
        if(instance == null) {
            instance = new DatabaseManager();
        }

        return instance;
    }

    public SQLErrorMessageEnums addNewUserToDatabase(UUID newUserUUID, String newUsername, byte[] newUserPassword, String newUserEmail, Timestamp newUserCreationTimeStamp) {

        return databaseTalker.addNewUserToDatabase(newUserUUID,newUsername, newUserPassword, newUserEmail,newUserCreationTimeStamp);
    }

    public String getRecordFromTable(TableNameEnums tableName, UsersTableColumnNameEnums refColumn, String reference, UsersTableColumnNameEnums columnOfInterest) {
        return databaseTalker.getRecordFromTable(tableName, refColumn, reference,columnOfInterest);
    }

    public byte[] getPasswordFromTable(UsersTableColumnNameEnums refColumn,String reference) {
        return databaseTalker.getPasswordFromTable(refColumn,reference);
    }

    public boolean updateRecord(TableNameEnums tableName, UsersTableColumnNameEnums refColumn, String reference, UsersTableColumnNameEnums columnToUpdate, String updatedContent) {
        return databaseTalker.updateRecord(tableName,refColumn,reference,columnToUpdate,updatedContent);
    }


    public UserPurchaseRecord[] getUserPurchaseRecordInfo (UUID currentUserUUID) {
        return databaseTalker.getUserPurchaseRecordInfo (currentUserUUID);
    }

    public ProductDisplayInfo[] getProductsDisplayInfo() {
        return databaseTalker.getProductsDisplayInfo();
    }

    public boolean insertNewPurchaseInfo(ShoppingWindowController.NewPurchaseInfo newPurchaseInfo) {
        return databaseTalker.insertNewPurchaseInfo(newPurchaseInfo);
    }
}
