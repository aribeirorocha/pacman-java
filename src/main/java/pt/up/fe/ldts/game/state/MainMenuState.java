package pt.up.fe.ldts.game.state;

import pt.up.fe.ldts.game.graphics.GraphicInterface;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import java.io.IOException;

public class MainMenuState implements State {
    private int option = 0; // 0 = Play, 1 = Exit

    @Override
    public void draw(GraphicInterface graphics) throws IOException {
        graphics.clear();
        graphics.drawText(10, 5, "===== PACMAN =====");
        graphics.drawText(10, 8, option == 0 ? "> Play" : "  Play");
        graphics.drawText(10, 9, option == 1 ? "> Exit" : "  Exit");
        graphics.refresh();
    }

    @Override
    public State processKey(KeyStroke key) throws IOException {
        if (key.getKeyType() == KeyType.ArrowUp || key.getKeyType() == KeyType.ArrowDown) {
            option = 1 - option; // toggle between 0 and 1
        } else if (key.getKeyType() == KeyType.Enter) {
            if (option == 0) {
                return new LevelSelectState(); // go to levels menu
            } else {
                System.exit(0); // exit the game
            }
        }
        return this; // stay in menu
    }
}
