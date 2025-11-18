package pt.up.fe.ldts.game;

import pt.up.fe.ldts.game.graphics.GraphicInterface;

import java.util.Random;

public class Ghost extends Element {

    public Ghost(int x, int y) {
        super(x, y);
    }

    @Override
    public void draw(GraphicInterface graphics) {
        graphics.setForegroundColor("#FF0000"); // Vermelho
        graphics.enableBold();
        graphics.putChar(position.getX(), position.getY(), "G");
    }

    // Movimento aleatório (por enquanto simples, depois melhoramos com Strategy Pattern)
    public Position move() {
        Random random = new Random();
        int direction = random.nextInt(4);

        switch (direction) {
            case 0: return new Position(position.getX(), position.getY() - 1); // UP
            case 1: return new Position(position.getX(), position.getY() + 1); // DOWN
            case 2: return new Position(position.getX() - 1, position.getY()); // LEFT
            case 3: return new Position(position.getX() + 1, position.getY()); // RIGHT
            default: return position;
        }
    }
}