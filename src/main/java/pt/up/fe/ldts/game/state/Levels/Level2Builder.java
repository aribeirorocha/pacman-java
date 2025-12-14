package pt.up.fe.ldts.game.state.Levels;

import pt.up.fe.ldts.game.model.Arena;
import pt.up.fe.ldts.game.model.CreateArena;
import pt.up.fe.ldts.game.model.element.Ghost;

public class Level2Builder implements LevelBuilder {
    @Override
    public void createArena(Arena arena) {
        // Mesmo layout
        new CreateArena().createArena(arena.getWalls(), arena.getGhosts(), arena.getPacman(),
                arena.getWidth(), arena.getHeight(),  arena.getLevel());
        // Fantasmas mais rápidos
        for (Ghost ghost : arena.getGhosts()) {
            ghost.setSpeed(2); // velocidade duplicada
        }
    }
}
