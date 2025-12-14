package pt.up.fe.ldts.game.state.Levels;

import pt.up.fe.ldts.game.model.Arena;
import pt.up.fe.ldts.game.model.CreateArenaLevel3;

public class Level3Builder implements LevelBuilder {
    @Override
    public void createArena(Arena arena) {
        // Usa um layout maior
        new CreateArenaLevel3().createArena(
                arena.getWalls(),
                arena.getGhosts(),
                arena.getPacman(),
                60, 30
        );
    }
}
