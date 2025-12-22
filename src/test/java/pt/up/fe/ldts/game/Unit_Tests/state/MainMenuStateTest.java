package pt.up.fe.ldts.game.Unit_Tests.state;

import org.junit.jupiter.api.Test;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import pt.up.fe.ldts.game.state.LevelSelectState;
import pt.up.fe.ldts.game.state.MainMenuState;
import pt.up.fe.ldts.game.state.State;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class MainMenuStateTest {

    @Test
    void testEnterStartsGame() throws IOException {
        MainMenuState state = new MainMenuState();
        KeyStroke enter = new KeyStroke(KeyType.Enter);

        State next = state.processKey(enter);
        assertTrue(next instanceof LevelSelectState);
    }

    @Test
    void testOtherKeyDoesNothing() throws IOException {
        MainMenuState state = new MainMenuState();
        KeyStroke other = new KeyStroke('A', false, false);

        State next = state.processKey(other);
        assertEquals(state, next);
    }
}
