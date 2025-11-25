package pt.up.fe.ldts.game.model;

import org.junit.jupiter.api.Test;
import pt.up.fe.ldts.game.model.element.Pacman;

import static org.junit.jupiter.api.Assertions.*;

public class ArenaPointTest {
    @Test
    void testPacmanCollectsPointIncreasesScore() {
        Arena arena = new Arena(5, 5);
        Pacman pacman = arena.getPacman();

        arena.getWalls().clear();
        arena.getPacman().setPosition(new Position(2,2));
        arena.getWalls().removeIf(w -> w.getPosition().equals(new Position(3,2)));

        int initialScore = pacman.getScore();
        arena.processKey(new com.googlecode.lanterna.input.KeyStroke(
                com.googlecode.lanterna.input.KeyType.ArrowRight));

        assertEquals(initialScore + 1, pacman.getScore());
    }
}
