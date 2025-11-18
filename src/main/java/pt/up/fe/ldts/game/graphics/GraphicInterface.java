package pt.up.fe.ldts.game.graphics;


public interface GraphicInterface {
    void setForegroundColor(String color);
    void enableBold();
    void putChar(int x, int y, String c);
    void fillRectangle(int x, int y, int width, int height, char c);
}