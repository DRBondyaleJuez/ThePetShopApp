package application.viewController;

import application.controller.ProfilePageController;
import application.core.ObservableView;
import application.core.ViewObserver;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import application.persistence.assets.LogoType;

import java.io.ByteArrayInputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.UUID;

/**
 * Provides the controller of the ProfilePageView.fxml view therefore controlling the effects of interactions
 * with the view. It implements initializable so the methods initialize is overridden. It also implements ObservableView
 * to follow an observer-observable design pattern with ThePetShopAppLauncher class.
 */
public class ProfilePageViewController implements Initializable, ObservableView {

    private ProfilePageController controller;
    private ArrayList<ViewObserver> observerList;
    private ListViewFiller listViewfiller;

    //TextLabels:
    @FXML
    Label usernameLabel;

    //ImageViews:
    @FXML
    ImageView topLogoImageView;

    @FXML
    ImageView petShopingDecorationImageView;

    //TextFields
    @FXML
    TextField currentPageNumberTextField;

    //"Buttons" i.e. clickables and similar
    @FXML
    Label toFirstPageArrow;
    @FXML
    Label toLastPageArrow;
    @FXML
    Label previousPageArrow;
    @FXML
    Label nextPageArrow;

    @FXML
    StackPane continueBrowsingPseudoButton;

    //ContextMenu and Menu items
    @FXML
    ContextMenu usernameContextMenu;

    @FXML
    MenuItem settingsMenuItem;

    @FXML
    MenuItem signOutMenuItem;

    @FXML
    ListView purchasesListView;


    /**
     * This is the constructor. It initializes a controller of the Class ProfilePageController and assigns it to
     * the controller attribute. It initializes the observerList which will contain ViewObserver implementations that are
     * triggered by particular actions or events following the observer and observable design pattern. It also initializes and assigns
     * the ListViewFiller which will fill the view with a list of purchases by the user.
     * @param userUUUID UUID the id of the user so this particular user's purchase info is retrieved from the database
     */
    public ProfilePageViewController(UUID userUUUID) {

        controller = new ProfilePageController(userUUUID);
        observerList = new ArrayList<>();
        listViewfiller = new ListViewFiller(controller);

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

        //Update LastLogin of the profile
        controller.updateLastLogin();

        //Set username
        setUsername();

        //SetImages
        setLogoImage();
        setDecorationImage();

        //Set initial listView page 1
        setPurchasesListView();

        //SETTING ON ACTION

        settingsMenuItem.setOnAction(settingsClicked());
        signOutMenuItem.setOnAction(signOutClicked());

        //Back Button
        continueBrowsingPseudoButton.setOnMouseClicked(goToBrowseProducts());
        //View Password Button
        nextPageArrow.setOnMouseClicked(changePurchasesPageNumber(ArrowTypeClicked.NEXT));
        previousPageArrow.setOnMouseClicked(changePurchasesPageNumber(ArrowTypeClicked.PREVIOUS));
        toFirstPageArrow.setOnMouseClicked(changePurchasesPageNumber(ArrowTypeClicked.FIRST));
        toLastPageArrow.setOnMouseClicked(changePurchasesPageNumber(ArrowTypeClicked.LAST));

        usernameLabel.setOnMouseClicked(usernameClicked());

    }

    private void setUsername() {
        usernameLabel.setText(controller.getProfileUsername());
    }

    private void setLogoImage() {
        Image horizontalLogoImage = new Image(new ByteArrayInputStream(controller.getLogoImageData(LogoType.HORIZONTAL)));
        topLogoImageView.setImage(horizontalLogoImage);
    }

    private void setDecorationImage() {
        Image decorationImage = new Image(new ByteArrayInputStream(controller.getDecorationImageData()));
        petShopingDecorationImageView.setImage(decorationImage);
    }

    private void setPurchasesListView(){

        if(purchasesListView.getItems() != null) {
            purchasesListView.getItems().removeAll();
        }



        for (int i = 0; i < controller.getNumberOfEntriesPerPage(); i++) {

            HBox currentHBoxEntry = listViewfiller.getPurchasedProductEntry(i);
            if(currentHBoxEntry == null) {
               break;
            }
            purchasesListView.getItems().add(currentHBoxEntry);
        }



    }

    //The recent purchase pseudo button setter section
    private EventHandler<? super MouseEvent> changePurchasesPageNumber(ArrowTypeClicked arrowClicked) {

        return new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {

                int currentPageNumber = controller.changePageNumber(arrowClicked);
                currentPageNumberTextField.setText(currentPageNumber+"");
                setPurchasesListView();

            }
        };

    }

    private EventHandler<ActionEvent> settingsClicked() {
        return new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                System.out.println("Settings clicked");
            }
        };
    }

    private EventHandler<ActionEvent> signOutClicked() {
        return new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                for (ViewObserver stalker : observerList) {
                    stalker.changeView(ViewObserver.PossibleViews.SIGNIN);
                }
            }
        };
    }



    private EventHandler<? super MouseEvent> goToBrowseProducts() {

        return new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {

                System.out.println("Go to products page" + controller.getProfileUUID());

                for (ViewObserver stalker : observerList) {
                    stalker.changeView(ViewObserver.PossibleViews.PRODUCTS,controller.getProfileUUID());
                }

            }
        };
    }

    private EventHandler<? super MouseEvent> usernameClicked() {

        return new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {

                usernameContextMenu.show(usernameLabel, mouseEvent.getScreenX(), mouseEvent.getScreenY());
                System.out.println("Username Clicked");


                /*
                for (ViewObserver stalker : observerList) {
                    stalker.changeView(ViewObserver.PossibleViews.PRODUCTS,controller.getProfileUUID());
                }
                 */

            }
        };
    }


    @Override
    public void addObserver(ViewObserver currentViewObserver) {
        observerList.add(currentViewObserver);
    }

    public enum ArrowTypeClicked{
        FIRST,LAST,NEXT,PREVIOUS
    }
}
