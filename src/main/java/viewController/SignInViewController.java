package viewController;

import controller.SignInController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import java.net.URISyntaxException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class SignInViewController implements Initializable {

    private SignInController controller;
    private boolean passwordHiddenState;

    //TextFields and PasswordFields
    @FXML
    private TextField usernameTextField;

    @FXML
    private TextField passwordTextField;

    @FXML
    private PasswordField hiddenPasswordField;

    //ImageViews
    @FXML
    private ImageView backgroundImageView;

    @FXML
    private ImageView topLogoImageView;

    @FXML
    private ImageView viewPasswordImageView;

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
        passwordHiddenState = true;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //SETTING LAYOUT DETAILS

        //Setting the logo image at the top
        setLogoImage();

        //Setting the closed eye image
        setEyeIconOnViewPasswordButton();

        //SETTING ON ACTION

        //Enter Button
        enterButton.setOnAction(enterWithUsernameAndPassword());
        //View Password Button
        viewPasswordButton.setOnAction(changeViewPasswordState());
        //LabelsCLicked
        forgotPasswordLabel.setOnMouseClicked(forgotPasswordEvent());
        createNewAccountLabel.setOnMouseClicked(createNewAccountEvent());
        browseProductsWithoutAccountLabel.setOnMouseClicked(browseWithoutAccountEvent());

    }

    private void setLogoImage() {
        //TODO: set assest using persistence through the controller
        try {
            Image colouredLogoImage = new Image(Objects.requireNonNull(getClass().getResource("/assets/images/colouredLogo.png")).toURI().toString()); //// MOSTRABA UNA ADVERTENCIA Y LA SOLUCIÓN FUE ESE object.requireNonNUL
            topLogoImageView.setImage(colouredLogoImage);
        } catch (URISyntaxException | NullPointerException e) {
            e.printStackTrace();
            System.out.println("LogoImage could not be found");
        }
    }


    //Enter button event handler
    private EventHandler<ActionEvent> enterWithUsernameAndPassword() {

        return new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                replicateContentInTexFieldAndPasswordField();
                //TODO: compare the username with the database and compare the password with the database through the controller's persistence
                // (REMEBER TO ENCRYPT THE PASSWORD SAME AS DURING THE CREATION OF THE ACOUNT)
                System.out.println("Username: " + usernameTextField.getText());
                System.out.println("Password: " + passwordTextField.getText());
            }
        };

    }

    //Password related events

    //View Password Button event handler
    private EventHandler<ActionEvent> changeViewPasswordState() {
        return new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                //Replicating content of password field and password text field
                replicateContentInTexFieldAndPasswordField();
                //Change passwordHiddenState
                passwordHiddenState = !passwordHiddenState;
                //Make the overlapping passwordField not visible
                hiddenPasswordField.setVisible(passwordHiddenState);
                //Change Icon
                //TODO: handle this icon change using the controller's persistence
                setEyeIconOnViewPasswordButton();
            }
        };
    }

    //Method to assure that the content in both password field and text field are the same
    private void replicateContentInTexFieldAndPasswordField(){
        if(passwordHiddenState){
            passwordTextField.setText(hiddenPasswordField.getText());
        } else {
            hiddenPasswordField.setText(passwordTextField.getText());
        }
    }

    //Method handling the changing icon of the viewPassword button
    private void setEyeIconOnViewPasswordButton(){
        //TODO: handle this icon change using the controller's persistence
        try {
            Image eyeImage;
        if(passwordHiddenState){
            eyeImage = new Image(Objects.requireNonNull(getClass().getResource("/assets/icons/eye/closeEye.png")).toURI().toString()); //// MOSTRABA UNA ADVERTENCIA Y LA SOLUCIÓN FUE ESE object.requireNonNUL
        } else {
            eyeImage = new Image(Objects.requireNonNull(getClass().getResource("/assets/icons/eye/openEye.png")).toURI().toString()); //// MOSTRABA UNA ADVERTENCIA Y LA SOLUCIÓN FUE ESE object.requireNonNUL
        }

        viewPasswordImageView.setImage(eyeImage);
        } catch (URISyntaxException | NullPointerException e) {
            e.printStackTrace();
            System.out.println("icon eye could not be found");
        }

    }

    //Label events handled when clicked

    private EventHandler<? super MouseEvent> browseWithoutAccountEvent() {
        return new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                System.out.println("Browse without Account CLicked.");
            }
        };
    }

    private EventHandler<? super MouseEvent> createNewAccountEvent() {

        return new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {

                System.out.println("Create new Account CLicked.");

            }
        };

    }

    private EventHandler<? super MouseEvent> forgotPasswordEvent() {

        return new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {

                System.out.println("Forgot password CLicked.");

            }
        };
    }


}
