package pt.up.fe.ldts.game.Unit_Tests.state.Levels;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.up.fe.ldts.game.model.Arena;
import pt.up.fe.ldts.game.model.element.Ghost;
import pt.up.fe.ldts.game.state.Levels.Level2Builder;

import static org.junit.jupiter.api.Assertions.*;

public class Level2BuilderTest {
    private Arena arena;
    private Level2Builder builder;

    @BeforeEach
    void setUp() {

        arena = new Arena(40, 20, 2);
        builder = new Level2Builder();
    }

    @Test

    void testCreateArenaAndGhostSpeed() {
        Arena arena = new Arena(40, 20, 2);

        arena.getGhosts().clear();

        builder.createArena(arena);

        assertFalse(arena.getGhosts().isEmpty(), "Devem existir fantasmas");
        for (Ghost ghost : arena.getGhosts()) {
            assertEquals(2, ghost.getSpeed(), "Velocidade deve ser 2 no nível 2");
        }
    }

    @Test
    void testLevelConsistency() {
        builder.createArena(arena);

        assertEquals(2, arena.getLevel());
    }
}