package viewController;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import model.ProductDisplayInfo;

import java.net.URL;
import java.util.ResourceBundle;

public class SingleProductViewController implements Initializable {

    ProductDisplayInfo productInfo;

    @FXML
    ImageView productImageView;

    @FXML
    Label productNameLabel;
    @FXML
    Label productSubtypeLabel;
    @FXML
    Label productPriceLabel;

    public SingleProductViewController(ProductDisplayInfo productInfo) {
        this.productInfo = productInfo;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setProductImage();
        setProductLabels();
    }

    private void setProductLabels() {
        //TODO: get the attributes and set them in the corresponding label
    }

    private void setProductImage() {
        //TODO: get the imageView and set them in the corresponding image using the byte[] information
    }
}
