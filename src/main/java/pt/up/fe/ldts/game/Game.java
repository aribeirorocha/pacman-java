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
        screen.setCursorPosition(null);
    }

    private void draw() throws IOException {
        // ... (método draw permanece o mesmo)
        screen.clear();

        TextGraphics textGraphics = screen.newTextGraphics();
        GraphicInterface graphics = new LanternaGraphicsAdapter(textGraphics);

        arena.draw(graphics);

        screen.refresh();
    }

    public void run() throws IOException {
        try {
            while (true) {
                draw();
                KeyStroke key = screen.readInput();

                if (key != null) {
                    // 1. Condição de Saída por 'Escape'
                    if (key.getKeyType() == KeyType.Escape) {
                        break; // Sai do loop 'while'
                    }

                    // 2. Condição de Saída por Fecho da Janela ('X')
                    if (key.getKeyType() == KeyType.EOF) {
                        break; // Sai do loop 'while' quando a janela é fechada
                    }

                    arena.processKey(key);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 3. Bloco 'finally' para garantir o fecho
            if (screen != null) {
                try {
                    screen.close(); // Fecha o terminal da Lanterna
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            // 🔑 A LINHA QUE FORÇA O FIM DO PROCESSO JAVA
            System.exit(0);
        }
    }
}