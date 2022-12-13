package controller;

import persistence.assets.AssetManager;
import persistence.database.DatabaseManager;
import persistence.assets.EyeIconType;
import persistence.assets.LogoType;
import persistence.database.dbConnection.dbTablesEnums.TableNameEnums;
import persistence.database.dbConnection.dbTablesEnums.UsersTableColumnNameEnums;
import utils.EncryptionHandler;

import java.util.Objects;
import java.util.UUID;

public class SignInController {

    //PERSISTENCE perhaps assetManager and databaseManager
    private final AssetManager assetManager;
    private final DatabaseManager databaseManager;
    private final EncryptionHandler encryptionHandler;


    public SignInController() {

        assetManager = AssetManager.getInstance();
        databaseManager = DatabaseManager.getInstance();
        encryptionHandler = new EncryptionHandler();

    }

    public byte[] getLogoImageData(LogoType logoType) {
        return assetManager.getLogoImageData(logoType);
    }

    public byte[] getEyeIconImageData(EyeIconType eyeIconType) {
        return assetManager.getEyeIconImageData(eyeIconType);
    }

    public boolean verifyUsername(String enteredUserRef){
        //Verifying the first input (userReference) either email or username
        String returnedUserRef = "";
        if(enteredUserRef.contains("@")){
            String encryptUserRef = encryptText(enteredUserRef);
            returnedUserRef = databaseManager.getRecordFromTable(TableNameEnums.USERS, UsersTableColumnNameEnums.USER_EMAIL, encryptUserRef, UsersTableColumnNameEnums.USER_EMAIL);
            returnedUserRef = decryptText(returnedUserRef);
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

    public boolean verifyPassword(String enteredUserRef, String enteredPassword){
        if(enteredPassword.length() < 8){
            return false;
        }

        //Retrieving password using the first input (userReference) either email or username
        String returnedPasswordEncrypted = "";
        if(enteredUserRef.contains("@")){
            String encryptUserRef = encryptText(enteredUserRef);
            returnedPasswordEncrypted = databaseManager.getRecordFromTable(TableNameEnums.USERS,UsersTableColumnNameEnums.USER_EMAIL, encryptUserRef, UsersTableColumnNameEnums.USER_PASSWORD);
        } else {
            returnedPasswordEncrypted = databaseManager.getRecordFromTable(TableNameEnums.USERS,UsersTableColumnNameEnums.USERNAME, enteredUserRef, UsersTableColumnNameEnums.USER_PASSWORD);
        }

        String returnedPasswordDecrypted = decryptText(returnedPasswordEncrypted);

        if (Objects.equals(enteredPassword, returnedPasswordDecrypted)) {
            System.out.println("EVERYTHING WAS CORRECT. The password is valid");
            return true;
        } else {
            System.out.println("SOMETHING WENT WRONG. The password is not valid");
            return false;
        }
    }

    public UUID fetchCorrespondingUUID(String username){
        String stringUUID = databaseManager.getRecordFromTable(TableNameEnums.USERS,UsersTableColumnNameEnums.USERNAME, username, UsersTableColumnNameEnums.USER_UUID);
        return UUID.fromString(stringUUID);
    }

    private String encryptText(String textToEncrypt){
        return encryptionHandler.encrypt(textToEncrypt);
    }

    private String decryptText(String textToDecrypt){
        return encryptionHandler.decrypt(textToDecrypt);
    }

    public void addNewUserToDatabase(String newUsername, String newUserPassword, String newUserEmail) {
    }
}
