package controller;

import persistence.assets.AssetManager;
import persistence.database.DatabaseManager;

public class CreateAccountController {

    //PERSISTENCE perhaps assetManager and databaseManager
    private final AssetManager assetManager;
    private final DatabaseManager databaseManager;


    public CreateAccountController() {

        assetManager = AssetManager.getInstance();
        databaseManager = DatabaseManager.getInstance();

    }

}
