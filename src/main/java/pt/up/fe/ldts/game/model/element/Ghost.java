package pt.up.fe.ldts.game.model.element;

import pt.up.fe.ldts.game.graphics.GraphicInterface;
import pt.up.fe.ldts.game.model.Position;
import java.util.Random;

public class Ghost extends Element {

    private final String color;
    private Position direction;
    private final Random random;

    // ⭐️ CONSTRUTOR DE 2 ARGUMENTOS (CORRIGIDO PARA COMPATIBILIDADE) ⭐️
    // Chama o construtor de 3 argumentos, usando uma cor padrão (ex: Vermelho)
    public Ghost(int x, int y) {
        this(x, y, "#FF0000"); // Atribui a cor vermelha padrão
    }

    // CONSTRUTOR DE 3 ARGUMENTOS (usado no CreateArena)
    public Ghost(int x, int y, String color) {
        super(x, y);
        this.color = color;
        this.random = new Random();
        this.direction = getInitialRandomDirection();
    }
    private Position getInitialRandomDirection() {
        Position[] initialMoves = {
                new Position(0, -1), // Cima
                new Position(0, 1),  // Baixo
                new Position(-1, 0), // Esquerda
                new Position(1, 0)   // Direita
        };
        // Escolhe uma direção inicial aleatória
        return initialMoves[random.nextInt(initialMoves.length)];
    }

    // Getter da direção (necessário para o GameControler)
    public Position getDirection() {
        return direction;
    }

    // Setter da direção (necessário para o GameControler)
    public void setDirection(Position newDirection) {
        this.direction = newDirection;
    }

    public Position move() {
        // A lógica de movimento é tratada no GameControler para o movimento fluido
        return getNextPosition(direction);
    }

    // Método auxiliar para calcular a posição futura com base numa direção delta (dx, dy)
    public Position getNextPosition(Position delta) {
        return new Position(getPosition().getX() + delta.getX(), getPosition().getY() + delta.getY());
    }

    @Override
    public void draw(GraphicInterface graphics) {
        // Usa a cor específica deste fantasma
        graphics.setForegroundColor(color);

        // Símbolo do Fantasma
        graphics.putChar(getPosition().getX(), getPosition().getY(), "⌽");
    }
}