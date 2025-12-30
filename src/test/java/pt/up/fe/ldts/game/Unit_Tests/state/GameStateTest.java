package pt.up.fe.ldts.game.Unit_Tests.state;

import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.up.fe.ldts.game.graphics.GraphicInterface;
import pt.up.fe.ldts.game.model.Arena;
import pt.up.fe.ldts.game.state.GameState;
import pt.up.fe.ldts.game.state.LevelSelectState;
import pt.up.fe.ldts.game.state.State;

import java.io.IOException;
import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class GameStateTest {
    private GameState gameState;
    private GraphicInterface gui;

    @BeforeEach
    void setUp() throws IOException {
        // Começamos no nível 1
        gameState = new GameState(1);
        gui = mock(GraphicInterface.class);
    }

    @Test
    void testInitArenaLevels() throws IOException, NoSuchFieldException, IllegalAccessException {

        GameState g1 = new GameState(1);
        GameState g2 = new GameState(2);
        GameState g3 = new GameState(3);
        GameState gDefault = new GameState(99);


        assertNotNull(getArena(g1));
        assertNotNull(getArena(g2));
        assertNotNull(getArena(g3));
        assertNotNull(getArena(gDefault));
    }

    @Test
    void testDrawCallsArenaMethods() throws InterruptedException, NoSuchFieldException, IllegalAccessException {
        Arena arenaMock = mock(Arena.class);
        setArena(gameState, arenaMock);

        gameState.draw(gui);


        verify(arenaMock).update();
        verify(arenaMock).draw(gui);
    }

    @Test
    void testProcessKeyEscapeTransitions() throws IOException {
        KeyStroke escapeKey = new KeyStroke(KeyType.Escape);

        State nextState = gameState.processKey(escapeKey);


        assertTrue(nextState instanceof LevelSelectState);
    }

    @Test
    void testProcessKeyNormalPlay() throws IOException, NoSuchFieldException, IllegalAccessException {
        Arena arenaMock = mock(Arena.class);
        setArena(gameState, arenaMock);
        KeyStroke anyKey = new KeyStroke(KeyType.ArrowUp);

        State nextState = gameState.processKey(anyKey);


        verify(arenaMock).processKey(anyKey);

        assertEquals(gameState, nextState);
    }

    @Test
    void testRestartLogicOnGameOver() throws IOException, NoSuchFieldException, IllegalAccessException {
        Arena arenaMock = mock(Arena.class);
        when(arenaMock.isGameOver()).thenReturn(true);
        setArena(gameState, arenaMock);


        KeyStroke rKey = new KeyStroke('r', false, false);

        gameState.processKey(rKey);


        Arena currentArena = getArena(gameState);
        assertNotEquals(arenaMock, currentArena, "A arena devia ter sido reiniciada");
    }

    @Test
    void testNoRestartIfGameNotOver() throws IOException, NoSuchFieldException, IllegalAccessException {
        Arena arenaMock = mock(Arena.class);
        when(arenaMock.isGameOver()).thenReturn(false);
        setArena(gameState, arenaMock);

        KeyStroke rKey = new KeyStroke('r', false, false);
        gameState.processKey(rKey);


        assertEquals(arenaMock, getArena(gameState));
    }


    private Arena getArena(GameState state) throws NoSuchFieldException, IllegalAccessException {
        Field field = GameState.class.getDeclaredField("arena");
        field.setAccessible(true);
        return (Arena) field.get(state);
    }

    private void setArena(GameState state, Arena arena) throws NoSuchFieldException, IllegalAccessException {
        Field field = GameState.class.getDeclaredField("arena");
        field.setAccessible(true);
        field.set(state, arena);
    }
}