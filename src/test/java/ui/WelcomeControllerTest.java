package ui;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

class WelcomeControllerTest {

    @Test
    void Constructor_FreshInstance_WelcomeViewNotVisible() {
        WelcomeController controller = new WelcomeController();
        assertFalse(controller.getWelcomeView().isVisible());
    }

    @Test
    void Show_WhenCalled_WelcomeViewBecomesVisible() {
        WelcomeController controller = new WelcomeController();
        controller.show();
        assertTrue(controller.getWelcomeView().isVisible());
    }
}
