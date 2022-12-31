package controller;

import persistence.assets.AssetManager;
import persistence.assets.EyeIconType;
import persistence.assets.LogoType;
import persistence.database.DatabaseManager;
import persistence.database.dbConnection.SQLErrorMessageEnums;
import persistence.database.dbConnection.dbTablesEnums.TableNameEnums;
import persistence.database.dbConnection.dbTablesEnums.UsersTableColumnNameEnums;
import utils.EncryptionHandler;

import java.sql.Timestamp;
import java.util.Objects;
import java.util.UUID;

public class CreateAccountController {

    private final AssetManager assetManager;
    private final DatabaseManager databaseManager;
    private final EncryptionHandler encryptionHandler;


    public CreateAccountController() {

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


    public SQLErrorMessageEnums addNewUserToDatabase(String newUsername, String newUserPassword, String newUserEmail) {

        UUID newUserUUID = generateNewUUID();
        Timestamp newUserCreationTimeStamp = generateCurrentTimeStamp();

        SQLErrorMessageEnums sqlMessage = databaseManager.addNewUserToDatabase(newUserUUID,newUsername, encryptText(newUserPassword), newUserEmail,newUserCreationTimeStamp);

        while(sqlMessage == SQLErrorMessageEnums.UUID){
            newUserUUID = generateNewUUID();
            newUserCreationTimeStamp = generateCurrentTimeStamp();

            sqlMessage = databaseManager.addNewUserToDatabase(newUserUUID,newUsername, encryptText(newUserPassword), newUserEmail,newUserCreationTimeStamp);
        }

        return sqlMessage;
    }

    private UUID generateNewUUID(){
        return UUID.randomUUID();
    }

    private Timestamp generateCurrentTimeStamp() {
        return new Timestamp(System.currentTimeMillis());
    }

    private String encryptText(String textToEncrypt){
        return encryptionHandler.encrypt(textToEncrypt);
    }

    private String decryptText(String textToDecrypt){
        return encryptionHandler.decrypt(textToDecrypt);
    }

    public boolean isNameUnique(String newUsername){

        String returnedUsername = databaseManager.getRecordFromTable(TableNameEnums.USERS, UsersTableColumnNameEnums.USERNAME, newUsername, UsersTableColumnNameEnums.USERNAME);

        if (!Objects.equals(newUsername, returnedUsername)) {
            System.out.println("EVERYTHING WAS CORRECT. The new username is unique");
            return true;
        } else {
            System.out.println("SOMETHING WENT WRONG. The new username is not unique");
            return false;
        }
    }


    public boolean isEmailUnique(String newEmail){

        String newEmailEncrypted = encryptText(newEmail);
        String returnedEmail = databaseManager.getRecordFromTable(TableNameEnums.USERS, UsersTableColumnNameEnums.USER_EMAIL, newEmailEncrypted, UsersTableColumnNameEnums.USER_EMAIL);
        String returnedEmailDecrypted = decryptText(returnedEmail);

        if (!Objects.equals(newEmail, returnedEmailDecrypted)) {
            System.out.println("EVERYTHING WAS CORRECT. The new email is unique");
            return true;
        } else {
            System.out.println("SOMETHING WENT WRONG. The new email is not unique");
            return false;
        }
    }

}
