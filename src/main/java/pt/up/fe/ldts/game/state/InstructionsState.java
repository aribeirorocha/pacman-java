package pt.up.fe.ldts.game.state;

import pt.up.fe.ldts.game.graphics.GraphicInterface;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import java.io.IOException;

public class InstructionsState implements State {

    @Override
    public void draw(GraphicInterface graphics) throws IOException {
        graphics.clear();
        graphics.drawText(10, 3, "===== INSTRUCTIONS =====");
        graphics.drawText(10, 6, "Use Arrow Keys to move Pacman:");
        graphics.drawText(12, 7, "↑  Up");
        graphics.drawText(12, 8, "↓  Down");
        graphics.drawText(12, 9, "←  Left");
        graphics.drawText(12, 10, "→  Right");

        graphics.drawText(10, 13, "Objective:");
        graphics.drawText(12, 14, "- Collect all points ");
        graphics.drawText(12, 15, "- Avoid the ghosts ");
        graphics.drawText(12, 16, "- Escape through corridors and survive!");

        graphics.drawText(10, 19, "Press ENTER or ESC to return to Main Menu");
        graphics.refresh();
    }

    @Override
    public State processKey(KeyStroke key) throws IOException {
        if (key.getKeyType() == KeyType.Enter || key.getKeyType() == KeyType.Escape) {
            return new MainMenuState();
        }
        return this;
    }
}
