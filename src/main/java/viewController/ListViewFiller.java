package viewController;

import controller.ProfilePageController;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class ListViewFiller {

    private ProfilePageController controller;

    public ListViewFiller(ProfilePageController controller) {
        this.controller = controller;
    }

    //This method returns the HBox corresponding to a particular position
    public HBox getPurchasedProductEntry(int positionNumber){

        //Calculate the position in the database of the information
        int sourceInformationPosition = (controller.getCurrentRecentPurchasePageNumber()-1) * controller.getNumberOfEntriesPerPage() + positionNumber;

        //If the position is larger than the number of items purchased by the user there is no more information and null is returned
        if(sourceInformationPosition > controller.getNumberOfPurchasesByUser()-1) return null;

        //If the position is adequate the Entry is constructed from the information retrieved from the database

        //First retieve and store info temporarily
        String [] currentPurchasedItemInformation = controller.getPurchaseRecordInfo(positionNumber);
        PurchasedItem currentPurchaseItem = new PurchasedItem(currentPurchasedItemInformation);

        System.out.println("This should display the purchase item information: " + currentPurchaseItem); // DELETE WHEN FINISHED ---------------------------------------------------------------------------------------------------------

        //Then build the HBox that will display the info inna certain distribution
        HBox purchaseProductEntry = new HBox();

        Label filler = new Label(currentPurchaseItem.productCompleteName + " ");
        purchaseProductEntry.getChildren().add(filler);
        filler = new Label(currentPurchaseItem.purchasedQuantity + " ");
        purchaseProductEntry.getChildren().add(filler);
        filler = new Label(currentPurchaseItem.purchasePrice + " € ");
        purchaseProductEntry.getChildren().add(filler);
        filler = new Label(currentPurchaseItem.purchaseDate + " ");
        purchaseProductEntry.getChildren().add(filler);

        return purchaseProductEntry;
    }

    private class PurchasedItem {

        private String productType;
        private String productCompleteName;

        private int purchasedQuantity;
        private double purchasePrice;
        private String purchaseDate;

        private PurchasedItem(String [] currentPurchasedItemInfo) {

            productType = currentPurchasedItemInfo[0];
            productCompleteName = currentPurchasedItemInfo[1] + " (" + currentPurchasedItemInfo[2] + ")";
            purchasedQuantity = Integer.parseInt(currentPurchasedItemInfo[3]);
            String formattedPrice = currentPurchasedItemInfo[4].replace(",",".").replace(" €","");
            purchasePrice =  purchasedQuantity * Double.parseDouble(formattedPrice);
            purchaseDate = currentPurchasedItemInfo[5];

        }

        public String toString(){
            return productCompleteName + " " + purchasedQuantity + purchasePrice + " €" + " " + purchaseDate;
        }
    }

}
