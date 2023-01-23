package controller;

import model.ProductDisplayInfo;
import model.UserPurchaseRecord;
import persistence.assets.AssetManager;
import persistence.assets.LogoType;
import persistence.database.DatabaseManager;
import viewController.ProfilePageViewController;

import java.util.UUID;

public class ProductsPageController {

    private UUID currentUserUUID;
    private int currentProductsPageNumber;
    private int numberProductsPerPage;

    private AssetManager assetManager;

    private DatabaseManager databaseManager;

    private ProductDisplayInfo[] allProductsInfo;

    public ProductsPageController(UUID currentUserUUID) {

        assetManager = AssetManager.getInstance();
        databaseManager = DatabaseManager.getInstance();

        this.currentUserUUID = currentUserUUID;
        currentProductsPageNumber = 1;
        numberProductsPerPage = 6;
        allProductsInfo = getAllProductsFromDatabase();
    }

    private ProductDisplayInfo[] getAllProductsFromDatabase() {
        //TODO: create the method that retrieves all products from the database based on the list retrieval from the profilepagecontroller
        return null;
    }

    public byte[] getLogoImageData(LogoType logoType) {
        return assetManager.getLogoImageData(logoType);
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
