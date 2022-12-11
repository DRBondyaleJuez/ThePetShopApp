package persistence.database;

import java.sql.Timestamp;
import java.util.UUID;

public interface DatabaseTalker {
     boolean addNewUserToDatabase(UUID newUserUUID, String newUsername, String newUserPassword, String newUserEmail, Timestamp newUserCreationTimeStamp);

    String getUsernameIfInTable(String newUsername);

    String getEmailIfInTable(String newEmail);

    String getCorrespondingEncryptedPassword(String username);
}
