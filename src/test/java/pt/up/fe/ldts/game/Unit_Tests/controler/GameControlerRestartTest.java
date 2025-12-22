package pt.up.fe.ldts.game.Unit_Tests.controler;

import com.googlecode.lanterna.input.KeyStroke;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import pt.up.fe.ldts.game.controler.GameControler;
import pt.up.fe.ldts.game.model.Arena;
import pt.up.fe.ldts.game.model.element.Pacman;

import java.util.List;

public class GameControlerRestartTest {

    @Test
    void testRestartOnRKeyl1() {
        Arena arena = new Arena(10, 10, 1);
        Pacman pacman = arena.getPacman();
        arena.setGameOver(true);
        GameControler controller = new GameControler();
        KeyStroke rKey = new KeyStroke('R', false, false);
        controller.processKey(rKey, pacman,
                List.of(), List.of(), List.of(), arena);
        assertFalse(arena.isGameOver(),
                "Arena deve reiniciar quando se pressiona R");
    }
    @Test
    void testRestartOnRKeyl2() {
        Arena arena = new Arena(10, 10, 2);
        Pacman pacman = arena.getPacman();
        arena.setGameOver(true);
        GameControler controller = new GameControler();
        KeyStroke rKey = new KeyStroke('R', false, false);
        controller.processKey(rKey, pacman,
                List.of(), List.of(), List.of(), arena);
        assertFalse(arena.isGameOver(),
                "Arena deve reiniciar quando se pressiona R");
    }
    @Test
    void testRestartOnRKeyl3() {
        Arena arena = new Arena(10, 10, 3);
        Pacman pacman = arena.getPacman();
        arena.setGameOver(true);
        GameControler controller = new GameControler();
        KeyStroke rKey = new KeyStroke('R', false, false);
        controller.processKey(rKey, pacman,
                List.of(), List.of(), List.of(), arena);
        assertFalse(arena.isGameOver(),
                "Arena deve reiniciar quando se pressiona R");
    }
}
