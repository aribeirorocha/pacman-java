package pt.up.fe.ldts.game.model;

import org.junit.jupiter.api.Test;
import pt.up.fe.ldts.game.model.element.*;

import static org.junit.jupiter.api.Assertions.*;

public class ArenaGhostTest {

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
