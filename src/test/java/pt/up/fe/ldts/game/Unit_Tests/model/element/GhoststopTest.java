package pt.up.fe.ldts.game.Unit_Tests.model.element;

import org.junit.jupiter.api.Test;
import pt.up.fe.ldts.game.model.Position;
import pt.up.fe.ldts.game.model.element.Ghoststop;

import static org.junit.jupiter.api.Assertions.*;

public class GhoststopTest {

    @Test
    void testMoveReturnsCurrentPosition() {
        Ghoststop ghost = new Ghoststop(5, 10);

        Position currentPos = ghost.getPosition();
        Position nextPos = ghost.move();

        assertNotNull(nextPos, "O movimento não deve retornar null");

        assertEquals(currentPos.getX(), nextPos.getX());
        assertEquals(currentPos.getY(), nextPos.getY());
        assertSame(currentPos, nextPos, "Deve retornar a mesma instância de posição");
    }
}