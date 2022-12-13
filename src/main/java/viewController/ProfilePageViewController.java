package viewController;

import controller.CreateAccountController;
import controller.ProfilePageController;
import core.ObservableView;
import core.ViewObserver;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import persistence.assets.LogoType;

import java.io.ByteArrayInputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.UUID;

public class ProfilePageViewController implements Initializable, ObservableView {

    private ProfilePageController controller;
    private ArrayList<ViewObserver> observerList;

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


    public ProfilePageViewController(UUID userUUUID) {

        controller = new ProfilePageController(userUUUID);
        observerList = new ArrayList<>();

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //Update LastLogin of the profile
        controller.updateLastLogin();

        //Set username
        setUsername();

        //SetImages
        setLogoImage();
        setDecorationImage();

        //SETTING ON ACTION

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

    //The recent purchase pseudo button setter section
    private EventHandler<? super MouseEvent> changePurchasesPageNumber(ArrowTypeClicked arrowClicked) {

        return new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {

                int currentPageNumber = controller.changePageNumber(arrowClicked);
                currentPageNumberTextField.setText(currentPageNumber+"");
                setRecentPurchasesListView(currentPageNumber);

            }
        };

    }

    private void setRecentPurchasesListView(int pageNumber){



    }

    private EventHandler<? super MouseEvent> goToBrowseProducts() {

        return new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {

                System.out.println("Go to products page" + controller.getProfileUUID());


                /*
                for (ViewObserver stalker : observerList) {
                    stalker.changeView(ViewObserver.PossibleViews.PRODUCTS,controller.getProfileUUID());
                }
                 */

            }
        };
    }

    private EventHandler<? super MouseEvent> usernameClicked() {

        return new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {

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

    }

    public enum ArrowTypeClicked{
        FIRST,LAST,NEXT,PREVIOUS
    }
}
