package application.viewController;

import application.controller.ProfilePageController;
import application.core.ObservableView;
import application.core.ViewObserver;
import application.model.UserPurchaseRecord;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import application.persistence.assets.LogoType;
import javafx.scene.text.Font;

import java.io.ByteArrayInputStream;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.UUID;

/**
 * Provides the controller of the ProfilePageView.fxml view therefore controlling the effects of interactions
 * with the view. It implements initializable so the methods initialize is overridden. It also implements ObservableView
 * to follow an observer-observable design pattern with ThePetShopAppLauncher class.
 */
public class ProfilePageViewController implements Initializable, ObservableView {

    private final ProfilePageController controller;
    private final ArrayList<ViewObserver> observerList;

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
     * triggered by particular actions or events following the observer and observable design pattern.
     * @param userUUUID UUID the id of the user so this particular user's purchase info is retrieved from the database
     */
    public ProfilePageViewController(UUID userUUUID) {

        controller = new ProfilePageController(userUUUID);
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
            purchasesListView.getItems().clear();
        }

        for (int i = 0; i < controller.getNumberOfEntriesPerPage(); i++) {

            HBox currentHBoxEntry = getPurchasedProductEntry(i);
            if(currentHBoxEntry == null) {
               break;
            }
            purchasesListView.getItems().add(currentHBoxEntry);
        }
    }

    //The recent purchase pseudo button setter section
    private EventHandler<? super MouseEvent> changePurchasesPageNumber(ArrowTypeClicked arrowClicked) {

        return (EventHandler<MouseEvent>) mouseEvent -> {

            int currentPageNumber = controller.changePageNumber(arrowClicked);
            currentPageNumberTextField.setText(currentPageNumber+"");
            setPurchasesListView();

        };

    }

    private EventHandler<ActionEvent> settingsClicked() {
        return actionEvent -> System.out.println("Settings clicked");
    }

    private EventHandler<ActionEvent> signOutClicked() {
        return actionEvent -> {
            for (ViewObserver stalker : observerList) {
                stalker.changeView(ViewObserver.PossibleViews.SIGNIN);
            }
        };
    }



    private EventHandler<? super MouseEvent> goToBrowseProducts() {

        return (EventHandler<MouseEvent>) mouseEvent -> {

            System.out.println("Go to products page" + controller.getProfileUUID());

            for (ViewObserver stalker : observerList) {
                stalker.changeView(ViewObserver.PossibleViews.PRODUCTS,controller.getProfileUUID());
            }

        };
    }

    private EventHandler<? super MouseEvent> usernameClicked() {

        return (EventHandler<MouseEvent>) mouseEvent -> {

            usernameContextMenu.show(usernameLabel, mouseEvent.getScreenX(), mouseEvent.getScreenY());
            System.out.println("Username Clicked");


            /*
            for (ViewObserver stalker : observerList) {
                stalker.changeView(ViewObserver.PossibleViews.PRODUCTS,controller.getProfileUUID());
            }
             */

        };
    }

    /**
     * This method builds the corresponding HBox that will represent an entry in the list showing the details of a particular purchase.
     * <p>
     *     To do this it calls the controller to retrieve the purchase info that corresponds to the positionNumber provided
     * </p>
     * @param positionNumber int the row in the listView this entry will occupy
     * @return HBox object a scene layout object containing the detail of the purchase in the format you want them to be displayed
     */
    private HBox getPurchasedProductEntry(int positionNumber){

        //Calculate the position in the database of the information
        int sourceInformationPosition = (controller.getCurrentRecentPurchasePageNumber()-1) * controller.getNumberOfEntriesPerPage() + positionNumber;

        //If the position is larger than the number of items purchased by the user there is no more information and null is returned
        if(sourceInformationPosition > controller.getNumberOfPurchasesByUser()-1) return null;

        //If the position is adequate the Entry is constructed from the information retrieved from the database

        //First retrieve and store info temporarily
        UserPurchaseRecord currentUserSinglePurchaseRecord = controller.getSingleUserPurchaseRecord(positionNumber);

        System.out.println("This should display the purchase item information: " + currentUserSinglePurchaseRecord); // DELETE WHEN FINISHED ---------------------------------------------------------------------------------------------------------

        //Then build the HBox that will display the info inna certain distribution
        HBox purchaseProductEntry = new HBox();
        purchaseProductEntry.setSpacing(25); //This spacing is matching the header of the list view
        int[] widthsOfListContent = {170,10,35,100};

        //Filling productName in listView row
        Label filler = new Label(currentUserSinglePurchaseRecord.getProductCompleteName() + " ");
        filler.setFont(new Font(filler.getFont().getName(),10));
        filler.setMaxWidth(widthsOfListContent[0]);
        filler.setPrefWidth(widthsOfListContent[0]);
        purchaseProductEntry.getChildren().add(filler);
        //Filling number of items purchased in listView row
        filler = new Label(currentUserSinglePurchaseRecord.getPurchasedQuantity() + " ");
        filler.setFont(new Font(filler.getFont().getName(),10));
        filler.setMaxWidth(widthsOfListContent[1]);
        filler.setPrefWidth(widthsOfListContent[1]);
        purchaseProductEntry.getChildren().add(filler);
        //Filling total cost of the purchase in listView row
        DecimalFormat decimalFormat = new DecimalFormat("#.00");
        String priceWithCorrectDecimalFormat = decimalFormat.format(currentUserSinglePurchaseRecord.getPurchasePrice());
        filler = new Label(priceWithCorrectDecimalFormat+ " €");
        filler.setFont(new Font(filler.getFont().getName(),10));
        filler.setMaxWidth(widthsOfListContent[2]);
        filler.setPrefWidth(widthsOfListContent[2]);
        purchaseProductEntry.getChildren().add(filler);
        //Filling timestamp of the purchase in listView row
        filler = new Label(currentUserSinglePurchaseRecord.getPurchaseDate() + " ");
        filler.setFont(new Font(filler.getFont().getName(),10));
        filler.setMaxWidth(widthsOfListContent[3]);
        filler.setPrefWidth(widthsOfListContent[3]);
        purchaseProductEntry.getChildren().add(filler);

        //Format correcting
        ObservableList<Node> nodeInTheHBox = purchaseProductEntry.getChildren();
        ArrayList<Label> labelsInTheEntry = new ArrayList<>();
        for (Node currentNode : nodeInTheHBox) {
            labelsInTheEntry.add((Label) currentNode);
        }
        for (Label currentLabel : labelsInTheEntry) {
            currentLabel.setFont(new Font(currentLabel.getFont().getName(),10));
        }

        purchaseProductEntry.setSpacing(25);

        return purchaseProductEntry;
    }

    @Override
    public void addObserver(ViewObserver currentViewObserver) {
        observerList.add(currentViewObserver);
    }

    public enum ArrowTypeClicked{
        FIRST,LAST,NEXT,PREVIOUS
    }
}
