package pt.up.fe.ldts.game.model.element;

import pt.up.fe.ldts.game.graphics.GraphicInterface;
import pt.up.fe.ldts.game.model.Position;

public class Pacman extends Element {
    private int score = 0;
    private Position direction;


    public Pacman(int x, int y) {
        super(x, y);
        this.direction = new Position(0,0);
    }
    public Position getDirection(){return direction;}


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

    public void setDirection (Position direction){
        this.direction=direction;
    }
    public  Position getNextPosition(){
        return position.plus(direction);
    }

    public int getScore() {
        return score;
    }

    public void addScore(int value) {
        this.score += value;
    }

    @Override
    public void draw(GraphicInterface graphics) {
        graphics.setForegroundColor("#FFFF33"); // Amarelo Pacman
        graphics.enableBold();
        graphics.putChar(position.getX(), position.getY(), "C");
    }

}