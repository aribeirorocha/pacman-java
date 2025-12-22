package pt.up.fe.ldts.game.Unit_Tests.model;

import org.junit.jupiter.api.Test;

import pt.up.fe.ldts.game.model.Arena;
import pt.up.fe.ldts.game.model.Position;
import pt.up.fe.ldts.game.model.element.Ghost;
import pt.up.fe.ldts.game.model.element.Ghoststop;
import pt.up.fe.ldts.game.model.element.Pacman;



import static org.junit.jupiter.api.Assertions.*;

public class ArenaGhostTest {

    @Test
    void testPacmanDiesWhenCollidesWithGhostl1() {
        Arena arena = new Arena(10, 10, 1);
        Pacman pacman = arena.getPacman();
        arena.getGhosts().clear();
        pacman.setPosition(new Position(2, 2));
        pacman.setDirection(new Position(1, 0));
        Ghost ghost = new Ghoststop(3, 2);
        ghost.setSpeedForTest(0);
        arena.getGhosts().add(ghost);
        arena.update();
        assertTrue(arena.isGameOver());
    }

    @Test
    void testPacmanDiesWhenCollidesWithGhostl2() {
        Arena arena = new Arena(10, 10, 2);
        Pacman pacman = arena.getPacman();
        arena.getGhosts().clear();
        pacman.setPosition(new Position(2, 2));
        pacman.setDirection(new Position(1, 0));
        Ghost ghost = new Ghoststop(3, 2);
        ghost.setSpeedForTest(0);
        arena.getGhosts().add(ghost);
        arena.update();
        assertTrue(arena.isGameOver());
    }
    @Test
    void testPacmanDiesWhenCollidesWithGhostl3() {
        Arena arena = new Arena(10, 10, 3);
        Pacman pacman = arena.getPacman();
        arena.getGhosts().clear();
        pacman.setPosition(new Position(2, 2));
        pacman.setDirection(new Position(1, 0));
        Ghost ghost = new Ghoststop(3, 2);
        ghost.setSpeedForTest(0);
        arena.getGhosts().add(ghost);
        arena.update();
        assertTrue(arena.isGameOver());
    }
}