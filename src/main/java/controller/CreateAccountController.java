package controller;

import persistence.assets.AssetManager;
import persistence.assets.EyeIconType;
import persistence.assets.LogoType;
import persistence.database.DatabaseManager;
import utils.EncryptionHandler;

import java.sql.Timestamp;
import java.util.UUID;

public class CreateAccountController {

    //PERSISTENCE perhaps assetManager and databaseManager
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


    public boolean addNewUserToDatabase(String newUsername, String newUserPassword, String newUserEmail) {

        UUID newUserUUID = generateNewUUID();
        Timestamp newUserCreationTimeStamp = generateCurrentTimeStamp();

        return databaseManager.addNewUserToDatabase(newUserUUID,newUsername, encryptText(newUserPassword), encryptText(newUserEmail),newUserCreationTimeStamp);
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
        return encryptionHandler.encrypt(textToDecrypt);
    }

    private boolean isNameUnique(String newUsername){
        return false;
    }

}
