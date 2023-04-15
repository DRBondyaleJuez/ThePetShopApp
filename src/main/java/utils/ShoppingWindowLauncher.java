package utils;

import core.ObservableView;
import core.ViewObserver;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.ProductDisplayInfo;
import viewController.ProductsPageViewController;
import viewController.ProfilePageViewController;
import viewController.ShoppingWindowViewController;

import java.io.IOException;
import java.util.UUID;

public class ShoppingWindowLauncher {

    private Stage mainStage;

    public ShoppingWindowLauncher() { mainStage = new Stage();}

    public void start(ProductDisplayInfo productDisplayInfo)  throws Exception {
        loadingShoppingWindow(productDisplayInfo);

        mainStage.setTitle("The Pet Shop App - Shopping Procedure");
        mainStage.centerOnScreen();
        mainStage.show();
    }

    private void loadingShoppingWindow(ProductDisplayInfo productDisplayInfo) {
        // TO access to the Resource folder, you have to do the following:
        // getClass().getResource("/path/of/the/resource")
        System.out.println(getClass().getResource("/view/ShoppingWindowView.fxml"));
        FXMLLoader paneLoader = new FXMLLoader(getClass().getResource("/view/ShoppingWindowView.fxml"));
        ShoppingWindowViewController shoppingWindowViewController = new ShoppingWindowViewController(productDisplayInfo);
        shoppingWindowViewController.addLauncherObserver(this);
        paneLoader.setController(shoppingWindowViewController);
        Parent root = loadPaneLoader(paneLoader);
        Scene newScene = new Scene(root);
        mainStage.setScene(newScene);
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
        mainStage.close();
    }


}
