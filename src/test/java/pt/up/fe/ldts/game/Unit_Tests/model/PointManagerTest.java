package pt.up.fe.ldts.game.Unit_Tests.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.up.fe.ldts.game.model.PointManager;
import pt.up.fe.ldts.game.model.Position;
import pt.up.fe.ldts.game.model.element.*;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class PointManagerTest {
    private PointManager manager;
    private List<Point> points;
    private List<Wall> walls;
    private List<Ghost> ghosts;
    private Pacman pacman;

    @BeforeEach
    void setUp() {
        manager = new PointManager();
        points = new ArrayList<>();
        walls = new ArrayList<>();
        ghosts = new ArrayList<>();
        pacman = new Pacman(1, 1);
    }

    @Test
    void testCreatePointsFronteiras() {
        // Mata mutantes 16.1, 16.2, 17.1, 17.2 (Ciclos for e Subtrações)
        // Criamos uma arena minúscula 5x5
        manager.createPoints(points, walls, pacman, ghosts, 5, 5);

        // Pelos teus loops: y vai de 2 a 3, x vai de 1 a 3.
        // Se o PIT mudar "height - 1" para "height + 1", o número de pontos muda.
        assertFalse(points.isEmpty());
        for (Point p : points) {
            assertTrue(p.getPosition().getX() >= 1 && p.getPosition().getX() < 4);
            assertTrue(p.getPosition().getY() >= 2 && p.getPosition().getY() < 4);
        }
    }

    @Test
    void testCanPlacePointFilters() {
        // Mata mutantes 38, 39, 40 e 41 (Filtros de elementos)
        Position pos = new Position(5, 5);

        // 1. Testar parede (Mata 38.1 e 38.2)
        walls.add(new Wall(5, 5));
        manager.createPoints(points, walls, pacman, ghosts, 10, 10);
        assertTrue(points.stream().noneMatch(p -> p.getPosition().equals(pos)),
                "Não deve colocar ponto onde há uma parede");

        // 2. Testar Pacman (Mata 39.1 e 39.2)
        points.clear();
        pacman.setPosition(new Position(2, 2));
        manager.createPoints(points, new ArrayList<>(), pacman, ghosts, 10, 10);
        assertTrue(points.stream().noneMatch(p -> p.getPosition().equals(new Position(2,2))),
                "Não deve colocar ponto onde está o Pacman");

        // 3. Testar Fantasma (Mata 40.1 e 40.2)
        points.clear();
        ghosts.add(new Ghost(3, 3, "#FF0000"));
        manager.createPoints(points, new ArrayList<>(), pacman, ghosts, 10, 10);
        assertTrue(points.stream().noneMatch(p -> p.getPosition().equals(new Position(3,3))),
                "Não deve colocar ponto onde há um fantasma");
    }

    @Test
    void testCollectPointWithScore() {
        int initialScore = pacman.getScore();
        points.add(new Point(5, 5));

        manager.collectPoint(points, pacman, new Position(5, 5));

        assertEquals(initialScore + 1, pacman.getScore(), "O score deve aumentar em 1 unidade");
        assertTrue(points.isEmpty(), "O ponto deve ter sido removido da lista");


        manager.collectPoint(points, pacman, new Position(5, 5));
        assertEquals(initialScore + 1, pacman.getScore(), "O score não deve aumentar se o ponto já não existe");
    }
}