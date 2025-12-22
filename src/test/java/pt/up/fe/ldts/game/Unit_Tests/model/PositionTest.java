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
}