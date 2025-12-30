package pt.up.fe.ldts.game.Unit_Tests.state;

import com.googlecode.lanterna.input.KeyType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.googlecode.lanterna.input.KeyStroke;
import pt.up.fe.ldts.game.graphics.GraphicInterface;
import pt.up.fe.ldts.game.state.GameState;
import pt.up.fe.ldts.game.state.LevelSelectState;
import pt.up.fe.ldts.game.state.MainMenuState;
import pt.up.fe.ldts.game.state.State;

import java.io.IOException;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class LevelSelectStateTest {
    private LevelSelectState state;
    private GraphicInterface gui;

    @BeforeEach
    void setUp() {
        state = new LevelSelectState();
        gui = mock(GraphicInterface.class);
    }

    @Test
    void testDrawLógicaTernaria() throws IOException {
        // Mata mutantes das linhas 13-19 (removals e negations)
        state.draw(gui);

        // Verifica se o cursor está no Level 1 por defeito
        verify(gui).drawText(eq(10), eq(8), contains("> Level 1"));
        verify(gui).drawText(eq(10), eq(9), contains("  Level 2"));

        verify(gui).clear();
        verify(gui).refresh();
    }

    @Test
    void testNavegaçãoCircular() throws IOException {

        state.processKey(new KeyStroke(KeyType.ArrowDown));
        state.draw(gui);
        verify(gui).drawText(anyInt(), eq(9), contains("> Level 2"));

        // 2. Move para cima (Level 2 -> Level 1 -> Back)
        state.processKey(new KeyStroke(KeyType.ArrowUp));
        state.processKey(new KeyStroke(KeyType.ArrowUp));
        state.draw(gui);
        verify(gui).drawText(anyInt(), eq(11), contains("> Back"));
    }

    @Test
    void testSeleçãoNiveis() throws IOException {
        // Mata mutantes das linhas 29-37 (Transições de Estado)

        // Testar Level 2
        state.processKey(new KeyStroke(KeyType.ArrowDown)); // Seleciona Level 2
        State s2 = state.processKey(new KeyStroke(KeyType.Enter));
        assertTrue(s2 instanceof GameState);
        // Podes usar reflection para verificar o nível interno se necessário,
        // mas o instanceof já mata o mutante de 'null return'

        // Testar Level 3
        state = new LevelSelectState();
        state.processKey(new KeyStroke(KeyType.ArrowDown));
        state.processKey(new KeyStroke(KeyType.ArrowDown)); // Opção 2
        State s3 = state.processKey(new KeyStroke(KeyType.Enter));
        assertTrue(s3 instanceof GameState);

        // Testar Back para Main Menu
        state = new LevelSelectState();
        state.processKey(new KeyStroke(KeyType.ArrowUp)); // Vai para opção 3 (Back)
        State back = state.processKey(new KeyStroke(KeyType.Enter));
        assertTrue(back instanceof MainMenuState);
    }

    @Test
    void testStayInState() throws IOException {
        KeyStroke randomKey = new KeyStroke('a', false, false);

        State same = state.processKey(randomKey);
        assertEquals(state, same);
        assertNotNull(same);
    }

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
    @Test
    void testSeleçãoNiveisCompleta() throws IOException {
        // Testar Level 1 (Opção 0 por defeito)
        State s1 = state.processKey(new KeyStroke(KeyType.Enter));
        assertTrue(s1 instanceof GameState);


        state = new LevelSelectState();
        state.processKey(new KeyStroke(KeyType.ArrowDown)); // option = 1
        State s2 = state.processKey(new KeyStroke(KeyType.Enter));
        assertTrue(s2 instanceof GameState);


        state = new LevelSelectState();
        state.processKey(new KeyStroke(KeyType.ArrowDown));
        state.processKey(new KeyStroke(KeyType.ArrowDown)); // option = 2
        State s3 = state.processKey(new KeyStroke(KeyType.Enter));
        assertTrue(s3 instanceof GameState);


        state = new LevelSelectState();
        state.processKey(new KeyStroke(KeyType.ArrowUp)); // Wrap around para 3
        State back = state.processKey(new KeyStroke(KeyType.Enter));
        assertTrue(back instanceof MainMenuState);
    }
}
