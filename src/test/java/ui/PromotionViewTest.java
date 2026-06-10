package ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

import domain.piece.PieceColor;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.awt.GraphicsEnvironment;
import java.util.Locale;
import javax.swing.JDialog;
import javax.swing.JFrame;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class PromotionViewTest {

    @SuppressFBWarnings(
            value = "RV_RETURN_VALUE_IGNORED_NO_SIDE_EFFECT",
            justification = "Return value not needed; called for side-effectful headless check")
    @BeforeAll
    static void requireDisplay() {
        assumeTrue(
                !GraphicsEnvironment.getLocalGraphicsEnvironment().isHeadless(),
                "Skipping PromotionView tests: no display available");
    }

    @AfterEach
    void disposeOpenSwingWindows() {
        for (java.awt.Window window : java.awt.Window.getWindows()) {
            if (window instanceof JFrame || window instanceof JDialog) {
                window.dispose();
            }
        }
    }

    @Test
    void Constructor_OnEnglishLocale_DialogTitleFromBundle() {
        PromotionView view = new PromotionView(new JFrame(), PieceColor.WHITE, Locale.ENGLISH);

        String expected = "Promote Pawn";
        String actual = view.getDialogTitleText();
        assertEquals(expected, actual);
    }

    @Test
    void Constructor_OnEnglishLocale_PromptLabelFromBundle() {
        PromotionView view = new PromotionView(new JFrame(), PieceColor.WHITE, Locale.ENGLISH);

        String expected = "Choose promotion piece:";
        String actual = view.getPromptLabelText();
        assertEquals(expected, actual);
    }

    @Test
    void Constructor_OnSpanishLocale_DialogTitleFromBundle() {
        PromotionView view = new PromotionView(
                new JFrame(), PieceColor.WHITE, Locale.forLanguageTag("es"));

        String expected = "Promover peón";
        String actual = view.getDialogTitleText();
        assertEquals(expected, actual);
    }

    @Test
    void Constructor_OnSpanishLocale_PromptLabelFromBundle() {
        PromotionView view = new PromotionView(
                new JFrame(), PieceColor.WHITE, Locale.forLanguageTag("es"));

        String expected = "Elija la pieza de promoción:";
        String actual = view.getPromptLabelText();
        assertEquals(expected, actual);
    }
}
