package pt.up.fe.ldts.game.view;

import pt.up.fe.ldts.game.model.element.Ghost;
import pt.up.fe.ldts.game.model.element.Pacman;
import pt.up.fe.ldts.game.model.element.Point;
import pt.up.fe.ldts.game.model.element.Wall;
import pt.up.fe.ldts.game.graphics.GraphicInterface;
import java.util.List;
import java.util.Random;

public class DrawArena {

    // Função auxiliar para desenhar strings, usada em toda a classe
    private void drawString(GraphicInterface graphics, int x, int y, String text) {
        for (int i = 0; i < text.length(); i++) {
            graphics.putChar(x + i, y, String.valueOf(text.charAt(i)));
        }
    }

    // Função para desenhar confetes (mantida para a tela de vitória)
    private void drawConfetti(int width, int height, GraphicInterface graphics) {
        Random rand = new Random();
        String[] confettiChars = {"*", "+", "o", ".", "#", "@"};
        String[] confettiColors = {"#FF0000", "#00FF00", "#0000FF", "#FFFF00", "#FF00FF", "#00FFFF"};
        int numConfetti = (width * height) / 20;

        for (int i = 0; i < numConfetti; i++) {
            int x = rand.nextInt(width);
            int y = rand.nextInt(height);
            String charToDraw = confettiChars[rand.nextInt(confettiChars.length)];
            String color = confettiColors[rand.nextInt(confettiColors.length)];

            graphics.setForegroundColor(color);
            graphics.putChar(x, y, charToDraw);
        }
    }

    // --- Métodos Públicos ---

    public void draw(List<Point> points, List<Wall> walls, List<Ghost> ghosts, Pacman pacman, int totalPoints, boolean gameOver, boolean gameWon, int width, int height, GraphicInterface graphics) {
        if (!gameOver && !gameWon) {
            for (Point point : points) point.draw(graphics);
            for (Wall wall : walls) wall.draw(graphics);
            for (Ghost ghost : ghosts) ghost.draw(graphics);
            pacman.draw(graphics);
            drawHUD(points, totalPoints, graphics);
        }

        if (gameOver) {
            drawGameOver(width, height, pacman.getScore(), graphics);
        }
        else if (gameWon) {
            drawGameWin(width, height, pacman.getScore(), graphics);
        }
    }

    public String getHUDText(List<Point> points, int totalPoints) {
        int pointsCollected = totalPoints - points.size();
        int percentage = totalPoints > 0 ? (pointsCollected * 100) / totalPoints : 0;
        return "Points: " + pointsCollected + "/" + totalPoints + " (" + percentage + "%)";
    }

    public String getRestartText() {
        return "Press R to restart or ESC to quit";
    }

    // --- Métodos Privados de Desenho ---

    private void drawHUD(List<Point> points, int totalPoints, GraphicInterface graphics) {
        String hudText = getHUDText(points, totalPoints);
        graphics.setForegroundColor("#FFFFFF");
        graphics.enableBold();

        // Uso de drawString
        drawString(graphics, 2, 0, hudText);

        // graphics.disableBold();
    }

    private void drawGameOver(int width, int height, int score, GraphicInterface graphics) {
        // 1. Fundo
        graphics.setForegroundColor("#000000");
        graphics.fillRectangle(0, 0, width, height, ' ');

        // 2. Título "GAME OVER"
        String title = "YOU DIED!";
        int titleX = (width - title.length()) / 2;
        int titleY = height / 2 - 2;

        graphics.setForegroundColor("#FF0000");
        graphics.enableBold();
        // Uso de drawString
        drawString(graphics, titleX, titleY, title);

        // 3. Pontuação
        String scoreText = "Score: " + score;
        int scoreX = (width - scoreText.length()) / 2;
        int scoreY = height / 2;

        graphics.setForegroundColor("#FFFFFF");
        // Uso de drawString
        drawString(graphics, scoreX, scoreY, scoreText);

        // 4. Mensagem de Reinício
        String restart = getRestartText();
        int restartX = (width - restart.length()) / 2;
        int restartY = height / 2 + 2;

        graphics.setForegroundColor("#FFFF00");
        // Uso de drawString
        drawString(graphics, restartX, restartY, restart);
    }

    private void drawGameWin(int width, int height, int score, GraphicInterface graphics) {
        // 1. Fundo
        graphics.setForegroundColor("#00008B");
        graphics.fillRectangle(0, 0, width, height, ' ');

        // 2. Confetes
        drawConfetti(width, height, graphics);

        // 3. Título "YOU WIN!"
        String title = "YOU WIN!";
        int titleX = (width - title.length()) / 2;
        int titleY = height / 2 - 2;

        graphics.setForegroundColor("#00FF00");
        graphics.enableBold();
        // Uso de drawString
        drawString(graphics, titleX, titleY, title);

        // 4. Pontuação
        String scoreText = "FINAL SCORE: " + score;
        int scoreX = (width - scoreText.length()) / 2;
        int scoreY = height / 2;

        graphics.setForegroundColor("#FFFFFF");
        // Uso de drawString
        drawString(graphics, scoreX, scoreY, scoreText);

        // 5. Mensagem de Reinício
        String restart = getRestartText();
        int restartX = (width - restart.length()) / 2;
        int restartY = height / 2 + 2;

        graphics.setForegroundColor("#FFFF00");
        // Uso de drawString
        drawString(graphics, restartX, restartY, restart);
    }
}