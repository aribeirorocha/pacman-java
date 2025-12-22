package pt.up.fe.ldts.game.Unit_Tests.state;

import org.junit.jupiter.api.Test;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import pt.up.fe.ldts.game.state.InstructionsState;
import pt.up.fe.ldts.game.state.MainMenuState;
import pt.up.fe.ldts.game.state.State;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class InstructionsStateTest {

    @Test
    void testEscReturnsToMenu() throws IOException {
        InstructionsState state = new InstructionsState();
        KeyStroke esc = new KeyStroke(KeyType.Escape);

        State next = state.processKey(esc);
        assertTrue(next instanceof MainMenuState);
    }

    @Test
    void testOtherKeyDoesNothing() throws IOException {
        InstructionsState state = new InstructionsState();
        KeyStroke other = new KeyStroke('X', false, false);

        State next = state.processKey(other);
        assertEquals(state, next);
    }
}
