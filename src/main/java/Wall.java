import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;

public class Wall extends Element {

    public Wall(int x, int y) {
        super(x, y);
    }


    public void draw(GraphicInterface graphics) {
        graphics.setForegroundColor("#999999"); // gray wall
        graphics.putChar(position.getX(), position.getY(), "#");
    }
}
