package ui;

public class WelcomeController {

    private final WelcomeView welcomeView;

    public WelcomeController() {
        welcomeView = new WelcomeView();
    }

    WelcomeView getWelcomeView() {
        return welcomeView;
    }
}
