package application.controller;

import application.model.ProductDisplayInfo;
import application.persistence.assets.AssetManager;
import application.persistence.assets.LogoType;
import application.persistence.database.DatabaseManager;
import application.viewController.ProfilePageViewController;

import java.util.Arrays;
import java.util.UUID;

/**
 * Provides an object that acts as an intermediary between the ProductPageView and the persistence. It performs the
 * actions necessary to fulfill the requests of view interactions
 */
public class ProductsPageController {

    private final UUID currentUserUUID;
    private int currentProductsPageNumber;
    private final int numberProductsPerPage;

    private final int numberProductsPerRow;

    private final AssetManager assetManager;

    private final DatabaseManager databaseManager;

    private final ProductDisplayInfo[] allProductsInfo;

    /**
     * This is the constructor. Here  an instance of the DatabaseManager class and of the AssetManager are assigned to
     * the databaseManager and assetManager attribute respectively. As well as a series of parameters of the products display
     * and the retrieval of all product display info from the database.
     * @param currentUserUUID UUID corresponding to the user that called for the productsPageView
     */
    public ProductsPageController(UUID currentUserUUID) {

        assetManager = AssetManager.getInstance();
        databaseManager = DatabaseManager.getInstance();

        this.currentUserUUID = currentUserUUID;
        currentProductsPageNumber = 1;
        numberProductsPerPage = 6;
        numberProductsPerRow = 3;
        allProductsInfo = getAllProductsFromDatabase();
    }

    public UUID getCurrentUserUUID() {
        return currentUserUUID;
    }

    public int getNumberProductsPerPage() {
        return numberProductsPerPage;
    }

    public int getNumberProductsPerRow() {
        return numberProductsPerRow;
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
     * Selective getter that returns from all the allProductsInfo attribute the productDisplayInfo which corresponds to
     * the current page the user is at
     * @return Array of ProductDisplayInfo containing the ProductDisplayinfor objects of the products needed for the current page
     */
    public ProductDisplayInfo[] getCurrentProductsDisplayInfo(){

        int startingProductInfoIndex = (currentProductsPageNumber-1)*numberProductsPerPage;
        int finalProductInfoIndex = startingProductInfoIndex + numberProductsPerPage;

        if(allProductsInfo == null || allProductsInfo.length == 0) return null;
        if(finalProductInfoIndex > allProductsInfo.length) finalProductInfoIndex = allProductsInfo.length;

        return Arrays.copyOfRange(allProductsInfo, startingProductInfoIndex, finalProductInfoIndex);
    }

    /**
     * Update base on the type of arrow clicked the current page the ProductPageView product display info grid is on to
     * display the correct purchase records following a ascending order based on the product name
     * @param arrowClicked ArrowTypeClicked enum nested in the ProfilePageViewController which informs the change the page number
     *                     is going to undergo
     * @return int the new updated page number based on the arrow clicked
     */
    public int changePageNumber(ProfilePageViewController.ArrowTypeClicked arrowClicked){

        switch(arrowClicked){
            case FIRST:
                currentProductsPageNumber = 1;
                break;
            case NEXT:
                if(currentProductsPageNumber < (double)allProductsInfo.length /(double) numberProductsPerPage) currentProductsPageNumber++;
                break;
            case PREVIOUS:
                if(currentProductsPageNumber > 1) currentProductsPageNumber--;
                break;
            case LAST:
                currentProductsPageNumber = allProductsInfo.length/numberProductsPerPage + 1;
                break;
            default:
                currentProductsPageNumber = 1;
                break;
        }
        return currentProductsPageNumber;
    }

    private ProductDisplayInfo[] getAllProductsFromDatabase() {
        //TODO: create the method that retrieves all products from the database based on the list retrieval from the profilepagecontroller

        return databaseManager.getProductsDisplayInfo();
    }

}
