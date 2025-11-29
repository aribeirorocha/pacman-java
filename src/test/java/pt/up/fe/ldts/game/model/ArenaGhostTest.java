package pt.up.fe.ldts.game.model;

import org.junit.jupiter.api.Test;

import pt.up.fe.ldts.game.model.element.Ghost;
import pt.up.fe.ldts.game.model.element.Ghoststop;
import pt.up.fe.ldts.game.model.element.Pacman;



import static org.junit.jupiter.api.Assertions.*;

public class ArenaGhostTest {

    @Test
    void testPacmanDiesWhenCollidesWithGhost() {
        Arena arena = new Arena(10, 10);
        Pacman pacman = arena.getPacman();

        arena.getGhosts().clear();
        pacman.setPosition(new Position(2, 2));

        Position collisionPosition = new Position(3, 2);
        Ghost ghost = new Ghoststop(collisionPosition.getX(), collisionPosition.getY());
        arena.getGhosts().add(ghost);

        pacman.setDirection(new Position(1, 0));

        Position nextPosition = pacman.getNextPosition();
        pacman.setPosition(nextPosition);


        for (Ghost g : arena.getGhosts()) {
            if (pacman.getPosition().equals(g.getPosition())) {
                arena.setGameOver(true);
                break;
            }
        }

        assertEquals(collisionPosition, pacman.getPosition());
        assertTrue(arena.isGameOver());
    }
}