package core;

import java.util.UUID;

public interface ViewObserver {

    enum PossibleViews{
        SIGNIN, CREATEACCOUNT, PROFILE, PRODUCTS
    }

    void changeView(PossibleViews newView);

    void changeView(PossibleViews newView, UUID userUUID);

}
