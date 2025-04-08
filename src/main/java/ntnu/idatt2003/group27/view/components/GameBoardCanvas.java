package ntnu.idatt2003.group27.view.components;

import java.util.stream.IntStream;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class GameBoardCanvas {
  private double tileSize;
  private final Canvas canvas;
  private final int columns = 10;
  private final int rows = 9;

  public GameBoardCanvas(double tileSize) {
    this.tileSize = tileSize;
    this.canvas = new Canvas();
  }

  public Canvas createBoard() {
    drawBoard();
    return canvas;
  }

  public void redrawBoard(double tileSize) {
    this.tileSize = tileSize;
    drawBoard();
  }

  private void drawBoard() {
    GraphicsContext gc = canvas.getGraphicsContext2D();
    gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

    drawTileActions(gc);
    drawArrows(gc);

    double yPos = tileSize * (columns - 1) - tileSize;

    IntStream.range(1, columns * rows + 1).forEach(i -> {
      int row = (i - 1) / columns;
      int col = (i - 1) % columns;

      double xPos;
      boolean leftToRight = (row % 2 == 0);

      if (leftToRight) {
        xPos = col * tileSize;
      } else {
        xPos = (columns - 1 - col) * tileSize;
      }

      double currentYPos = yPos - (row * tileSize);

      gc.setStroke(Color.BLACK);
      gc.setLineWidth(3.0);
      gc.strokeRect(xPos, currentYPos, tileSize,tileSize);

      gc.setFill(Color.BLACK);
      gc.setFont(Font.font("Inter", 14));
      gc.fillText(String.valueOf(i), xPos + 30, currentYPos + 20);
    });
  }

  private void drawTileActions(GraphicsContext gc) {
    gc.setFill(Color.YELLOW);
    gc.fillRect(0, (rows - 1) * tileSize, tileSize, tileSize);
    gc.fillRect((columns - 1) * tileSize, 0, tileSize, tileSize);
  }

  private void drawArrows(GraphicsContext gc) {
    IntStream.range(1, rows + 1).forEach(i -> {

    });
  }
}
