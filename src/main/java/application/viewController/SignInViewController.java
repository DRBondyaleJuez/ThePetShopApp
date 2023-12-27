package application.viewController;

import application.controller.SignInController;
import application.core.ObservableView;
import application.core.ViewObserver;
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
import application.persistence.assets.EyeIconType;
import application.persistence.assets.LogoType;

import java.io.ByteArrayInputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.UUID;

/**
 * Provides the controller of the SignInView.fxml view therefore controlling the effects of interactions
 * with the view. It implements initializable so the the methods initialize is overridden. It also implements ObservableView
 * to follow an observer-observable design pattern with ThePetShopAppLauncher class.
 */
public class SignInViewController implements Initializable, ObservableView {

    private final SignInController controller;
    private final ArrayList<ViewObserver> observerList;
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

    @FXML
    private Label usernameHelpLabel;

    @FXML
    private Label passwordHelpLabel;


    /**
     * This is the constructor. It initializes a controller of the Class SignInController and assigns it to
     * the controller attribute. It initializes the observerList which will contain ViewObserver implementations that are
     * triggered by particular actions or events following the observer and observable design pattern. It also sets the
     * passwordHiddenState initially as true.
     */
    public SignInViewController() {

        controller = new SignInController();
        passwordHiddenState = true;
        observerList = new ArrayList<>();
    }

    /**
     * This is the implementation of initialize abstract method.
     * <p>
     *     When this is called during the construction of the view a series of methods are called to assign
     *     events to the different elements of the view that can be interacted with.
     *     This includes all the clickable sections of the board.
     *     Also setting the initial visual state of the elements in the view.
     * </p>
     * @param url the URL type argument required by the Initializable interface
     * @param resourceBundle the ResourceBundle type argument required by the Initializable interface
     */
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

        return actionEvent -> {
            replicateContentInTexFieldAndPasswordField();
            String enteredUsername = usernameTextField.getText();
            String enteredPassword = passwordTextField.getText();
            boolean validCredentials = checkCredentials(enteredUsername,enteredPassword);
            if(validCredentials){

                System.out.println("Username: " + enteredUsername);
                System.out.println("Password: " + enteredPassword);

                //Start the steps to open profile page
                UUID userUUID = controller.fetchCorrespondingUUID(enteredUsername);
                System.out.println(userUUID);

                for (ViewObserver stalker : observerList) {
                    stalker.changeView(ViewObserver.PossibleViews.PROFILE,userUUID);
                }

            }else{
                System.out.println("Invalid credentials check page for details in the help sections");
            }
        };

    }

    boolean checkCredentials(String enteredUsername,String enteredPassword){
        boolean usernameValidity = controller.verifyUsername(enteredUsername);
        if(!usernameValidity){
            usernameHelpLabel.setText("The username is not valid.");
            usernameHelpLabel.setUnderline(true);
            Color color = Color.DARKRED;
            usernameHelpLabel.setTextFill(color);
            return false;
        } else {
            usernameHelpLabel.setText("Valid username.");
            Color color = Color.LIGHTGREEN;
            usernameHelpLabel.setTextFill(color);
        }

        boolean passwordValidity = controller.verifyPassword(enteredUsername,enteredPassword);
        if(!passwordValidity){
            passwordHelpLabel.setText("The password is not valid.");
            passwordHelpLabel.setUnderline(true);
            Color color = Color.DARKRED;
            passwordHelpLabel.setTextFill(color);
            return false;
        } else {
            passwordHelpLabel.setText("Valid password.");
            Color color = Color.LIGHTGREEN;
            passwordHelpLabel.setTextFill(color);
        }

        return (usernameValidity && passwordValidity);
    }

    //Password related events

    //View Password Button event handler
    private EventHandler<ActionEvent> changeViewPasswordState() {
        return actionEvent -> {
            //Replicating content of password field and password text field
            replicateContentInTexFieldAndPasswordField();
            //Change passwordHiddenState
            passwordHiddenState = !passwordHiddenState;
            //Make the overlapping passwordField not visible
            hiddenPasswordField.setVisible(passwordHiddenState);
            //Change Icon
            //TODO: handle this icon change using the controller's persistence
            setEyeIconOnViewPasswordButton();
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
        return (EventHandler<MouseEvent>) mouseEvent -> {
            for (ViewObserver stalker : observerList) {
                stalker.changeView(ViewObserver.PossibleViews.PRODUCTS,null);
            }
            System.out.println("Browse without Account CLicked.");
        };
    }

    private EventHandler<? super MouseEvent> createNewAccountEvent() {

        return (EventHandler<MouseEvent>) mouseEvent -> {

            for (ViewObserver stalker : observerList) {
                stalker.changeView(ViewObserver.PossibleViews.CREATEACCOUNT);
            }
        };
    }

    private EventHandler<? super MouseEvent> forgotPasswordEvent() {

        return (EventHandler<MouseEvent>) mouseEvent -> System.out.println("Forgot password CLicked.");
    }


    @Override
    public void addObserver(ViewObserver currentViewObserver) {
        observerList.add(currentViewObserver);
    }
}
