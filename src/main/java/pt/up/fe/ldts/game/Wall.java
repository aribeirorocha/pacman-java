package pt.up.fe.ldts.game;

import pt.up.fe.ldts.game.graphics.GraphicInterface;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;

public class Wall extends Element {

    public Wall(int x, int y) {
        super(x, y);
    }

    @Override
    public void draw(GraphicInterface graphics) {
        graphics.setForegroundColor("#0000FF"); // Azul (estilo Pacman clássico)
        graphics.putChar(position.getX(), position.getY(), "#");
    }
}