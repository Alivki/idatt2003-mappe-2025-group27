package ntnu.idatt2003.group27.view.components;

import java.util.stream.IntStream;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class GameBoardCanvas {
  public Canvas createBoard() {
    final Canvas canvas = new Canvas(640, 576.2);
    GraphicsContext gc = canvas.getGraphicsContext2D();

    double squareSize = 63.8;
    int squaresPerRows = 10;
    int rows = 9;
    double yPos = squareSize * (squaresPerRows - 1) - 63.8;

    IntStream.range(1, squaresPerRows * rows + 1).forEach(i -> {
      int row = (i - 1) / squaresPerRows;
      int col = (i - 1) % squaresPerRows;

      double xPos;
      boolean leftToRight = (row % 2 == 0);
      if (leftToRight) {
        xPos = col * squareSize;
      } else {
        xPos = (squaresPerRows - 1 - col) * squareSize;
      }

      double currentYPos = yPos - (row * squareSize);

      gc.setStroke(Color.BLACK);
      gc.setLineWidth(3.0);
      gc.strokeRect(xPos, currentYPos, squareSize,squareSize);

      gc.setFill(Color.BLACK);
      gc.setFont(Font.font("Inter", 14));
      gc.fillText(String.valueOf(i), xPos + 40, currentYPos + 25);
    });

    return canvas;
  }
}
