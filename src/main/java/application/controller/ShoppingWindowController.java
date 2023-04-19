package application.controller;

import application.web.ImageCollectorClient;

public class ShoppingWindowController {

    private ImageCollectorClient imageCollectorClient;

    public ShoppingWindowController() {
        imageCollectorClient = new ImageCollectorClient();
    }

    public byte[] getOnlineImageToSetProductImageView(String imageURL){
        return imageCollectorClient.requestImageData(imageURL);
    }
}
