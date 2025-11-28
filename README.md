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
- -**Colisão contra parede** - Garante que, quando o pacman se move contra uma parede o seu movimento seja impossibilitado.
- **Pontos** - Garante que os pontos são contabilizados e mostrados corretamente.
- **Recomeçar**- Garante que, ao perder o jogo, a mensagem é corretamente mostrada.
- **GameControler**- Garante que, ao precisonnar a tecla "R" o jogo é reiniciado.

### Design patterns used

**Contexto Problemático:**
Num projeto que envolve sistemas interativos complexos, como um jogo, torna-se imperativo estabelecer uma arquitetura estruturada que promova a modularidade, a separação de preocupações (Separation of Concerns) e a manutenção do código. A ausência de uma organização clara poderia conduzir a um acoplamento excessivo entre a lógica do jogo, as interações do utilizador e a representação visual, dificultando o desenvolvimento e as futuras modificações.

**Solução Adotada:**
Para mitigar estes desafios e organizar o fluxo de dados do sistema, implementou-se o Padrão de Arquitetura Model-View-Controller (MVC). Este padrão de engenharia de software estabelece uma divisão tripartida de responsabilidades, assegurando que cada camada detenha uma função singular e bem definida. Esta abordagem garante que qualquer alteração introduzida numa das camadas (e.g., uma modificação na interface gráfica) não gere um impacto direto nas demais, maximizando a coerência e a extensibilidade do sistema.

**Model (Modelo):** Esta componente encapsula a totalidade do estado do sistema e a lógica inerente ao domínio do problema. No contexto do jogo, o Modelo é responsável por gerir os elementos centrais (e.g., Pacman, fantasmas, colecionáveis) e as regras que governam as suas interações e o game state (estado do jogo).

**View (Vista):** A Vista é dedicada à apresentação dos dados fornecidos pelo Modelo, gerindo exclusivamente a representação visual dos elementos. A sua implementação recorreu à biblioteca Lanterna, que permite a renderização dos elementos do jogo numa interface de consola (terminal).

**Controller (Controlador):** O Controlador atua como o intermediário entre o utilizador e o Modelo. A sua função é interpretar e processar as entradas do utilizador (e.g., comandos de teclado), traduzindo-as em ações que atualizam o estado e a lógica contida no Modelo.

