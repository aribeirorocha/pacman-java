package pt.up.fe.ldts.game.Unit_Tests.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.up.fe.ldts.game.model.Arena;
import pt.up.fe.ldts.game.model.Position;
import pt.up.fe.ldts.game.model.element.*;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

public class ArenaTest {
    private Arena arena;

    @BeforeEach
    void setUp() {
        // Criar uma arena pequena para facilitar o controlo das posições
        arena = new Arena(10, 10, 1);
    }

    @Test
    void testConstructorInitializesCorrectly() {
        // Mata mutantes 46.1, 48.1 e 52.1 (survivals do construtor)
        assertFalse(arena.getWalls().isEmpty(), "CreateArena deve ter sido chamado");
        assertFalse(arena.getPoints().isEmpty(), "PointManager deve ter sido chamado");
        assertFalse(arena.getPowerPellets().isEmpty(), "Pelo menos uma PowerPellet deve ser adicionada");
    }


    @Test
    void testPacmanGhostCollisionLogic_Real() {
        // 1. Setup
        arena.setGameOver(false);
        arena.getGhosts().clear();

        Position deathPos = new Position(5, 5);
        Ghost ghost = new Ghoststop(5, 5); // Ou uma subclasse concreta de Ghost
        arena.getGhosts().add(ghost);
        arena.getPacman().setPosition(deathPos);

        // 2. Action - Chama a lógica da Arena
        arena.update();

        // 3. Assertion
        assertTrue(arena.isGameOver(), "A Arena deve detetar colisão e terminar o jogo.");
    }


    @Test
    void testPowerPelletSpawnsAfterTime() throws Exception {
        // 1. Limpa pellets
        arena.getPowerPellets().clear();

        // 2. Manipula o tempo usando Reflection para simular que passaram 11 segundos
        Field lastSpawnField = Arena.class.getDeclaredField("lastSpawnTime");
        lastSpawnField.setAccessible(true);
        // Define o último spawn para 15 segundos atrás
        lastSpawnField.setLong(arena, System.currentTimeMillis() - 15000);

        // 3. Update
        arena.update();

        // 4. Verifica se nasceu um novo pellet
        assertFalse(arena.getPowerPellets().isEmpty(), "Deveria ter nascido um PowerPellet após o tempo de cooldown.");
    }

    @Test
    void testIsWall() {
        // Mata mutantes 173 e 175
        Position wallPos = arena.getWalls().get(0).getPosition();
        Position freePos = new Position(0, 0); // Assume-se livre ou testa-se

        assertTrue(arena.isWall(wallPos));
        assertFalse(arena.isWall(freePos));
    }

    @Test
    void testRestartLogic() {
        // Mata mutantes 99 a 102 (List clear)
        arena.setGameOver(true);
        arena.restart();

        assertFalse(arena.isGameOver());
        assertFalse(arena.getWalls().isEmpty());
    }

    @Test
    void testGetRandomFreePosition() {
        // Mata mutantes 127 a 136 (Lambda expressions)
        Position pos = arena.getRandomFreePosition();

        assertNotNull(pos);
        assertFalse(arena.isWall(pos));
        assertNotEquals(arena.getPacman().getPosition(), pos);
    }
    @Test
    void testVictoryCondition() {
        Arena arena = new Arena(10, 10, 1);
        arena.getPoints().clear(); // Remove todos os pontos

        arena.update();

        assertTrue(arena.isGameWon()); // Mata mutante que ignora lista vazia
    }

    @Test
    void testPointCollection() {
        // 1. Cria a arena
        Arena arena = new Arena(10, 10, 1);

        // 2. Limpa TUDO para teres controle total
        arena.getPoints().clear();


        Position pos = new Position(1, 1);
        arena.getPoints().add(new Point(pos.getX(), pos.getY()));
        arena.getPacman().setPosition(pos);


        arena.update();
        assertEquals(0, arena.getPoints().size(), "A lista de pontos devia estar vazia após o Pacman comer o ponto");
    }

    @Test
    void testWallCollisionCheck() {
        // 1. Obter a posição de uma parede real
        Position wallPos = arena.getWalls().get(0).getPosition();

        // 2. Em vez de "mover" (que é ação do Controller),
        // perguntamos à Arena se aquela posição é uma parede.
        boolean isObstacle = arena.isWall(wallPos);

        // 3. Verificamos se a Arena diz "Sim, é parede"
        assertTrue(isObstacle, "A Arena deve identificar a posição da parede como intransitável");

        // (Opcional) Se tiveres um método 'isEmpty(Position)', podes testar o inverso:
        // assertFalse(arena.isEmpty(wallPos));
    }
    @Test
    void testArenaBoundaries() {
        // Safer way to test if you aren't sure of the exact map layout:
        if (!arena.getWalls().isEmpty()) {
            Position knownWallPos = arena.getWalls().get(0).getPosition();

            // This creates a NEW position with the same coordinates
            // This confirms your 'equals' method works
            Position testPos = new Position(knownWallPos.getX(), knownWallPos.getY());

            assertTrue(arena.isWall(testPos));
        }
    }
}