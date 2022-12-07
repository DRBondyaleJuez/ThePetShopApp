package core;

public interface ViewObserver {

    enum PossibleViews{
        SIGNIN, CREATEACCOUNT, PROFILE, PRODUCTS
    }

    void changeView(PossibleViews newView);

}
