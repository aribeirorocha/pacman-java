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
    private Pacman pacman;
    private List<Ghost> ghosts;
    private List<Wall> walls;
    private List<Point> points;
    private int totalPoints;
    private boolean gameOver;
    private boolean gameWon;
    private int currentLevel = 1;

    private List<PowerPellet> powerPellets = new ArrayList<>();
    private long lastSpawnTime = System.currentTimeMillis();
    private final long spawnInterval = 10000;
    private final Random random = new Random();

    public Arena(int width, int height, int level) {
        this.width = width;
        this.height = height;
        this.currentLevel = level;
        this.walls = new ArrayList<>();
        this.ghosts = new ArrayList<>();
        this.points = new ArrayList<>();
        this.pacman = new Pacman(2, 2);
        this.gameOver = false;
        this.gameWon=false;


        new CreateArena().createArena(walls, ghosts, pacman, width, height, level);

        new PointManager().createPoints(points, walls, pacman, ghosts, width, height);

        this.totalPoints = points.size(); // Total de pontos

        this.addPowerPellet(new PowerPellet(6,5));
    }

    public void draw(GraphicInterface graphics) throws InterruptedException {
        new DrawArena().draw(points, walls, ghosts, pacman, totalPoints, gameOver,gameWon, width, height, graphics, powerPellets);
    }

    public void processKey(KeyStroke key) {
        new GameControler().processKey(key, pacman, ghosts, walls, points, this);

    }

    public void update() {
        // mover elementos (pacman, ghosts, etc.)
        new GameControler().moveElements(this);
        for (Ghost g : ghosts) {
            if (pacman.getPosition().equals(g.getPosition())) {
                setGameOver(true);
                return; // para testes
            }
        }
        // gerar power pellets
        spawnPowerPellet();
        Iterator<PowerPellet> it = powerPellets.iterator();
        while (it.hasNext()) {
            PowerPellet pellet = it.next();
            if (pacman.getPosition().equals(pellet.getPosition())) {
                pacman.activatePower();
                it.remove();
            }
        }
    }




    private void checkGameStatus(){
        if(points.isEmpty()){
            this.gameWon=true;
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
        this.gameWon=false;

        new CreateArena().createArena(walls, ghosts, pacman, width, height, currentLevel);
        new PointManager().createPoints(points, walls, pacman, ghosts, width, height);
        this.addPowerPellet(new PowerPellet(6, 5));


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

            boolean isWall = walls.stream().anyMatch(w -> w.getPosition().equals(pos));
            if (isWall) continue;

            if (pacman.getPosition().equals(pos)) continue;

            boolean isGhost = ghosts.stream().anyMatch(g -> g.getPosition().equals(pos));
            if (isGhost) continue;

            boolean isPellet = powerPellets.stream().anyMatch(p -> p.getPosition().equals(pos));
            if (isPellet) continue;

            return pos;
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
    public boolean isGameWon(){return gameWon;}

    public int getWidth() { return width; }
    public int getHeight() { return height; }
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

}