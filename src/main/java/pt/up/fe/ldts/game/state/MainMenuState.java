package pt.up.fe.ldts.game.state;

import pt.up.fe.ldts.game.graphics.GraphicInterface;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import java.io.IOException;

public class MainMenuState implements State {
    private int option = 0;
    private final String[] options = { "Play", "Instructions", "Exit" };

    @Override
    public void draw(GraphicInterface graphics) throws IOException {
        graphics.clear();
        graphics.drawText(10, 3, "########################################");
        graphics.drawText(10, 4, "#             PACMAN                   #");
        graphics.drawText(10, 5, "########################################");

        for (int i = 0; i < options.length; i++) {
            String prefix = (i == option) ? "> " : "  ";
            graphics.drawText(12, 8 + i, prefix + options[i]);
        }

        graphics.refresh();
    }

    @Override
    public State processKey(KeyStroke key) throws IOException {
        if (key.getKeyType() == KeyType.ArrowUp) {
            option = (option - 1 + options.length) % options.length;
        } else if (key.getKeyType() == KeyType.ArrowDown) {
            option = (option + 1) % options.length;
        } else if (key.getKeyType() == KeyType.Enter) {
            switch (option) {
                case 0: return new LevelSelectState(); // seleção de nível
                case 1: return new InstructionsState(); // mostra instruções
                case 2: System.exit(0); // Exit
            }
        }
        return this;
    }
}

