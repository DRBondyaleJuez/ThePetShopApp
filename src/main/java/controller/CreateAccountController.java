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
        byte [] encryptedPasswordByteArray = encryptText(newUserPassword);

        SQLErrorMessageEnums sqlMessage = databaseManager.addNewUserToDatabase(newUserUUID,newUsername, encryptedPasswordByteArray, newUserEmail,newUserCreationTimeStamp);

        while(sqlMessage == SQLErrorMessageEnums.UUID){
            newUserUUID = generateNewUUID();
            newUserCreationTimeStamp = generateCurrentTimeStamp();

            sqlMessage = databaseManager.addNewUserToDatabase(newUserUUID,newUsername, encryptedPasswordByteArray, newUserEmail,newUserCreationTimeStamp);
        }

        return sqlMessage;
    }

    private UUID generateNewUUID(){
        return UUID.randomUUID();
    }

    private Timestamp generateCurrentTimeStamp() {
        return new Timestamp(System.currentTimeMillis());
    }

    private byte[] encryptText(String textToEncrypt){
        return encryptionHandler.encrypt(textToEncrypt);
    }

}
