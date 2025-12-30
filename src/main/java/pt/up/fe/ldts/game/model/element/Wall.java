package pt.up.fe.ldts.game.model.element;

import pt.up.fe.ldts.game.graphics.GraphicInterface;

public class Wall extends Element {

    public Wall(int x, int y) {
        super(x, y);
    }


    @Override
    public void draw(GraphicInterface graphics) {

        graphics.setForegroundColor("#0000FF");
        graphics.putChar(getPosition().getX(), getPosition().getY(), "█");
    }
}