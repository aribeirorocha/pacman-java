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

    public Arena(int width, int height, int level) {
        this.width = width;
        this.height = height;
        this.currentLevel = level;

        // Inicializamos os componentes de lógica e visão apenas uma vez
        this.controller = new GameControler();
        this.viewer = new DrawArena();

        this.restart(); // Usa o método restart para inicializar o estado do jogo
    }

    // A Arena delega o desenho para o Viewer especializado
    public void draw(GraphicInterface graphics) throws InterruptedException {
        viewer.draw(points, walls, ghosts, pacman, totalPoints, gameOver, gameWon, width, height, graphics, powerPellets);
    }

    // A Arena delega o processamento de teclas para o Controller
    public void processKey(KeyStroke key) {
        controller.processKey(key, pacman, ghosts, walls, points, this);
    }

    // O update coordena a lógica sem instanciar novos controllers
    public void update() {
        if (gameOver || gameWon) return;

        controller.moveElements(this);
        spawnPowerPellet(); // Esta chamada gera os novos poderes

        // Lógica para comer o poder
        powerPellets.removeIf(pellet -> {
            if (pacman.getPosition().equals(pellet.getPosition())) {
                pacman.activatePower(); // Ativa a imunidade no Pacman
                return true;
            }
            return false;
        });
    }
    public void restart() {
        this.walls = new ArrayList<>();
        this.ghosts = new ArrayList<>();
        this.points = new ArrayList<>();
        this.powerPellets = new ArrayList<>();
        this.pacman = new Pacman(2, 2);

        this.gameOver = false;
        this.gameWon = false;

        // Criadores de Labirinto e Pontos (Padrão Builder/Factory)
        new CreateArena().createArena(walls, ghosts, pacman, width, height, currentLevel);
        new PointManager().createPoints(points, walls, pacman, ghosts, width, height);

        this.totalPoints = points.size();
        this.lastSpawnTime = System.currentTimeMillis();
    }

    private void checkGameStatus() {
        if (points.isEmpty()) this.gameWon = true;
    }

    private void spawnPowerPellet() {
        long now = System.currentTimeMillis();
        if (now - lastSpawnTime >= spawnInterval) {
            Position pos = getRandomFreePosition();
            powerPellets.add(new PowerPellet(pos.getX(), pos.getY()));
            lastSpawnTime = now;
        }
    }

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


    public List<PowerPellet> getPowerPellets() { return powerPellets; }
    public Pacman getPacman() { return pacman; }
    public List<Wall> getWalls() { return walls; }
    public List<Ghost> getGhosts() { return ghosts; }
    public List<Point> getPoints() { return points; }
    public boolean isGameOver() { return gameOver; }
    public void setGameOver(boolean value) { this.gameOver = value; }
    public boolean isGameWon() { return gameWon; }
    public int getWidth() { return width; }
    public int getHeight() { return height; }
    public Position getGhostCagePosition() { return new Position(width / 2, height / 2); }
}