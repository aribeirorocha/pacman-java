package pt.up.fe.ldts.game.state.Levels;

import pt.up.fe.ldts.game.model.Arena;

public interface LevelBuilder {
    void createArena(Arena arena);
}
//Algo abstrato para depois implementar os diferentes níveis