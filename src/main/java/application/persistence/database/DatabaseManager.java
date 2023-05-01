package application.persistence.database;

import application.model.NewPurchaseInfo;
import application.model.ProductDisplayInfo;
import application.model.UserPurchaseRecord;
import application.persistence.database.dbTablesEnums.TableNameEnums;
import application.persistence.database.dbTablesEnums.UsersTableColumnNameEnums;

import java.sql.Timestamp;
import java.util.UUID;

/**
 * Provides objects of a class connecting the controller with the class in charged of interacting with the database. It follows
 * a singleton architecture so there is only one possible instance of this class.
 */
public class DatabaseManager {

    private final DatabaseTalker databaseTalker;
    private static DatabaseManager instance;

    //This is the private constructor where the databaseTalker implementation is initialized for a particular table
    private DatabaseManager(){
        databaseTalker = new DBConnection("the_pet_shop");
        instance = null;
    }

    /**
     * Following the singleton architecture this is the method to use an instance of this class which will always be the instance
     * assigned to the instance attribute except the first time when it is instantiated.
     * @return DatabaseManager object assigned to the attribute instance
     */
    public static DatabaseManager getInstance() {
        if(instance == null) {
            instance = new DatabaseManager();
        }

        return instance;
    }


    /**
     * Method to connect the creation of a user entry request with the class in charged of interacting with the database.
     * @param newUserUUID UUID randomly created and assigned to the entered user
     * @param newUsername String the username which should be unique
     * @param newUserPassword String the password of this user
     * @param newUserEmail String the e-mail of user
     * @param newUserCreationTimeStamp Timestamp the date and hour at which this insertion was called
     * @return SQLErrorMessageEnums object it can be a NO_ERROR y there was no apparent issue or USERNAME, Email, if it is not unique
     */
    public SQLErrorMessageEnums addNewUserToDatabase(UUID newUserUUID, String newUsername, byte[] newUserPassword, String newUserEmail, Timestamp newUserCreationTimeStamp) {

        return databaseTalker.addNewUserToDatabase(newUserUUID,newUsername, newUserPassword, newUserEmail,newUserCreationTimeStamp);
    }

    /**
     * Method to connect the retrieval of String object request with the class in charged of interacting with the database.
     * @param tableName TableNameEnums enum to inform the table the String should be gotten from
     * @param refColumn UsersTableColumnNameEnums enum which corresponds to the name of the column that is going to be consulted to as reference to
     *                  find the desired corresponding entry
     * @param reference String the string to which the ref column is going to be compared in order to find the row of the desired string
     * @param columnOfInterest UsersTableColumnNameEnums enum corresponding to the name of the column that contains the desired information
     * @return String corresponds to the string in column of interest that has been requested following the arguments provided
     */
    public String getRecordFromTable(TableNameEnums tableName, UsersTableColumnNameEnums refColumn, String reference, UsersTableColumnNameEnums columnOfInterest) {
        return databaseTalker.getRecordFromTable(tableName, refColumn, reference,columnOfInterest);
    }

    /**
     * Method to connect the retrieval of the password in the form of a byte array request with the class in charged of interacting with the database.
     * @param refColumn UsersTableColumnNameEnums enum which corresponds to the name of the column that is going to be consulted to as reference to
     *                  find the desired corresponding entry
     * @param reference String the string to which the ref column is going to be compared in order to find the row of the desired string
     * @return byte[] of the password corresponding to the information provided. The byte array is the encrypted password.
     */
    public byte[] getPasswordFromTable(UsersTableColumnNameEnums refColumn,String reference) {
        return databaseTalker.getPasswordFromTable(refColumn,reference);
    }

    /**
     * Method to connect the update or slight change of the content of an entry request with the class in charged of interacting with the database.
     * @param tableName TableNameEnums enum to inform the table the String should be gotten from
     * @param refColumn UsersTableColumnNameEnums enum which corresponds to the name of the column that is going to be consulted to as reference to
     *                  find the desired corresponding entry
     * @param reference String the string to which the ref column is going to be compared in order to find the row of the desired string
     * @param columnToUpdate UsersTableColumnNameEnums enum corresponding to the name of the column that contains the data that requires update
     * @param updatedContent String containing the updated information to set in the corresponding entry based on the information provided
     * @return boolean true if the update is successful false if the entry is not updated
     */
    public boolean updateRecord(TableNameEnums tableName, UsersTableColumnNameEnums refColumn, String reference, UsersTableColumnNameEnums columnToUpdate, String updatedContent) {
        return databaseTalker.updateRecord(tableName,refColumn,reference,columnToUpdate,updatedContent);
    }


    /**
     * Method to connect the retrieval of the user's purchase record based on the user's id request with the class in charged of interacting with the database.
     * @param currentUserUUID UUID representing the user_id in the database.
     * @return UserPurchaseRecord[] an array of the class used to encapsulate this particular group of information retrieved from the database
     */
    public UserPurchaseRecord[] getUserPurchaseRecordInfo (UUID currentUserUUID) {
        return databaseTalker.getUserPurchaseRecordInfo (currentUserUUID);
    }

    /**
     * Method to connect the retrieval of all the product information to display request with the class in charged of interacting with the database.
     * @return ProductDisplayInfo[] an array containing object that encapsulate each piece of information of the corresponding product
     */
    public ProductDisplayInfo[] getProductsDisplayInfo() {
        return databaseTalker.getProductsDisplayInfo();
    }

    /**
     * Method to connect the creation of a purchase entry request with the class in charged of interacting with the database.
     * @param newPurchaseInfo NewPurchaseInfo object which encapsulates all the information needed for the insertion.
     *                        This class is nested in the ShoppingWindowController class
     * @return boolean true to confirm the creation of the entry or false if the entry was not achieved
     */
    public boolean insertNewPurchaseInfo(NewPurchaseInfo newPurchaseInfo) {
        return databaseTalker.insertNewPurchaseInfo(newPurchaseInfo);
    }
}
