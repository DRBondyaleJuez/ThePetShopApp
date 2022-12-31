package viewController;

import controller.CreateAccountController;
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
import javafx.scene.paint.Color;
import persistence.assets.EyeIconType;
import persistence.assets.LogoType;
import persistence.database.dbConnection.SQLErrorMessageEnums;

import java.io.ByteArrayInputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

                replicateContentInTexFieldAndPasswordField();

                String newUsername = usernameTextField.getText();
                String newUserPassword = passwordField1.getText();
                String newUserPassword2 = passwordField2.getText();
                String newUserEmail = contactEmailTextField.getText();

                boolean isDataValid = checkNewUserDataValidity(newUsername,newUserPassword,newUserPassword2,newUserEmail);
                if(isDataValid){
                    System.out.println("Username: " + newUsername);
                    System.out.println("Password: " + newUserPassword);
                    System.out.println("Password: " + newUserPassword2);
                    System.out.println("Email: " + newUserEmail);

                    SQLErrorMessageEnums newUserHasBeenAddedSQLMessage = addNewUserToDatabase(newUsername,newUserPassword,newUserEmail);

                    if(newUserHasBeenAddedSQLMessage != SQLErrorMessageEnums.NO_ERROR){
                        entryNotUniqueDisplayErrorMessage(newUserHasBeenAddedSQLMessage);
                    }
                                       /*
                    for (ViewObserver stalker : observerList) {
                        stalker.changeView(ViewObserver.PossibleViews.SIGNIN);
                    }

                    //--------------------------------------------------ERROR AL USAR CONTRASEÑAS NUMERICAS: "Error: los datos restantes del mensaje son insuficientes" ------- https://www.postgresql.org/message-id/BAY139-W17522EA1404D646B6A38B3D0DE0@phx.gbl

                     */
                }
            }
        };
    }

    private void entryNotUniqueDisplayErrorMessage(SQLErrorMessageEnums sqlErrorMessageEnum){
        Color color = Color.DARKRED;

        switch(sqlErrorMessageEnum){
            case UNKNOWN_ERROR:
            break;
            case USERNAME:
                usernameHelpLabel.setText("This username already exists please try another username.");
                usernameHelpLabel.setUnderline(true);
                usernameHelpLabel.setTextFill(color);
            break;
            case USER_EMAIL:
                contactEmailHelpLabel.setText("This email has already been associated to another username.");
                contactEmailHelpLabel.setUnderline(true);
                contactEmailHelpLabel.setTextFill(color);
            break;

            default:
        }

    }

    public boolean checkNewUserDataValidity(String newUsername, String newUserPassword, String newUserPassword2, String newUserEmail){
        boolean validUsername = checkUsername(newUsername);
        boolean validPassword = checkPassword(newUserPassword,newUserPassword2);
        boolean validEmail = checkEmail(newUserEmail);

        if(validUsername && validPassword && validEmail){
            return true;
        } else {
            System.out.println("Something is wrong. Unable to create a new account. Check fields and field help labels");
            return false;
        }
    }

    private boolean checkUsername(String currentUsername){
        //TODO: use the controller's persistence to compare with other name in database and confirm it is new

        if(currentUsername.length() < 2 || currentUsername.length() > 50 || usernameContainsSymbols(currentUsername)){
            usernameHelpLabel.setText("Not a valid Username. It must contain between 2 and 50 characters (No punctuation marks or Symbols).");
            usernameHelpLabel.setUnderline(true);
            Color color = Color.DARKRED;
            usernameHelpLabel.setTextFill(color);
            return false;
        }

        usernameHelpLabel.setText("Valid Username");
        Color color = Color.LIGHTGREEN;
        usernameHelpLabel.setTextFill(color);
        return true;
    }

    private boolean usernameContainsSymbols(String username) {
        //Source: https://stackoverflow.com/questions/24191040/checking-to-see-if-a-string-is-letters-spaces-only
        Pattern p = Pattern.compile("^[ \\w]+$");
        Matcher m = p.matcher(username);
        return !m.matches();
    }

    private boolean checkPassword(String password1,String password2){

        if(!Objects.equals(password1, password2)){
            passwordHelpLabel.setText("The passwords don't match");
            Color color = Color.DARKRED;
            passwordHelpLabel.setTextFill(color);
            passwordHelpLabel.setUnderline(true);
            passwordHelpLabel.setTextFill(color);
            return  false;
        }
        if(password1.length() < 8 || password1.length() > 499) {
            passwordHelpLabel.setText("The password must be at least 8 characters long and not excessively long");
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

    private boolean checkEmail(String email ){
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
    private SQLErrorMessageEnums addNewUserToDatabase(String newUsername, String newUserPassword, String newUserEmail) {
        return controller.addNewUserToDatabase(newUsername, newUserPassword, newUserEmail);
    }

    @Override
    public void addObserver(ViewObserver currentViewObserver) {
        observerList.add(currentViewObserver);
    }
}
