package persistence.database;

import persistence.database.dbConnection.DBConnection;
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
    public boolean addNewUserToDatabase(UUID newUserUUID, String newUsername, String newUserPassword, String newUserEmail, Timestamp newUserCreationTimeStamp) {
        if(!checkConnection()){
            System.out.println("Unable to connect to the database");
            return false;
        }

        return currentDBConnection.addNewUserToDatabase(newUserUUID,newUsername,newUserPassword,newUserEmail,newUserCreationTimeStamp);

    }


    @Override
    public String getRecordFromTable(TableNameEnums tableName, UsersTableColumnNameEnums refColumn, String reference, UsersTableColumnNameEnums columnOfInterest) {
        return currentDBConnection.getRecordFromTable(tableName, refColumn, reference, columnOfInterest);
    }

    private boolean checkConnection(){
        return currentDBConnection.getConnection() != null;
    }
}
