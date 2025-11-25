package pt.up.fe.ldts.game.model;

import pt.up.fe.ldts.game.model.element.Ghost;
import pt.up.fe.ldts.game.model.element.Pacman;
import pt.up.fe.ldts.game.model.element.Wall;

import java.util.List;

public class CreateArena {
    public void createArena(List<Wall> walls, List<Ghost> ghosts, Pacman pacman, int width, int height) {
        for (int x = 0; x < width; x++) {
            walls.add(new Wall(x, 0));
            walls.add(new Wall(x, height - 1));
        }
        for (int y = 1; y < height - 1; y++) {
            walls.add(new Wall(0, y));
            walls.add(new Wall(width - 1, y));
        }
        for (int x = 5; x < 10; x++) {
            walls.add(new Wall(x, 3));
            walls.add(new Wall(x, 7));
        }
        for (int y = 3; y < 7; y++) walls.add(new Wall(5, y));
        for (int x = 15; x < 22; x++) {
            walls.add(new Wall(x, 5));
            walls.add(new Wall(x, 10));
        }
        for (int y = 5; y < 10; y++) {
            walls.add(new Wall(15, y));
            walls.add(new Wall(25, y));
        }
        for (int x = 28; x < 35; x++) {
            walls.add(new Wall(x, 12));
            walls.add(new Wall(x, 16));
        }
        for (int y = 8; y < 15; y++) walls.add(new Wall(10, y));
        for (int i = 0; i < 5; i++) walls.add(new Wall(20 + i, 12 + i));

        pacman.setPosition(new Position(2, 2));
        ghosts.add(new Ghost(width - 3, 2));
        ghosts.add(new Ghost(width - 3, height - 3));
        ghosts.add(new Ghost(6, height - 6));
        ghosts.add(new Ghost(12, height - 8));
        ghosts.add(new Ghost(26, height - 10));
        ghosts.add(new Ghost(7, height - 14));
    }
}
