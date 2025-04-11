package ntnu.idatt2003.group27.view.components;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Duration;
import ntnu.idatt2003.group27.models.Player;
import ntnu.idatt2003.group27.models.Tile;
import ntnu.idatt2003.group27.models.actions.BackToStartAction;
import ntnu.idatt2003.group27.models.actions.LadderAction;
import ntnu.idatt2003.group27.models.actions.ThrowNewDiceAction;
import ntnu.idatt2003.group27.models.interfaces.TileAction;

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

  public void createBoard(Map<Integer, Tile> tileActions, int boardSize) {
    this.tileActions = tileActions;
    this.boardSize = boardSize;
    redrawBoard();
  }

  public void updateBoard(List<Player> players) {
    this.players = players;
    redrawBoard();
  }

  public void resizeBoard(double tileSize) {
    this.tileSize = tileSize;
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
    IntStream.range(0, (columns * rows )).forEach(i -> {
      double[] tilePosition = getTilePos(i);

      gc.setStroke(Color.BLACK);
      gc.setLineWidth(3.0);
      gc.strokeRect(tilePosition[0], tilePosition[1], tileSize, tileSize);

      gc.setFill(Color.BLACK);
      gc.setFont(Font.font("Inter", 14));
      gc.fillText(String.valueOf(i + 1), tilePosition[0] + 30, tilePosition[1] + 20);
    });
  }

  private void drawTileActions(GraphicsContext gc) {
    gc.setFill(Color.YELLOW);
    gc.fillRect(0, (rows - 1) * tileSize, tileSize, tileSize);
    gc.fillRect((columns - 1) * tileSize, 0, tileSize, tileSize);

    tileActions.forEach((k, v) -> {
      if (v.getLandAction() != null) {
        double[] tilePosition = getTilePos(k - 1);

        switch (v.getLandAction()) {
          case LadderAction ladderAction -> {
            double[] tileLandPosition = getTilePos(ladderAction.getDestinationTileId() - 1);
            if (k < ladderAction.getDestinationTileId()) {
              gc.setFill(Color.LIGHTGREEN);
              gc.fillRect(tileLandPosition[0], tileLandPosition[1], tileSize, tileSize);
              gc.setFill(Color.GREEN);
            } else {
              gc.setFill(Color.RED);
              gc.fillRect(tileLandPosition[0], tileLandPosition[1], tileSize, tileSize);
              gc.setFill(Color.DARKRED);
            }
            drawLadder(gc, k - 1, ladderAction.getDestinationTileId() - 1);
          }
          case BackToStartAction backToStartAction -> {
            gc.setFill(Color.LIGHTPINK);
            // Draw icon for action
          }
          case ThrowNewDiceAction throwNewDiceAction -> {
            gc.setFill(Color.BLUE);
            // Draw icon for action
          }
          default -> {break;}
        }

        //gc.fillRect(tilePosition[0], tilePosition[1], tileSize, tileSize);
      }
    });
  }

  private void drawArrows(GraphicsContext gc) {
    IntStream.range(1, rows + 1).forEach(i -> {

    });
  }

  private void drawLadder(GraphicsContext gc, int tile, int tileLand) {
    double[] tileCenter = getTileCenter(tile);
    double[] tileLandCenter = getTileCenter(tileLand);

    gc.setStroke(Color.BLACK);
    gc.strokeOval(tileLandCenter[0] - 10, tileLandCenter[1] - 10, 20, 20);
    gc.strokeOval(tileCenter[0] - 10, tileCenter[1] - 10, 20, 20);
    double[] baseVector = new double[] {100, 0};
    double[] vector = new double[] {tileLandCenter[0] - tileCenter[0], tileCenter[1] - tileLandCenter[1]};

    int num = (int) (vector[0] * baseVector[0] + vector[1] * baseVector[1]);
    double den = (Math.sqrt(Math.pow(vector[0], 2) + Math.pow(vector[1], 2))) * (Math.sqrt(Math.pow(baseVector[0], 2) + Math.pow(baseVector[1], 2)));
    double cos = num / den;
    double angles = Math.toDegrees(Math.acos(cos));
    double angle = 0;

    int radius = 10;
    double[] firstLadderLegPointOne = new double[] {radius * Math.sin(angle), radius * Math.cos(angle)};
    double[] firstLadderLegPointTwo = new double[] {radius * Math.sin(angle - 90), radius * Math.cos(angle - 90)};

    gc.setStroke(Color.PURPLE);
    gc.beginPath();
    gc.moveTo(tileCenter[0] - firstLadderLegPointOne[0], tileCenter[1] - firstLadderLegPointOne[1]);
    gc.lineTo(tileLandCenter[0] - firstLadderLegPointOne[0], tileLandCenter[1] - firstLadderLegPointOne[1]);
    gc.stroke();
    gc.setStroke(Color.GREEN);
    gc.beginPath();
    gc.moveTo(tileLandCenter[0] - firstLadderLegPointTwo[0], tileLandCenter[1] - firstLadderLegPointTwo[1]);
    gc.lineTo(tileCenter[0] - firstLadderLegPointTwo[0], tileCenter[1] - firstLadderLegPointTwo[1]);
    gc.stroke();
  }

  private void drawPlayers(GraphicsContext gc) {
    gc.setStroke(Color.BLUE);
    gc.setLineWidth(3.0);

    gc.beginPath();
    double[] startPos = getTileCenter(0);
    gc.moveTo(startPos[0], startPos[1]);
    IntStream.range(0, (columns * rows )).forEach(i -> {
      double[] tileCenter = getTileCenter(i);

      gc.lineTo(tileCenter[0],tileCenter[1]);
    });
    //gc.stroke();


    players.forEach(player -> {
      int i = player.getCurrentTile().getTileId() - 1;

      double[] tileCenter = getTileCenter(i);

      gc.setFill(Color.BLACK);
      gc.fillOval(tileCenter[0] - tileSize / 8, tileCenter[1] - tileSize / 8, tileSize / 4,  tileSize / 4);
    });
  }

  private double[] getTilePos(int tileId) {
    double yPos = (tileSize * rows) - tileSize;

    int col = tileId % columns;
    int row = tileId / columns;

    boolean leftToRight = (row % 2 == 0);
    double xPos = leftToRight ? col * tileSize : (columns - 1 - col) * tileSize;
    double currentYPos = yPos - (row * tileSize);

    return new double[] {xPos, currentYPos};
  }

  private double[] getTileCenter(int tileId) {
    double yPos = (tileSize * rows) - tileSize;

    int col = tileId % columns;
    int row = tileId / columns;

    boolean leftToRight = (row % 2 == 0);
    double xPos = leftToRight ? col * tileSize : (columns - 1 - col) * tileSize;
    double currentYPos = yPos - (row * tileSize);

    return new double[] {xPos + (tileSize / 2), currentYPos + (tileSize / 2)};
  }
}
