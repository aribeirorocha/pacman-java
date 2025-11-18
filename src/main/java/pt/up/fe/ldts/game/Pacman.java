package pt.up.fe.ldts.game;

import pt.up.fe.ldts.game.graphics.GraphicInterface;
public class Pacman extends Element {

    public Pacman(int x, int y) {
        super(x, y);
    }

    public Position moveUp() {
        return new Position(position.getX(), position.getY() - 1);
    }

    public Position moveDown() {
        return new Position(position.getX(), position.getY() + 1);
    }

    public Position moveLeft() {
        return new Position(position.getX() - 1, position.getY());
    }

    public Position moveRight() {
        return new Position(position.getX() + 1, position.getY());
    }

    @Override
    public void draw(GraphicInterface graphics) {
        graphics.setForegroundColor("#FFFF33"); // Amarelo Pacman
        graphics.enableBold();
        graphics.putChar(position.getX(), position.getY(), "C");
    }
}