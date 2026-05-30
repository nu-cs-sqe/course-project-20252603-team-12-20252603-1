package ui;

import static org.junit.jupiter.api.Assertions.assertFalse;
import org.junit.jupiter.api.Test;

class WelcomeControllerTest {

    @Test
    void Constructor_FreshInstance_WelcomeViewNotVisible() {
        WelcomeController controller = new WelcomeController();
        assertFalse(controller.getWelcomeView().isVisible());
    }
}
