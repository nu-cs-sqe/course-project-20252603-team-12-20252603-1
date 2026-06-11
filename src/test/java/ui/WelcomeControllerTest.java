package ui;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.Window;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

class WelcomeControllerTest {

    @AfterEach
    void disposeOpenUiWindows() {
        for (Window window : Window.getWindows()) {
            if (window instanceof WelcomeView || window instanceof MainView) {
                window.dispose();
            }
        }
    }

    @Test
    void Show_WhenCalled_WelcomeViewBecomesVisible() {
        WelcomeController controller = new WelcomeController();
        controller.show();
        assertTrue(controller.getWelcomeView().isVisible());
    }
}
