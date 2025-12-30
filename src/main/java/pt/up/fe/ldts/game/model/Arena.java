package pt.up.fe.ldts.game.model;

import pt.up.fe.ldts.game.controler.GameControler;
import pt.up.fe.ldts.game.graphics.GraphicInterface;
import com.googlecode.lanterna.input.KeyStroke;
import pt.up.fe.ldts.game.model.element.*;
import pt.up.fe.ldts.game.view.DrawArena;

import java.util.ArrayList;
import java.util.Iterator;
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

    // --- OPTIMIZATION: Define Controller as a field ---
    private final GameControler controller;
    // --------------------------------------------------

    private int totalPoints;
    private boolean gameOver;
    private boolean gameWon;

    private long lastSpawnTime;
    private final long spawnInterval = 10000;
    private final Random random = new Random();
    private int score;

    public Arena(int width, int height, int level) {
        this.width = width;
        this.height = height;
        this.currentLevel = level;

        // Initialize the controller once
        this.controller = new GameControler();

        this.walls = new ArrayList<>();
        this.ghosts = new ArrayList<>();
        this.points = new ArrayList<>();
        this.powerPellets = new ArrayList<>();
        this.pacman = new Pacman(2, 2);
        this.gameOver = false;
        this.gameWon = false;

        // Instantiating helpers once
        new CreateArena().createArena(walls, ghosts, pacman, width, height, level);
        new PointManager().createPoints(points, walls, pacman, ghosts, width, height);

        this.totalPoints = points.size();

        this.addPowerPellet(new PowerPellet(6, 5));
    }

    // --- FIX: Added Getters to solve "cannot find symbol" ---
    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
    // --------------------------------------------------------

    public void draw(GraphicInterface graphics) throws InterruptedException {
        // ideally DrawArena should also be a field or passed in, but this is okay for now
        new DrawArena().draw(this, graphics);
    }

    public void processKey(KeyStroke key) {
        // Use the existing controller instance
        controller.processKey(key, pacman, ghosts, walls, points, this);
    }

    public void update() {
        verifyCollisions();
        if (gameOver) return;

        // Use the existing controller instance
        controller.moveElements(this);

        verifyCollisions();

        spawnPowerPellet();

        // Using iterator is good practice for removing items while looping
        Iterator<PowerPellet> it = powerPellets.iterator();
        while (it.hasNext()) {
            PowerPellet pellet = it.next();
            if (pacman.getPosition().equals(pellet.getPosition())) {
                pacman.activatePower();
                it.remove();
            }
        }

        publicCheckGameStatus();
    }

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

    public Pacman getPacman() { return pacman; }
    public List<Wall> getWalls() { return walls; }
    public List<Ghost> getGhosts() { return ghosts; }
    public List<Point> getPoints() { return points; }
    public int getTotalPoints() { return totalPoints; }

    public Position getGhostCagePosition() {
        return new Position(width / 2, height / 2);
    }

    public int getLevel() { return currentLevel; }
    public int getScore() { return this.score; }

    public boolean isWall(Position position) {
        for (Wall wall : walls) {
            if (wall.getPosition().equals(position)) return true;
        }
        return false;
    }
}