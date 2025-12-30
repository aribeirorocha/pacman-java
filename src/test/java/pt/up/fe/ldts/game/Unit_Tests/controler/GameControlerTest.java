package pt.up.fe.ldts.game.Unit_Tests.controler;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import pt.up.fe.ldts.game.controler.GameControler;
import pt.up.fe.ldts.game.model.Arena;
import pt.up.fe.ldts.game.model.Position;
import pt.up.fe.ldts.game.model.element.Ghost;
import pt.up.fe.ldts.game.model.element.Pacman;
import pt.up.fe.ldts.game.model.element.Wall;

import java.util.Collections;
import static org.mockito.Mockito.*;

public class GameControlerTest {

    private GameControler gameControler;

    @Mock
    private Arena mockArena;
    @Mock
    private Pacman mockPacman;
    @Mock
    private Ghost mockGhost;
    @Mock
    private Wall mockWall;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        gameControler = new GameControler();

        when(mockArena.getPacman()).thenReturn(mockPacman);
        when(mockArena.getGhosts()).thenReturn(Collections.singletonList(mockGhost));
        when(mockArena.getWalls()).thenReturn(Collections.emptyList());
        when(mockArena.getPoints()).thenReturn(Collections.emptyList());
        when(mockArena.isGameOver()).thenReturn(false);
        when(mockArena.isGameWon()).thenReturn(false);

        when(mockPacman.getPosition()).thenReturn(new Position(5, 5));
        when(mockPacman.getNextPosition()).thenReturn(new Position(6, 5));
        doNothing().when(mockPacman).setPosition(any());

        when(mockGhost.getDirection()).thenReturn(new Position(1, 0));
        when(mockGhost.getNextPosition(any())).thenReturn(new Position(10, 11));
        doNothing().when(mockGhost).setDirection(any());
        doNothing().when(mockGhost).setPosition(any());
        when(mockGhost.getPosition()).thenReturn(new Position(10, 10));
    }

    @Test
    void testGhostCollisionSetsGameOver() {
        Position collisionPosition = new Position(5, 5);

        when(mockPacman.getPosition()).thenReturn(collisionPosition);
        when(mockGhost.getPosition()).thenReturn(collisionPosition);

        gameControler.moveElements(mockArena);

        verify(mockArena, atLeastOnce()).setGameOver(true);
    }

    @Test
    void testNoGhostCollision() {
        gameControler.moveElements(mockArena);

        verify(mockArena, never()).setGameOver(true);
    }

    @Test
    void testMovementWhenNoWall() {
        Position nextValidPosition = new Position(6, 5);

        when(mockPacman.getNextPosition()).thenReturn(nextValidPosition);
        when(mockArena.getWalls()).thenReturn(Collections.emptyList());

        gameControler.moveElements(mockArena);

        verify(mockPacman, times(1)).setPosition(nextValidPosition);
    }

    @Test
    void testMovementBlockedByWall() {
        Position nextInvalidPosition = new Position(6, 5);

        when(mockPacman.getNextPosition()).thenReturn(nextInvalidPosition);

        when(mockWall.getPosition()).thenReturn(nextInvalidPosition);
        when(mockArena.getWalls()).thenReturn(Collections.singletonList(mockWall));

        gameControler.moveElements(mockArena);

        verify(mockPacman, never()).setPosition(any());

        verify(mockPacman, atLeastOnce()).getPosition();
    }
}