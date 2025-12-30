package pt.up.fe.ldts.game.Unit_Tests.model.element;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import pt.up.fe.ldts.game.graphics.GraphicInterface;
import pt.up.fe.ldts.game.model.Position;
import pt.up.fe.ldts.game.model.element.Pacman;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PacmanTest {
    private Pacman pacman;

    @BeforeEach
    void setUp() {
        // Inicializa o Pacman numa posição central para testar movimentos
        pacman = new Pacman(10, 10);
    }

    @Test
    void testInitialState() {
        assertEquals(new Position(10, 10), pacman.getPosition());
        assertEquals(0, pacman.getScore());
        assertEquals(new Position(0, 0), pacman.getDirection());
        assertFalse(pacman.isImmune());
    }

    @Test
    void testMoveMethods() {
        // Testa se as funções de movimento calculam a posição correta (Matemática)
        // Isso mata mutantes que trocam + por -
        assertEquals(new Position(10, 9), pacman.moveUp());
        assertEquals(new Position(10, 11), pacman.moveDown());
        assertEquals(new Position(9, 10), pacman.moveLeft());
        assertEquals(new Position(11, 10), pacman.moveRight());
    }

    @Test
    void testDirectionAndBuffer() {
        Position dir = new Position(1, 0);
        Position buffer = new Position(0, -1);

        pacman.setDirection(dir);
        pacman.setBufferedDirection(buffer);

        assertEquals(dir, pacman.getDirection());
        assertEquals(buffer, pacman.getBufferedDirection());
    }

    @Test
    void testGetNextPosition() {
        pacman.setDirection(new Position(1, 0)); // Direita
        // 10,10 + 1,0 = 11,10
        assertEquals(new Position(11, 10), pacman.getNextPosition());
    }

    @Test
    void testScore() {
        pacman.addScore(100);
        assertEquals(100, pacman.getScore());

        pacman.addScore(50);
        assertEquals(150, pacman.getScore()); // Mata mutante que substitui += por =
    }

    @Test
    void testImmunityLogic() {
        // Caso 1: Não imune
        assertFalse(pacman.isImmune());
        assertEquals(0, pacman.getImmuneTimeLeft());

        // Caso 2: Ativar poder (5000ms)
        pacman.activatePower();
        assertTrue(pacman.isImmune());

        long timeLeft = pacman.getImmuneTimeLeft();
        // Verificamos se o tempo está no intervalo esperado (aprox 5s)
        assertTrue(timeLeft >= 4 && timeLeft <= 5);
    }

    @Test
    void testImmunityExpiration() {
        // Forçamos uma imunidade negativa para simular tempo expirado
        // Isto mata mutantes de comparação ( < vs <= )
        pacman.setImmune(-1000);
        assertFalse(pacman.isImmune());
        assertEquals(0, pacman.getImmuneTimeLeft());
    }

    @Test
    void testDraw() throws InterruptedException {
        GraphicInterface gui = mock(GraphicInterface.class);

        pacman.draw(gui);

        // Verifica se as cores e o caractere estão corretos
        // Mata mutantes que alteram a String da cor ou o símbolo "C"
        verify(gui).setForegroundColor("#FFFF33");
        verify(gui).enableBold();
        verify(gui).putChar(10, 10, "C");
    }
}