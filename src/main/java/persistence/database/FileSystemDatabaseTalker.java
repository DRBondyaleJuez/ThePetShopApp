package persistence.database;

import model.UserPurchaseRecord;
import persistence.database.dbConnection.DBConnection;
import persistence.database.dbConnection.SQLErrorMessageEnums;
import persistence.database.dbConnection.dbTablesEnums.TableNameEnums;
import persistence.database.dbConnection.dbTablesEnums.UsersTableColumnNameEnums;

import java.sql.Timestamp;
import java.util.UUID;

public class FileSystemDatabaseTalker implements DatabaseTalker{

    private DBConnection currentDBConnection;

    public FileSystemDatabaseTalker() {
        currentDBConnection = new DBConnection("the_pet_shop");
    }

    @Override
    public SQLErrorMessageEnums addNewUserToDatabase(UUID newUserUUID, String newUsername, String newUserPassword, String newUserEmail, Timestamp newUserCreationTimeStamp) {
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
