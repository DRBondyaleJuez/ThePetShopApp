package viewController;

import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import controller.CreateAccountController;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import persistence.assets.EyeIconType;
import persistence.assets.LogoType;

import java.io.ByteArrayInputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;

public class CreateAccountViewController implements Initializable, ObservableView {

    private CreateAccountController controller;
    private ArrayList<ViewObserver> observerList;
    private boolean passwordHiddenState;

    //TextFields and PasswordFields
    @FXML
    private TextField usernameTextField;
    @FXML
    private TextField passwordTextField1;
    @FXML
    private PasswordField passwordField1;
    @FXML
    private PasswordField passwordField2;
    @FXML
    private TextField contactEmailTextField;

    //ImageViews
    @FXML
    private ImageView logoImageView;

    @FXML
    private ImageView viewPasswordImageView;

    //Buttons
    @FXML
    private Button createNewAccountButton;
    @FXML
    private Button viewPasswordButton;
    @FXML
    private Button backButton;

    //Labels
    @FXML
    private Label usernameHelpLabel;
    @FXML
    private Label passwordHelpLabel;
    @FXML
    private Label contactEmailHelpLabel;

    public CreateAccountViewController() {
        controller = new CreateAccountController();
        passwordHiddenState = true;
        observerList = new ArrayList<>();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //SETTING LAYOUT DETAILS
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

    }


    private void setLogoImage() {
        Image colouredLogoImage = new Image(new ByteArrayInputStream(controller.getLogoImageData(LogoType.BLACK)));
        logoImageView.setImage(colouredLogoImage);
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
                passwordField1.setVisible(passwordHiddenState);
                //Change Icon
                //TODO: handle this icon change using the controller's persistence
                setEyeIconOnViewPasswordButton();
            }
        };
    }
    //Method to assure that the content in both password field and text field are the same
    private void replicateContentInTexFieldAndPasswordField(){
        if(passwordHiddenState){
            passwordTextField1.setText(passwordField1.getText());
        } else {
            passwordField1.setText(passwordTextField1.getText());
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

    //The buttons back and create new account
    private EventHandler<ActionEvent> goBackToPreviousPage() {
        return new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                for (ViewObserver stalker : observerList) {
                    stalker.changeView(ViewObserver.PossibleViews.SIGNIN);
                }

            }
        };
    }

    private EventHandler<ActionEvent> createNewAccountEvent() {
        return new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

                boolean validUsername = checkUsername();
                boolean validPassword = checkPassword();
                boolean validEmail = checkEmail();

                String newUsername = usernameTextField.getText();
                String newUserPassword = passwordField1.getText();
                String newUserEmail = contactEmailTextField.getText();

                if(validUsername && validPassword && validEmail){
                    System.out.println("Username: " + newUsername);
                    System.out.println("Password: " + newUserPassword);
                    System.out.println("Password: " + passwordField2.getText());
                    System.out.println("Email: " + newUserEmail);

                    //Update SQL Table
                    boolean newUserHasBeenAdded = addNewUserToDatabase(newUsername,newUserPassword,newUserEmail);

                    /*
                    for (ViewObserver stalker : observerList) {
                        stalker.changeView(ViewObserver.PossibleViews.SIGNIN);
                    }
                     */
                } else {
                    System.out.println("Something is wrong. Unable to create a new account. Check fields and field help labels");

                }

            }
        };
    }




    private boolean checkUsername(){
        //TODO: use the controller's persistence to compare with other name in database and confirm it is new
        if(usernameTextField.getText().isEmpty()){
            usernameHelpLabel.setText("Not a valid Username");
            usernameHelpLabel.setUnderline(true);
            Color color = Color.DARKRED;
            usernameHelpLabel.setTextFill(color);
            return false;
        }
        System.out.println("Comparing with database ...");

        usernameHelpLabel.setText("Valid Username");
        Color color = Color.LIGHTGREEN;
        usernameHelpLabel.setTextFill(color);
        return true;
    }

    private boolean checkPassword(){

        String password1 = passwordField1.getText();
        String password2 = passwordField2.getText();
        System.out.println(password1.length());

        if(!Objects.equals(password1, password2)){
            passwordHelpLabel.setText("The passwords don't match");
            Color color = Color.DARKRED;
            passwordHelpLabel.setTextFill(color);
            passwordHelpLabel.setUnderline(true);
            passwordHelpLabel.setTextFill(color);
            return  false;
        }
        if(password1.length() < 8) {
            passwordHelpLabel.setText("The password must be at least 8 characters long");
            Color color = Color.DARKRED;
            passwordHelpLabel.setUnderline(true);
            passwordHelpLabel.setTextFill(color);

            return  false;
        }
        else {
            passwordHelpLabel.setText("Valid Password");
            Color color = Color.LIGHTGREEN;
            passwordHelpLabel.setTextFill(color);
            return  true;
        }
    }

    private boolean checkEmail(){
        String email = contactEmailTextField.getText();
        String[] splitEmail = email.split("@");
        if(splitEmail.length == 2 && splitEmail[0].length() > 0 && splitEmail[1].length() > 0 ){
            contactEmailHelpLabel.setText("Valid Email. Confirmation will be sent.");
            Color color = Color.LIGHTGREEN;
            contactEmailHelpLabel.setTextFill(color);
            return true;
        } else {
            contactEmailHelpLabel.setText("Something might be wrong with the current email.");
            contactEmailHelpLabel.setUnderline(true);
            Color color = Color.DARKRED;
            contactEmailHelpLabel.setTextFill(color);
            return false;
        }
    }
    private boolean addNewUserToDatabase(String newUsername, String newUserPassword, String newUserEmail) {

        return controller.addNewUserToDatabase(newUsername, newUserPassword, newUserEmail);

    }

    @Override
    public void addObserver(ViewObserver currentViewObserver) {
        observerList.add(currentViewObserver);
    }
}
