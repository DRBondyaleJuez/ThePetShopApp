package application.viewController;

import application.controller.ProductsPageController;
import application.core.ObservableView;
import application.core.ViewObserver;
import application.model.ProductDisplayInfo;
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
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

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

    private final ProductsPageController controller;
    private final ArrayList<ViewObserver> observerList;

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
     * triggered by particular actions or events following the observer and observable design pattern.
     * @param userUUID UUID the id of the user so in the case of a buying process this user is identified
     */
    public ProductsPageViewController(UUID userUUID) {

        controller = new ProductsPageController(userUUID);
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

        return (EventHandler<MouseEvent>) mouseEvent -> {

            int currentPageNumber = controller.changePageNumber(arrowClicked);
            currentPageNumberTextField.setText(currentPageNumber+"");
            setProductGridView();

        };

    }

    //The buttons back
    private EventHandler<? super MouseEvent> goBackToPreviousPage() {
        return (EventHandler<MouseEvent>) mouseEvent -> {
            if(controller.getCurrentUserUUID() == null) {
                for (ViewObserver stalker : observerList) {
                    stalker.changeView(ViewObserver.PossibleViews.SIGNIN);
                }
            } else {
                for (ViewObserver stalker : observerList) {
                    stalker.changeView(ViewObserver.PossibleViews.PROFILE,controller.getCurrentUserUUID());
                }
            }
        };
    }

    private void setProductGridView() {

        System.out.println("Starting to set gridView"); //---------------------------------------------------------------DELETE

        HBox[] productCardsToDisplay = getGridViewProductDisplay();
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

    /**
     * Builds an array of HBoxes which correspond to the product info display for each product and that will be assigned to the
     * Grid sections
     * <p>
     *     To do this it collects from the controller all the objects of the class ProductDisplayInfo which encapsulate the information necessary
     *     represent the product and uses this information to build each product's display info.
     *     During the display info of each product a buy button is added to them with an event where pressing the buy button
     *     triggers the display of another window to confirm the number of units to buy and showing an image.
     * </p>
     * @return Array of HBox
     */
    public HBox[] getGridViewProductDisplay(){

        ProductDisplayInfo[] currentProductsDisplayInfo = controller.getCurrentProductsDisplayInfo();

        HBox[] productDisplayCards = new HBox[currentProductsDisplayInfo.length];

        for (int i = 0; i < productDisplayCards.length; i++) {
            productDisplayCards[i] = buildProductCard(currentProductsDisplayInfo[i]);
        }

        return productDisplayCards;
    }

    private HBox buildProductCard(ProductDisplayInfo currentProductDisplayInfo){

        HBox currentHBox = new HBox();

        //Building the children of the HBox
        ImageView productImageView = new ImageView();
        productImageView.setFitWidth(currentHBox.getWidth()/2.5);

        VBox productInfoVBox = new VBox();
        productInfoVBox.setSpacing(5);
        Label productNameLabel = new Label(currentProductDisplayInfo.getProductName());
        Label productSubtypeLabel = new Label(currentProductDisplayInfo.getSubtype());
        Label productPriceLabel = new Label(currentProductDisplayInfo.getPrice());
        Label inStockLabel = new Label();
        inStockLabel.setFont(new Font(10));
        if(currentProductDisplayInfo.getStockInfo()) {
            inStockLabel.setText("IN STOCK");
            Color color = Color.GREEN;
            inStockLabel.setTextFill(color);
        } else {
            inStockLabel.setText("OUT OF STOCK");
            Color color = Color.DARKRED;
            inStockLabel.setTextFill(color);
        }

        Button buyButton = new Button("BUY");
        buyButton.setOnMouseClicked(setShoppingProcedureAction(currentProductDisplayInfo));

        productInfoVBox.getChildren().add(productNameLabel);
        productInfoVBox.getChildren().add(productSubtypeLabel);
        productInfoVBox.getChildren().add(productPriceLabel);
        productInfoVBox.getChildren().add(inStockLabel);
        if(currentProductDisplayInfo.getStockInfo()) {
            productInfoVBox.getChildren().add(buyButton);
        }

        //Adding children to the HBox
        currentHBox.getChildren().add(productImageView);
        currentHBox.getChildren().add(productInfoVBox);

        return currentHBox;
    }

    private EventHandler<? super MouseEvent> setShoppingProcedureAction(ProductDisplayInfo currentProductInfo) {

        return (EventHandler<MouseEvent>) mouseEvent -> {

            if(controller.getCurrentUserUUID() == null){
                for (ViewObserver stalker : observerList) {
                    stalker.changeView(ViewObserver.PossibleViews.SIGNIN);
                }
                return;
            }

            System.out.println("Shopping Procedure Triggered");
            for (ViewObserver stalker : observerList) {
                stalker.loadShoppingWindow(currentProductInfo,controller.getCurrentUserUUID());
            }
        };
    }

    @Override
    public void addObserver(ViewObserver currentViewObserver) {
        observerList.add(currentViewObserver);
    }
}
