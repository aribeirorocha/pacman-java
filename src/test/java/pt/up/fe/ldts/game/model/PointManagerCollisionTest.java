package pt.up.fe.ldts.game.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import pt.up.fe.ldts.game.model.element.Pacman;
import pt.up.fe.ldts.game.model.element.Point;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class PointManagerCollisionTest {

    private PointManager pointManager;

    @Mock
    private Pacman mockPacman;
    @Mock
    private Point mockPointColliding;
    @Mock
    private Point mockPointSafe;

    private List<Point> points;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        pointManager = new PointManager();

        Position collisionPosition = new Position(5, 5);
        Position safePosition = new Position(10, 10);

        when(mockPacman.getPosition()).thenReturn(collisionPosition);

        when(mockPointColliding.getPosition()).thenReturn(collisionPosition);
        when(mockPointSafe.getPosition()).thenReturn(safePosition);

        points = new ArrayList<>();
        points.add(mockPointColliding);
        points.add(mockPointSafe);
    }

    @Test
    void testPointCollectionAddsScore() {
        int initialSize = points.size();

        pointManager.checkPointCollision(mockPacman, points);

        verify(mockPacman, times(1)).addScore(10);

        assertEquals(initialSize - 1, points.size());
    }

    @Test
    void testOnlyOnePointCollected() {
        pointManager.checkPointCollision(mockPacman, points);

        verify(mockPointColliding, times(1)).getPosition();
        verify(mockPointSafe, never()).getPosition();

        assertEquals(1, points.size());
    }
}