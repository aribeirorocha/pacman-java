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
        if (gameOver) drawGameOver(width, height, graphics);
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

    private void drawGameOver(int width, int height, GraphicInterface graphics) {
        String text = "YOU DIED!";
        int startX = (width - text.length()) / 2;
        int startY = height / 2;
        graphics.setForegroundColor("#FF0000");
        graphics.enableBold();
        for (int i = 0; i < text.length(); i++) {
            graphics.putChar(startX + i, startY, String.valueOf(text.charAt(i)));
        }
        String restart = "Press R to restart or ESC to quit";
        int restartX = (width - restart.length()) / 2;
        graphics.setForegroundColor("#FFFFFF");
        for (int i = 0; i < restart.length(); i++) {
            graphics.putChar(restartX + i, startY + 2, String.valueOf(restart.charAt(i)));
        }
    }
}

