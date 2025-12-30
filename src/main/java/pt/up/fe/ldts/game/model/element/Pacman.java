package pt.up.fe.ldts.game.model.element;

import pt.up.fe.ldts.game.graphics.GraphicInterface;
import pt.up.fe.ldts.game.model.Position;

public class Pacman extends Element {
    private int score = 0;
    private Position direction;
    private Position bufferedDirection; // guardar a direção
    private boolean immune; //para os poderes
    private long immuneUntil;


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
    public Position getBufferedDirection() { return bufferedDirection; }
    public void setBufferedDirection(Position dir) { this.bufferedDirection = dir; }
    public  Position getNextPosition(){
        return position.plus(direction);
    }

    public int getScore() {
        return score;
    }

    public void addScore(int value) {
        this.score += value;
    }


    public boolean isImmune() {
        return immune && System.currentTimeMillis() < immuneUntil; //Ainda está sob efeito dos poderes
    }
    public void setImmune(long durationMillis) {
        this.immune = true;
        this.immuneUntil = System.currentTimeMillis() + durationMillis;
    }
    public void activatePower() {
        setImmune(5000);
    }

    public long getImmuneTimeLeft() {
        if (!immune) return 0;
        long remaining = immuneUntil - System.currentTimeMillis();
        return remaining > 0 ? remaining / 1000 : 0; // tempo dos poderes
    }


}