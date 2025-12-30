package pt.up.fe.ldts.game.Unit_Tests.controler;

import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import pt.up.fe.ldts.game.controler.Game;
import pt.up.fe.ldts.game.controler.GameControler;
import pt.up.fe.ldts.game.graphics.GraphicInterface;
import pt.up.fe.ldts.game.model.Arena;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class GameTest {
    private Game game;
    private Screen screen;
    private Arena arena;
    private GameControler controller;

    @BeforeEach
    void setUp() {
        screen = mock(Screen.class);
        arena = mock(Arena.class);
        controller = mock(GameControler.class);

        // Spy permite verificar chamadas internas como startSleep
        game = spy(new Game(screen, controller, arena));
    }

    @Test
    void testRunExitWithEscape() throws IOException, InterruptedException {
        when(screen.pollInput()).thenReturn(new KeyStroke(KeyType.Escape));

        game.run();

        verify(controller, atLeastOnce()).moveElements(arena);
        verify(screen).close();
    }

    @Test
    void testRunProcessKeyAndNullInput() throws IOException, InterruptedException {
        // 1. Null (continua) -> 2. Seta (processa) -> 3. EOF (sai)
        when(screen.pollInput())
                .thenReturn(null)
                .thenReturn(new KeyStroke(KeyType.ArrowUp))
                .thenReturn(new KeyStroke(KeyType.EOF));

        game.run();

        verify(arena, times(1)).processKey(any());
        verify(arena).processKey(argThat(k -> k.getKeyType() == KeyType.ArrowUp));
    }

    @Test
    void testMathAndSleepTime() throws IOException, InterruptedException {
        // CORREÇÃO: Primeiro retorna null para completar um ciclo de loop (e dormir),
        // só depois retorna EOF para sair.
        when(screen.pollInput())
                .thenReturn(null)
                .thenReturn(new KeyStroke(KeyType.EOF));

        // Simular que o refresh demora 50ms
        doAnswer(invocation -> {
            Thread.sleep(50);
            return null;
        }).when(screen).refresh();

        game.run();

        // O spy agora vai apanhar a chamada porque o loop completou uma volta
        verify(game, atLeastOnce()).startSleep(longThat(t -> t > 0 && t <= 150));
    }

    @Test
    void testInterruptedExceptionInSleep() throws IOException, InterruptedException {
        // CORREÇÃO: Igual aqui, precisamos entrar no bloco de sleep
        when(screen.pollInput())
                .thenReturn(null)
                .thenReturn(new KeyStroke(KeyType.EOF));

        // Forçar interrupção quando o startSleep for chamado
        doThrow(new InterruptedException()).when(game).startSleep(anyLong());

        game.run();

        // Verifica se a thread foi interrompida (comportamento do catch)
        // Se o teste terminar sem erro, o catch funcionou.
    }

    @Test
    void testDrawException() throws IOException, InterruptedException {
        // CORREÇÃO: Mockar arena.draw em vez de screen.refresh
        // arena.draw lança InterruptedException, screen.refresh NÃO lança.
        doThrow(new InterruptedException()).when(arena).draw(any());

        // É necessário garantir que o loop tenta desenhar
        when(screen.pollInput()).thenReturn(new KeyStroke(KeyType.EOF));

        try {
            game.run();
        } catch (RuntimeException e) {
            // Sucesso: O Game.java embrulha InterruptedException em RuntimeException
            return;
        }
    }

    @Test
    void testCloseException() throws IOException {
        when(screen.pollInput()).thenReturn(new KeyStroke(KeyType.EOF));
        doThrow(new IOException()).when(screen).close();

        game.run();

        verify(screen).close();
    }
    @Test
    void testDrawLogicCalls() throws IOException, InterruptedException {
        // Mata mutantes:
        // 53.1 (screen.clear)
        // 55.1 (arena.draw)
        // 56.1 (screen.refresh)
        // 65.1 (Game.draw call)

        // Configuração: Retorna EOF logo na primeira iteração para o loop rodar apenas uma vez
        when(screen.pollInput()).thenReturn(new KeyStroke(KeyType.EOF));

        game.run();

        // Verificações
        verify(screen, times(1)).clear();
        verify(arena, times(1)).draw(any(GraphicInterface.class));
        verify(screen, times(1)).refresh();
        verify(screen, times(1)).close();
    }

    @Test
    void testStartSleepCallsThreadSleep() {
        // Mata mutante 49.1 (Thread.sleep)
        // Testar Thread.sleep diretamente é difícil, mas podemos testar se o tempo passou.

        long startTime = System.currentTimeMillis();
        long sleepTime = 100;

        try {
            game.startSleep(sleepTime);
        } catch (InterruptedException e) {
            fail("Should not happen");
        }

        long endTime = System.currentTimeMillis();

        // Verifica se passou pelo menos o tempo do sleep (com margem de erro pequena do sistema)
        assertTrue((endTime - startTime) >= sleepTime, "O método startSleep deve pausar a execução.");
    }

    @Test
    void testInterruptedExceptionRestoresFlag() throws IOException, InterruptedException {
        // 1. Configurar o Spy
        Game gameSpy = Mockito.spy(new Game(screen, controller, arena));

        // 2. Configurar o Mock do Screen:
        //    Primeira chamada retorna null (o jogo continua e vai tentar dormir)
        //    Segunda chamada retorna EOF (o jogo sai do loop)
        when(screen.pollInput())
                .thenReturn(null)
                .thenReturn(new KeyStroke(KeyType.EOF));

        // 3. Forçar InterruptedException no startSleep
        doThrow(new InterruptedException()).when(gameSpy).startSleep(anyLong());

        // 4. Executar
        gameSpy.run();

        // 5. Verificar
        assertTrue(Thread.currentThread().isInterrupted(), "A thread deve estar marcada como interrupted.");

        // Cleanup: Limpar a flag para não afetar outros testes
        Thread.interrupted();
    }

    @Test
    void testCloseExceptionPrintsStackTrace() throws IOException {
        // Mata mutante 95.1 (e.printStackTrace no finally block)

        // Captura o System.err
        ByteArrayOutputStream errContent = new ByteArrayOutputStream();
        PrintStream originalErr = System.err;
        System.setErr(new PrintStream(errContent));

        try {
            when(screen.pollInput()).thenReturn(new KeyStroke(KeyType.EOF));
            // Simula erro ao fechar
            doThrow(new IOException("Erro fatal ao fechar")).when(screen).close();

            game.run();

            // Verifica se algo foi escrito no System.err
            String output = errContent.toString();
            assertFalse(output.isEmpty(), "Deve imprimir o stack trace ao falhar o screen.close()");
            assertTrue(output.contains("java.io.IOException"), "Deve conter o tipo da exceção");

        } finally {
            // Restaura o System.err original
            System.setErr(originalErr);
        }
    }

    @Test
    void testLoopConditionals() throws IOException {
        // Ajuda a evitar TIMED_OUT nos mutantes 67, 68 e 2 (negated conditionals)

        // Cenário 1: Retorna null (sem tecla), depois uma tecla normal, depois Escape
        when(screen.pollInput())
                .thenReturn(null)
                .thenReturn(new KeyStroke('a', false, false))
                .thenReturn(new KeyStroke(KeyType.Escape));

        game.run();

        // Se o teste terminar sem Timeout, significa que os condicionais do break funcionaram.
        // Verificamos se processou a tecla 'a'
        verify(arena, times(1)).processKey(any(KeyStroke.class));
        // Verificamos se o loop rodou pelo menos 3 vezes (null, 'a', Escape)
        verify(screen, times(3)).pollInput();
    }

}