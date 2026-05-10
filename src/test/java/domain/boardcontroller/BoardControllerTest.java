package domain.boardcontroller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class BoardControllerTest {

    @Test
    void Constructor_FreshInstance_LastSelectedUnset() {
        BoardController controller = new BoardController();

        boolean expected = false;
        boolean actual = controller.hasSelection();
        assertEquals(expected, actual);
    }
}
