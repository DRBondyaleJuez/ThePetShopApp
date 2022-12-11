package persistence.database;

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

    public boolean addNewUserToDatabase(UUID newUserUUID,String newUsername, String newUserPassword, String newUserEmail, Timestamp newUserCreationTimeStamp) {

        return databaseTalker.addNewUserToDatabase(newUserUUID,newUsername, newUserPassword, newUserEmail,newUserCreationTimeStamp);
    }


    public String getUsernameIfInTable (String newUsername){
        return databaseTalker.getUsernameIfInTable(newUsername);
    }

    public String getEmailIfInTable(String newEmail) {
        return databaseTalker.getEmailIfInTable(newEmail);
    }
    
}
