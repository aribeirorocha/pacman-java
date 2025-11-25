package pt.up.fe.ldts.game.controler;

import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import pt.up.fe.ldts.game.model.Arena;
import pt.up.fe.ldts.game.model.PointManager;
import pt.up.fe.ldts.game.model.Position;
import pt.up.fe.ldts.game.model.element.Ghost;
import pt.up.fe.ldts.game.model.element.Pacman;
import pt.up.fe.ldts.game.model.element.Point;
import pt.up.fe.ldts.game.model.element.Wall;

import java.util.List;

public class GameControler {
    public void processKey(KeyStroke key, Pacman pacman, List<Ghost> ghosts, List<Wall> walls, List<Point> points, Arena arena) {
        if (arena.isGameOver()) {
            if (key.getKeyType() == KeyType.Character &&
                    (key.getCharacter() == 'r' || key.getCharacter() == 'R')) {
                arena.restart();
            }
            return;
        }

        Position newPos = null;
        if (key.getKeyType() == KeyType.ArrowUp) newPos = pacman.moveUp();
        else if (key.getKeyType() == KeyType.ArrowDown) newPos = pacman.moveDown();
        else if (key.getKeyType() == KeyType.ArrowLeft) newPos = pacman.moveLeft();
        else if (key.getKeyType() == KeyType.ArrowRight) newPos = pacman.moveRight();

        if (newPos != null && canMoveTo(newPos, walls)) {
            pacman.setPosition(newPos);
            new PointManager().collectPoint(points, pacman, newPos);
        }

        moveGhosts(ghosts, walls);
        checkGhostCollision(pacman, ghosts, arena);
    }

    private boolean canMoveTo(Position position, List<Wall> walls) {
        for (Wall wall : walls) {
            if (wall.getPosition().equals(position)) return false;
        }
        return true;
    }

    private void moveGhosts(List<Ghost> ghosts, List<Wall> walls) {
        for (Ghost ghost : ghosts) {
            Position newPosition = ghost.move();
            if (canMoveTo(newPosition, walls)) {
                ghost.setPosition(newPosition);
            }
        }
    }

    private void checkGhostCollision(Pacman pacman, List<Ghost> ghosts, Arena arena) {
        for (Ghost ghost : ghosts) {
            if (pacman.getPosition().equals(ghost.getPosition())) {
                arena.setGameOver(true);
                return;
            }
        }
    }
}
