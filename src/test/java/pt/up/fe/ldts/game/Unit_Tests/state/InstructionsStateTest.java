package pt.up.fe.ldts.game.Unit_Tests.state;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import pt.up.fe.ldts.game.graphics.GraphicInterface;
import pt.up.fe.ldts.game.state.InstructionsState;
import pt.up.fe.ldts.game.state.MainMenuState;
import pt.up.fe.ldts.game.state.State;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class InstructionsStateTest {
    private InstructionsState state;
    private GraphicInterface gui;

    @BeforeEach
    void setUp() {
        state = new InstructionsState();
        gui = mock(GraphicInterface.class);
    }

    @Test
    void testDrawFullCoverage() throws IOException {
        // Executa o método
        state.draw(gui);

        // Verifica limpeza e refresh (Mata linhas 12 e 26)
        verify(gui, times(1)).clear();
        verify(gui, times(1)).refresh();

        // --- VERIFICAÇÃO EXAUSTIVA DE CADA LINHA DE TEXTO ---
        // Se qualquer uma destas linhas for removida pelo PIT, o verify falha e o mutante morre.

        // Linha 13
        verify(gui).drawText(10, 3, "===== INSTRUCTIONS =====");

        // Linha 14 (SURVIVED anteriormente)
        verify(gui).drawText(10, 6, "Use Arrow Keys to move Pacman:");

        // Linha 15
        verify(gui).drawText(12, 7, "↑  Up");

        // Linha 16 (SURVIVED anteriormente)
        verify(gui).drawText(12, 8, "↓  Down");

        // Linha 17 (SURVIVED anteriormente)
        verify(gui).drawText(12, 9, "←  Left");

        // Linha 18 (SURVIVED anteriormente)
        verify(gui).drawText(12, 10, "→  Right");

        // Linha 20 (SURVIVED anteriormente)
        verify(gui).drawText(10, 13, "Objective:");

        // Linha 21 (SURVIVED anteriormente)
        verify(gui).drawText(12, 14, "- Collect all points ");

        // Linha 22 (SURVIVED anteriormente)
        verify(gui).drawText(12, 15, "- Avoid the ghosts ");

        // Linha 23 (SURVIVED anteriormente)
        verify(gui).drawText(12, 16, "- Escape through corridors and survive!");

        // Linha 25
        verify(gui).drawText(10, 19, "Press ENTER or ESC to return to Main Menu");
    }

    @Test
    void testProcessKeyTransitions() throws IOException {
        // Testar ENTER
        State s1 = state.processKey(new KeyStroke(KeyType.Enter));
        assertTrue(s1 instanceof MainMenuState);

        // Testar ESCAPE
        State s2 = state.processKey(new KeyStroke(KeyType.Escape));
        assertTrue(s2 instanceof MainMenuState);
    }

    @Test
    void testStayInState() throws IOException {
        KeyStroke randomKey = new KeyStroke('a', false, false);
        State same = state.processKey(randomKey);

        assertEquals(state, same);
        assertNotNull(same);
    }
}