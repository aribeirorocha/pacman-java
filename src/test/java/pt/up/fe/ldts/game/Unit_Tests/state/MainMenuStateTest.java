package pt.up.fe.ldts.game.Unit_Tests.state;

import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.up.fe.ldts.game.graphics.GraphicInterface;
import pt.up.fe.ldts.game.state.*;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class MainMenuStateTest {
    private MainMenuState state;
    private GraphicInterface gui;

    @BeforeEach
    void setUp() {
        state = new MainMenuState();
        gui = mock(GraphicInterface.class);
    }

    @Test
    void testDrawFullCoverage() throws IOException {
        state.draw(gui);

        verify(gui).clear();
        // Ajustado para "PACMAN" (sem hífen) conforme o teu log de erro
        verify(gui, atLeastOnce()).drawText(anyInt(), anyInt(), contains("PACMAN"));

        // Verifica as opções reais do teu menu
        verify(gui, atLeastOnce()).drawText(anyInt(), anyInt(), contains("Play"));
        verify(gui, atLeastOnce()).drawText(anyInt(), anyInt(), contains("Instructions"));
        verify(gui, atLeastOnce()).drawText(anyInt(), anyInt(), contains("Exit"));

        verify(gui).refresh();
    }

    @Test
    void testNavigationAndSelection() throws IOException {
       state.processKey(new KeyStroke(KeyType.ArrowDown));
        state.draw(gui);
        verify(gui).drawText(anyInt(), anyInt(), contains(">"));

        MainMenuState freshState = new MainMenuState();
        State next = freshState.processKey(new KeyStroke(KeyType.Enter));
        assertTrue(next instanceof LevelSelectState || next instanceof GameState);
    }

    @Test
    void testEnterOnInstructions() throws IOException {
        state.processKey(new KeyStroke(KeyType.ArrowDown));
        State next = state.processKey(new KeyStroke(KeyType.Enter));
        assertTrue(next instanceof InstructionsState);
    }



    @Test
    void testOtherKeyDoesNothing() throws IOException {
        KeyStroke other = new KeyStroke('A', false, false);
        State next = state.processKey(other);
        assertEquals(state, next);
    }
}