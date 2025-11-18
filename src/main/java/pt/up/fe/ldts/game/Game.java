package pt.up.fe.ldts.game;

import pt.up.fe.ldts.game.graphics.GraphicInterface;
import pt.up.fe.ldts.game.graphics.LanternaGraphicsAdapter;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.graphics.TextGraphics;
import java.io.IOException;

public class Game {
    private Screen screen;
    private Arena arena;

    public Game() throws IOException {
        arena = new Arena(40, 20);

        screen = new DefaultTerminalFactory()
                .setInitialTerminalSize(new TerminalSize(40, 20))
                .createScreen();

        screen.startScreen();
        screen.setCursorPosition(null); // Esconder cursor
    }

    private void draw() throws IOException {
        screen.clear();

        TextGraphics textGraphics = screen.newTextGraphics();
        GraphicInterface graphics = new LanternaGraphicsAdapter(textGraphics);

        arena.draw(graphics);

        screen.refresh();
    }

    public void run() throws IOException {
        while (true) {
            draw();
            KeyStroke key = screen.readInput();

            if (key != null) {
                // ESC para sair
                if (key.getKeyType() == KeyType.Escape) {
                    screen.close();
                    break;
                }

                arena.processKey(key);
            }
        }
    }
}
