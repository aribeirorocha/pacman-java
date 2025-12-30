package pt.up.fe.ldts.game.Unit_Tests.model.element;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import pt.up.fe.ldts.game.graphics.GraphicInterface;
import pt.up.fe.ldts.game.model.Arena;
import pt.up.fe.ldts.game.model.Position;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import pt.up.fe.ldts.game.model.element.Wall;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class WallTest {

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3}) // Corre o teste para os 3 níveis automaticamente
    void testPacmanBlockedByWall(int level) {
        Arena arena = new Arena(10, 10, level);
        Position start = arena.getPacman().getPosition();

        // Adiciona uma parede exatamente onde o Pacman tentará ir
        arena.getWalls().add(new pt.up.fe.ldts.game.model.element.Wall(start.getX() + 1, start.getY()));

        arena.processKey(new KeyStroke(KeyType.ArrowRight));
        arena.update();

        assertEquals(start, arena.getPacman().getPosition(), "Pacman não deve mover-se contra uma parede no nível " + level);
    }
    @Test
    void testConstructorAndPosition() {
        Wall wall = new Wall(10, 20);
        assertEquals(10, wall.getPosition().getX());
        assertEquals(20, wall.getPosition().getY());
    }

    @Test
    void testDrawWall() {
        // 1. Criar o Mock da interface gráfica
        GraphicInterface gui = mock(GraphicInterface.class);

        // 2. Criar uma parede numa posição específica
        Wall wall = new Wall(5, 5);

        // 3. Chamar o método draw
        wall.draw(gui);

        // 4. MATA O MUTANTE da linha 17: Verifica se definiu a cor azul (#0000FF)
        verify(gui, times(1)).setForegroundColor("#0000FF");

        // 5. MATA O MUTANTE da linha 21: Verifica se desenhou o caractere na posição certa
        // Nota: Usa exatamente o caractere que está no teu código ("█" ou "#")
        verify(gui, times(1)).putChar(5, 5, "█");
    }
}