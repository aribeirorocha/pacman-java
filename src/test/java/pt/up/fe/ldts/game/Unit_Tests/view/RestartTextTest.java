package pt.up.fe.ldts.game.Unit_Tests.view;

import org.junit.jupiter.api.Test;
import pt.up.fe.ldts.game.view.DrawArena;
import pt.up.fe.ldts.game.model.Arena;
import pt.up.fe.ldts.game.model.element.Pacman;
import pt.up.fe.ldts.game.graphics.GraphicInterface;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class RestartTextTest {

    @Test
    void testRestartTextContent() {
        DrawArena drawArena = new DrawArena();

        String restartMessage = drawArena.getRestartText();
        assertEquals("Press R to Restart", restartMessage);
    }

    @Test
    void testRestartTextIsDrawn() throws InterruptedException {
        DrawArena drawArena = new DrawArena();
        Arena arena = mock(Arena.class);
        GraphicInterface gui = mock(GraphicInterface.class);
        Pacman pacman = mock(Pacman.class);

        // Configura o estado para forçar a escrita do texto de restart
        when(arena.isGameOver()).thenReturn(true);
        when(arena.getPacman()).thenReturn(pacman);
        when(pacman.getScore()).thenReturn(100);
        when(arena.getWidth()).thenReturn(20);
        when(arena.getHeight()).thenReturn(20);

        drawArena.draw(arena, gui);

        // Verifica se o texto (ou partes dele) foi enviado para a interface
        verify(gui, atLeastOnce()).putChar(anyInt(), anyInt(), anyString());
    }
}