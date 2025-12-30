package pt.up.fe.ldts.game.Unit_Tests.model.element;

import org.junit.jupiter.api.Test;
import pt.up.fe.ldts.game.graphics.GraphicInterface;
import pt.up.fe.ldts.game.model.Position;
import pt.up.fe.ldts.game.model.element.Element;
import pt.up.fe.ldts.game.model.element.Wall;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

public class ElementsTest {

    // Criamos uma classe concreta temporária apenas para testar a classe abstrata Element
    private static class TestElement extends Element {
        public TestElement(int x, int y) {
            super(x, y);
        }

        @Override
        public void draw(GraphicInterface graphics) {
            // Método vazio apenas para cumprir o contrato da classe abstrata
        }
    }

    @Test
    void testElementConstructorAndPosition() {
        // Testa o construtor: Element(int x, int y)
        Element element = new TestElement(10, 20);

        // Testa o getPosition()
        assertNotNull(element.getPosition());
        assertEquals(10, element.getPosition().getX());
        assertEquals(20, element.getPosition().getY());
    }

    @Test
    void testSetPosition() {
        Element element = new TestElement(0, 0);
        Position newPos = new Position(5, 5);

        // Testa o setPosition(Position position)
        element.setPosition(newPos);

        // Mata mutantes: verifica se a posição foi realmente alterada para a nova
        assertEquals(newPos, element.getPosition());
        assertEquals(5, element.getPosition().getX());
    }

    @Test
    void testWallAsElement() {
        // Usando uma classe real do teu projeto para garantir que a herança funciona
        Element wall = new Wall(1, 1);

        assertEquals(1, wall.getPosition().getX());

        Position updatedPos = new Position(2, 2);
        wall.setPosition(updatedPos);

        assertEquals(updatedPos, wall.getPosition());
    }
}