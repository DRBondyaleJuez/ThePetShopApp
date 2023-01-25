package viewController;

import controller.ProductsPageController;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import model.ProductDisplayInfo;


public class GridViewFiller {

    private ProductsPageController controller;

    public GridViewFiller(ProductsPageController controller) {
        this.controller = controller;
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
        productInfoVBox.getChildren().add(productNameLabel);
        productInfoVBox.getChildren().add(productSubtypeLabel);
        productInfoVBox.getChildren().add(productPriceLabel);
        productInfoVBox.getChildren().add(inStockLabel);

        //Adding children to the HBox
        currentHBox.getChildren().add(productImageView);
        currentHBox.getChildren().add(productInfoVBox);

        return currentHBox;
    }

}
