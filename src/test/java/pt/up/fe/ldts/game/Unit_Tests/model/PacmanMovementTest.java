package pt.up.fe.ldts.game.Unit_Tests.model;

import org.junit.jupiter.api.Test;
import pt.up.fe.ldts.game.model.Position;
import pt.up.fe.ldts.game.model.element.Pacman;

import static org.junit.jupiter.api.Assertions.*;

public class PacmanMovementTest {
    @Test
    void testPacmanMovesRight() {
        Pacman pacman = new Pacman(0, 0);
        Position newPos = pacman.moveRight();
        assertEquals(new Position(1, 0), newPos);
    }

    @Test
    void testPacmanMovesLeft() {
        Pacman pacman = new Pacman(2, 0);
        Position newPos = pacman.moveLeft();
        assertEquals(new Position(1, 0), newPos);
    }

    @Test
    void testPacmanMovesUp() {
        Pacman pacman = new Pacman(0, 2);
        Position newPos = pacman.moveUp();
        assertEquals(new Position(0, 1), newPos);
    }

    @Test
    void testPacmanMovesDown() {
        Pacman pacman = new Pacman(0, 0);
        Position newPos = pacman.moveDown();
        assertEquals(new Position(0, 1), newPos);
    }
}