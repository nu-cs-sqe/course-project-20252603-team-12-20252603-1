package domain.location;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class LocationTest {

    @Test
    public void ConstructorOnRepresentativeInputHorizontalCoordinateMatches() {
        Location location = new Location(3, 4);
        int expected = 3;
        int actual = location.getX();

        assertEquals(expected, actual);
    }

    @Test
    public void ConstructorOnRepresentativeInputVerticalCoordinateMatches() {
        Location location = new Location(3, 4);
        int expected = 4;
        int actual = location.getY();

        assertEquals(expected, actual);
    }
}
