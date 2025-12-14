package pt.up.fe.ldts.game.state;

import pt.up.fe.ldts.game.graphics.GraphicInterface;
import pt.up.fe.ldts.game.model.Arena;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import pt.up.fe.ldts.game.state.Levels.Level1Builder;
import pt.up.fe.ldts.game.state.Levels.Level2Builder;
import pt.up.fe.ldts.game.state.Levels.Level3Builder;
import pt.up.fe.ldts.game.state.Levels.LevelBuilder;

import java.io.IOException;

public class GameState implements State {
    private Arena arena;
    private final int initialLevel;

    public GameState(int level) throws IOException {
        this.initialLevel = level;
        initArena(level);
    }

    private void initArena(int level) throws IOException {
        // Criar arena baseada no nível
        arena = switch (level) {
            case 3 -> new Arena(60, 30, 3);
            case 2 -> new Arena(40, 20, 2);
            case 1 -> new Arena(40, 20, 1);
            default -> new Arena(40, 20, 1);
        };

        LevelBuilder builder = switch (level) {
            case 1 -> new Level1Builder();
            case 2 -> new Level2Builder();
            case 3 -> new Level3Builder();
            default -> new Level1Builder();
        };

        builder.createArena(arena);
    }

    @Override
    public void draw(GraphicInterface graphics) throws InterruptedException {
        arena.update(); // mover elementos
        try {
            arena.draw(graphics); // desenhar arena
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public State processKey(KeyStroke key) throws IOException {
        if (key.getKeyType() == KeyType.Escape) {
            return new LevelSelectState();
        }

        arena.processKey(key);

        if (arena.isGameOver()) {
            if (key.getKeyType() == KeyType.Character &&
                    (key.getCharacter() == 'r' || key.getCharacter() == 'R')) {

                initArena(initialLevel);
            }
        }

        return this;
    }

}
