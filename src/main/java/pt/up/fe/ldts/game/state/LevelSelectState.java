package pt.up.fe.ldts.game.state;

import pt.up.fe.ldts.game.graphics.GraphicInterface;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import java.io.IOException;

public class LevelSelectState implements State {
    private int option = 0; // 0 = Level 1, 1 = Level 2, 2 = Level 3, 3 = Back

    @Override
    public void draw(GraphicInterface graphics) throws IOException {
        graphics.clear();
        graphics.drawText(10, 5, "===== SELECT LEVEL =====");
        graphics.drawText(10, 8, option == 0 ? "> Level 1" : "  Level 1");
        graphics.drawText(10, 9, option == 1 ? "> Level 2" : "  Level 2");
        graphics.drawText(10, 10, option == 2 ? "> Level 3" : "  Level 3");
        graphics.drawText(10, 11, option == 3 ? "> Back" : "  Back");
        graphics.refresh();
    }

    @Override
    public State processKey(KeyStroke key) throws IOException {
        if (key.getKeyType() == KeyType.ArrowUp) {
            option = (option + 3) % 4; // move up (wrap around)
        } else if (key.getKeyType() == KeyType.ArrowDown) {
            option = (option + 1) % 4; // move down (wrap around)
        } else if (key.getKeyType() == KeyType.Enter) {
            if (option == 3) {
                return new MainMenuState(); // back to main menu
            } else {
                return new GameState(1); // start level 1 for all levels
            }
        }
        return this; // stay in level select
    }
}
