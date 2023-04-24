package application.persistence.database;

import application.controller.ShoppingWindowController;
import application.model.NewPurchaseInfo;
import application.model.ProductDisplayInfo;
import application.model.UserPurchaseRecord;
import application.persistence.database.dbTablesEnums.TableNameEnums;
import application.persistence.database.dbTablesEnums.UsersTableColumnNameEnums;

import java.sql.Timestamp;
import java.util.UUID;

/**
 * Provides the abstract methods necessary for a proper use by calls from the DatabaseManager Class basically for performing
 * the appropriate operations in the database. The class which implements this interface will need to implement the methods.
 */
public interface DatabaseTalker {

    /**
     * Build and submit the SQL statement to command the creation of a user entry in the database based on the provided info
     * @param newUserUUID UUID randomly created and assigned to the entered user
     * @param newUsername String the username which should be unique
     * @param newUserPassword String the password of this user
     * @param newUserEmail String the e-mail of user
     * @param newUserCreationTimeStamp Timestamp the date and hour at which this insertion was called
     * @return SQLErrorMessageEnums object it can be a NO_ERROR y there was no apparent issue or USERNAME, Email, if it is not unique
     */
    SQLErrorMessageEnums addNewUserToDatabase(UUID newUserUUID, String newUsername, byte[] newUserPassword, String newUserEmail, Timestamp newUserCreationTimeStamp);

    /**
     * Build and submit the SQL statement to command the retrieval of a particular record from the database based on the provided info
     * @param tableName TableNameEnums enum to inform the table the String should be gotten from
     * @param refColumn UsersTableColumnNameEnums enum which corresponds to the name of the column that is going to be consulted to as reference to
     *                  find the desired corresponding entry
     * @param reference String the string to which the ref column is going to be compared in order to find the row of the desired string
     * @param columnOfInterest UsersTableColumnNameEnums enum corresponding to the name of the column that contains the desired information
     * @return String corresponds to the string in column of interest that has been requested following the arguments provided
     */
    String getRecordFromTable(TableNameEnums tableName, UsersTableColumnNameEnums refColumn, String reference, UsersTableColumnNameEnums columnOfInterest);

    /**
     * Build and submit the SQL statement to command the update of a particular record in the database based on the provided info
     * @param tableName TableNameEnums enum to inform the table the String should be gotten from
     * @param refColumn UsersTableColumnNameEnums enum which corresponds to the name of the column that is going to be consulted to as reference to
     *                  find the desired corresponding entry
     * @param reference String the string to which the ref column is going to be compared in order to find the row of the desired string
     * @param columnToUpdate UsersTableColumnNameEnums enum corresponding to the name of the column that contains the data that requires update
     * @param updatedContent String containing the updated information to set in the corresponding entry based on the information provided
     * @return boolean true if the update is successful false if the entry is not updated
     */
    boolean updateRecord(TableNameEnums tableName, UsersTableColumnNameEnums refColumn, String reference,UsersTableColumnNameEnums columnToUpdate,String updatedContent);

    /**
     * Build and submit the SQL statement to command the retrieval of a user's purchase record info from the database based on the provided info
     * @param currentUserUUID UUID representing the user_id in the database.
     * @return UserPurchaseRecord[] an array of the class used to encapsulate this particular group of information retrieved from the database
     */
    UserPurchaseRecord[] getUserPurchaseRecordInfo (UUID currentUserUUID);

    /**
     * Build and submit the SQL statement to command the retrieval of the password of a particular user from the database based on the provided info
     * @param refColumn UsersTableColumnNameEnums enum which corresponds to the name of the column that is going to be consulted to as reference to
     *                  find the desired corresponding entry
     * @param reference String the string to which the ref column is going to be compared in order to find the row of the desired string
     * @return byte[] of the password corresponding to the information provided. The byte array is the encrypted password.
     */
    byte[] getPasswordFromTable(UsersTableColumnNameEnums refColumn, String reference);

    /**
     * Build and submit the SQL statement to command the retrieval of all info of all products from the database based on the provided info
     * @return ProductDisplayInfo[] an array containing object that encapsulate each piece of information of the corresponding product
     */
    ProductDisplayInfo[] getProductsDisplayInfo();

    /**
     * Build and submit the SQL statement to command the creation of a purchase entry in the database based on the provided info
     * @param newPurchaseInfo NewPurchaseInfo object which encapsulates all the information needed for the insertion.
     *                        This class is nested in the ShoppingWindowController class
     * @return boolean true to confirm the creation of the entry or false if the entry was not achieved
     */
    boolean insertNewPurchaseInfo(NewPurchaseInfo newPurchaseInfo);
}
