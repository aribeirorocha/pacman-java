package pt.up.fe.ldts.game.controler;

import pt.up.fe.ldts.game.model.Arena;
import pt.up.fe.ldts.game.graphics.GraphicInterface;
import pt.up.fe.ldts.game.graphics.LanternaGraphicsAdapter;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.swing.AWTTerminalFontConfiguration;
import java.awt.Font;
import java.io.IOException;

public class Game {
    private Screen screen;
    private Arena arena;
    private static final int FRAME_TIME = 150;
    private GameControler gameControler;

    // CONSTRUTOR 1: Usado pelo Main (Cria a janela real)
    public Game() throws IOException {
        int width = 40;
        int height = 20;
        this.arena = new Arena(width, height, 1);

        Font font = new Font("Monospaced", Font.PLAIN, 15);
        AWTTerminalFontConfiguration fontConfig = AWTTerminalFontConfiguration.newInstance(font);

        this.screen = new DefaultTerminalFactory()
                .setInitialTerminalSize(new TerminalSize(width, height))
                .setTerminalEmulatorFontConfiguration(fontConfig)
                .createScreen();

        this.screen.startScreen();
        this.screen.setCursorPosition(null);
        this.gameControler = new GameControler();
    }

    // CONSTRUTOR 2: Usado pelos Testes (Recebe Mocks)
    public Game(Screen screen, GameControler gameControler, Arena arena) {
        this.screen = screen;
        this.gameControler = gameControler;
        this.arena = arena;
    }

    // Método extraído para permitir testes de tempo (Mata mutantes de Thread.sleep)
    public void startSleep(long time) throws InterruptedException {
        Thread.sleep(time);
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
                        // AQUI: Usamos o método startSleep em vez de Thread.sleep direto
                        startSleep(sleepTime);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            throw new RuntimeException(e); // Converte exceção checada para Runtime
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