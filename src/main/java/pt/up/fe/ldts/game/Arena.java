package pt.up.fe.ldts.game;

import pt.up.fe.ldts.game.graphics.GraphicInterface;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import java.util.ArrayList;
import java.util.List;

public class Arena {
    private final int width;
    private final int height;
    private Pacman pacman;
    private List<Ghost> ghosts;
    private List<Wall> walls;

    public Arena(int width, int height) {
        this.width = width;
        this.height = height;
        this.walls = new ArrayList<>();
        this.ghosts = new ArrayList<>();
        createArena();
    }

    private void createArena() {
        // Criar bordas
        for (int x = 0; x < width; x++) {
            walls.add(new Wall(x, 0));
            walls.add(new Wall(x, height - 1));
        }
        for (int y = 1; y < height - 1; y++) {
            walls.add(new Wall(0, y));
            walls.add(new Wall(width - 1, y));
        }

        // Algumas paredes internas (labirinto simples)
        for (int x = 10; x < 15; x++) {
            walls.add(new Wall(x, 5));
        }
        for (int y = 10; y < 15; y++) {
            walls.add(new Wall(20, y));
        }

        // Criar Pacman
        pacman = new Pacman(2, 2);

        // Criar Fantasmas
        ghosts.add(new Ghost(width - 3, 2));
        ghosts.add(new Ghost(width - 3, height - 3));
        ghosts.add(new Ghost(2, height - 3));
    }

    public void draw(GraphicInterface graphics) {
        // Desenhar paredes
        for (Wall wall : walls) {
            wall.draw(graphics);
        }

        // Desenhar fantasmas
        for (Ghost ghost : ghosts) {
            ghost.draw(graphics);
        }

        // Desenhar pacman por último (fica em cima)
        pacman.draw(graphics);
    }

    public void processKey(KeyStroke key) {
        if (key.getKeyType() == KeyType.ArrowUp) {
            movePacman(pacman.moveUp());
        } else if (key.getKeyType() == KeyType.ArrowDown) {
            movePacman(pacman.moveDown());
        } else if (key.getKeyType() == KeyType.ArrowLeft) {
            movePacman(pacman.moveLeft());
        } else if (key.getKeyType() == KeyType.ArrowRight) {
            movePacman(pacman.moveRight());
        }

        // Mover fantasmas (a cada movimento do jogador)
        moveGhosts();
    }

    private void movePacman(Position newPosition) {
        if (canMoveTo(newPosition)) {
            pacman.setPosition(newPosition);
        }
    }

    private void moveGhosts() {
        for (Ghost ghost : ghosts) {
            Position newPosition = ghost.move();
            if (canMoveTo(newPosition)) {
                ghost.setPosition(newPosition);
            }
        }
    }

    private boolean canMoveTo(Position position) {
        // Verificar se não é parede
        for (Wall wall : walls) {
            if (wall.getPosition().equals(position)) {
                return false;
            }
        }
        return true;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}