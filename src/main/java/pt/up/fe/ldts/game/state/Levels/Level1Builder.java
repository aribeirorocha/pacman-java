package pt.up.fe.ldts.game.state.Levels;

import pt.up.fe.ldts.game.model.Arena;
import pt.up.fe.ldts.game.model.CreateArena;

public class Level1Builder implements LevelBuilder {
    @Override
    public void createArena(Arena arena) {
        // Layout normal
        new CreateArena().createArena(arena.getWalls(), arena.getGhosts(), arena.getPacman(),
                arena.getWidth(), arena.getHeight(), arena.getLevel());
    }
}
