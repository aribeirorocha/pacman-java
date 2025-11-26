package pt.up.fe.ldts.game.model;

import org.junit.jupiter.api.Test;
import pt.up.fe.ldts.game.model.element.Ghost;
import pt.up.fe.ldts.game.model.element.Ghoststop;
import pt.up.fe.ldts.game.model.element.Pacman;

import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;

import static org.junit.jupiter.api.Assertions.*;

public class ArenaGhostTest {

    @Test
    void testPacmanDiesWhenCollidesWithGhost() {
        // Arrange
        Arena arena = new Arena(10, 10);
        Pacman pacman = arena.getPacman();

        // ⚠️ Nota: Para garantir que o teste é isolado, pode ser necessário limpar
        // os elementos criados no construtor da Arena (se for o caso)
        arena.getGhosts().clear();

        // Garante que o Pacman começa numa posição conhecida e simples (ex: (2, 2) por padrão)
        pacman.setPosition(new Position(2, 2));

        // Cria um fantasma (Ghoststop) uma posição à direita do Pacman: (3, 2)
        Ghost ghost = new Ghoststop(pacman.getPosition().getX() + 1, pacman.getPosition().getY());
        arena.getGhosts().add(ghost);

        // Act
        // Move o Pacman para a direita (ArrowRight), fazendo-o colidir com o fantasma
        arena.processKey(new KeyStroke(KeyType.ArrowRight));

        // Assert
        // O jogo deve estar em estado "Game Over"
        assertTrue(arena.isGameOver(), "Game over after death");

        // (Opcional) Verifica se o Pacman se moveu para o local de colisão
        assertEquals(new Position(3, 2), pacman.getPosition(), "Pacman should move to the collision spot");
    }
}