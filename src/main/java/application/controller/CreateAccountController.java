package application.controller;

import application.persistence.assets.AssetManager;
import application.persistence.assets.EyeIconType;
import application.persistence.assets.LogoType;
import application.persistence.database.DatabaseManager;
import application.persistence.database.SQLErrorMessageEnums;
import application.utils.EncryptionHandler;

import java.sql.Timestamp;
import java.util.UUID;

/**
 * Provides an object that acts as an intermediary between the CreateAccountView and the persistence. It performs the
 * actions necessary to fulfill the events of view interactions
 */
public class CreateAccountController {

    private final AssetManager assetManager;
    private final DatabaseManager databaseManager;
    private final EncryptionHandler encryptionHandler;

    /**
     * This is the constructor. Here  an instance of the DatabaseManager class and of the AssetManager are assigned to
     * the databaseManager and assetManager attribute respectively. As well as the encryptionHandler to encrypt or decrypt passwords
     */
    public CreateAccountController() {

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
     * Insert new user based on the data provided in the database if it meets the database's criteria. It requires
     * creating some of the parameters of the user such as the userid creating a new random UUID
     * @param newUsername String username provided by the user (should be unique)
     * @param newUserPassword String password provided by the user
     * @param newUserEmail String the email provided by the user (should be unique)
     * @return SQLErrorMessageEnums enum informing of the state of the process after attempting to insert the user this could be
     * any of the enums which contemplate many scenarios depending on if there was NO_ERROR or if any part of the process failed
     */
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
