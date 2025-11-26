## LDTS_<T><G> - <PACMAN>

Neste jogo clássico chamado PACMAN, o user controla uma personagem que percorre um labirinto comendo pontos e evitando os fantasmas. O objetivo é limpar o mapa sem ser apanhado pelos fantasmas.

Este projeto foi desenvolvido por André Rocha (up202407636@up.pt) Catarina Mendes (up202406155@up.pt) e Rodrigo Monteiro (up202005454@up.pt) para LDTS 2025/2026.

### IMPLEMENTED FEATURES

- **Movimento do personagem** - o user controla o seu personagem em quatro direções (cima,baixo,esquerda,direita), está sempre em movimento dependendo da sua direção, só para quando colide com uma parede.

- **Labirinto** - o mapa é composto por paredes que o user não pode passar mas apenas controlar de modo a existir uma navegação estratégica.

- **Pontos** - quantos mais pontos o user coleta maior será a sua pontuação no final, se conseguir comer todos os pontos do mapa o user atinge a pontuação máxima e ganha o jogo.

- **Fantasmas** - Um ou mais fantasmas percorrem o mapa com o objetivo de apanhar o user, se o fizerem o user perde o jogo.

### PLANNED FEATURES

- **Poderes** - O user não morre ser for apanhado pelos fantasmas enquanto o poder estiver ativo.

- **Níveis** - À medida que o user completa o mapa com a pontuação máxima terá à sua disposição um novo mapa com uma dificuldade acrescida.

### Testes

- **Movimento** - Garante que, quando o Pac-Man se move em qualquer sentido, as suas coordenadas alteram consoante o seu movimento.
- **Captura de pontos** - Garante que, quando o Pac-Man captura um ponto, a pontuação é aumentada em 1 valor.
- **Posição** - Garante que duas instâncias diferentes da classe Position, criadas com as mesmas coordenadas, são consideradas iguais
- **Colisão com fantasma** - Garante que o Pac-Man perde a vida quando há colisão com o fantasma.

