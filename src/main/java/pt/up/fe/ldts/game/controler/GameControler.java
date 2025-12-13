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
    private boolean isOpposite(Position dir1, Position dir2) {
        if (dir1 == null || dir2 == null) return false;
        return dir1.getX() == -dir2.getX() && dir1.getY() == -dir2.getY();
    }

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
            for (int step = 0; step < ghost.getSpeed(); step++) {
                Position previousDirection = ghost.getDirection();

                List<Position> available = new ArrayList<>();
                List<Position> nonOpposite = new ArrayList<>();

                Position[] all = {
                        new Position(0, -1),
                        new Position(0, 1),
                        new Position(-1, 0),
                        new Position(1, 0)
                };

                for (Position attempt : all) {
                    Position next = ghost.getPosition().plus(attempt);
                    if (!isWall(next, walls)) {
                        available.add(attempt);
                        if (!isOpposite(previousDirection, attempt)) {
                            nonOpposite.add(attempt);
                        }
                    }
                }

                Position chosen = null;
                if (!nonOpposite.isEmpty()) {
                    chosen = nonOpposite.get(random.nextInt(nonOpposite.size()));
                } else if (!available.isEmpty()) {
                    chosen = available.get(random.nextInt(available.size()));
                }

                if (chosen != null) {
                    ghost.setDirection(chosen);
                    ghost.setPosition(ghost.getNextPosition(chosen));
                }
            }
        }
    }


}