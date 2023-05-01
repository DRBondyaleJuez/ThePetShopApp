package application.core;

import application.model.ProductDisplayInfo;

import java.util.UUID;

/**
 * Provides an interface representing one of the components of an observer-observable design pattern. This component
 * represents an object that can observe this facilitates triggers and interactions caused by objects which implement
 * the observable equivalent interface.
 */
public interface ViewObserver {

    enum PossibleViews{
        SIGNIN, CREATEACCOUNT, PROFILE, PRODUCTS
    }

    /**
     * Overloaded method to change the layout displayed by a scene without any further information than the desired view
     * @param newView PossibleViews enum which informs about the desired view
     */
    void changeView(PossibleViews newView);

    /**
     * Overloaded method to change the layout displayed by a scene in this case informing about the user changing the view
     * apart from the desired view
     * @param newView PossibleViews enum which informs about the desired view
     * @param userUUID UUID corresponding to the user soliciting the view change
     */
    void changeView(PossibleViews newView, UUID userUUID);

    /**
     * Method for the particular scene display of the shopping window which requires product and user information
     * @param productDisplayInfo ProductDisplayInfo object encapsulating all the info required about the selected product for the window
     * @param userId UUID corresponding to the user who selected the product
     */
    void loadShoppingWindow(ProductDisplayInfo productDisplayInfo, UUID userId);

}
