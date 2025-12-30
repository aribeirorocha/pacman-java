package pt.up.fe.ldts.game.Unit_Tests.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.up.fe.ldts.game.model.CreateArena;
import pt.up.fe.ldts.game.model.element.Ghost;
import pt.up.fe.ldts.game.model.element.Pacman;
import pt.up.fe.ldts.game.model.element.Wall;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CreateArenaTest {
    private CreateArena creator;
    private List<Wall> walls;
    private List<Ghost> ghosts;
    private Pacman pacman;

    @BeforeEach
    void setUp() {
        creator = new CreateArena();
        walls = new ArrayList<>();
        ghosts = new ArrayList<>();
        pacman = new Pacman(0, 0);
    }

    @Test
    void testClearLists() {
        // Mata mutantes 81.1 e 82.1 (removed call to clear)
        walls.add(new Wall(0, 0));
        ghosts.add(new Ghost(0, 0, "#000000"));

        creator.createArena(walls, ghosts, pacman, 40, 20, 1);

        assertEquals(4, ghosts.size());
    }

    @Test
    void testPacmanPositioning() {
        // Mata mutante 98.1 (removed call to setPosition)
        creator.createArena(walls, ghosts, pacman, 40, 20, 1);

        assertNotEquals(0, pacman.getPosition().getX());
        assertNotEquals(0, pacman.getPosition().getY());
    }

    @Test
    void testGhostPositionsAndColors() {

        creator.createArena(walls, ghosts, pacman, 40, 20, 1);

        assertEquals(4, ghosts.size(), "Devem ser criados sempre 4 fantasmas");


        assertEquals("#FF0000", ghosts.get(0).getColor()); // Blinky
        assertEquals("#FF69B4", ghosts.get(1).getColor()); // Pinky
        assertEquals("#00FFFF", ghosts.get(2).getColor()); // Inky
        assertEquals("#FFA500", ghosts.get(3).getColor()); // Clyde

        // Verifica o anchorX e as posições adjacentes (Mata 123.1, 124.1, 125.1)
        int anchorX = ghosts.get(0).getPosition().getX();
        int cageY = 11;

        assertEquals(anchorX - 1, ghosts.get(1).getPosition().getX(), "Pinky deve estar à esquerda");
        assertEquals(anchorX + 1, ghosts.get(2).getPosition().getX(), "Inky deve estar à direita");
        assertEquals(anchorX - 2, ghosts.get(3).getPosition().getX(), "Clyde deve estar mais à esquerda");

        for(Ghost g : ghosts) assertEquals(cageY, g.getPosition().getY(), "Todos devem estar na linha 11");
    }

    @Test
    void testLevel3Layout() {

        creator.createArena(walls, ghosts, pacman, 60, 30, 3);

        assertTrue(walls.size() > 200, "O nível 3 deve ter muito mais paredes que o nível 1");
    }
}