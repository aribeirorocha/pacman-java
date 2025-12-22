package pt.up.fe.ldts.game.Unit_Tests.model;

import org.junit.jupiter.api.Test;
import pt.up.fe.ldts.game.model.Arena;
import pt.up.fe.ldts.game.model.Position;
import pt.up.fe.ldts.game.model.element.Wall;

import static org.junit.jupiter.api.Assertions.*;

public class ArenaWallTest {
    @Test
    void testPacmanBlockedByWalll1() {
        Arena arena = new Arena(10, 10,1);
        Position start = arena.getPacman().getPosition();
        Wall wall = new Wall(start.getX() + 1, start.getY());
        arena.getWalls().add(wall);

        arena.processKey(new com.googlecode.lanterna.input.KeyStroke(
                com.googlecode.lanterna.input.KeyType.ArrowRight));

        assertEquals(start, arena.getPacman().getPosition());
    }
    @Test
    void testPacmanBlockedByWalll2() {
        Arena arena = new Arena(10, 10,2);
        Position start = arena.getPacman().getPosition();
        Wall wall = new Wall(start.getX() + 1, start.getY());
        arena.getWalls().add(wall);

        arena.processKey(new com.googlecode.lanterna.input.KeyStroke(
                com.googlecode.lanterna.input.KeyType.ArrowRight));

        assertEquals(start, arena.getPacman().getPosition());
    }
    @Test
    void testPacmanBlockedByWalll3() {
        Arena arena = new Arena(10, 10,3);
        Position start = arena.getPacman().getPosition();
        Wall wall = new Wall(start.getX() + 1, start.getY());
        arena.getWalls().add(wall);

        arena.processKey(new com.googlecode.lanterna.input.KeyStroke(
                com.googlecode.lanterna.input.KeyType.ArrowRight));

        assertEquals(start, arena.getPacman().getPosition());
    }
}
