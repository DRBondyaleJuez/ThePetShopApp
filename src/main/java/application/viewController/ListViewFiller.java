package application.viewController;

import application.controller.ProfilePageController;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import application.model.UserPurchaseRecord;

import java.util.ArrayList;

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

        //First retrieve and store info temporarily
        UserPurchaseRecord currentUserSinglePurchaseRecord = controller.getSingleUserPurchaseRecord(positionNumber);

        System.out.println("This should display the purchase item information: " + currentUserSinglePurchaseRecord); // DELETE WHEN FINISHED ---------------------------------------------------------------------------------------------------------

        //Then build the HBox that will display the info inna certain distribution
        HBox purchaseProductEntry = new HBox();

        Label filler = new Label(currentUserSinglePurchaseRecord.getProductCompleteName() + " ");
        filler.setFont(new Font(filler.getFont().getName(),10));
        purchaseProductEntry.getChildren().add(filler);
        filler = new Label(currentUserSinglePurchaseRecord.getPurchasedQuantity() + " ");
        filler.setFont(new Font(filler.getFont().getName(),10));
        purchaseProductEntry.getChildren().add(filler);
        filler = new Label(currentUserSinglePurchaseRecord.getPurchasePrice()+ " € ");
        filler.setFont(new Font(filler.getFont().getName(),10));
        purchaseProductEntry.getChildren().add(filler);
        filler = new Label(currentUserSinglePurchaseRecord.getPurchaseDate() + " ");
        filler.setFont(new Font(filler.getFont().getName(),10));
        purchaseProductEntry.getChildren().add(filler);

        //Format correcting
        ObservableList<Node> nodeInTheHBox = purchaseProductEntry.getChildren();
        ArrayList<Label> labelsInTheEntry = new ArrayList<>();
        for (Node currentNode : nodeInTheHBox) {
            labelsInTheEntry.add((Label) currentNode);
        }
        for (Label currentLabel : labelsInTheEntry) {
            currentLabel.setFont(new Font(currentLabel.getFont().getName(),10));
        }

        purchaseProductEntry.setSpacing(25);

        return purchaseProductEntry;
    }



}
