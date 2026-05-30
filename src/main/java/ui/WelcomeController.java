package ui;

public class WelcomeController {

    private final WelcomeView welcomeView;

    public WelcomeController() {
        welcomeView = new WelcomeView();
    }

    public void show() {
        welcomeView.setVisible(true);
    }

    WelcomeView getWelcomeView() {
        return welcomeView;
    }
}
