package pt.up.fe.ldts.game.controler;

import com.googlecode.lanterna.input.KeyStroke;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import pt.up.fe.ldts.game.model.Arena;
import pt.up.fe.ldts.game.model.element.Pacman;

import java.util.List;

public class GameControlerRestartTest {

    @Test
    void testRestartOnRKey() {
        Arena arena = new Arena(10, 10);
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
