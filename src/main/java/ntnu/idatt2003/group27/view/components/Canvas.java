package ntnu.idatt2003.group27.view.components;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Duration;
import ntnu.idatt2003.group27.models.Player;
import ntnu.idatt2003.group27.models.Tile;
import ntnu.idatt2003.group27.models.actions.BackToStartAction;
import ntnu.idatt2003.group27.models.actions.LadderAction;
import ntnu.idatt2003.group27.models.actions.ThrowNewDiceAction;

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
    drawAllIcons(gc);
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
        switch (v.getLandAction()) {
          case LadderAction ladderAction -> {
            if (k < ladderAction.getDestinationTileId()) {
              gc.setFill(Color.LIGHTGREEN);
              double[] tileLandPosition = getTilePos(ladderAction.getDestinationTileId() - 1);
              gc.fillRect(tileLandPosition[0], tileLandPosition[1], tileSize, tileSize);
              gc.setFill(Color.GREEN);
            } else {
              gc.setFill(Color.RED);
              double[] tileLandPosition = getTilePos(ladderAction.getDestinationTileId() - 1);
              gc.fillRect(tileLandPosition[0], tileLandPosition[1], tileSize, tileSize);
              gc.setFill(Color.DARKRED);
            }
          }
          case BackToStartAction backToStartAction -> {
            gc.setFill(Color.LIGHTPINK);
          }
          case ThrowNewDiceAction throwNewDiceAction -> {
            gc.setFill(Color.BLUE);
            // Draw icon for action
          }
          default -> {break;}
        }

        double[] tilePosition = getTilePos(k - 1);

        gc.fillRect(tilePosition[0], tilePosition[1], tileSize, tileSize);
      }
    });
  }

  private void drawAllIcons(GraphicsContext gc) {
    tileActions.entrySet().stream()
      .filter(e -> e.getValue() != null && e.getValue().getLandAction() != null)
      .forEach(e -> {
        int tileId = e.getKey() - 1;
        Object landAction = e.getValue().getLandAction();
        String iconPath = null;

        if (landAction instanceof BackToStartAction) {
          iconPath = "/icons/home.png";
        } else if (landAction instanceof ThrowNewDiceAction) {
          iconPath = "/icons/reroll-white.png";
        }

        if (iconPath != null) {
          drawIcon(gc, tileId, iconPath);
        }
      });
  }

  private void drawIcon(GraphicsContext gc, int tileId, String iconPath) {
    double[] tilePosition = getTileCenter(tileId);

    InputStream stream = getClass().getResourceAsStream(iconPath);
    if (stream == null) {
      System.err.println("Resource not found: " + iconPath);
      return;
    }
    Image image = new Image(stream);

    double targetWidth = tileSize / 2;
    double targetHeight = targetWidth * (image.getHeight() / image.getWidth());

    gc.drawImage(image,
      tilePosition[0] - (targetWidth / 2),
      tilePosition[1] - (targetHeight / 2),
      targetWidth,
      targetHeight);
  }

  private void drawArrows(GraphicsContext gc) {
    IntStream.range(1, rows + 1).forEach(i -> {

    });
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

    int row = tileId % columns;
    int col = tileId / columns;

    boolean leftToRight = (col % 2 == 0);
    double xPos = leftToRight ? row * tileSize : (columns - 1 - row) * tileSize;
    double currentYPos = yPos - (col * tileSize);

    return new double[] {xPos, currentYPos};
  }

  private double[] getTileCenter(int tileId) {
    double yPos = (tileSize * rows) - tileSize;

    int row = tileId % columns;
    int col = tileId / columns;

    boolean leftToRight = (col % 2 == 0);
    double xPos = leftToRight ? row * tileSize : (columns - 1 - row) * tileSize;
    double currentYPos = yPos - (col * tileSize);

    return new double[] {xPos + (tileSize / 2), currentYPos + (tileSize / 2)};
  }
}
