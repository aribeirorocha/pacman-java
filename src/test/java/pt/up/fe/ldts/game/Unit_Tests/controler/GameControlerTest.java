package pt.up.fe.ldts.game.Unit_Tests.controler;

import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import pt.up.fe.ldts.game.controler.GameControler;
import pt.up.fe.ldts.game.model.Arena;
import pt.up.fe.ldts.game.model.Position;
import pt.up.fe.ldts.game.model.element.*;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

public class GameControlerTest {
    @Mock Arena arena;
    @Mock Pacman pacman;
    @Mock Ghost ghost;
    private GameControler controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        controller = new GameControler();

        // Mock default behaviors to avoid NullPointerExceptions
        when(arena.getPacman()).thenReturn(pacman);
        when(arena.getWalls()).thenReturn(new ArrayList<>());
        when(arena.getGhosts()).thenReturn(new ArrayList<>());
        when(arena.getPoints()).thenReturn(new ArrayList<>());
        when(arena.getPowerPellets()).thenReturn(new ArrayList<>());
    }

    // --- ORIGINAL TESTS ---

    @Test
    void testMovePacmanIntoWall() {
        Position currentPos = new Position(1, 1);
        when(pacman.getNextPosition()).thenReturn(new Position(2, 1));
        when(pacman.getPosition()).thenReturn(currentPos);
        when(arena.getWalls()).thenReturn(Collections.singletonList(new Wall(2, 1)));

        controller.moveElements(arena);

        verify(pacman, never()).setPosition(any(Position.class));
        verify(pacman).setDirection(argThat(pos -> pos.getX() == 0 && pos.getY() == 0));
    }

    @Test
    void testGhostEatsPacman() {
        Position samePos = new Position(5, 5);
        when(arena.getGhosts()).thenReturn(Collections.singletonList(ghost));
        when(pacman.getPosition()).thenReturn(samePos);
        when(ghost.getPosition()).thenReturn(samePos);
        when(pacman.isImmune()).thenReturn(false);

        controller.moveElements(arena);

        verify(arena).setGameOver(true);
    }

    @Test
    void testPacmanEatsGhostWithImmunity() {
        Position samePos = new Position(5, 5);
        Position cagePos = new Position(10, 10);
        when(arena.getGhosts()).thenReturn(Collections.singletonList(ghost));
        when(pacman.getPosition()).thenReturn(samePos);
        when(ghost.getPosition()).thenReturn(samePos);
        when(pacman.isImmune()).thenReturn(true);
        when(arena.getGhostCagePosition()).thenReturn(cagePos);

        controller.moveElements(arena);

        verify(ghost).setPosition(cagePos);
        verify(arena, never()).setGameOver(true);
    }

    @Test
    void testProcessKeyBufferedInput() {
        when(pacman.moveUp()).thenReturn(new Position(1, 0));
        when(pacman.getPosition()).thenReturn(new Position(1, 1));

        controller.processKey(new KeyStroke(KeyType.ArrowUp), pacman, Collections.emptyList(),
                Collections.emptyList(), Collections.emptyList(), arena);

        verify(pacman).setBufferedDirection(argThat(pos -> pos.getX() == 0 && pos.getY() == -1));
    }

    @Test
    void testRestartOnGameOverKey() {
        when(arena.isGameOver()).thenReturn(true);
        KeyStroke rKey = new KeyStroke('r', false, false);

        controller.processKey(rKey, pacman, Collections.emptyList(), Collections.emptyList(),
                Collections.emptyList(), arena);

        verify(arena).restart();
    }

    // --- ADVANCED TESTS (Mutation / Reflection) ---

    @Test
    void testIsOppositeLogic_Reflection() throws Exception {
        // KILLS MUTANTS 18 and 19 (Private Boolean Logic)
        Method method = GameControler.class.getDeclaredMethod("isOpposite", Position.class, Position.class);
        method.setAccessible(true);

        Position up = new Position(0, -1);
        Position down = new Position(0, 1);
        Position left = new Position(-1, 0);

        // Verify correct opposites
        assertTrue((boolean) method.invoke(controller, up, down));
        // Verify non-opposites
        assertFalse((boolean) method.invoke(controller, up, left));
        // Verify null safety
        assertFalse((boolean) method.invoke(controller, null, down));
    }



    @Test
    void testGhostSpeedLoopAndMovement() {
        // KILLS MUTANT 129 (Loop Boundaries)

        // --- FIX: TYPO FIXED (Ghoststop -> Ghost) ---
        // Assuming 'Ghost' is the concrete class.
        Ghost movingGhost = new Ghost(5, 5);
        movingGhost.setDirection(new Position(0, 1)); // Current Direction: Down

        // Walls surrounding everything except Right
        List<Wall> walls = Arrays.asList(
                new Wall(5, 6), // Block Front (Down)
                new Wall(5, 4), // Block Back (Up - Opposite)
                new Wall(4, 5)  // Block Left
        );
        // Only (6, 5) -> Right is free

        controller.moveGhosts(Collections.singletonList(movingGhost), walls, pacman);

        // Assert the ghost moved to the only open spot
        assertEquals(new Position(6, 5), movingGhost.getPosition());
        assertEquals(new Position(1, 0), movingGhost.getDirection());
    }
}