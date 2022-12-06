package viewController;

import controller.SignInController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

public class SignInViewController implements Initializable {

    private SignInController controller;

    //TextFields
    @FXML
    private TextField usernameTextField;

    @FXML
    private TextField passwordTextField;

    //ImageViews
    @FXML
    private ImageView backgroundImageView;

    @FXML
    private ImageView topLogoImageView;

    //Buttons
    @FXML
    private Button enterButton;

    @FXML
    private Button viewPasswordButton;

    //Labels
    @FXML
    private Label forgotPasswordLabel;

    @FXML
    private Label createNewAccountLabel;

    @FXML
    private Label browseProductsWithoutAccountLabel;


    public SignInViewController() {
        controller = new SignInController();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
