package pt.up.fe.ldts.game.view;

import pt.up.fe.ldts.game.model.Arena;
import pt.up.fe.ldts.game.model.element.*;
import pt.up.fe.ldts.game.graphics.GraphicInterface;

import java.io.IOException;
import java.util.List;
import java.util.Random;

public class DrawArena {

    public void draw(Arena arena, GraphicInterface graphics) throws InterruptedException {
        try {
            graphics.clear();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if (!arena.isGameOver() && !arena.isGameWon()) {
            // 1. Desenhar Paredes (Azul)
            graphics.setForegroundColor("#0000FF");
            for (Wall wall : arena.getWalls()) {
                graphics.putChar(wall.getPosition().getX(), wall.getPosition().getY(), "█");
            }

            // 2. Desenhar Pontos (Branco)
            graphics.setForegroundColor("#FFFFFF");
            for (Point point : arena.getPoints()) {
                graphics.putChar(point.getPosition().getX(), point.getPosition().getY(), ".");
            }

            // 3. Desenhar Power Pellets (Dourado)
            graphics.setForegroundColor("#FFD700");
            for (PowerPellet pellet : arena.getPowerPellets()) {
                graphics.putChar(pellet.getPosition().getX(), pellet.getPosition().getY(), "O");
            }

            // 4. Desenhar Fantasmas (Cores dinâmicas)
            for (Ghost ghost : arena.getGhosts()) {
                graphics.setForegroundColor(ghost.getColor());
                graphics.putChar(ghost.getPosition().getX(), ghost.getPosition().getY(), "G");
            }

            // 5. Desenhar Pacman (Amarelo)
            graphics.setForegroundColor("#FFFF33");
            graphics.putChar(arena.getPacman().getPosition().getX(), arena.getPacman().getPosition().getY(), "C");

            drawHUD(arena, graphics);
        } else if (arena.isGameOver()) {
            drawGameOver(arena.getWidth(), arena.getHeight(), arena.getPacman().getScore(), graphics);
        } else {
            drawGameWin(arena.getWidth(), arena.getHeight(), arena.getPacman().getScore(), graphics);
        }

        try {
            graphics.refresh();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void drawHUD(Arena arena, GraphicInterface graphics)  throws InterruptedException{
        int total = arena.getTotalPoints();
        int collected = total - arena.getPoints().size();
        int percentage = total > 0 ? (collected * 100) / total : 0;

        String hudText = "Points: " + collected + "/" + total + " (" + percentage + "%)";

        if (arena.getPacman().isImmune()) {
            hudText += " | POWER: " + (arena.getPacman().getImmuneTimeLeft()) + "s";
        }

        graphics.setForegroundColor("#FFFFFF");
        graphics.enableBold();
        drawString(graphics, 2, 0, hudText);
    }

    private void drawString(GraphicInterface graphics, int x, int y, String text) {
        for (int i = 0; i < text.length(); i++) {
            graphics.putChar(x + i, y, String.valueOf(text.charAt(i)));
        }
    }

    private void drawGameOver(int width, int height, int score, GraphicInterface graphics)throws InterruptedException {
        graphics.setForegroundColor("#FF0000");
        graphics.enableBold();
        String msg = "GAME OVER! Score: " + score;
        drawString(graphics, (width - msg.length()) / 2, height / 2, msg);

        graphics.setForegroundColor("#FFFFFF");
        String restart = "Press R to Restart";
        drawString(graphics, (width - restart.length()) / 2, height / 2 + 2, restart);
    }

    private void drawGameWin(int width, int height, int score, GraphicInterface graphics) throws InterruptedException{
        graphics.setForegroundColor("#00FF00");
        graphics.enableBold();
        String msg = "YOU WIN! Score: " + score;
        drawString(graphics, (width - msg.length()) / 2, height / 2, msg);

        drawConfetti(width, height, graphics);
    }

    private void drawConfetti(int width, int height, GraphicInterface graphics) {
        Random rand = new Random();
        String[] colors = {"#FF0000", "#00FF00", "#FFFF00", "#00FFFF"};
        for (int i = 0; i < 20; i++) {
            graphics.setForegroundColor(colors[rand.nextInt(colors.length)]);
            graphics.putChar(rand.nextInt(width), rand.nextInt(height), "*");
        }
    }
    public String getRestartText() {
        return "Press R to Restart";
    }
}