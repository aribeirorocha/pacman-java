package pt.up.fe.ldts.game.model;

import pt.up.fe.ldts.game.model.element.Ghost;
import pt.up.fe.ldts.game.model.element.Pacman;
import pt.up.fe.ldts.game.model.element.Wall;

import java.util.Arrays;
import java.util.List;

public class CreateArena {

    // LISTA DE CORES CLÁSSICAS DOS FANTASMAS (Centralizado)
    private static final List<String> GHOST_COLORS = Arrays.asList(
            "#FF0000", // 0: Blinky (Vermelho)
            "#FF69B4", // 1: Pinky (Rosa)
            "#00FFFF", // 2: Inky (Ciano)
            "#FFA500"  // 3: Clyde (Laranja)
    );

    // LAYOUT CORRIGIDO COM BASE NO SEU ENVIO (40x20)
    private static final String[] MAP_LAYOUT = {
            "                                        ", // Linha 0: HUD
            "########################################", // Linha 1: Parede Superior
            "#..................##..................#", // Linha 2
            "#.####.###.##.####.##.###.##.####.####.#", // Linha 3
            "#.####.###.##.####.##.###.##.####.####.#", // Linha 4
            "#.####.###.##.###..##..##.##.####.####.#", // Linha 5
            "#......................................#", // Linha 6
            "#.####.##..##..##.##.##.##.##.##.##.##.#", // Linha 7
            "#......##..##.............##..##.....#.#", // Linha 8
            "####..##...##..##.......#.##....##.##..#", // Linha 9 (Parede superior da gaiola)
            "####..##...##..##       #....##.##.##.##", // Linha 10 (Portão/Passagem)
            "#..##..##..##..##   G   #.##....##.##.##", // Linha 11 (Gaiola: 1 G)
            "#...............#########..............#", // Linha 12
            "#.####.###.##.#...........##.####.####.#", // Linha 13
            "#.####.###.##.###..##..##.##.####.####.#", // Linha 14
            "#.####.###.##.###..##..##.##.####.####.#", // Linha 15
            "#....................P.................#", // Linha 16 (P: Pacman)
            "#.####.##.#######.##.#######.##.####.#.#", // Linha 17
            "#......................................#", // Linha 18
            "########################################"  // Linha 19: Parede Inferior
    };

    public void createArena(List<Wall> walls, List<Ghost> ghosts, Pacman pacman, int width, int height) {
        walls.clear();
        ghosts.clear();
        int ghostIndex = 0; // Índice para buscar a cor

        // 1. PRIMEIRA PASSAGEM: Cria Pacman, Paredes e o primeiro Fantasma (G)
        for (int y = 0; y < MAP_LAYOUT.length; y++) {
            String line = MAP_LAYOUT[y];
            for (int x = 0; x < line.length(); x++) {
                char character = line.charAt(x);

                switch (character) {
                    case '#':
                        walls.add(new Wall(x, y));
                        break;
                    case 'P':
                        pacman.setPosition(new Position(x, y));
                        break;
                    case 'G':
                        if (ghostIndex < GHOST_COLORS.size()) {
                            // Cria o PRIMEIRO fantasma (Vermelho) no lugar do 'G'
                            String color = GHOST_COLORS.get(ghostIndex);
                            ghosts.add(new Ghost(x, y, color));
                            ghostIndex++;
                        }
                        break;
                }
            }
        }

        // ⭐️ 2. SEGUNDA PASSAGEM: ADICIONA OS FANTASMAS EM FALTA ⭐️

        // Determinamos a posição central e a linha da gaiola (onde o 'G' foi encontrado)
        // Linha da gaiola é fixa no layout (y=11)
        int cageY = 11;
        // Posição X do primeiro fantasma 'G' como âncora
        int anchorX = (ghosts.size() > 0) ? ghosts.get(0).getPosition().getX() : width / 2;

        // Adiciona os restantes fantasmas (se houver menos de 4)
        while (ghosts.size() < 4 && ghostIndex < GHOST_COLORS.size()) {
            int xPos;

            // Atribui posições adjacentes ao primeiro 'G' (anchorX)
            switch (ghosts.size()) {
                case 1: xPos = anchorX - 1; break; // Pinky (Posição 2)
                case 2: xPos = anchorX + 1; break; // Inky (Posição 3)
                case 3: xPos = anchorX - 2; break; // Clyde (Posição 4)
                default: continue; // Deve sair
            }

            // Atribui a cor sequencial (o ghostIndex já foi incrementado após o 'G')
            String color = GHOST_COLORS.get(ghostIndex);
            ghosts.add(new Ghost(xPos, cageY, color));
            ghostIndex++;
        }
    }
}