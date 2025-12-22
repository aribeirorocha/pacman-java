package pt.up.fe.ldts.game.Unit_Tests.model;

import org.junit.jupiter.api.Test;
import pt.up.fe.ldts.game.model.Arena;
import pt.up.fe.ldts.game.model.Position;
import pt.up.fe.ldts.game.model.element.Pacman;
import pt.up.fe.ldts.game.model.element.Point;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;

import static org.junit.jupiter.api.Assertions.*;

public class ArenaPointTest {

    @Test
    void testPacmanCollectsPointIncreasesScorel1() {
        Arena arena = new Arena(5, 5, 1);
        Pacman pacman = arena.getPacman();

        arena.getPoints().clear();
        arena.getPoints().add(new Point(3, 2));

        pacman.setPosition(new Position(2, 2));

        int initialScore = pacman.getScore();

        pacman.setDirection(new Position(1, 0));

        arena.update();

        assertEquals(initialScore + 10, pacman.getScore());
        assertTrue(arena.getPoints().isEmpty());
    }

    @Test
    void testPacmanCollectsPointIncreasesScorel2() {
        Arena arena = new Arena(5, 5, 2);
        Pacman pacman = arena.getPacman();

        arena.getPoints().clear();
        arena.getPoints().add(new Point(3, 2));

        pacman.setPosition(new Position(2, 2));

        int initialScore = pacman.getScore();

        pacman.setDirection(new Position(1, 0));

        arena.update();

        assertEquals(initialScore + 10, pacman.getScore());
        assertTrue(arena.getPoints().isEmpty());
    }

    @Test
    void testPacmanCollectsPointIncreasesScorel3() {
        Arena arena = new Arena(5, 5, 3);
        Pacman pacman = arena.getPacman();

        arena.getPoints().clear();
        arena.getPoints().add(new Point(3, 2));

        pacman.setPosition(new Position(2, 2));

        int initialScore = pacman.getScore();

        pacman.setDirection(new Position(1, 0));

        arena.update();

        assertEquals(initialScore + 10, pacman.getScore());
        assertTrue(arena.getPoints().isEmpty());
    }
}