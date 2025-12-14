package pt.up.fe.ldts.game;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.input.KeyStroke;
import pt.up.fe.ldts.game.graphics.LanternaGraphicsAdapter;
import pt.up.fe.ldts.game.state.State;
import pt.up.fe.ldts.game.state.LevelSelectState;

import java.io.IOException;

public class Application {
    public static void main(String[] args) throws IOException, InterruptedException {
        DefaultTerminalFactory terminalFactory = new DefaultTerminalFactory()
                .setInitialTerminalSize(new TerminalSize(80, 30));
        Screen screen = terminalFactory.createScreen();
        screen.startScreen();
        screen.setCursorPosition(null);
        LanternaGraphicsAdapter graphics = new LanternaGraphicsAdapter(screen);
        State state = new LevelSelectState();
        while (true) {
            state.draw(graphics);
            screen.refresh();
            KeyStroke key = screen.pollInput();
            if (key != null) {
                state = state.processKey(key);
            }
            try {
                Thread.sleep(120);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
