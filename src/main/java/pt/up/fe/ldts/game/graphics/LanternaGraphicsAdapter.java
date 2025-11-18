package pt.up.fe.ldts.game.graphics;

import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;

public class LanternaGraphicsAdapter implements GraphicInterface {
    private final TextGraphics graphics;

    public LanternaGraphicsAdapter(TextGraphics graphics) {
        this.graphics = graphics;
    }

    @Override
    public void setForegroundColor(String color) {
        graphics.setForegroundColor(TextColor.Factory.fromString(color));
    }

    @Override
    public void enableBold() {
        graphics.enableModifiers(SGR.BOLD);
    }

    @Override
    public void putChar(int x, int y, String c) {
        graphics.putString(new TerminalPosition(x, y), c);
    }

    @Override
    public void fillRectangle(int x, int y, int width, int height, char c) {
        graphics.fillRectangle(
                new TerminalPosition(x, y),
                new TerminalSize(width, height),
                c
        );
    }
}