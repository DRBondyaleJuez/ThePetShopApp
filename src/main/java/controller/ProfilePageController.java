package controller;

import model.UserPurchaseRecord;
import persistence.assets.AssetManager;
import persistence.assets.LogoType;
import persistence.database.DatabaseManager;
import persistence.database.dbConnection.dbTablesEnums.TableNameEnums;
import persistence.database.dbConnection.dbTablesEnums.UsersTableColumnNameEnums;
import utils.EncryptionHandler;
import viewController.ProfilePageViewController;

import java.sql.Timestamp;
import java.util.UUID;

public class ProfilePageController {

    private final AssetManager assetManager;
    private final DatabaseManager databaseManager;
    private final EncryptionHandler encryptionHandler;
    private final UUID profileUUID;
    private String profileUsername;
    private String profileEmail;
    private UserPurchaseRecord[] userPurchaseInfo;

    private int numberOfEntriesPerPage;
    private int currentRecentPurchasePageNumber;

    private int numberOfPurchasesByUser;

    public ProfilePageController(UUID userUUUID) {

        assetManager = AssetManager.getInstance();
        databaseManager = DatabaseManager.getInstance();
        encryptionHandler = new EncryptionHandler();

        profileUUID = userUUUID;
        profileUsername = databaseManager.getRecordFromTable(TableNameEnums.USERS, UsersTableColumnNameEnums.USER_UUID,userUUUID.toString(),UsersTableColumnNameEnums.USERNAME);
        profileEmail = databaseManager.getRecordFromTable(TableNameEnums.USERS, UsersTableColumnNameEnums.USER_UUID,userUUUID.toString(),UsersTableColumnNameEnums.USER_EMAIL);

        getUserPurchaseRecordInfo();

        numberOfEntriesPerPage = 10;
        currentRecentPurchasePageNumber = 1;
        numberOfPurchasesByUser = userPurchaseInfo.length;
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

    public int getNumberOfEntriesPerPage() { return numberOfEntriesPerPage; }
    public int getNumberOfPurchasesByUser() { return numberOfPurchasesByUser; }

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

    public byte[] getLogoImageData(LogoType logoType) {
        return assetManager.getLogoImageData(logoType);
    }

    public byte[] getDecorationImageData() {
        return assetManager.getDecorationImageData();
    }

    public int getCurrentRecentPurchasePageNumber() {
        return currentRecentPurchasePageNumber;
    }

    public int changePageNumber(ProfilePageViewController.ArrowTypeClicked arrowClicked){

        switch(arrowClicked){
            case FIRST:
                currentRecentPurchasePageNumber = 1;
                break;
            case NEXT:
                if(currentRecentPurchasePageNumber < numberOfPurchasesByUser /numberOfEntriesPerPage) currentRecentPurchasePageNumber++;
                break;
            case PREVIOUS:
                if(currentRecentPurchasePageNumber > 1) currentRecentPurchasePageNumber--;
                break;
            default:
                currentRecentPurchasePageNumber = 1;
                break;
        }

        return currentRecentPurchasePageNumber;

    }

    public void getUserPurchaseRecordInfo () {
        userPurchaseInfo = databaseManager.getUserPurchaseRecordInfo(profileUUID);
    }

    public UserPurchaseRecord getSingleUserPurchaseRecord(int position){
        return userPurchaseInfo[position];
    }



}
