package pt.up.fe.ldts.game.Unit_Tests.graphics;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.up.fe.ldts.game.graphics.LanternaGraphicsAdapter;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class LanternaGraphicsAdapterTest {
    private Screen screen;
    private TextGraphics tg;
    private LanternaGraphicsAdapter adapter;

    @BeforeEach
    void setUp() {
        screen = mock(Screen.class);
        tg = mock(TextGraphics.class);
        when(screen.newTextGraphics()).thenReturn(tg);
        adapter = new LanternaGraphicsAdapter(screen);
    }

    @Test
    void testSetForegroundColor() {
        adapter.setForegroundColor("#FFFFFF");
        verify(tg).setForegroundColor(TextColor.Factory.fromString("#FFFFFF"));
    }

    @Test
    void testSetInvalidColor() {
        // Mata mutante do bloco catch (linha 24)
        adapter.setForegroundColor("invalid-color");
        verify(tg).setForegroundColor(TextColor.ANSI.WHITE);
    }

    @Test
    void testFillRectangle() {
        // Mata mutantes das linhas 39 e 40 (limites e aritmética)
        adapter.fillRectangle(1, 1, 2, 2, '#');
        verify(tg).putString(1, 1, "#");
        verify(tg).putString(2, 1, "#");
        verify(tg).putString(1, 2, "#");
        verify(tg).putString(2, 2, "#");
    }

    @Test
    void testScreenOperations() throws IOException {
        // Mata mutantes das linhas 48 e 58
        adapter.clear();
        verify(screen).clear();
        adapter.refresh();
        verify(screen).refresh();
    }

    @Test
    void testReadKey() throws IOException {
        // Mata mutante da linha 63 (null return)
        KeyStroke ks = new KeyStroke(KeyType.Escape);
        when(screen.readInput()).thenReturn(ks);

        assertEquals(ks, adapter.readKey());
        verify(screen).readInput();
    }
}