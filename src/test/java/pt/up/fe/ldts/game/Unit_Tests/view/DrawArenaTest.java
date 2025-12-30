package pt.up.fe.ldts.game.Unit_Tests.view;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.up.fe.ldts.game.graphics.GraphicInterface;
import pt.up.fe.ldts.game.model.Arena;
import pt.up.fe.ldts.game.model.Position;
import pt.up.fe.ldts.game.model.element.*;
import pt.up.fe.ldts.game.view.DrawArena;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class DrawArenaTest {
    private Arena arena;
    private GraphicInterface gui;
    private Pacman pacman;
    private DrawArena drawArena;

    @BeforeEach
    void setUp() {
        arena = mock(Arena.class);
        gui = mock(GraphicInterface.class);
        pacman = mock(Pacman.class);
        drawArena = new DrawArena();

        // Configuração básica para evitar NullPointer e erros de Random
        when(arena.getPacman()).thenReturn(pacman);
        when(pacman.getPosition()).thenReturn(new Position(10, 10));
        when(arena.getWidth()).thenReturn(40);
        when(arena.getHeight()).thenReturn(20);
    }

    @Test
    void testDrawNormalGameplay() throws InterruptedException, IOException {
        // Estado: Em jogo
        when(arena.isGameOver()).thenReturn(false);
        when(arena.isGameWon()).thenReturn(false);

        // Mock de elementos para percorrer todos os ciclos 'for'
        when(arena.getWalls()).thenReturn(Collections.singletonList(new Wall(1, 1)));
        when(arena.getPoints()).thenReturn(Collections.singletonList(new Point(2, 2)));
        when(arena.getPowerPellets()).thenReturn(Collections.singletonList(new PowerPellet(3, 3)));
        when(arena.getGhosts()).thenReturn(Collections.singletonList(new Ghost(4, 4, "#FF0000")));

        // HUD
        when(arena.getTotalPoints()).thenReturn(10);
        when(pacman.isImmune()).thenReturn(true);
        when(pacman.getImmuneTimeLeft()).thenReturn(5000L);

        drawArena.draw(arena, gui);

        verify(gui, atLeast(2)).setForegroundColor("#FFFFFF");
        verify(gui).putChar(2, 2, ".");

        // Outras cores
        verify(gui, atLeastOnce()).setForegroundColor("#0000FF");
        verify(gui, atLeastOnce()).setForegroundColor("#FFD700");
    }

    @Test
    void testDrawGameOver() throws InterruptedException, IOException {
        when(arena.isGameOver()).thenReturn(true);
        when(pacman.getScore()).thenReturn(150);

        drawArena.draw(arena, gui);

        verify(gui).setForegroundColor("#FF0000");
        // Verifica se a string de score aparece
        verify(gui, atLeastOnce()).putChar(anyInt(), anyInt(), anyString());
    }

    @Test
    void testDrawGameWin() throws InterruptedException, IOException {
        when(arena.isGameOver()).thenReturn(false);
        when(arena.isGameWon()).thenReturn(true);
        when(pacman.getScore()).thenReturn(500);

        drawArena.draw(arena, gui);



        verify(gui, atLeastOnce()).setForegroundColor("#00FF00");

        verify(gui, atLeast(20)).putChar(anyInt(), anyInt(), eq("*"));
    }

    @Test
    void testHUDDivisionByZero() throws InterruptedException {
        when(arena.isGameOver()).thenReturn(false);
        when(arena.isGameWon()).thenReturn(false);
        when(arena.getPoints()).thenReturn(Collections.emptyList());

        // Caso em que total de pontos é 0 (Mata mutante de divisão por zero)
        when(arena.getTotalPoints()).thenReturn(0);

        drawArena.draw(arena, gui);

        // Verifica se o HUD desenhou a percentagem 0%
        verify(gui, atLeastOnce()).putChar(anyInt(), anyInt(), eq("0"));
    }

    @Test
    void testIOExceptionHandling() throws IOException {
        // Testa o bloco catch que lança RuntimeException no clear()
        doThrow(new IOException()).when(gui).clear();

        assertThrows(RuntimeException.class, () -> drawArena.draw(arena, gui));
    }

    @Test
    void testMultipleGhostColors() throws InterruptedException {
        Ghost redGhost = new Ghost(1, 1, "#FF0000");
        Ghost pinkGhost = new Ghost(2, 2, "#FFB8FF");

        when(arena.isGameOver()).thenReturn(false);
        when(arena.isGameWon()).thenReturn(false);
        when(arena.getGhosts()).thenReturn(Arrays.asList(redGhost, pinkGhost));
        when(arena.getWalls()).thenReturn(Collections.emptyList());
        when(arena.getPoints()).thenReturn(Collections.emptyList());
        when(arena.getPowerPellets()).thenReturn(Collections.emptyList());

        drawArena.draw(arena, gui);


        verify(gui).setForegroundColor("#FF0000");
        verify(gui).setForegroundColor("#FFB8FF");
        verify(gui).putChar(1, 1, "G");
        verify(gui).putChar(2, 2, "G");
    }
    @Test
    void testHUDPercentageCalculation() throws InterruptedException {
        when(arena.isGameOver()).thenReturn(false);
        when(arena.isGameWon()).thenReturn(false);


        when(arena.getTotalPoints()).thenReturn(10);
        when(arena.getPoints()).thenReturn(Arrays.asList(new Point(0,0), new Point(0,0), new Point(0,0), new Point(0,0), new Point(0,0)));
        when(pacman.isImmune()).thenReturn(false);

        drawArena.draw(arena, gui);



        verify(gui, atLeastOnce()).putChar(anyInt(), anyInt(), eq("5"));
        verify(gui, atLeastOnce()).putChar(anyInt(), anyInt(), eq("0"));
    }
    @Test
    void testHUDImmunityTimeFormatting() throws InterruptedException {
        when(arena.isGameOver()).thenReturn(false);
        when(arena.isGameWon()).thenReturn(false);
        when(pacman.isImmune()).thenReturn(true);


        when(pacman.getImmuneTimeLeft()).thenReturn(5500L);
        when(arena.getTotalPoints()).thenReturn(10);
        when(arena.getPoints()).thenReturn(Collections.emptyList());

        drawArena.draw(arena, gui);


        verify(gui, atLeastOnce()).putChar(anyInt(), anyInt(), eq("5"));

        verify(gui, atLeastOnce()).putChar(anyInt(), anyInt(), eq("P"));
    }
    @Test
    void testConfettiColors() throws InterruptedException {
        when(arena.isGameOver()).thenReturn(false);
        when(arena.isGameWon()).thenReturn(true);
        when(pacman.getScore()).thenReturn(10);

        drawArena.draw(arena, gui);



        verify(gui, atLeast(21)).setForegroundColor(argThat(color ->
                color.equals("#00FF00") || color.equals("#FF0000") ||
                        color.equals("#FFFF00") || color.equals("#00FFFF")
        ));
    }
    @Test
    void testGetRestartText() {

        assertEquals("Press R to Restart", drawArena.getRestartText());
    }
    @Test
    void testStringDrawingLogic() throws InterruptedException {
        when(arena.isGameOver()).thenReturn(true);
        when(arena.getWidth()).thenReturn(40);
        when(arena.getHeight()).thenReturn(20);
        when(pacman.getScore()).thenReturn(10);

        drawArena.draw(arena, gui);


        String msg = "GAME OVER! Score: 10";
        int startX = (40 - msg.length()) / 2;

        verify(gui).putChar(eq(startX), anyInt(), eq("G"));
        verify(gui).putChar(eq(startX + 1), anyInt(), eq("A"));
        verify(gui).putChar(eq(startX + 2), anyInt(), eq("M"));
    }
    @Test
    void testKillSurvivorsInNormalGameplay() throws InterruptedException, IOException {

        when(arena.isGameOver()).thenReturn(false);
        when(arena.isGameWon()).thenReturn(false);

        when(arena.getWalls()).thenReturn(Collections.singletonList(new Wall(1, 1)));
        when(arena.getPowerPellets()).thenReturn(Collections.singletonList(new PowerPellet(3, 3)));
        when(arena.getPoints()).thenReturn(Collections.emptyList());
        when(arena.getGhosts()).thenReturn(Collections.emptyList());

        drawArena.draw(arena, gui);


        verify(gui).putChar(1, 1, "█");
        verify(gui).putChar(3, 3, "O");
        verify(gui).setForegroundColor("#FFFF33");
        verify(gui).putChar(10, 10, "C");
        verify(gui).refresh();
    }
    @Test
    void testKillHUDMathSurvivors() throws InterruptedException {
        when(arena.isGameOver()).thenReturn(false);
        when(arena.isGameWon()).thenReturn(false);


        when(arena.getTotalPoints()).thenReturn(200);
        when(arena.getPoints()).thenReturn(Arrays.asList(new Point(0,0), new Point(0,0)));

        when(pacman.isImmune()).thenReturn(true);
        when(pacman.getImmuneTimeLeft()).thenReturn(5000L);

        drawArena.draw(arena, gui);

        verify(gui, atLeast(2)).putChar(anyInt(), anyInt(), eq("9")); // Percentagem 99%
        verify(gui, atLeastOnce()).putChar(anyInt(), anyInt(), eq("5")); // Tempo 5s


        verify(gui, atLeastOnce()).enableBold();
    }
    @Test
    void testKillGameOverAndWinCenteringSurvivors() throws InterruptedException {

        when(arena.isGameOver()).thenReturn(true);
        when(pacman.getScore()).thenReturn(100);

        drawArena.draw(arena, gui);


        verify(gui).setForegroundColor("#FF0000"); // Game Over Red
        verify(gui).setForegroundColor("#FFFFFF"); // Restart White (Mata 91.1)
        verify(gui, atLeastOnce()).enableBold(); // Mata 87.1, 98.1


        verify(gui).putChar(eq(9), anyInt(), eq("G"));
    }
    @Test
    void testKillConfettiBoundarySurvivor() throws InterruptedException {
        when(arena.isGameOver()).thenReturn(false);
        when(arena.isGameWon()).thenReturn(true);

        drawArena.draw(arena, gui);

        verify(gui, times(20)).putChar(anyInt(), anyInt(), eq("*"));
    }
    @Test
    void testCenteringMathematics() throws InterruptedException {
        when(arena.isGameOver()).thenReturn(true);
        when(arena.getWidth()).thenReturn(40);
        when(arena.getHeight()).thenReturn(20);
        when(pacman.getScore()).thenReturn(100);

        drawArena.draw(arena, gui);

       verify(gui).putChar(eq(9), eq(10), eq("G"));

        verify(gui).putChar(eq(11), eq(12), eq("P"));
    }
    @Test
    void testConfettiExactCount() throws InterruptedException {
        when(arena.isGameOver()).thenReturn(false);
        when(arena.isGameWon()).thenReturn(true);

        drawArena.draw(arena, gui);

        verify(gui, times(20)).putChar(anyInt(), anyInt(), eq("*"));
    }

}