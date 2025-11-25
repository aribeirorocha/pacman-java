package pt.up.fe.ldts.game.view;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class RestartTextTest {

    @Test
    void testRestartMessageText() {
        DrawArena drawArena = new DrawArena();
        String restartMessage = drawArena.getRestartText();
        assertEquals("Press R to restart or ESC to quit", restartMessage,
                "Deve mostrar a mensagem certa");
    }
}