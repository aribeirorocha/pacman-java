package pt.up.fe.ldts.game;

import com.googlecode.lanterna.gui2.Direction;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PacmanTest {

    @Test
    void testPositionsAreEqual() {
        Position p1 = new Position(2, 3);
        Position p2 = new Position(2, 3);
        assertEquals(p1, p2);
    }
    @Test
    void testPacmanMovesRight() {
        Pacman pacman = new Pacman(0, 0);
        Position newPos = pacman.moveRight();
        assertEquals(new Position(1, 0), newPos);
    }
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
