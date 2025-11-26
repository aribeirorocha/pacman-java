package pt.up.fe.ldts.game.model.element;

import pt.up.fe.ldts.game.graphics.GraphicInterface;
import pt.up.fe.ldts.game.model.Position;

public class Point extends Element {

    // Novo atributo para distinguir Power Pellets
    private final boolean isPowerPellet;

    public Point(int x, int y) {
        this(x, y, false); // Construtor para pontos normais
    }

    public Point(int x, int y, boolean isPowerPellet) {
        super(x, y);
        this.isPowerPellet = isPowerPellet;
    }

    // Getter necessário para lógica do jogo (pode ser usado no GameControler)
    public boolean isPowerPellet() {
        return isPowerPellet;
    }

    @Override
    public void draw(GraphicInterface graphics) {
        graphics.setForegroundColor("#FFFFFF"); // Cor branca

        if (isPowerPellet) {
            // Power Pellet (Bola Grande)
            graphics.enableBold(); // Torna o caractere mais grosso
            graphics.putChar(getPosition().getX(), getPosition().getY(), "●"); // Caractere de círculo cheio
            // Alternativa se '●' não funcionar: "O"
        } else {
            // Ponto Normal (Bola Pequena)
            graphics.putChar(getPosition().getX(), getPosition().getY(), ".");
        }
    }
}