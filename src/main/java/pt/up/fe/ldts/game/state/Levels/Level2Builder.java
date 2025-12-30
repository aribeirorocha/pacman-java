package pt.up.fe.ldts.game.state.Levels;

import pt.up.fe.ldts.game.model.Arena;
import pt.up.fe.ldts.game.model.CreateArena;
import pt.up.fe.ldts.game.model.element.Ghost;

public class Level2Builder implements LevelBuilder {
    @Override
    public void createArena(Arena arena) {
        // Passamos '2' diretamente em vez de arena.getLevel()
        new CreateArena().createArena(arena.getWalls(), arena.getGhosts(), arena.getPacman(),
                arena.getWidth(), arena.getHeight(), 2);

        // Fantasmas mais rápidos para o nível 2
        for (Ghost ghost : arena.getGhosts()) {
            ghost.setSpeed(2);
        }
    }
}