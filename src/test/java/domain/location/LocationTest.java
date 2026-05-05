package domain.location;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LocationTest {

    @Test
    public void Constructor_OnRepresentativeInput_GetXReturnsX() {
        Location location = new Location(3, 4);
        int expected = 3;
        int actual = location.getX();

        assertEquals(expected, actual);
    }
}