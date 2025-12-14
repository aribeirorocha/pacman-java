package pt.up.fe.ldts.game.state.Levels;

import pt.up.fe.ldts.game.model.Arena;
import pt.up.fe.ldts.game.model.CreateArena;

import java.io.IOException;

public class Level3Builder implements LevelBuilder {

    @Override
    public void createArena(Arena arena) {
        CreateArena creator = new CreateArena();
        creator.createArena(
                arena.getWalls(),
                arena.getGhosts(),
                arena.getPacman(),
                arena.getWidth(),
                arena.getHeight(),
                3
        );
    }
}
