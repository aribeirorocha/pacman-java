package pt.up.fe.ldts.game.Unit_Tests.state;

import com.googlecode.lanterna.input.KeyType;
import org.junit.jupiter.api.Test;
import com.googlecode.lanterna.input.KeyStroke;
import pt.up.fe.ldts.game.state.GameState;
import pt.up.fe.ldts.game.state.LevelSelectState;
import pt.up.fe.ldts.game.state.MainMenuState;
import pt.up.fe.ldts.game.state.State;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class LevelSelectStateTest {

    @Test
    void testSelectLevel1() throws IOException {
        LevelSelectState state = new LevelSelectState();
        KeyStroke enter = new KeyStroke(KeyType.Enter);

        State next = state.processKey(enter);

        assertTrue(next instanceof GameState);
    }

    @Test
    void testEscReturnsToMenu() throws IOException {
        LevelSelectState state = new LevelSelectState();
        KeyStroke esc = new KeyStroke(KeyType.Escape);
        State next = state.processKey(esc);
        assertEquals(state, next);
    }

    @Test
    void testInvalidKeyDoesNothing() throws IOException {
        LevelSelectState state = new LevelSelectState();
        KeyStroke key = new KeyStroke('X', false, false);

        State next = state.processKey(key);
        assertEquals(state, next);
    }
}
