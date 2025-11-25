package pt.up.fe.ldts.game.model;

import org.junit.jupiter.api.Test;
import pt.up.fe.ldts.game.model.element.Wall;

import static org.junit.jupiter.api.Assertions.*;

public class ArenaWallTest {
    @Test
    void testPacmanBlockedByWall() {
        Arena arena = new Arena(10, 10);
        Position start = arena.getPacman().getPosition();
        Wall wall = new Wall(start.getX() + 1, start.getY());
        arena.getWalls().add(wall);

        arena.processKey(new com.googlecode.lanterna.input.KeyStroke(
                com.googlecode.lanterna.input.KeyType.ArrowRight));

        assertEquals(start, arena.getPacman().getPosition());
    }
}
