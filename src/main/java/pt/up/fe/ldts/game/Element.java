package pt.up.fe.ldts.game;

import pt.up.fe.ldts.game.graphics.GraphicInterface;

public abstract class Element {
    protected Position position;

    public Element(int x, int y) {
        this.position = new Position(x, y);
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    // Método abstrato - cada elemento desenha-se de forma diferente
    public abstract void draw(GraphicInterface graphics);
}