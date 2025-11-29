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

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameControler {

    public void moveElements(Arena arena) {
        if (arena.isGameOver() || arena.isGameWon()) return;

        movePacmanContinuously(arena.getPacman(), arena.getWalls());
        moveGhosts(arena.getGhosts(), arena.getWalls(), arena.getPacman());

        checkPointCollision(arena.getPacman(), arena.getPoints());
        checkGhostCollision(arena.getPacman(), arena.getGhosts(), arena);
        arena.publicCheckGameStatus();
    }

    private void movePacmanContinuously(Pacman pacman, List<Wall> walls) {
        Position nextPos = pacman.getNextPosition();

        if (!isWall(nextPos, walls)) {
            pacman.setPosition(nextPos);
        } else {
            pacman.setDirection(new Position(0, 0));
        }
    }

    public void processKey(KeyStroke key, Pacman pacman, List<Ghost> ghosts, List<Wall> walls, List<Point> points, Arena arena) {
        if (arena.isGameOver()) {
            if (key.getKeyType() == KeyType.Character &&
                    (key.getCharacter() == 'r' || key.getCharacter() == 'R')) {
                arena.restart();
            }
            return;
        }

        Position newDirection = null;

        if (key.getKeyType() == KeyType.ArrowUp) newDirection = pacman.moveUp().minus(pacman.getPosition());
        else if (key.getKeyType() == KeyType.ArrowDown) newDirection = pacman.moveDown().minus(pacman.getPosition());
        else if (key.getKeyType() == KeyType.ArrowLeft) newDirection = pacman.moveLeft().minus(pacman.getPosition());
        else if (key.getKeyType() == KeyType.ArrowRight) newDirection = pacman.moveRight().minus(pacman.getPosition());

        if (newDirection != null) {
            Position potentialNextPos = pacman.getPosition().plus(newDirection);

            if (!isWall(potentialNextPos, walls)) {
                pacman.setDirection(newDirection);
            }
        }
    }

    private boolean isWall(Position pos, List<Wall> walls) {
        for (Wall wall : walls) {
            if (wall.getPosition().equals(pos)) {
                return true;
            }
        }
        return false;
    }

    private void checkPointCollision(Pacman pacman, List<Point> points) {
        new PointManager().checkPointCollision(pacman, points);
    }

    private void checkGhostCollision(Pacman pacman, List<Ghost> ghosts, Arena arena) {
        for (Ghost ghost : ghosts) {
            if (ghost.getPosition().equals(pacman.getPosition())) {
                arena.setGameOver(true);
                return;
            }
        }
    }

    public void moveGhosts(List<Ghost> ghosts, List<Wall> walls, Pacman pacman) {
        Random random = new Random();
        for (Ghost ghost : ghosts) {
            Position previousDirection = ghost.getDirection();

            List<Position> availableDirections = new ArrayList<>();
            List<Position> nonOppositeAvailableDirections = new ArrayList<>();

            Position[] allDirections = {
                    new Position(0, -1),
                    new Position(0, 1),
                    new Position(-1, 0),
                    new Position(1, 0)
            };

            for (Position attemptedDirection : allDirections) {
                Position nextPosition = ghost.getPosition().plus(attemptedDirection);

                if (!isWall(nextPosition, walls)) {
                    availableDirections.add(attemptedDirection);
                    if (!isOpposite(previousDirection, attemptedDirection)) {
                        nonOppositeAvailableDirections.add(attemptedDirection);
                    }
                }
            }

            Position validNewDirection = null;

            if (nonOppositeAvailableDirections.size() > 0) {
                validNewDirection = nonOppositeAvailableDirections.get(random.nextInt(nonOppositeAvailableDirections.size()));
            } else if (availableDirections.size() > 0) {
                validNewDirection = availableDirections.get(random.nextInt(availableDirections.size()));
            }

            if (validNewDirection != null) {
                ghost.setDirection(validNewDirection);
                ghost.setPosition(ghost.getNextPosition(validNewDirection));
            }
        }
    }

    private boolean isOpposite(Position dir1, Position dir2) {
        return dir1.getX() == -dir2.getX() && dir1.getY() == -dir2.getY();
    }
}