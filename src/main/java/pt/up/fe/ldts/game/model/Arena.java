package pt.up.fe.ldts.game.model;

import pt.up.fe.ldts.game.controler.GameControler;
import pt.up.fe.ldts.game.graphics.GraphicInterface;
import com.googlecode.lanterna.input.KeyStroke;
import pt.up.fe.ldts.game.model.element.*;
import pt.up.fe.ldts.game.view.DrawArena;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Arena {
    private final int width;
    private final int height;
    private final int currentLevel;

    private Pacman pacman;
    private List<Ghost> ghosts;
    private List<Wall> walls;
    private List<Point> points;
    private List<PowerPellet> powerPellets;

    private int totalPoints;
    private boolean gameOver;
    private boolean gameWon;

    // Instâncias fixas para evitar criação repetitiva de objetos (Injeção/Associação)
    private final GameControler controller;
    private final DrawArena viewer;

    private long lastSpawnTime;
    private final long spawnInterval = 10000;
    private final Random random = new Random();
    private int score;

    public Arena(int width, int height, int level) {
        this.width = width;
        this.height = height;
        this.currentLevel = level;
        this.walls = new ArrayList<>();
        this.ghosts = new ArrayList<>();
        this.points = new ArrayList<>();
        this.pacman = new Pacman(2, 2);
        this.gameOver = false;
        this.gameWon = false;

        new CreateArena().createArena(walls, ghosts, pacman, width, height, level);
        new PointManager().createPoints(points, walls, pacman, ghosts, width, height);

        this.totalPoints = points.size(); // Total de pontos

        this.addPowerPellet(new PowerPellet(6, 5));
    }

    // A Arena delega o desenho para o Viewer especializado
    public void draw(GraphicInterface graphics) throws InterruptedException {
        new DrawArena().draw(this, graphics);
    }

    // A Arena delega o processamento de teclas para o Controller
    public void processKey(KeyStroke key) {
        new GameControler().processKey(key, pacman, ghosts, walls, points, this);
    }

    // --- MAIN UPDATE METHOD ---
    public void update() {
        // 1. Verificar colisão IMEDIATA (crucial para os testes onde setamos posições iguais)
        verifyCollisions();
        if (gameOver) return; // Se já perdeu, não vale a pena mover

        // 2. Mover elementos
        new GameControler().moveElements(this);

        // 3. Verificar colisão PÓS-MOVIMENTO (crucial para o jogo real)
        verifyCollisions();

        // 4. Gerir PowerPellets
        spawnPowerPellet();
        Iterator<PowerPellet> it = powerPellets.iterator();
        while (it.hasNext()) {
            PowerPellet pellet = it.next();
            if (pacman.getPosition().equals(pellet.getPosition())) {
                pacman.activatePower();
                it.remove();
            }
        }

        // 5. Verificar Vitória
        publicCheckGameStatus();
    }

    // Método auxiliar para evitar código duplicado
    private void verifyCollisions() {
        for (Ghost g : ghosts) {
            if (pacman.getPosition().equals(g.getPosition())) {
                setGameOver(true);
            }
        }
    }

    private void checkGameStatus() {
        if (points.isEmpty()) {
            this.gameWon = true;
        }
    }

    public void publicCheckGameStatus() {
        checkGameStatus();
    }
    public void restart() {
        walls.clear();
        ghosts.clear();
        points.clear();
        powerPellets.clear();

        this.pacman = new Pacman(2, 2);

        this.gameOver = false;
        this.gameWon = false;

        // Criadores de Labirinto e Pontos (Padrão Builder/Factory)
        new CreateArena().createArena(walls, ghosts, pacman, width, height, currentLevel);
        new PointManager().createPoints(points, walls, pacman, ghosts, width, height);

        totalPoints = points.size();
        lastSpawnTime = System.currentTimeMillis();
    }

    public List<PowerPellet> getPowerPellets() { return powerPellets; }
    public void addPowerPellet(PowerPellet pellet) { powerPellets.add(pellet); }
    public void removePowerPellet(PowerPellet pellet) { powerPellets.remove(pellet); }

    public Position getRandomFreePosition() {
        while (true) {
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            Position pos = new Position(x, y);

            boolean isOccupied = walls.stream().anyMatch(w -> w.getPosition().equals(pos)) ||
                    ghosts.stream().anyMatch(g -> g.getPosition().equals(pos)) ||
                    pacman.getPosition().equals(pos);

            if (!isOccupied) return pos;
        }
    }

    private void spawnPowerPellet() {
        long now = System.currentTimeMillis();
        if (now - lastSpawnTime < spawnInterval) return;

        Position pos = getRandomFreePosition();
        addPowerPellet(new PowerPellet(pos.getX(), pos.getY()));
        lastSpawnTime = now;
    }

    public boolean isGameOver() { return gameOver; }
    public void setGameOver(boolean value) { this.gameOver = value; }
    public boolean isGameWon() { return gameWon; }

    public List<PowerPellet> getPowerPellets() { return powerPellets; }
    public Pacman getPacman() { return pacman; }
    public List<Wall> getWalls() { return walls; }
    public List<Ghost> getGhosts() { return ghosts; }
    public List<Point> getPoints() { return points; }
    public int getTotalPoints() { return totalPoints; }

    public Position getGhostCagePosition() {
        return new Position(width / 2, height / 2);
    }

    public int getLevel() {
        return currentLevel;
    }

    public int getScore() {
        return this.score;
    }

    public boolean isWall(Position position) {
        for (Wall wall : walls) {
            if (wall.getPosition().equals(position)) return true;
        }
        return false;
    }
}