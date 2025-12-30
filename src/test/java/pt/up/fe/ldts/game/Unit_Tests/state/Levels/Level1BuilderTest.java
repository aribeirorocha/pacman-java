package pt.up.fe.ldts.game.Unit_Tests.state.Levels;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.up.fe.ldts.game.model.Arena;
import pt.up.fe.ldts.game.state.Levels.Level1Builder;

import static org.junit.jupiter.api.Assertions.*;

public class Level1BuilderTest {
    private Arena arena;
    private Level1Builder builder;

    @BeforeEach
    void setUp() {

        arena = new Arena(40, 20, 1);
        builder = new Level1Builder();
    }

    @Test
    void testCreateArenaPopulatesElements() {

        Arena arena = new Arena(40, 20, 1);
        arena.getWalls().clear();
        arena.getGhosts().clear();

        builder.createArena(arena);


        assertFalse(arena.getWalls().isEmpty(), "O Builder devia ter adicionado paredes");
    }

    @Test
    void testLevelConsistency() {
        builder.createArena(arena);

        assertEquals(1, arena.getLevel());
    }
}