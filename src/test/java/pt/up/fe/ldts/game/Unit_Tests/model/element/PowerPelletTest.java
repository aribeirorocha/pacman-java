package pt.up.fe.ldts.game.Unit_Tests.model.element;

import org.junit.jupiter.api.Test;
import pt.up.fe.ldts.game.graphics.GraphicInterface;
import pt.up.fe.ldts.game.model.Position;
import pt.up.fe.ldts.game.model.element.PowerPellet;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PowerPelletTest {

    @Test
    void testGetPosition() {
        PowerPellet pellet = new PowerPellet(10, 15);
        Position pos = pellet.getPosition();

        assertEquals(10, pos.getX());
        assertEquals(15, pos.getY());
    }


}