package pt.up.fe.ldts.game.model.element;

import pt.up.fe.ldts.game.graphics.GraphicInterface;
import pt.up.fe.ldts.game.model.Position;

public class Wall extends Element {

    public Wall(int x, int y) {
        super(x, y);
    }

    // Se usar a estrutura com classe Element:
    // public Wall(Position position) { super(position); }

    @Override
    public void draw(GraphicInterface graphics) {
        // Cor: Azul Clássico do Labirinto Pac-Man
        graphics.setForegroundColor("#0000FF");

        // Caractere: Bloco Sólido (█) para robustez máxima
        // Se o seu terminal não suportar este caractere, mude para "#"
        graphics.putChar(getPosition().getX(), getPosition().getY(), "█");
    }
}