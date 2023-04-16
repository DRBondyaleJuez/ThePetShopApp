package application.core;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import application.viewController.ProductsPageViewController;
import application.viewController.ProfilePageViewController;

import java.io.IOException;
import java.util.UUID;

public class ThePetShopAppLauncher extends Application implements ViewObserver {

    private Stage mainStage;

    public ThePetShopAppLauncher() { mainStage = new Stage();}

    @Override public void start(Stage stage)  throws Exception {
        mainStage = stage;
        loadSignInView();

        mainStage.setTitle("The Pet Shop App");
        mainStage.centerOnScreen();
        mainStage.show();
    }

    private void loadingMainScene() {
        // TO access to the Resource folder, you have to do the following:
        // getClass().getResource("/path/of/the/resource")
        System.out.println(getClass().getResource("/view/SignInView.fxml"));
        FXMLLoader paneLoader = new FXMLLoader(getClass().getResource("/view/SignInView.fxml"));
        Parent root = loadPaneLoader(paneLoader);
        Scene newScene = new Scene(root);
        mainStage.setScene(newScene);
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


}
