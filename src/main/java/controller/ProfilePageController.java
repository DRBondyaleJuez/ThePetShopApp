package controller;

import persistence.assets.AssetManager;
import persistence.database.DatabaseManager;
import persistence.database.dbConnection.dbTablesEnums.TableNameEnums;
import persistence.database.dbConnection.dbTablesEnums.UsersTableColumnNameEnums;
import utils.EncryptionHandler;

import java.util.UUID;

public class ProfilePageController {

    private final AssetManager assetManager;
    private final DatabaseManager databaseManager;
    private final EncryptionHandler encryptionHandler;
    UUID profileUUID;
    String profileUsername;
    String profileEmail;

    public ProfilePageController(UUID userUUUID) {

        assetManager = AssetManager.getInstance();
        databaseManager = DatabaseManager.getInstance();
        encryptionHandler = new EncryptionHandler();

        profileUUID = userUUUID;
        profileUsername = databaseManager.getRecordFromTable(TableNameEnums.USERS, UsersTableColumnNameEnums.USER_UUID,userUUUID.toString(),UsersTableColumnNameEnums.USERNAME);
        profileEmail = databaseManager.getRecordFromTable(TableNameEnums.USERS, UsersTableColumnNameEnums.USER_UUID,userUUUID.toString(),UsersTableColumnNameEnums.USER_EMAIL);

    }

    public UUID getProfileUUID() {
        return profileUUID;
    }

    public String getProfileUsername() {
        return profileUsername;
    }

    public String getProfileEmail() {
        return profileEmail;
    }
}
