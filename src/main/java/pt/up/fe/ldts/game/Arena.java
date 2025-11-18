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
    private List<Point> points;
    private int totalPoints;
    private boolean gameOver;

    public Arena(int width, int height) {
        this.width = width;
        this.height = height;
        this.walls = new ArrayList<>();
        this.ghosts = new ArrayList<>();
        this.points = new ArrayList<>();
        this.gameOver = false;
        createArena();
        createPoints();
        this.totalPoints = points.size();
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

        // Bloco superior esquerdo
        for (int x = 5; x < 10; x++) {
            walls.add(new Wall(x, 3));
            walls.add(new Wall(x, 7));
        }
        for (int y = 3; y < 7; y++) {
            walls.add(new Wall(5, y));
        }

        // Bloco central
        for (int x = 15; x < 22; x++) {
            walls.add(new Wall(x, 5));
            walls.add(new Wall(x, 10));
        }
        for (int y = 5; y < 10; y++) {
            walls.add(new Wall(15, y));
            walls.add(new Wall(25, y));
        }

        // Bloco inferior direito
        for (int x = 28; x < 35; x++) {
            walls.add(new Wall(x, 12));
            walls.add(new Wall(x, 16));
        }

        // Corredor vertical esquerdo
        for (int y = 8; y < 15; y++) {
            walls.add(new Wall(10, y));
        }

        // Paredes diagonais (efeito interessante)
        for (int i = 0; i < 5; i++) {
            walls.add(new Wall(20 + i, 12 + i));
        }

        // Criar Pacman
        pacman = new Pacman(2, 2);

        // Criar Fantasmas
        ghosts.add(new Ghost(width - 3, 2));
        ghosts.add(new Ghost(width - 3, height - 3));
        ghosts.add(new Ghost(6, height - 6));
        ghosts.add(new Ghost(12, height - 8));
        ghosts.add(new Ghost(26, height - 10));
        ghosts.add(new Ghost(7, height - 14));


    }

    private void createPoints() {
        // Criar pontos em todas as posições vazias
        for (int y = 1; y < height - 1; y++) {
            for (int x = 1; x < width - 1; x++) {
                Position pos = new Position(x, y);

                // Não criar ponto onde há parede, pacman ou fantasma
                if (canPlacePoint(pos)) {
                    points.add(new Point(x, y));
                }
            }
        }
    }

    private boolean canPlacePoint(Position position) {
        // Verificar se não é parede
        for (Wall wall : walls) {
            if (wall.getPosition().equals(position)) {
                return false;
            }
        }

        // Verificar se não é posição do pacman
        if (pacman.getPosition().equals(position)) {
            return false;
        }

        // Verificar se não é posição de fantasma
        for (Ghost ghost : ghosts) {
            if (ghost.getPosition().equals(position)) {
                return false;
            }
        }

        return true;
    }

    public void draw(GraphicInterface graphics) {
        // Desenhar pontos primeiro
        for (Point point : points) {
            point.draw(graphics);
        }

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

        // Desenhar HUD (contador e progresso)
        drawHUD(graphics);

        // Desenhar "YOU DIED" se game over
        if (gameOver) {
            drawGameOver(graphics);
        }
    }

    private void drawHUD(GraphicInterface graphics) {
        int pointsCollected = totalPoints - points.size();
        int percentage = totalPoints > 0 ? (pointsCollected * 100) / totalPoints : 0;

        String hudText = "Points: " + pointsCollected + "/" + totalPoints + " (" + percentage + "%)";

        graphics.setForegroundColor("#FFFFFF");
        graphics.enableBold();

        // Desenhar no topo
        for (int i = 0; i < hudText.length(); i++) {
            graphics.putChar(2 + i, 0, String.valueOf(hudText.charAt(i)));
        }
    }

    private void drawGameOver(GraphicInterface graphics) {
        String text = "YOU DIED!";
        int startX = (width - text.length()) / 2;
        int startY = height / 2;

        graphics.setForegroundColor("#FF0000");
        graphics.enableBold();

        for (int i = 0; i < text.length(); i++) {
            graphics.putChar(startX + i, startY, String.valueOf(text.charAt(i)));
        }

        String restart = "Press R to restart or ESC to quit";
        int restartX = (width - restart.length()) / 2;
        graphics.setForegroundColor("#FFFFFF");
        for (int i = 0; i < restart.length(); i++) {
            graphics.putChar(restartX + i, startY + 2, String.valueOf(restart.charAt(i)));
        }
    }

    public void processKey(KeyStroke key) {
        if (gameOver) {
            // Se morreu, só aceita R para restart ou ESC para sair
            if (key.getKeyType() == KeyType.Character &&
                    (key.getCharacter() == 'r' || key.getCharacter() == 'R')) {
                restart();
            }
            return;
        }

        if (key.getKeyType() == KeyType.ArrowUp) {
            movePacman(pacman.moveUp());
        } else if (key.getKeyType() == KeyType.ArrowDown) {
            movePacman(pacman.moveDown());
        } else if (key.getKeyType() == KeyType.ArrowLeft) {
            movePacman(pacman.moveLeft());
        } else if (key.getKeyType() == KeyType.ArrowRight) {
            movePacman(pacman.moveRight());
        }

        // Mover fantasmas
        moveGhosts();

        // Verificar colisão com fantasmas
        checkGhostCollision();
    }

    private void movePacman(Position newPosition) {
        if (canMoveTo(newPosition)) {
            pacman.setPosition(newPosition);
            collectPoint(newPosition);
        }
    }

    private void collectPoint(Position position) {
        // Remover ponto se o Pacman passou por cima
        points.removeIf(point -> point.getPosition().equals(position));

        // Verificar vitória
        if (points.isEmpty()) {
            System.out.println("YOU WIN! All points collected!");
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

    private void checkGhostCollision() {
        for (Ghost ghost : ghosts) {
            if (pacman.getPosition().equals(ghost.getPosition())) {
                gameOver = true;
                return;
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

    private void restart() {
        // Limpar tudo
        walls.clear();
        ghosts.clear();
        points.clear();

        // Recriar arena
        gameOver = false;
        createArena();
        createPoints();
        totalPoints = points.size();
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}