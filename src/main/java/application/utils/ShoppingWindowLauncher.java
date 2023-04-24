package application.utils;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import application.model.ProductDisplayInfo;
import application.viewController.ShoppingWindowViewController;

import java.io.IOException;
import java.util.UUID;

public class ShoppingWindowLauncher {

    private Stage productShoppingStage;

    public ShoppingWindowLauncher() { productShoppingStage = new Stage();}

    public void start(ProductDisplayInfo productDisplayInfo, UUID userId)  throws Exception {
        loadingShoppingWindow(productDisplayInfo, userId);

        productShoppingStage.setTitle("The Pet Shop App - Shopping Procedure");
        productShoppingStage.centerOnScreen();
        productShoppingStage.initModality(Modality.APPLICATION_MODAL);
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
        Scene newScene = new Scene(root);
        productShoppingStage.setScene(newScene);
    }

    private Parent loadPaneLoader(FXMLLoader paneLoader) {
        try {
            return paneLoader.load();
        } catch (IOException e) {
            //Todo: log!!
            //Todo do something if the try fails
            System.out.println("FAIL!!! EXPLOTION!!!! BOOOOOOM");
            System.out.println(e);
            return null;
        }
    }

    public void closeShoppingWindow(){
        productShoppingStage.close();
    }

    //Make a nested class implements runnable with the downloading of the image and the sitting of the image in its overidden method maybe called run I don't remember

}
