package pt.up.fe.ldts.game.model.element;

import pt.up.fe.ldts.game.graphics.GraphicInterface;

public class Point extends Element {

    public Point(int x, int y) {
        super(x, y);
    }

    @Override
    public void draw(GraphicInterface graphics) {
        graphics.setForegroundColor("#FFD700");
        graphics.putChar(position.getX(), position.getY(), "o");
    }
}
