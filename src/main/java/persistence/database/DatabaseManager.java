package persistence.database;

import model.UserPurchaseRecord;
import persistence.database.dbConnection.SQLErrorMessageEnums;
import persistence.database.dbConnection.dbTablesEnums.TableNameEnums;
import persistence.database.dbConnection.dbTablesEnums.UsersTableColumnNameEnums;

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

    /*
    public SQLErrorMessageEnums addNewUserToDatabase(UUID newUserUUID, String newUsername, String newUserPassword, String newUserEmail, Timestamp newUserCreationTimeStamp) {

        return databaseTalker.addNewUserToDatabase(newUserUUID,newUsername, newUserPassword, newUserEmail,newUserCreationTimeStamp);
    }
     */

    public SQLErrorMessageEnums addNewUserToDatabase(UUID newUserUUID, String newUsername, byte[] newUserPassword, String newUserEmail, Timestamp newUserCreationTimeStamp) {

        return databaseTalker.addNewUserToDatabase(newUserUUID,newUsername, newUserPassword, newUserEmail,newUserCreationTimeStamp);
    }

    public String getRecordFromTable(TableNameEnums tableName, UsersTableColumnNameEnums refColumn, String reference, UsersTableColumnNameEnums columnOfInterest) {
        return databaseTalker.getRecordFromTable(tableName, refColumn, reference,columnOfInterest);
    }

    public boolean updateRecord(TableNameEnums tableName, UsersTableColumnNameEnums refColumn, String reference, UsersTableColumnNameEnums columnToUpdate, String updatedContent) {
        return databaseTalker.updateRecord(tableName,refColumn,reference,columnToUpdate,updatedContent);
    }


    public UserPurchaseRecord[] getUserPurchaseRecordInfo (UUID currentUserUUID) {
        return databaseTalker.getUserPurchaseRecordInfo (currentUserUUID);
    }

}
