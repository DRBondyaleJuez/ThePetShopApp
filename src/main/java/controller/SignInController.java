package controller;

import persistence.assets.AssetManager;
import persistence.database.DatabaseManager;
import persistence.assets.EyeIconType;
import persistence.assets.LogoType;

public class SignInController {

    //PERSISTENCE perhaps assetManager and databaseManager
    private final AssetManager assetManager;
    private final DatabaseManager databaseManager;


    public SignInController() {

        assetManager = AssetManager.getInstance();
        databaseManager = DatabaseManager.getInstance();

    }

    public byte[] getLogoImageData(LogoType logoType) {
        return assetManager.getLogoImageData(logoType);
    }

    public byte[] getEyeIconImageData(EyeIconType eyeIconType) {
        return assetManager.getEyeIconImageData(eyeIconType);
    }


}
