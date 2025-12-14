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
    private final int level;

    public GameState(int level) throws IOException {
        this.level = level;
        initArena();
    }

    private void initArena() throws IOException {
        // Criar arena baseada no nível
        arena = switch (level) {
            case 3 -> new Arena(60, 30);
            default -> new Arena(40, 20);
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
        arena.update();          // mover
        try {
            arena.draw(graphics);    // desenhar arena
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public State processKey(KeyStroke key) throws IOException {
        if (key.getKeyType() == KeyType.Escape) {
            // Voltar ao menu de níveis
            return new LevelSelectState();
        }

        arena.processKey(key);

        // Verificar game over
        if (arena.isGameOver()) {
            // Podes criar um GameOverState aqui
            // Por agora, volta ao menu
            if (key.getKeyType() == KeyType.Character &&
                    (key.getCharacter() == 'r' || key.getCharacter() == 'R')) {
                // Restart mesmo nível
                initArena();
            }
        }

        return this;
    }
}