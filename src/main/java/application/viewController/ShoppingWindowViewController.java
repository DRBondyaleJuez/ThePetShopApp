package application.viewController;

import application.controller.ShoppingWindowController;
import application.persistence.assets.LogoType;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import application.model.ProductDisplayInfo;
import application.utils.ShoppingWindowLauncher;

import java.io.ByteArrayInputStream;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ResourceBundle;

public class ShoppingWindowViewController implements Initializable {

    ShoppingWindowController controller;
    ProductDisplayInfo currentProductInfo;
    ShoppingWindowLauncher launcher;

    @FXML
    ImageView productImageView;
    @FXML
    Label productNameLabel;
    @FXML
    Label priceLabel;
    @FXML
    TextField unitsTextField;
    @FXML
    Button unitButton;
    @FXML
    Button confirmButton;
    @FXML
    Button rejectButton;
    public ShoppingWindowViewController(ProductDisplayInfo currentProductInfo) {
        this.currentProductInfo = currentProductInfo;
        controller = new ShoppingWindowController();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setName();
        setPrice();
        setProductImageView(currentProductInfo.getImageURL());

        unitButton.setOnMouseClicked(setUnitButton());

        confirmButton.setOnMouseClicked(setConfirmButton());
        rejectButton.setOnMouseClicked(setRejectButton());
    }

    public void addLauncherObserver(ShoppingWindowLauncher shoppingWindowLauncher){
        launcher = shoppingWindowLauncher;
    }

    private void setPrice() {
        priceLabel.setText(currentProductInfo.getPrice());
    }

    private void setName() {
        productNameLabel.setText(currentProductInfo.getProductName() + " " + currentProductInfo.getSubtype());
    }

    public void setProductImageView(String imageURL){
        byte[] imageByteArray = controller.getOnlineImageToSetProductImageView(imageURL);
        Image productImage = new Image(new ByteArrayInputStream(imageByteArray));
        //productImageView.setPreserveRatio(true);
        productImageView.setImage(productImage);
    }

    private EventHandler<? super MouseEvent> setRejectButton() {
        return new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {

                launcher.closeShoppingWindow();

            }
        };
    }

    private EventHandler<? super MouseEvent> setConfirmButton() {
        return new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {

                //TODO: create methods to update database information in purchase table
                System.out.println("Confirm purchase");

                launcher.closeShoppingWindow();

            }
        };
    }

    private EventHandler<? super MouseEvent> setUnitButton() {
        return new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {

                try{
                    int num = Integer.parseInt(unitsTextField.getText());
                    // is an integer!
                } catch (NumberFormatException e) {
                    // not an integer!
                    unitsTextField.setText(1+"");
                }

                double unitPrice = Double.parseDouble(currentProductInfo.getPrice().replace(" €","").replace(",","."));
                double totalPrice = unitPrice * Integer.parseInt(unitsTextField.getText());
                DecimalFormat df = new DecimalFormat("#.00");
                String displayPrice = df.format(totalPrice) + "";
                priceLabel.setText(displayPrice.replace(".",",") + " €");
            }
        };
    }

}
