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

    // ⭐️ ATRIBUTOS EM FALTA (CORRIGIDO) ⭐️
    private final int width;
    private final int height;
    private Pacman pacman;
    private List<Ghost> ghosts;
    private List<Wall> walls;
    private List<Point> points;
    private int totalPoints;
    private boolean gameOver;
    private boolean gameWon;
    // FIM DOS ATRIBUTOS CORRIGIDOS

    // Posições onde Power Pellets serão colocadas
    private static final Position[] POWER_PELLET_POSITIONS = {
            new Position(1, 3),
            new Position(38, 3),
            new Position(1, 15),
            new Position(38, 15)
    };

    public Arena(int width, int height) {
        this.width = width;
        this.height = height;
        this.walls = new ArrayList<>();
        this.ghosts = new ArrayList<>();
        this.points = new ArrayList<>();
        this.pacman = new Pacman(2, 2); // A posição real será definida pelo CreateArena
        this.gameOver = false;
        this.gameWon=false;

        // 1. Cria paredes, fantasmas e define Pacman
        new CreateArena().createArena(walls, ghosts, pacman, width, height);

        // 2. Cria pontos normais (excluindo as posições PP)
        new PointManager().createPoints(points, walls, pacman, ghosts, width, height);





        this.totalPoints = points.size(); // Total de pontos
    }

    public void draw(GraphicInterface graphics) {
        new DrawArena().draw(points, walls, ghosts, pacman, totalPoints, gameOver,gameWon, width, height, graphics);
    }

    public void processKey(KeyStroke key) {
        new GameControler().processKey(key, pacman, ghosts, walls, points, this);
        checkGameStatus();
    }
    private void checkGameStatus(){
        if(points.isEmpty()){
            this.gameWon=true;
        }
    }
    public int getScore() {
        // Assume que a classe Pacman tem um método getScore()
        return pacman.getScore();
    }

    public void restart() {
        walls.clear();
        ghosts.clear();
        points.clear();

        // Reinicializa o pacman para a posição de respawn, o score pode ser mantido ou resetado.
        // Se quiser resetar o score, adicione um método resetScore() em Pacman.
        this.pacman = new Pacman(2, 2);
        this.gameOver = false;
        this.gameWon=false;

        new CreateArena().createArena(walls, ghosts, pacman, width, height);
        new PointManager().createPoints(points, walls, pacman, ghosts, width, height);


        totalPoints = points.size();
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

}