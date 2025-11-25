package pt.up.fe.ldts.game.view;

import org.junit.jupiter.api.Test;

import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class DrawArenaTest {

    @Test
    void testHUDShowsPoints() {
        DrawArena drawArena = new DrawArena();
        String hud = drawArena.getHUDText(List.of(), 10);
        assertEquals("Points: 10/10 (100%)", hud);
    }

}
