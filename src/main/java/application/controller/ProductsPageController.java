package application.controller;

import application.model.ProductDisplayInfo;
import application.persistence.assets.AssetManager;
import application.persistence.assets.LogoType;
import application.persistence.database.DatabaseManager;
import application.viewController.ProfilePageViewController;

import java.util.Arrays;
import java.util.UUID;

public class ProductsPageController {

    private UUID currentUserUUID;
    private int currentProductsPageNumber;
    private int numberProductsPerPage;

    private int numberProductsPerRow;

    private AssetManager assetManager;

    private DatabaseManager databaseManager;

    private ProductDisplayInfo[] allProductsInfo;

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

    private ProductDisplayInfo[] getAllProductsFromDatabase() {
        //TODO: create the method that retrieves all products from the database based on the list retrieval from the profilepagecontroller

        return databaseManager.getProductsDisplayInfo();
    }

    public int getNumberProductsPerPage() {
        return numberProductsPerPage;
    }

    public int getNumberProductsPerRow() {
        return numberProductsPerRow;
    }

    public byte[] getLogoImageData(LogoType logoType) {
        return assetManager.getLogoImageData(logoType);
    }

    public ProductDisplayInfo[] getCurrentProductsDisplayInfo(){

        int startingProductInfoIndex = (currentProductsPageNumber-1)*numberProductsPerPage;
        int finalProductInfoIndex = startingProductInfoIndex + numberProductsPerPage;

        if(allProductsInfo == null || allProductsInfo.length == 0) return null;
        if(finalProductInfoIndex > allProductsInfo.length) finalProductInfoIndex = allProductsInfo.length;

        return Arrays.copyOfRange(allProductsInfo, startingProductInfoIndex, finalProductInfoIndex);
    }

    public int changePageNumber(ProfilePageViewController.ArrowTypeClicked arrowClicked){

        switch(arrowClicked){
            case FIRST:
                currentProductsPageNumber = 1;
                break;
            case NEXT:
                if(currentProductsPageNumber < allProductsInfo.length /numberProductsPerPage) currentProductsPageNumber++;
                break;
            case PREVIOUS:
                if(currentProductsPageNumber > 1) currentProductsPageNumber--;
                break;
            default:
                currentProductsPageNumber = 1;
                break;
        }
        return currentProductsPageNumber;
    }

}
