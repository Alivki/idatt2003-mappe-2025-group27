package ntnu.idatt2003.group27.view.components;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import ntnu.idatt2003.group27.models.Player;
import ntnu.idatt2003.group27.models.Tile;

public class Canvas extends javafx.scene.canvas.Canvas {
  private int boardSize;
  private double tileSize;
  private final int columns = 10;
  private final int rows = 9;
  private List<Player> players;
  private Map<Integer, Tile> tileActions;

  public Canvas() {
    this.tileSize = 0;
    this.boardSize = 0;
    this.players = new ArrayList<>();
    this.tileActions = new HashMap<>();
  }

  public int getBoardSize() {
    return boardSize;
  }

  public void setBoardSize(int boardSize) {
    this.boardSize = boardSize;
  }

  public void updateBoard(double tileSize, List<Player> players, Map<Integer, Tile> tileActions) {
    this.tileSize = tileSize;
    this.players = players != null ? new ArrayList<>(players) : new ArrayList<>();
    this.tileActions = tileActions != null ? new HashMap<>(tileActions) : new HashMap<>();
    redrawBoard();
  }

  public void redrawBoard() {
    GraphicsContext gc = getGraphicsContext2D();
    gc.clearRect(0, 0, getWidth(), getHeight());

    drawArrows(gc);
    drawTileActions(gc);
    drawTiles(gc);
    drawPlayers(gc);
  }

  private void drawTiles(GraphicsContext gc) {
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

  private void drawPlayers(GraphicsContext gc) {

  }
}
