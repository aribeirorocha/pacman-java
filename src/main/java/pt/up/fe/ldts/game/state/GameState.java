package pt.up.fe.ldts.game.state;

import pt.up.fe.ldts.game.graphics.GraphicInterface;
import pt.up.fe.ldts.game.model.Arena;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
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
        // Adapta isso para a tua CreateArena
        arena = new Arena(40, 20);

        // Podes criar diferentes configurações por nível:
        // if (level == 1) { ... easy setup ... }
        // if (level == 2) { ... medium setup ... }
        // if (level == 3) { ... hard setup ... }
    }

    @Override
    public void draw(GraphicInterface graphics) {
        arena.draw(graphics);
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