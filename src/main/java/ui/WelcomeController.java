package ui;

public class WelcomeController {

    private final WelcomeView welcomeView;

    public WelcomeController() {
        welcomeView = new WelcomeView();
    }

    public void show() {
        welcomeView.setVisible(true);
    }

    void startGame() {
        welcomeView.setVisible(false);
        welcomeView.dispose();
    }

    WelcomeView getWelcomeView() {
        return welcomeView;
    }
}
