package application.viewController;

import application.controller.ShoppingWindowController;
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
import application.core.ShoppingWindowLauncher;

import java.io.ByteArrayInputStream;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ResourceBundle;
import java.util.UUID;

/**
 * Provides the controller of the ShoppingWindowView.fxml view therefore controlling the effects of interactions
 * with the view. It implements initializable so the methods initialize is overridden.
 */
public class ShoppingWindowViewController implements Initializable {

    ShoppingWindowController controller;
    ProductDisplayInfo currentProductInfo;
    UUID userId;
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

    /**
     * This is the constructor. It initializes a controller of the Class ShoppingWindowController and assigns it to
     * the controller attribute. It also assigns its attributes for possible future actions.
     * @param userId UUID the id of the user so in the case of a buying process this user is identified
     * @param currentProductInfo ProductDisplayInfo object encapsulating the information of the product selected to buy to make it
     *                           easily searchable in the database
     */
    public ShoppingWindowViewController(ProductDisplayInfo currentProductInfo, UUID userId) {
        this.currentProductInfo = currentProductInfo;
        this.userId = userId;
        controller = new ShoppingWindowController();
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
                String quantityString = unitsTextField.getText();
                if(checkIfItIsANumber(quantityString)) {
                    boolean confirmShopping = controller.insertNewPurchaseInfo(currentProductInfo.getProductId(),userId,Integer.parseInt(quantityString));

                    launcher.closeShoppingWindow();
                }
            }
        };
    }

    private boolean checkIfItIsANumber(String possibleNumber){
        try{
            int intValue = Integer.parseInt(possibleNumber);
            return true;
        } catch (NumberFormatException e){
            return false;
        }
    }

    private EventHandler<? super MouseEvent> setUnitButton() {
        return new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {

                //Check if the content of the unitsTextField is a number
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
