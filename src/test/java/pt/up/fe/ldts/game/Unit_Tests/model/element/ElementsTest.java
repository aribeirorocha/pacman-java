package pt.up.fe.ldts.game.Unit_Tests.model.element; // Adjusted package name convention

import org.junit.jupiter.api.Test;
import pt.up.fe.ldts.game.graphics.GraphicInterface;
import pt.up.fe.ldts.game.model.Position;
import pt.up.fe.ldts.game.model.element.Element;
import pt.up.fe.ldts.game.model.element.Wall;

import static org.junit.jupiter.api.Assertions.*;

class ElementsTest { // Public is not strictly required for JUnit 5 classes

    // 1. Helper class to test abstract Element logic
    private static class TestElement extends Element {
        public TestElement(int x, int y) {
            super(x, y);
        }

    }

    @Test
    void testElementConstructorAndPosition() {
        Element element = new TestElement(10, 20);

        // Check if object is initialized
        assertNotNull(element.getPosition());

        // Check coordinate values
        assertEquals(10, element.getPosition().getX());
        assertEquals(20, element.getPosition().getY());
    }

    @Test
    void testSetPosition() {
        Element element = new TestElement(0, 0);
        Position newPos = new Position(5, 5);

        element.setPosition(newPos);

        // Mutation Test Check:
        // Ensures the reference was actually updated, not just the x/y values copied
        // (depending on how your setter is implemented).
        assertSame(newPos, element.getPosition(), "The position object reference should be updated.");

        // OR if you clone the position in the setter:
        // assertEquals(newPos, element.getPosition());
    }

    @Test
    void testWallInheritance() {
        // Ensures Wall correctly calls the super constructor
        Element wall = new Wall(1, 1);

        assertEquals(1, wall.getPosition().getX());
        assertEquals(1, wall.getPosition().getY());
    }
}