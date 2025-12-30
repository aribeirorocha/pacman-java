package pt.up.fe.ldts.game.Unit_Tests.model.element;

import org.junit.jupiter.api.Test;
import pt.up.fe.ldts.game.model.Position;
import pt.up.fe.ldts.game.model.element.Ghost;

import static org.junit.jupiter.api.Assertions.*;


public class GhostTest {

    @Test
    void testGhostConstructorAndColor() {
        // Agora 'new Ghost' refere-se corretamente à classe do model
        Ghost ghost = new Ghost(10, 20, "#00FF00");

        assertEquals(10, ghost.getPosition().getX());
        assertEquals(20, ghost.getPosition().getY());
        assertEquals("#00FF00", ghost.getColor());
        assertNotNull(ghost.getDirection());
    }

    @Test
    void testGhostSpeed() {
        Ghost ghost = new Ghost(5, 5);

        ghost.setSpeed(2);
        assertEquals(2, ghost.getSpeed());

        // Mata mutante: Testa se o Math.max(1, speed) funciona
        ghost.setSpeed(0);
        assertEquals(1, ghost.getSpeed());
    }

    @Test
    void testNextPosition() {
        Ghost ghost = new Ghost(5, 5);
        Position move = new Position(1, 0);

        Position next = ghost.getNextPosition(move);

        assertEquals(6, next.getX());
        assertEquals(5, next.getY());
    }

    @Test
    void testDirectionSetters() {
        Ghost ghost = new Ghost(5, 5);
        Position newDir = new Position(0, 1);

        ghost.setDirection(newDir);
        assertEquals(newDir, ghost.getDirection());
    }
}