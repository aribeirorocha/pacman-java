package pt.up.fe.ldts.game.controler;

import pt.up.fe.ldts.game.model.Arena;
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
    private static final int FRAME_TIME =150;
    private GameControler gameControler;

    public Game() throws IOException {
        arena = new Arena(40, 20);

        screen = new DefaultTerminalFactory()
                .setInitialTerminalSize(new TerminalSize(40, 20))
                .createScreen();

        screen.startScreen();
        screen.setCursorPosition(null);
        this.gameControler=new GameControler();
    }

    private void draw() throws IOException {

        screen.clear();

        TextGraphics textGraphics = screen.newTextGraphics();
        GraphicInterface graphics = new LanternaGraphicsAdapter(textGraphics);

        arena.draw(graphics);

        screen.refresh();
    }

    public void run() throws IOException {
        try {
            while (true) {
                long startTime = System.currentTimeMillis();
                draw();
                KeyStroke key = screen.pollInput();

                if (key != null) {

                    if (key.getKeyType() == KeyType.Escape||key.getKeyType()==  KeyType.EOF) {
                        break;
                    }


                    arena.processKey(key);
                }
                gameControler.moveElements(arena);
                long elapsedTime = System.currentTimeMillis()-startTime;
                long sleepTime=FRAME_TIME-elapsedTime;
                if (sleepTime>0){
                    try{
                        Thread.sleep(sleepTime);
                    } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
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