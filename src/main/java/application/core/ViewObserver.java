package application.core;

import application.model.ProductDisplayInfo;

import java.util.UUID;

public interface ViewObserver {

    enum PossibleViews{
        SIGNIN, CREATEACCOUNT, PROFILE, PRODUCTS
    }

    void changeView(PossibleViews newView);

    void changeView(PossibleViews newView, UUID userUUID);

    void loadShoppingWindow(ProductDisplayInfo productDisplayInfo, UUID userId);

}
