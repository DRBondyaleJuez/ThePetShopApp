package application.persistence.database;

import application.controller.ShoppingWindowController;
import application.model.ProductDisplayInfo;
import application.model.UserPurchaseRecord;
import application.persistence.database.dbConnection.SQLErrorMessageEnums;
import application.persistence.database.dbConnection.dbTablesEnums.TableNameEnums;
import application.persistence.database.dbConnection.dbTablesEnums.UsersTableColumnNameEnums;

import java.sql.Timestamp;
import java.util.UUID;

public interface DatabaseTalker {
    SQLErrorMessageEnums addNewUserToDatabase(UUID newUserUUID, String newUsername, byte[] newUserPassword, String newUserEmail, Timestamp newUserCreationTimeStamp);

    String getRecordFromTable(TableNameEnums tableName, UsersTableColumnNameEnums refColumn, String reference, UsersTableColumnNameEnums columnOfInterest);

    boolean updateRecord(TableNameEnums tableName, UsersTableColumnNameEnums refColumn, String reference,UsersTableColumnNameEnums columnToUpdate,String updatedContent);

    UserPurchaseRecord[] getUserPurchaseRecordInfo (UUID currentUserUUID);

    byte[] getPasswordFromTable(UsersTableColumnNameEnums refColumn, String reference);

    ProductDisplayInfo[] getProductsDisplayInfo();

    boolean insertNewPurchaseInfo(ShoppingWindowController.NewPurchaseInfo newPurchaseInfo);
}
