package application.viewController;

import application.controller.ProductsPageController;
import application.core.ObservableView;
import application.core.ViewObserver;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import application.persistence.assets.LogoType;

import java.io.ByteArrayInputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.UUID;

/**
 * Provides the controller of the ProductsPageView.fxml view therefore controlling the effects of interactions
 * with the view. It implements initializable so the methods initialize is overridden. It also implements ObservableView
 * to follow an observer-observable design pattern with ThePetShopAppLauncher class.
 */
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

    //GridPane
    @FXML
    GridPane productsGridPane;


    /**
     * This is the constructor. It initializes a controller of the Class ProductPageController and assigns it to
     * the controller attribute. It initializes the observerList which will contain ViewObserver implementations that are
     * triggered by particular actions or events following the observer and observable design pattern. It also initializes and assigns
     * the GridViewFiller which will fill the view with a grid of product info sections.
     * @param userUUID UUID the id of the user so in the case of a buying process this user is identified
     */
    public ProductsPageViewController(UUID userUUID) {

        controller = new ProductsPageController(userUUID);
        observerList = new ArrayList<>();
        gridViewfiller = new GridViewFiller(controller,observerList);

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

        setLogoImage();

        setProductGridView();

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

        System.out.println("Starting to set gridView"); //---------------------------------------------------------------DELETE

        HBox[] productCardsToDisplay = gridViewfiller.getGridViewProductDisplay();
        int rowIndex = 0;
        int columnIndex = 0;

        //Make sure the grid is empty before putting new gridded content
        productsGridPane.getChildren().clear();
        
        for (HBox hBox : productCardsToDisplay) {

            productsGridPane.add(hBox, columnIndex, rowIndex);
            columnIndex++;
            if (columnIndex > controller.getNumberProductsPerRow() - 1) {
                columnIndex = 0;
                rowIndex++;
            }
        }
    }

    @Override
    public void addObserver(ViewObserver currentViewObserver) {
        observerList.add(currentViewObserver);
        gridViewfiller.updateObserverList(observerList);

    }
}
