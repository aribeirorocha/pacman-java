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

        // Verificar colisão imediatamente após o Pacman se mover (Correção de Teste)
        checkGhostCollision(pacman, ghosts, arena);

        // Se o jogo terminou na verificação anterior, não avançamos os fantasmas
        if (arena.isGameOver()) return;

        // Mover fantasmas
        moveGhosts(ghosts, walls);

        // Verificar colisão novamente (caso o fantasma se mova para o Pacman)
        checkGhostCollision(pacman, ghosts, arena);
    }

    private boolean canMoveTo(Position position, List<Wall> walls) {
        for (Wall wall : walls) {
            if (wall.getPosition().equals(position)) return false;
        }
        return true;
    }

    // MÉTODO moveGhosts CORRIGIDO PARA QUEBRAR O LOOP
    private void moveGhosts(List<Ghost> ghosts, List<Wall> walls) {
        Position[] possibleMoves = {
                new Position(0, -1), // Cima
                new Position(0, 1),  // Baixo
                new Position(-1, 0), // Esquerda
                new Position(1, 0)   // Direita
        };

        Random random = new Random();

        for (Ghost ghost : ghosts) {
            Position currentDirection = ghost.getDirection();
            Position newPosition = ghost.getNextPosition(currentDirection);

            // 1. Tenta mover na direção atual (movimento fluido)
            if (canMoveTo(newPosition, walls)) {
                ghost.setPosition(newPosition);
                continue;
            }

            // 2. Se a direção atual estiver bloqueada, recolher direções disponíveis
            List<Position> availableDirections = new ArrayList<>();
            List<Position> nonOppositeAvailableDirections = new ArrayList<>();

            for (Position attemptedDirection : possibleMoves) {
                Position checkPos = ghost.getNextPosition(attemptedDirection);

                if (canMoveTo(checkPos, walls)) {
                    availableDirections.add(attemptedDirection);
                    if (!isOpposite(currentDirection, attemptedDirection)) {
                        nonOppositeAvailableDirections.add(attemptedDirection);
                    }
                }
            }

            // 3. Escolhe a melhor direção da lista de disponíveis
            Position validNewDirection = null;

            if (nonOppositeAvailableDirections.size() > 0) {
                // Prioridade A: Escolha aleatória entre as direções que não são a oposta
                validNewDirection = nonOppositeAvailableDirections.get(random.nextInt(nonOppositeAvailableDirections.size()));
            } else if (availableDirections.size() > 0) {
                // Prioridade B: Escolha aleatória entre as direções disponíveis (só deve acontecer em becos sem saída)
                validNewDirection = availableDirections.get(random.nextInt(availableDirections.size()));
            }

            // 4. Aplica a nova direção
            if (validNewDirection != null) {
                ghost.setDirection(validNewDirection);
                ghost.setPosition(ghost.getNextPosition(validNewDirection));
            }
        }
    }

    private boolean isOpposite(Position dir1, Position dir2) {
        return dir1.getX() == -dir2.getX() && dir1.getY() == -dir2.getY();
    }

    private void checkGhostCollision(Pacman pacman, List<Ghost> ghosts, Arena arena) {
        for (Ghost ghost : ghosts) {
            if (ghost.getPosition().equals(pacman.getPosition())) {
                arena.setGameOver(true);
                return;
            }
        }
    }
}