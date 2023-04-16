package application.controller;

import application.persistence.assets.AssetManager;
import application.persistence.database.DatabaseManager;
import application.persistence.assets.EyeIconType;
import application.persistence.assets.LogoType;
import application.persistence.database.dbConnection.dbTablesEnums.TableNameEnums;
import application.persistence.database.dbConnection.dbTablesEnums.UsersTableColumnNameEnums;
import application.utils.EncryptionHandler;

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

    public UUID fetchCorrespondingUUID(String username){
        String stringUUID = databaseManager.getRecordFromTable(TableNameEnums.USERS,UsersTableColumnNameEnums.USERNAME, username, UsersTableColumnNameEnums.USER_UUID);
        return UUID.fromString(stringUUID);
    }

    private String sqlIntArrayToStringConversion(String sqlIntArray){
        String newSqlIntArray = sqlIntArray.replace("{","");
        newSqlIntArray = newSqlIntArray.replace("}","");

        String [] separateBySplit = newSqlIntArray.split(",");
        byte[] convertedToInt = new byte[separateBySplit.length];
        for (int i = 0; i < separateBySplit.length; i++) {
            convertedToInt[i] = (byte) Integer.parseInt(separateBySplit[i]);
        }

        return new String(convertedToInt);

    }

}
