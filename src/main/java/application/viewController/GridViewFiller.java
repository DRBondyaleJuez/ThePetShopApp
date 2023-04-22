package application.viewController;

import application.controller.ProductsPageController;
import application.core.ViewObserver;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import application.model.ProductDisplayInfo;
import application.utils.ShoppingWindowLauncher;

import java.util.ArrayList;

/**
 * Provides an encapsulation of the responsibilities to build the grid of products that will be displayed in the ProductPageView.fxml view.
 * This class partially follows the observer observable design pattern. In this case a particular action triggers a change in the view
 * that requires interacting with the observer
 */
public class GridViewFiller {

    private ProductsPageController controller;
    private ArrayList<ViewObserver> productsPageObserverList;
    private ShoppingWindowLauncher shoppingWindowLauncher;

    /**
     * This is the constructor.
     * The controller is assigned.
     * @param controller ProductPageController object which corresponds to the controller of the ProductPageViewController which instantiated this object
     * @param productsPageObserverList List of ViewObservers following the partial observable-observer design pattern
     */
    public GridViewFiller(ProductsPageController controller, ArrayList<ViewObserver> productsPageObserverList) {
        this.controller = controller;
        this.productsPageObserverList = productsPageObserverList;
        shoppingWindowLauncher = new ShoppingWindowLauncher();
    }

    /**
     * This method adds or eliminates ViewObservers from the observer list based on the list provided
     * @param newProductsPageObserverList list of ViewObserver to update the current observer list
     */
    public void updateObserverList(ArrayList<ViewObserver> newProductsPageObserverList){
        productsPageObserverList = newProductsPageObserverList;
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

        return new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {

                if(controller.getCurrentUserUUID() == null){
                    for (ViewObserver stalker : productsPageObserverList) {
                        stalker.changeView(ViewObserver.PossibleViews.SIGNIN);
                    }
                    return;
                }

                System.out.println("Shopping Procedure Triggered");
                try {
                    shoppingWindowLauncher.start(currentProductInfo,controller.getCurrentUserUUID());
                } catch (Exception e) {
                    System.out.println("Exception: " + e + ". When shopping window was going to be launched.");
                    throw new RuntimeException(e);
                }
            }
        };
    }
}
