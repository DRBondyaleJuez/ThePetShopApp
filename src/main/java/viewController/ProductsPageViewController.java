package viewController;

import controller.ProductsPageController;
import core.ObservableView;
import core.ViewObserver;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
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

public class ProductsPageViewController implements Initializable, ObservableView {

    private ProductsPageController controller;
    private ArrayList<ViewObserver> observerList;
    private GridViewFiller gridViewfiller;

    //ImageViews:
    @FXML
    ImageView topLogoImageView;

    //TextFields
    @FXML
    TextField searchTextField;
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
    Label backLabel;

    public ProductsPageViewController(UUID userUUUID) {

        controller = new ProductsPageController(userUUUID);
        observerList = new ArrayList<>();
        gridViewfiller = new GridViewFiller(controller);

    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        setLogoImage();

        //Setting buttons
        //Product Grid display controller arrows
        nextPageArrow.setOnMouseClicked(changePurchasesPageNumber(ProfilePageViewController.ArrowTypeClicked.NEXT));
        previousPageArrow.setOnMouseClicked(changePurchasesPageNumber(ProfilePageViewController.ArrowTypeClicked.PREVIOUS));
        toFirstPageArrow.setOnMouseClicked(changePurchasesPageNumber(ProfilePageViewController.ArrowTypeClicked.FIRST));
        toLastPageArrow.setOnMouseClicked(changePurchasesPageNumber(ProfilePageViewController.ArrowTypeClicked.LAST));

        backLabel.setOnMouseClicked(goBackToPreviousPage());


    }

    private void setLogoImage() {
        Image horizontalLogoImage = new Image(new ByteArrayInputStream(controller.getLogoImageData(LogoType.H_TRANSPARENT)));
        topLogoImageView.setImage(horizontalLogoImage);
    }

    //The recent purchase pseudo button setter section
    private EventHandler<? super MouseEvent> changePurchasesPageNumber(ProfilePageViewController.ArrowTypeClicked arrowClicked) {

        return new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {

                int currentPageNumber = controller.changePageNumber(arrowClicked);
                currentPageNumberTextField.setText(currentPageNumber+"");
                setProductGridView();

            }
        };

    }

    //The buttons back
    private EventHandler<? super MouseEvent> goBackToPreviousPage() {
        return new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if(controller.getCurrentUserUUID() == null) {
                    for (ViewObserver stalker : observerList) {
                        stalker.changeView(ViewObserver.PossibleViews.SIGNIN);
                    }
                } else {
                    for (ViewObserver stalker : observerList) {
                        stalker.changeView(ViewObserver.PossibleViews.PROFILE,controller.getCurrentUserUUID());
                    }
                }
            }
        };
    }

    private void setProductGridView() {
        //TODO: method that calls gridViewfilller and fills the GridView
    }

    @Override
    public void addObserver(ViewObserver currentViewObserver) {
        observerList.add(currentViewObserver);

    }
}
