package pt.up.fe.ldts.game;

import pt.up.fe.ldts.game.graphics.GraphicInterface;
import pt.up.fe.ldts.game.graphics.LanternaGraphicsAdapter;
import pt.up.fe.ldts.game.state.MainMenuState;
import pt.up.fe.ldts.game.state.State;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;

import java.io.IOException;

public class Application {
    public static void main(String[] args) {
        try {
            DefaultTerminalFactory terminalFactory = new DefaultTerminalFactory();
            Screen screen = terminalFactory.createScreen();
            screen.startScreen();
            TextGraphics textGraphics = screen.newTextGraphics();

            GraphicInterface graphics = new LanternaGraphicsAdapter(textGraphics, screen);

            State state = new MainMenuState(); // start at main menu

            // Main loop
            while (true) {
                state.draw(graphics);
                graphics.refresh();
                KeyStroke key = graphics.readKey();    // read user input
                state = state.processKey(key);         // update state
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
