package pt.up.fe.ldts.game.Unit_Tests.state.Levels;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.up.fe.ldts.game.model.Arena;
import pt.up.fe.ldts.game.state.Levels.Level3Builder;

import static org.junit.jupiter.api.Assertions.*;

public class Level3BuilderTest {
    private Arena arena;
    private Level3Builder builder;

    @BeforeEach
    void setUp() {

        arena = new Arena(60, 30, 3);
        builder = new Level3Builder();
    }



    @Test
    void testArenaDimensionsAreRespected() {

        builder.createArena(arena);
        assertEquals(60, arena.getWidth());
        assertEquals(30, arena.getHeight());
    }
    @Test
    void testCreateArenaPopulatesElementsForLevel3() {

        Arena arena = new Arena(60, 30, 3);
        arena.getWalls().clear();

        builder.createArena(arena);

        assertFalse(arena.getWalls().isEmpty(), "Arena 3 deve ter paredes");

        assertEquals(3, arena.getLevel());
    }
}