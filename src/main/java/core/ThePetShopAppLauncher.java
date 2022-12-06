package core;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class ThePetShopAppLauncher extends Application {

    private Stage mainStage;

    public ThePetShopAppLauncher() { mainStage = new Stage();}

    @Override public void start(Stage stage)  throws Exception {
        mainStage = stage;
        loadingMainScene();

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
