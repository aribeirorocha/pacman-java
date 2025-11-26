package pt.up.fe.ldts.game.view;

import pt.up.fe.ldts.game.model.element.Ghost;
import pt.up.fe.ldts.game.model.element.Pacman;
import pt.up.fe.ldts.game.model.element.Point;
import pt.up.fe.ldts.game.model.element.Wall;
import pt.up.fe.ldts.game.graphics.GraphicInterface;
import java.util.List;

public class DrawArena {
    public void draw(List<Point> points, List<Wall> walls, List<Ghost> ghosts, Pacman pacman, int totalPoints, boolean gameOver, int width, int height, GraphicInterface graphics) {
        for (Point point : points) point.draw(graphics);
        for (Wall wall : walls) wall.draw(graphics);
        for (Ghost ghost : ghosts) ghost.draw(graphics);
        pacman.draw(graphics);
        drawHUD(points, totalPoints, graphics);
        if (gameOver) drawGameOver(width, height, pacman.getScore(), graphics);
    }
    public String getHUDText(List<Point> points, int totalPoints) {
        //Testes
        int pointsCollected = totalPoints - points.size();
        int percentage = totalPoints > 0 ? (pointsCollected * 100) / totalPoints : 0;
        return "Points: " + pointsCollected + "/" + totalPoints + " (" + percentage + "%)";
    }
    public String getRestartText() {
        //Test
        return "Press R to restart or ESC to quit";
    }


    private void drawHUD(List<Point> points, int totalPoints, GraphicInterface graphics) {
        int pointsCollected = totalPoints - points.size();
        int percentage = totalPoints > 0 ? (pointsCollected * 100) / totalPoints : 0;
        String hudText = "Points: " + pointsCollected + "/" + totalPoints + " (" + percentage + "%)";
        graphics.setForegroundColor("#FFFFFF");
        graphics.enableBold();
        for (int i = 0; i < hudText.length(); i++) {
            graphics.putChar(2 + i, 0, String.valueOf(hudText.charAt(i)));
        }
    }

    private void drawGameOver(int width, int height, int score, GraphicInterface graphics) {
        // 1. Preenche toda a área com PRETO
        graphics.setForegroundColor("#000000"); // Cor preta para o fundo
        graphics.fillRectangle(0, 0, width, height, ' '); // Preenche com espaços

        // 2. Título "GAME OVER"
        String title = "YOU DIED!";
        int titleX = (width - title.length()) / 2;
        int titleY = height / 2 - 2; // Duas linhas acima do centro

        graphics.setForegroundColor("#FF0000"); // Cor vermelha
        graphics.enableBold();
        graphics.putChar(titleX, titleY, title);

        // 3. Pontuação
        String scoreText = "Score: " + score;
        int scoreX = (width - scoreText.length()) / 2;
        int scoreY = height / 2; // No centro

        graphics.setForegroundColor("#FFFFFF"); // Cor branca
        graphics.putChar(scoreX, scoreY, scoreText);

        // 4. Mensagem de Reinício
        String restart = "Press R to restart or ESC to quit";
        int restartX = (width - restart.length()) / 2;
        int restartY = height / 2 + 2; // Duas linhas abaixo do centro

        graphics.setForegroundColor("#FFFF00"); // Cor amarela (destaque)
        graphics.putChar(restartX, restartY, restart);

        // Desliga o bold se foi ligado
        // graphics.disableBold();
    }
}

