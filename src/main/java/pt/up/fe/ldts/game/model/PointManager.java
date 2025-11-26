package pt.up.fe.ldts.game.model;

import pt.up.fe.ldts.game.model.element.Ghost;
import pt.up.fe.ldts.game.model.element.Pacman;
import pt.up.fe.ldts.game.model.element.Point;
import pt.up.fe.ldts.game.model.element.Wall;

import java.util.List;

public class PointManager {
    // ... (restante da classe, incluindo o array POWER_PELLET_POSITIONS se o tiver)

    public void createPoints(List<Point> points, List<Wall> walls, Pacman pacman, List<Ghost> ghosts, int width, int height) {
        // CORREÇÃO: Começamos a partir de y = 2 em vez de y = 1.
        // A linha y=0 é para o HUD (Score).
        // A linha y=1 é para o topo da parede.
        // Os pontos começam a ser criados na linha y=2.
        for (int y = 2; y < height - 1; y++) {
            for (int x = 1; x < width - 1; x++) {
                Position pos = new Position(x, y);
                if (canPlacePoint(pos, walls, pacman, ghosts)) {
                    points.add(new Point(x, y));
                }
            }
        }
    }

    private boolean canPlacePoint(Position position, List<Wall> walls, Pacman pacman, List<Ghost> ghosts) {
        for (Wall wall : walls) if (wall.getPosition().equals(position)) return false;
        if (pacman.getPosition().equals(position)) return false;
        for (Ghost ghost : ghosts) if (ghost.getPosition().equals(position)) return false;
        return true;
    }

    public void collectPoint(List<Point> points, Pacman pacman, Position position) {
        boolean removed = points.removeIf(point -> point.getPosition().equals(position));
        if (removed) pacman.addScore(1);
    }
}