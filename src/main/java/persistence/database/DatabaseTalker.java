package persistence.database;

import model.UserPurchaseRecord;
import persistence.database.dbConnection.SQLErrorMessageEnums;
import persistence.database.dbConnection.dbTablesEnums.TableNameEnums;
import persistence.database.dbConnection.dbTablesEnums.UsersTableColumnNameEnums;

import java.sql.Timestamp;
import java.util.UUID;

public interface DatabaseTalker {
     SQLErrorMessageEnums addNewUserToDatabase(UUID newUserUUID, String newUsername, String newUserPassword, String newUserEmail, Timestamp newUserCreationTimeStamp);

    String getRecordFromTable(TableNameEnums tableName, UsersTableColumnNameEnums refColumn, String reference, UsersTableColumnNameEnums columnOfInterest);

    boolean updateRecord(TableNameEnums tableName, UsersTableColumnNameEnums refColumn, String reference,UsersTableColumnNameEnums columnToUpdate,String updatedContent);

    UserPurchaseRecord[] getUserPurchaseRecordInfo (UUID currentUserUUID);

}
