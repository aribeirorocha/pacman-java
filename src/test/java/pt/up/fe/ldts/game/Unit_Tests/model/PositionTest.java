package pt.up.fe.ldts.game.Unit_Tests.model;

import org.junit.jupiter.api.Test;
import pt.up.fe.ldts.game.model.Position;

import static org.junit.jupiter.api.Assertions.*;

public class PositionTest {
    @Test
    void testPositionsAreEqual() {
        Position p1 = new Position(2, 3);
        Position p2 = new Position(2, 3);
        assertEquals(p1, p2);
    }
    @Test
    void testPositionLogic() {
        Position p1 = new Position(10, 10);
        Position p2 = new Position(10, 10);
        Position p3 = new Position(11, 10);


        assertEquals(p1, p2);
        assertNotEquals(p1, p3);
        assertNotEquals(p1, null);
        assertNotEquals(p1, "string");


        assertEquals(p1.hashCode(), p2.hashCode());
        assertNotEquals(p1.hashCode(), p3.hashCode());


        assertEquals(10, p1.getX());
        assertEquals(10, p1.getY());
    }
}