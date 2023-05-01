package application.core;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import application.model.ProductDisplayInfo;
import application.viewController.ShoppingWindowViewController;

import java.io.IOException;
import java.util.UUID;

/**
 * Provides the object in charged of displaying in a new window the shoppingWindowView
 */
public class ShoppingWindowLauncher {

    private final Stage productShoppingStage;

    /**
     * This is the constructor.
     * <p>
     *     A new stage object is instantiated to display the view in a new window.
     * </p>
     */
    public ShoppingWindowLauncher() { productShoppingStage = new Stage();}

    /**
     * Emulating Application inheritance this method starts this new window display.
     * <p>
     *     It loads the appropriate view, sets its viewControllers and assures the window has to be close to allow interaction
     *     with the window behind using the initModality(Modality.APPLICATION_MODAL) setting
     * </p>
     * @param productDisplayInfo ProductDisplayInfo object encapsulating all the info required about the selected product for the window
     * @param userId UUID corresponding to the user who selected the product
     */
    public void start(ProductDisplayInfo productDisplayInfo, UUID userId) {
        loadingShoppingWindow(productDisplayInfo, userId);

        productShoppingStage.setTitle("The Pet Shop App - Shopping Procedure");
        productShoppingStage.centerOnScreen();
        productShoppingStage.initModality(Modality.APPLICATION_MODAL); //To prevent interaction with the window behind until window interaction completes or closes
        productShoppingStage.show();
    }

    private void loadingShoppingWindow(ProductDisplayInfo productDisplayInfo, UUID userId) {
        // TO access to the Resource folder, you have to do the following:
        // getClass().getResource("/path/of/the/resource")
        System.out.println(getClass().getResource("/view/ShoppingWindowView.fxml"));
        FXMLLoader paneLoader = new FXMLLoader(getClass().getResource("/view/ShoppingWindowView.fxml"));
        ShoppingWindowViewController shoppingWindowViewController = new ShoppingWindowViewController(productDisplayInfo,userId);
        shoppingWindowViewController.addLauncherObserver(this);
        paneLoader.setController(shoppingWindowViewController);
        Parent root = loadPaneLoader(paneLoader);
        if(root == null) {
            // This is the extreme case if loaded fxml file is null
            gracefulShutdown();
        } else {
            Scene newScene = new Scene(root);
            productShoppingStage.setScene(newScene);
        }
    }
    private Parent loadPaneLoader(FXMLLoader paneLoader) {
        try {
            return paneLoader.load();
        } catch (IOException e) {
            //Todo: log!!
            System.out.println("The FXML file could not be loaded.");
            return null;
        }
    }

    private void gracefulShutdown(){
        // Show something to the user if apply
        // save a new log if a apply
        System.out.println("HERE");
        System.exit(-1);
    }

    public void closeShoppingWindow(){
        productShoppingStage.close();
    }

    //Make a nested class implements runnable with the downloading of the image and the sitting of the image in its overidden method maybe called run I don't remember

}
