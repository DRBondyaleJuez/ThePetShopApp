package viewController;

import controller.CreateAccountController;
import controller.ProfilePageController;
import core.ObservableView;
import core.ViewObserver;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.UUID;

public class ProfilePageViewController implements Initializable, ObservableView {

    private ProfilePageController controller;
    private ArrayList<ViewObserver> observerList;

    @FXML
    Label testLabel;


    public ProfilePageViewController(UUID userUUUID) {

        controller = new ProfilePageController(userUUUID);
        observerList = new ArrayList<>();

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //Update LastLogin of the profile
        controller.updateLastLogin();


        String testText = "The user profile from: " + controller.getProfileUUID() + " with UUID: " + controller.getProfileUsername() + " with email: " + controller.getProfileEmail() +"\n" +
                "Last login: " + controller.getLastLogin();
        testLabel.setText(testText);

        //SETTING LAYOUT DETAILS
        /*
        setLogoImage();

        //Setting the closed eye image
        setEyeIconOnViewPasswordButton();

        //SETTING ON ACTION

        //Back Button
        backButton.setOnAction(goBackToPreviousPage());
        //View Password Button
        viewPasswordButton.setOnAction(changeViewPasswordState());
        //Create New Account  Button
        createNewAccountButton.setOnAction(createNewAccountEvent());

         */

    }

    @Override
    public void addObserver(ViewObserver currentViewObserver) {

    }
}
