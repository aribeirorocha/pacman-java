package pt.up.fe.ldts.game.state.Levels;

import pt.up.fe.ldts.game.model.Arena;
import pt.up.fe.ldts.game.model.CreateArena;

public class Level1Builder implements LevelBuilder {
    @Override
    public void createArena(Arena arena) {
        new CreateArena().createArena(arena.getWalls(), arena.getGhosts(), arena.getPacman(),
                arena.getWidth(), arena.getHeight(), 1);
    }
}