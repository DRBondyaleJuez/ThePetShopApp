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


public class GridViewFiller {

    private ProductsPageController controller;
    private ArrayList<ViewObserver> productsPageObserverList;
    private ShoppingWindowLauncher shoppingWindowLauncher;

    public GridViewFiller(ProductsPageController controller, ArrayList<ViewObserver> productsPageObserverList) {
        this.controller = controller;
        this.productsPageObserverList = productsPageObserverList;
        shoppingWindowLauncher = new ShoppingWindowLauncher();
    }

    public void updateObserverList(ArrayList<ViewObserver> newProductsPageObserverList){
        productsPageObserverList = newProductsPageObserverList;
    }

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
