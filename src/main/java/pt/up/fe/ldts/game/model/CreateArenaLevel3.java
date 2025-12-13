package pt.up.fe.ldts.game.model;

import pt.up.fe.ldts.game.model.element.Ghost;
import pt.up.fe.ldts.game.model.element.Pacman;
import pt.up.fe.ldts.game.model.element.Wall;

import java.util.Arrays;
import java.util.List;

public class CreateArenaLevel3 {
    private static final List<String> GHOST_COLORS = Arrays.asList(
            "#FF0000", // Blinky
            "#FF69B4", // Pinky
            "#00FFFF", // Inky
            "#FFA500", // Clyde
            "#00FF00", // Extra verde
            "#FF00FF"  // Extra magenta
    );

    private static final String[] MAP_LAYOUT = {
            " ",
            "############################################################",
            "#......................##......................##..........#",
            "#.####.###.##.####.####.##.####.####.##.####.####.##.####..#",
            "#.####.###.##.####.####.##.####.####.##.####.####.##.####..#",
            "#.####.###.##.###..##..##..##..##..##..###..####.##.####..#",
            "#..........................................................#",
            "#.####.##..##..##.##.##.##.##.##.##.##.##.##.##.##.##.##..#",
            "#......##..##.............##..##.....##..##.....##..##...#",
            "####..##...##..##.......#.##....##.##....##.##....##.##..#",
            "####..##...##..## #....##.##.##.##.##.##.##.##.##.##.##..#",
            "#..##..##..##..## G #.##....##.##.##.##.##.##.##.##.##..#",
            "#...............#########..............#########..........#",
            "#.####.###.##.#...........##.####.####.#...........##.#####",
            "#.####.###.##.###..##..##.##.####.####.###..##..##.##.#####",
            "#.####.###.##.###..##..##.##.####.####.###..##..##.##.#####",
            "#....................P.....................................#",
            "#.####.##.#######.##.#######.##.#######.##.#######.##.####.#",
            "#..........................................................#",
            "#.####.###.##.####.####.##.####.####.##.####.####.##.####..#",
            "#.####.###.##.####.####.##.####.####.##.####.####.##.####..#",
            "#.####.###.##.###..##..##..##..##..##..###..####.##.####..#",
            "#..........................................................#",
            "#.####.##..##..##.##.##.##.##.##.##.##.##.##.##.##.##.##..#",
            "#......##..##.............##..##.....##..##.....##..##...#",
            "#.##..##...##..##.......#.##....##.##....##.##....##.##..#",
            "#.##..##...##..## #....##.##.##.##.##.##.##.##.##.##.##..#",
            "#..##..##..##..## G #.##....##.##.##.##.##.##.##.##.##..#",
            "#...............#########..............#########..........#",
            "############################################################"
    };

    public void createArena(List<Wall> walls, List<Ghost> ghosts, Pacman pacman, int width, int height) {
        walls.clear();
        ghosts.clear();
        int ghostIndex = 0;

        for (int y = 0; y < MAP_LAYOUT.length; y++) {
            String line = MAP_LAYOUT[y];
            for (int x = 0; x < line.length(); x++) {
                char character = line.charAt(x);
                switch (character) {
                    case '#': walls.add(new Wall(x, y)); break;
                    case 'P': pacman.setPosition(new Position(x, y)); break;
                    case 'G':
                        if (ghostIndex < GHOST_COLORS.size()) {
                            String color = GHOST_COLORS.get(ghostIndex);
                            ghosts.add(new Ghost(x, y, color));
                            ghostIndex++;
                        }
                        break;
                }
            }
        }

        int cageY = 11;
        int anchorX = (ghosts.size() > 0) ? ghosts.get(0).getPosition().getX() : width / 2;

        while (ghosts.size() < 6 && ghostIndex < GHOST_COLORS.size()) {
            int xPos;
            switch (ghosts.size()) {
                case 1: xPos = anchorX - 1; break;
                case 2: xPos = anchorX + 1; break;
                case 3: xPos = anchorX - 2; break;
                case 4: xPos = anchorX + 2; break;
                case 5: xPos = anchorX;     break;
                default: continue;
            }
            String color = GHOST_COLORS.get(ghostIndex);
            ghosts.add(new Ghost(xPos, cageY, color));
            ghostIndex++;
        }
    }
}

