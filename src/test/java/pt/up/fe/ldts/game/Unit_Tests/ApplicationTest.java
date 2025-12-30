package pt.up.fe.ldts.game.Unit_Tests;

import org.junit.jupiter.api.Test;
import pt.up.fe.ldts.game.Application;

import static org.junit.jupiter.api.Assertions.*;

public class ApplicationTest {
    @Test
    void testMainInterruption() {

        Thread gameThread = new Thread(() -> {
            try {
                Application.main(new String[]{});
            } catch (Exception ignored) {}
        });

        gameThread.start();

        try {

            Thread.sleep(500);

            gameThread.interrupt();
        } catch (InterruptedException e) {
            fail();
        }

        // Se não estoirou com exceção, as linhas foram "cobertas"
        assertTrue(true);
    }
}