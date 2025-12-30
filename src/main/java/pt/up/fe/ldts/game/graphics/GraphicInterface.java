package pt.up.fe.ldts.game.graphics;

import com.googlecode.lanterna.input.KeyStroke;
import java.io.IOException;

public interface GraphicInterface {
    void setForegroundColor(String color);
    void enableBold() throws InterruptedException;
    void putChar(int x, int y, String c);
    void fillRectangle(int x, int y, int width, int height, char c);


    void clear() throws IOException;
    void drawText(int x, int y, String text) throws IOException;
    void refresh() throws IOException;
    KeyStroke readKey() throws IOException;
}
