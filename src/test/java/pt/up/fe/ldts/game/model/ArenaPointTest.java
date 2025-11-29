package pt.up.fe.ldts.game.model;

import org.junit.jupiter.api.Test;
import pt.up.fe.ldts.game.model.element.Pacman;
import pt.up.fe.ldts.game.model.element.Point;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;

import static org.junit.jupiter.api.Assertions.*;

public class ArenaPointTest {
    @Test
    void testPacmanCollectsPointIncreasesScore() {
        Arena arena = new Arena(5, 5);
        Pacman pacman = arena.getPacman();

        arena.getPoints().clear();
        arena.getPoints().add(new Point(3, 2));

        pacman.setPosition(new Position(2, 2));

        int initialScore = pacman.getScore();

        pacman.setDirection(new Position(1, 0));

        Position nextPosition = pacman.getNextPosition();
        pacman.setPosition(nextPosition);

        Point collectedPoint = null;
        for (Point p : arena.getPoints()) {
            if (p.getPosition().equals(pacman.getPosition())) {
                collectedPoint = p;
                break;
            }
        }

        if (collectedPoint != null) {
            pacman.addScore(1);
            arena.getPoints().remove(collectedPoint);
        }

        assertEquals(initialScore + 1, pacman.getScore());
    }
}