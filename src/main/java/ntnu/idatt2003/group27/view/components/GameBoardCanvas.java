package ntnu.idatt2003.group27.view.components;

import java.util.stream.IntStream;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class GameBoardCanvas {
  private double tileSize;
  private final Canvas canvas;

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

    int tilePerRows = 10;
    int rows = 9;
    double yPos = tileSize * (tilePerRows - 1) - tileSize;

    IntStream.range(1, tilePerRows * rows + 1).forEach(i -> {
      int row = (i - 1) / tilePerRows;
      int col = (i - 1) % tilePerRows;

      double xPos;
      boolean leftToRight = (row % 2 == 0);
      if (leftToRight) {
        xPos = col * tileSize;
      } else {
        xPos = (tilePerRows - 1 - col) * tileSize;
      }

      double currentYPos = yPos - (row * tileSize);

      gc.setStroke(Color.BLACK);
      gc.setLineWidth(3.0);
      gc.strokeRect(xPos, currentYPos, tileSize,tileSize);

      gc.setFill(Color.BLACK);
      gc.setFont(Font.font("Inter", 14));
      gc.fillText(String.valueOf(i), xPos + 40, currentYPos + 25);
    });
  }
}
