package application.controller;

import application.model.UserPurchaseRecord;
import application.persistence.assets.AssetManager;
import application.persistence.assets.LogoType;
import application.persistence.database.DatabaseManager;
import application.persistence.database.dbTablesEnums.TableNameEnums;
import application.persistence.database.dbTablesEnums.UsersTableColumnNameEnums;
import application.utils.EncryptionHandler;
import application.viewController.ProfilePageViewController;

import java.sql.Timestamp;
import java.util.UUID;

/**
 * Provides an object that acts as an intermediary between the ProfilePageView and the persistence. It performs the
 * actions necessary to fulfill the events of view interactions
 */
public class ProfilePageController {

    private final AssetManager assetManager;
    private final DatabaseManager databaseManager;
    private final UUID profileUUID;
    private String profileUsername;
    private String profileEmail;
    private UserPurchaseRecord[] userPurchaseInfo;

    private int numberOfEntriesPerPage;
    private int currentRecentPurchasePageNumber;

    private int numberOfPurchasesByUser;

    /**
     * This is the constructor. Here  an instance of the DatabaseManager class and of the AssetManager are assigned to
     * the databaseManager and assetManager attribute respectively. As well as a series of parameters of the purchases and user display
     * and the retrieval of all necessary purchases and user info from the database.
     * @param userUUID UUID corresponding to the user that called for the productsPageView
     */
    public ProfilePageController(UUID userUUID) {

        assetManager = AssetManager.getInstance();
        databaseManager = DatabaseManager.getInstance();

        profileUUID = userUUID;
        profileUsername = databaseManager.getRecordFromTable(TableNameEnums.USERS, UsersTableColumnNameEnums.USER_UUID,userUUID.toString(),UsersTableColumnNameEnums.USERNAME);
        profileEmail = databaseManager.getRecordFromTable(TableNameEnums.USERS, UsersTableColumnNameEnums.USER_UUID,userUUID.toString(),UsersTableColumnNameEnums.USER_EMAIL);

        userPurchaseInfo = getUserPurchaseRecordInfo();

        numberOfEntriesPerPage = 10;
        currentRecentPurchasePageNumber = 1;
        if(userPurchaseInfo != null) {
            numberOfPurchasesByUser = userPurchaseInfo.length;
        } else {
            numberOfPurchasesByUser = 0;
        }
    }

    //GETTERS
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
    public int getCurrentRecentPurchasePageNumber() { return currentRecentPurchasePageNumber; }
    public UserPurchaseRecord getSingleUserPurchaseRecord(int position){
        return userPurchaseInfo[position];
    }
    /**
     * Retrieve from the database the last login information of the user of the profile
     * @return String containing the date and time of the last log in
     */
    public String getLastLogin(){
        return databaseManager.getRecordFromTable(TableNameEnums.USERS,UsersTableColumnNameEnums.USER_UUID,profileUUID.toString(),UsersTableColumnNameEnums.USER_LAST_LOGIN);
    }

    /**
     * Update the last login information of the user of the profile. Change it to the current last login
     */
    public void updateLastLogin() {
        databaseManager.updateRecord(
                TableNameEnums.USERS,UsersTableColumnNameEnums.USER_UUID,profileUUID.toString(),UsersTableColumnNameEnums.USER_LAST_LOGIN,generateCurrentTimeStamp().toString()
        );
    }

    private Timestamp generateCurrentTimeStamp() {
        return new Timestamp(System.currentTimeMillis());
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
     * Retrieve the decoration image from asset folder in resources using the assetManager
     * @return byte array of the decoration image requested
     */
    public byte[] getDecorationImageData() {
        return assetManager.getDecorationImageData();
    }

    /**
     * Update base on the type of arrow clicked the current page the ProfilePageView purchase list is on to display the correct purchase records following
     * a descending order based on the purchase timestamp
     * @param arrowClicked ArrowTypeClicked enum nested in the ProfilePageViewController which informs the change the page number
     *                     is going to undergo
     * @return int the new updated page number based on the arrow clicked
     */
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



    private UserPurchaseRecord[] getUserPurchaseRecordInfo () {
        return databaseManager.getUserPurchaseRecordInfo(profileUUID);
    }
}
