package viewController;

import controller.SignInController;
import core.ObservableView;
import core.ViewObserver;
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
import persistence.assets.EyeIconType;
import persistence.assets.LogoType;

import java.io.ByteArrayInputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;

public class SignInViewController implements Initializable, ObservableView {

    private SignInController controller;
    private ArrayList<ViewObserver> observerList;
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
        observerList = new ArrayList<>();
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
        Image colouredLogoImage = new Image(new ByteArrayInputStream(controller.getLogoImageData(LogoType.COLOR)));
        topLogoImageView.setImage(colouredLogoImage);
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
                if(true){
                    for (ViewObserver stalker : observerList) {
                        stalker.changeView(ViewObserver.PossibleViews.PROFILE);
                    }
                }
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
        Image eyeImage;
        if(passwordHiddenState){
            eyeImage = new Image(new ByteArrayInputStream(controller.getEyeIconImageData(EyeIconType.CLOSED)));
        } else {
            eyeImage = new Image(new ByteArrayInputStream(controller.getEyeIconImageData(EyeIconType.OPEN)));
        }
        viewPasswordImageView.setImage(eyeImage);
    }

    //Label events handled when clicked

    private EventHandler<? super MouseEvent> browseWithoutAccountEvent() {
        return new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                for (ViewObserver stalker : observerList) {
                    stalker.changeView(ViewObserver.PossibleViews.PRODUCTS);
                }
                System.out.println("Browse without Account CLicked.");
            }
        };
    }

    private EventHandler<? super MouseEvent> createNewAccountEvent() {

        return new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {

                for (ViewObserver stalker : observerList) {
                    stalker.changeView(ViewObserver.PossibleViews.CREATEACCOUNT);
                }

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


    @Override
    public void addObserver(ViewObserver currentViewObserver) {
        observerList.add(currentViewObserver);
    }
}
