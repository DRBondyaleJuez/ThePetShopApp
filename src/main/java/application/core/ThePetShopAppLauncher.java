package application.core;

import application.model.ProductDisplayInfo;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import application.viewController.ProductsPageViewController;
import application.viewController.ProfilePageViewController;

import java.io.IOException;
import java.util.UUID;

/**
 * This the entry point of the application launch called by the main. It displays the initial view of the application
 * <p>
 *     Inheriting from application requiring an implementation of abstract class start that allows the display of the view.
 *     It also implements ViewObserver to follow a observer-observable design pattern with several of the viewControllers
 *     facilitating interactions between classes and changes between the views displayed
 * </p>
 */
public class ThePetShopAppLauncher extends Application implements ViewObserver {

    private Stage mainStage;

    /**
     * This is the implementation of the start abstract method of the extended class Application.
     *  * <p>
     *     This method is called during the execution of the Application class static method launch. It loads the FXMl view files,
     *     therefore, it builds its controllers too. MainStage is also built and displayed with the method show of the Stage class.
     *  * </p>
     * @param stage Stage object provided during the static launch method execution.
     */
    @Override
    public void start(Stage stage)  throws Exception {
        mainStage = stage;
        loadSignInView();

        mainStage.setTitle("The Pet Shop App");
        mainStage.centerOnScreen();
        mainStage.show();
    }


    @Override
    public void changeView(PossibleViews newView) {
        switch (newView){
            case SIGNIN:
                loadSignInView();
                break;
            case CREATEACCOUNT:
                loadCreateAccountView();
                break;
            default:
                loadSignInView();
                break;
        }

        mainStage.centerOnScreen();
        mainStage.show();

    }

    @Override
    public void changeView(PossibleViews newView,UUID userUUID) {
        switch (newView){
            case PROFILE:
                loadProfileView(userUUID);
                break;
            case PRODUCTS:
                loadProductsView(userUUID);
                break;
            default:
                loadSignInView();
                break;
        }

        mainStage.centerOnScreen();
        mainStage.show();

    }

    @Override
    public void loadShoppingWindow(ProductDisplayInfo productDisplayInfo, UUID userId){
        ShoppingWindowLauncher shoppingWindowLauncher = new ShoppingWindowLauncher();
        try {
            shoppingWindowLauncher.start(productDisplayInfo,userId);
        } catch (Exception e) {
            System.out.println("Exception: " + e + ". When shopping window was going to be launched.");
            throw new RuntimeException(e);
        }
    }

    private void loadSignInView() {
        loadView("/view/SignInView.fxml");
    }
    private void loadCreateAccountView() {
        loadView("/view/CreateAccountView.fxml");
    }

    private void loadProfileView(UUID userUUID) { profilePageLoadView("/view/ProfilePageView.fxml", userUUID); }
    private void loadProductsView(UUID userUUID) {
        productPageLoadView("/view/ProductsPageView.fxml",userUUID);
    }

    private void loadView(String sceneResource){
        FXMLLoader paneLoader = new FXMLLoader(getClass().getResource(sceneResource));
        Parent root = loadPaneLoader(paneLoader);
        ObservableView observableViewController = (ObservableView) paneLoader.getController();
        observableViewController.addObserver(this);
        Scene newScene = new Scene(root);
        mainStage.setScene(newScene);
    }

    private void profilePageLoadView(String sceneResource, UUID userUUID){
        //Building profile fxml scene
        FXMLLoader paneLoader = new FXMLLoader(getClass().getResource(sceneResource));

        //Controller needs to be created apart to provide it with the UUID
        ProfilePageViewController newProfilePageViewController = new ProfilePageViewController(userUUID);
        newProfilePageViewController.addObserver(this);
        paneLoader.setController(newProfilePageViewController);

        //The loadPaneloader method is called after the view controller has finished setting
        Parent root = loadPaneLoader(paneLoader);
        Scene newScene = new Scene(root);
        mainStage.setScene(newScene);
    }

    private void productPageLoadView(String sceneResource, UUID userUUID){
        //Building products fxml scene
        FXMLLoader paneLoader = new FXMLLoader(getClass().getResource(sceneResource));

        //Controller needs to be created apart to provide it with the UUID
        ProductsPageViewController newProductsPageViewController = new ProductsPageViewController(userUUID);
        newProductsPageViewController.addObserver(this);
        paneLoader.setController(newProductsPageViewController);

        //The loadPaneloader method is called after the view controller has finished setting
        Parent root = loadPaneLoader(paneLoader);
        if(root == null) {
            // This is the extreme case if loaded fxml file is null
            gracefulShutdown();
        } else {
            Scene newScene = new Scene(root);
            mainStage.setScene(newScene);
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
}
