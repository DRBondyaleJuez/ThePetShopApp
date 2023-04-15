package viewController;

import controller.ShoppingWindowController;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import model.ProductDisplayInfo;
import utils.ShoppingWindowLauncher;

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
        controller = null;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setName();
        setPrice();

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
