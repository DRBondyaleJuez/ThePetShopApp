package controller;

import persistence.assets.AssetManager;
import persistence.database.DatabaseManager;
import persistence.database.dbConnection.dbTablesEnums.TableNameEnums;
import persistence.database.dbConnection.dbTablesEnums.UsersTableColumnNameEnums;
import utils.EncryptionHandler;

import java.sql.Timestamp;
import java.util.UUID;

public class ProfilePageController {

    private final AssetManager assetManager;
    private final DatabaseManager databaseManager;
    private final EncryptionHandler encryptionHandler;
    private final UUID profileUUID;
    private String profileUsername;
    private String profileEmail;

    public ProfilePageController(UUID userUUUID) {

        assetManager = AssetManager.getInstance();
        databaseManager = DatabaseManager.getInstance();
        encryptionHandler = new EncryptionHandler();

        profileUUID = userUUUID;
        profileUsername = databaseManager.getRecordFromTable(TableNameEnums.USERS, UsersTableColumnNameEnums.USER_UUID,userUUUID.toString(),UsersTableColumnNameEnums.USERNAME);
        profileEmail = encryptionHandler.decrypt(databaseManager.getRecordFromTable(TableNameEnums.USERS, UsersTableColumnNameEnums.USER_UUID,userUUUID.toString(),UsersTableColumnNameEnums.USER_EMAIL));

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

    public String getLastLogin(){
        return databaseManager.getRecordFromTable(TableNameEnums.USERS,UsersTableColumnNameEnums.USER_UUID,profileUUID.toString(),UsersTableColumnNameEnums.USER_LAST_LOGIN);
    }

    public void updateLastLogin() {
        databaseManager.updateRecord(
                TableNameEnums.USERS,UsersTableColumnNameEnums.USER_UUID,profileUUID.toString(),UsersTableColumnNameEnums.USER_LAST_LOGIN,generateCurrentTimeStamp().toString()
        );
    }
    private Timestamp generateCurrentTimeStamp() {
        return new Timestamp(System.currentTimeMillis());
    }
}
