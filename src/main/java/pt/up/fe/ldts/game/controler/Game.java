package pt.up.fe.ldts.game.controler;

import pt.up.fe.ldts.game.model.Arena;
import pt.up.fe.ldts.game.graphics.GraphicInterface;
import pt.up.fe.ldts.game.graphics.LanternaGraphicsAdapter;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import java.io.IOException;

public class Game {
    private Screen screen;
    private Arena arena;
    private static final int FRAME_TIME = 150;
    private GameControler gameControler;

    public Game() throws IOException {
        arena = new Arena(60, 20,1);

        screen = new DefaultTerminalFactory()
                .setInitialTerminalSize(new TerminalSize(40, 20))
                .createScreen();

        screen.startScreen();
        screen.setCursorPosition(null);
        this.gameControler = new GameControler();
    }

    private void draw() throws IOException, InterruptedException {
        screen.clear();
        GraphicInterface graphics = new LanternaGraphicsAdapter(screen);
        arena.draw(graphics);
        screen.refresh();
    }

    public void run() throws IOException {
        try {
            while (true) {
                long startTime = System.currentTimeMillis();

                gameControler.moveElements(arena);
                draw();
                KeyStroke key = screen.pollInput();
                if (key != null) {
                    if (key.getKeyType() == KeyType.Escape || key.getKeyType() == KeyType.EOF) {
                        break;
                    }
                    arena.processKey(key);
                }

                long elapsedTime = System.currentTimeMillis() - startTime;
                long sleepTime = FRAME_TIME - elapsedTime;
                if (sleepTime > 0) {
                    try {
                        Thread.sleep(sleepTime);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            if (screen != null) {
                try {
                    screen.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
