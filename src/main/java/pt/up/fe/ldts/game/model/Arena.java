package pt.up.fe.ldts.game.model;

import pt.up.fe.ldts.game.controler.GameControler;
import pt.up.fe.ldts.game.graphics.GraphicInterface;
import com.googlecode.lanterna.input.KeyStroke;
import pt.up.fe.ldts.game.model.element.Ghost;
import pt.up.fe.ldts.game.model.element.Pacman;
import pt.up.fe.ldts.game.model.element.Point;
import pt.up.fe.ldts.game.model.element.Wall;
import pt.up.fe.ldts.game.view.DrawArena;

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
        this.pacman = new Pacman(2, 2);
        this.gameOver = false;

        new CreateArena().createArena(walls, ghosts, pacman, width, height);
        new PointManager().createPoints(points, walls, pacman, ghosts, width, height);
        this.totalPoints = points.size();
    }

    public void draw(GraphicInterface graphics) {
        new DrawArena().draw(points, walls, ghosts, pacman, totalPoints, gameOver, width, height, graphics);
    }

    public void processKey(KeyStroke key) {
        new GameControler().processKey(key, pacman, ghosts, walls, points, this);
    }

    public void restart() {
        walls.clear();
        ghosts.clear();
        points.clear();
        gameOver = false;
        new CreateArena().createArena(walls, ghosts, pacman, width, height);
        new PointManager().createPoints(points, walls, pacman, ghosts, width, height);
        totalPoints = points.size();
    }

    public boolean isGameOver() { return gameOver; }
    public void setGameOver(boolean value) { this.gameOver = value; }
    public int getWidth() { return width; }
    public int getHeight() { return height; }
    public Pacman getPacman() { return pacman; }
    public List<Wall> getWalls() { return walls; }
    public List<Ghost> getGhosts() { return ghosts; }
    public List<Point> getPoints() { return points; }
    public int getTotalPoints() { return totalPoints; }
}

