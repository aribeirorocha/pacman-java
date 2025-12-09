package pt.up.fe.ldts.game.graphics;

import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.screen.Screen;
import java.io.IOException;

public class LanternaGraphicsAdapter implements GraphicInterface {
    private final TextGraphics tg;
    private final Screen screen;

    public LanternaGraphicsAdapter(TextGraphics tg, Screen screen) {
        this.tg = tg;
        this.screen = screen;
    }

    @Override
    public void setForegroundColor(String color) {
    }

    @Override
    public void enableBold() {

    }

    @Override
    public void putChar(int x, int y, String c) {
        tg.putString(x, y, c);
    }

    @Override
    public void fillRectangle(int x, int y, int width, int height, char c) {
        for (int i = y; i < y + height; i++) {
            for (int j = x; j < x + width; j++) {
                tg.putString(j, i, Character.toString(c));
            }
        }
    }

    @Override
    public void clear() throws IOException {
        screen.clear();
    }

    @Override
    public void drawText(int x, int y, String text) throws IOException {
        tg.putString(x, y, text);
    }

    @Override
    public void refresh() throws IOException {
        screen.refresh();
    }

    @Override
    public KeyStroke readKey() throws IOException {
        return screen.readInput();
    }
}
