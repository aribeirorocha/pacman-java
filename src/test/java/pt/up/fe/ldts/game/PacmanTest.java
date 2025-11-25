package pt.up.fe.ldts.game;

import org.junit.jupiter.api.Test;
import pt.up.fe.ldts.game.model.Arena;
import pt.up.fe.ldts.game.model.Position;
import pt.up.fe.ldts.game.model.element.*;

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
    @Test
    void testPacmanCollectsPointIncreasesScore() {
        Arena arena = new Arena(5, 5);
        Pacman pacman = arena.getPacman();
        Point coin = new Point(pacman.getPosition().getX() + 1, pacman.getPosition().getY());
        arena.getWalls().clear();
        arena.getWalls().add(new Wall(0,0));
        arena.getPacman().setPosition(new Position(2,2));
        arena.getWalls().removeIf(w -> w.getPosition().equals(new Position(3,2)));
        arena.getPacman().setPosition(new Position(2,2));
        arena.getPacman().addScore(0);

        int initialScore = pacman.getScore();
        arena.processKey(new com.googlecode.lanterna.input.KeyStroke(
                com.googlecode.lanterna.input.KeyType.ArrowRight));

        assertEquals(initialScore + 1, pacman.getScore());
    }
    @Test
    void testPacmanDiesWhenCollidesWithGhost() {
        Arena arena = new Arena(10, 10);
        Pacman pacman = arena.getPacman();

        Ghost ghost = new Ghoststop(pacman.getPosition().getX() + 1, pacman.getPosition().getY());
        arena.getGhosts().add(ghost);

        arena.processKey(new com.googlecode.lanterna.input.KeyStroke(
                com.googlecode.lanterna.input.KeyType.ArrowRight));

        assertTrue(arena.isGameOver(), "Game over after death");
    }




}
