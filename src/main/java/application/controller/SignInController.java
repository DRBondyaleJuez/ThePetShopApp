package application.controller;

import application.persistence.assets.AssetManager;
import application.persistence.database.DatabaseManager;
import application.persistence.assets.EyeIconType;
import application.persistence.assets.LogoType;
import application.persistence.database.dbTablesEnums.TableNameEnums;
import application.persistence.database.dbTablesEnums.UsersTableColumnNameEnums;
import application.utils.EncryptionHandler;

import java.util.Objects;
import java.util.UUID;

/**
 * Provides an object that acts as an intermediary between the SignInView and the persistence. It performs the
 * actions necessary to fulfill the events of view interactions
 */
public class SignInController {

    //PERSISTENCE perhaps assetManager and databaseManager
    private final AssetManager assetManager;
    private final DatabaseManager databaseManager;
    private final EncryptionHandler encryptionHandler;

    /**
     * This is the constructor. Here  an instance of the DatabaseManager class and of the AssetManager are assigned to
     * the databaseManager and assetManager attribute respectively. As well as the encryptionHandler to encrypt or decrypt passwords
     */
    public SignInController() {

        assetManager = AssetManager.getInstance();
        databaseManager = DatabaseManager.getInstance();
        encryptionHandler = new EncryptionHandler();

    }

    /**
     * Retrieve particular logo image from asset folder in resources using the assetManager
     * @param logoType LogoType enum of a particular logo type
     * @return byte array of the logo image requested
     */
    public byte[] getLogoImageData(LogoType logoType) {
        return assetManager.getLogoImageData(logoType);
    }

    /**
     * Retrieve particular eyeIcon image from asset folder in resources using the assetManager
     * @param eyeIconType EyeIconType enum of a particular eye icon type
     * @return byte array of the eye icon image requested
     */
    public byte[] getEyeIconImageData(EyeIconType eyeIconType) {
        return assetManager.getEyeIconImageData(eyeIconType);
    }

    /**
     * Check the username entered is present in the database
     * @param enteredUserRef String the entered by the user during log in
     * @return boolean true if the username is present in the database or false if it is not
     */
    public boolean verifyUsername(String enteredUserRef){
        //Verifying the first input (userReference) either email or username
        String returnedUserRef = "";
        if(Objects.equals(enteredUserRef, returnedUserRef)) return false; //check for empty form/textField
        if(enteredUserRef.contains("@")){
            returnedUserRef = databaseManager.getRecordFromTable(TableNameEnums.USERS, UsersTableColumnNameEnums.USER_EMAIL, enteredUserRef, UsersTableColumnNameEnums.USER_EMAIL);
        } else {
            returnedUserRef = databaseManager.getRecordFromTable(TableNameEnums.USERS, UsersTableColumnNameEnums.USERNAME, enteredUserRef, UsersTableColumnNameEnums.USERNAME);
        }
        if (Objects.equals(enteredUserRef, returnedUserRef)) {
            System.out.println("EVERYTHING WAS CORRECT. The new username is in the table");
            return true;
        } else {
            System.out.println("SOMETHING WENT WRONG. The new username is not in the table");
            return false;
        }
    }

    /**
     * Check if the password entered corresponds to the password stored in the database for the username
     * @param enteredUserRef String username entered during log in
     * @param enteredPassword String password entered during log in
     * @return boolean confirming if the password and username are correct and correspond to each other. True if both
     * everything is correct false if either don't match or are not present in the database
     */
    public boolean verifyPassword(String enteredUserRef, String enteredPassword){
        if(enteredPassword.length() < 8){
            return false;
        }

        //Retrieving password using the first input (userReference) either email or username
        byte[] returnedPasswordEncrypted = null;
        if(enteredUserRef.contains("@")){
            returnedPasswordEncrypted = databaseManager.getPasswordFromTable(UsersTableColumnNameEnums.USER_EMAIL, enteredUserRef);
        } else {
            returnedPasswordEncrypted = databaseManager.getPasswordFromTable(UsersTableColumnNameEnums.USERNAME, enteredUserRef);
        }

        String returnedPasswordDecrypted = encryptionHandler.decrypt(returnedPasswordEncrypted);
        System.out.println("This is the decrypted password: " + returnedPasswordDecrypted);

        if (Objects.equals(enteredPassword, returnedPasswordDecrypted)) {
            System.out.println("EVERYTHING WAS CORRECT. The password is valid");
            return true;
        } else {
            System.out.println("SOMETHING WENT WRONG. The password is not valid");
            return false;
        }
    }


    /**
     * Retrieve the user id from the database based on the provided username
     * @param username String username provided
     * @return UUID corresponding to the username provided if present in the database and null if username not found
     */
    public UUID fetchCorrespondingUUID(String username){
        String stringUUID = databaseManager.getRecordFromTable(TableNameEnums.USERS,UsersTableColumnNameEnums.USERNAME, username, UsersTableColumnNameEnums.USER_UUID);
        return UUID.fromString(stringUUID);
    }
}
