package controller;

import persistence.assets.AssetManager;
import persistence.database.DatabaseManager;
import persistence.assets.EyeIconType;
import persistence.assets.LogoType;
import utils.EncryptionHandler;

import java.util.Objects;

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

    public boolean verifyUsername(String enteredUsername){
        String returnedUsername = databaseManager.getUsernameIfInTable(enteredUsername);

        if (Objects.equals(enteredUsername, returnedUsername)) {
            System.out.println("EVERYTHING WAS CORRECT. The new username is in the table");
            return true;
        } else {
            System.out.println("SOMETHING WENT WRONG. The new username is not in the table");
            return false;
        }
    }

    public boolean verifyPassword(String username, String enteredPassword){
        if(enteredPassword.length() < 8){
            return false;
        }

        String returnedPasswordEncrypted = databaseManager.getCorrespondingEncryptedPassword(username);
        String returnedPasswordDecrypted = decryptText(returnedPasswordEncrypted);

        if (Objects.equals(enteredPassword, returnedPasswordDecrypted)) {
            System.out.println("EVERYTHING WAS CORRECT. The password ");
            return true;
        } else {
            System.out.println("SOMETHING WENT WRONG. The new email is not unique");
            return false;
        }
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
