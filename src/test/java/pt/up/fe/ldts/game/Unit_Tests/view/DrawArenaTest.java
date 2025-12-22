package pt.up.fe.ldts.game.Unit_Tests.view;

import org.junit.jupiter.api.Test;
import pt.up.fe.ldts.game.model.element.Point;
import pt.up.fe.ldts.game.view.DrawArena;

import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class DrawArenaTest {

    @Test
    void testHUDShowsPoints() {
        DrawArena drawArena = new DrawArena();
        String hud = drawArena.getHUDText(List.of(), 10);
        assertEquals("Points: 10/10 (100%)", hud);
    }
    @Test
    void testHUDZeroPoints() {
        DrawArena drawArena = new DrawArena();
        String hud = drawArena.getHUDText(List.of(), 0);
        assertEquals("Points: 0/0 (0%)", hud);
    }
}
