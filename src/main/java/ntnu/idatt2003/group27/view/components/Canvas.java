package ntnu.idatt2003.group27.view.components;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.text.Font;
import javafx.util.Duration;
import ntnu.idatt2003.group27.models.Player;
import ntnu.idatt2003.group27.models.Tile;
import ntnu.idatt2003.group27.models.actions.BackToStartAction;
import ntnu.idatt2003.group27.models.actions.LadderAction;
import ntnu.idatt2003.group27.models.actions.ThrowNewDiceAction;
import ntnu.idatt2003.group27.models.interfaces.TileAction;

public class Canvas extends javafx.scene.canvas.Canvas {
  private final int boardSize;
  private double tileSize;
  private final int columns = 10;
  private final int rows = 9;
  private List<Player> players;
  private Map<Player, Integer> playerPositions;
  private final Map<Integer, Tile> tileActions;

  public Canvas(Map<Integer, Tile> tileActions, List<Player> players, int boardSize) {
    this.tileSize = 0;
    this.players = new ArrayList<>();
    this.boardSize = boardSize;
    this.tileActions = tileActions;
    this.playerPositions = players.stream()
      .collect(Collectors.toMap(
        player -> player,
        player -> player.getCurrentTile().getTileId()
      ));
  }

  public int getBoardSize() {
    return boardSize;
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
    drawAllLadders(gc);
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
    gc.fillRect(30, (rows - 1) * tileSize + 9, tileSize, tileSize);
    gc.fillRect((columns - 1) * tileSize + 30,  9, tileSize, tileSize);

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
    double yPos = (tileSize * rows) + tileSize + 9;

    for (int i = 1; i < rows + 1; i++) {
      yPos -= tileSize;
      double xPos;

      if (i % 2 == 0) {
        xPos = this.getWidth() - 20;
        double[] xPoints = {xPos + 10, xPos, xPos + 10};
        double[] yPoints = {yPos - tileSize / 2 - 10, yPos - tileSize / 2, yPos - tileSize / 2 + 10};
        gc.setFill(Color.BLACK);
        gc.fillPolygon(xPoints, yPoints, 3);
      } else {
        xPos = 5;
        double[] xPoints = {xPos, xPos + 10, xPos};
        double[] yPoints = {yPos - tileSize / 2 - 10, yPos - tileSize / 2, yPos - tileSize / 2 + 10};
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(2.0);
        gc.strokePolygon(xPoints, yPoints, 3);
      }
    }
  }

  private void drawAllLadders(GraphicsContext gc) {
    tileActions.entrySet().stream()
      .filter(e -> e.getValue().getLandAction() instanceof LadderAction)
      .forEach(e -> drawLadder(gc, e.getKey() - 1, ((LadderAction) e.getValue().getLandAction()).getDestinationTileId() - 1));
  }

  private void drawLadder(GraphicsContext gc, int startTile, int endTile) {
    double[] start = getTileCenter(startTile);
    double[] end = getTileCenter(endTile);

    double dx = end[0] - start[0];
    double dy = end[1] - start[1];
    double angle = Math.atan2(dy, dx);
    double length = Math.sqrt(dx * dx + dy * dy);

    double radius = tileSize / 4;
    double[] offset = {radius * Math.sin(angle), radius * Math.cos(angle)};

    gc.setStroke(Color.BLACK);
    gc.setLineWidth(5);
    gc.setLineCap(StrokeLineCap.ROUND);
    gc.beginPath();
    gc.moveTo(start[0] + offset[0], start[1] - offset[1]);
    gc.lineTo(end[0] + offset[0], end[1] - offset[1]);
    gc.stroke();

    int numSteps = (int) (length / (tileSize / 2));
    double stepDx = dx / (numSteps + 1);
    double stepDy = dy / (numSteps + 1);
    double stepRadius = radius - 2;
    double[] stepOffset = {stepRadius * Math.sin(angle), stepRadius * Math.cos(angle)};

    gc.setStroke(Color.BLACK);
    gc.setLineWidth(7);
    gc.beginPath();
    for (int i = 1; i <= numSteps; i++) {
      double x = start[0] + i * stepDx;
      double y = start[1] + i * stepDy;
      gc.moveTo(x + stepOffset[0], y - stepOffset[1]);
      gc.lineTo(x - stepOffset[0], y + stepOffset[1]);
    }
    gc.stroke();

    gc.setStroke(Color.WHITE);
    gc.setLineWidth(3);
    gc.beginPath();
    for (int i = 1; i <= numSteps; i++) {
      double x = start[0] + i * stepDx;
      double y = start[1] + i * stepDy;
      gc.moveTo(x + stepOffset[0], y - stepOffset[1]);
      gc.lineTo(x - stepOffset[0], y + stepOffset[1]);
    }
    gc.stroke();

    gc.setStroke(Color.BLACK);
    gc.setLineWidth(5);
    gc.beginPath();
    gc.moveTo(start[0] - offset[0], start[1] + offset[1]);
    gc.lineTo(end[0] - offset[0], end[1] + offset[1]);
    gc.stroke();

    gc.setFill(Color.WHITE);
    double[] dotPos = (startTile > endTile) ? start : end;
    gc.fillOval(dotPos[0] + offset[0] - 1.5, dotPos[1] - offset[1] - 1.5, 3, 3);
    gc.fillOval(dotPos[0] - offset[0] - 1.5, dotPos[1] + offset[1] - 1.5, 3, 3);
  }

  private void drawPlayers(GraphicsContext gc) {
    Map<Player, Integer> newPlayerPositions = players.stream()
      .collect(Collectors.toMap(
        player -> player,
        player -> player.getCurrentTile().getTileId()
      ));

/*
    gc.setStroke(Color.BLUE);
    gc.setLineWidth(3.0);

    gc.beginPath();
    double[] startPos = getTileCenter(0);
    gc.moveTo(startPos[0], startPos[1]);
    IntStream.range(0, (columns * rows )).forEach(i -> {
      double[] tileCenter = getTileCenter(i);

      gc.lineTo(tileCenter[0],tileCenter[1]);
    });
    gc.stroke();
*/

    players.forEach(player -> {
      int j = newPlayerPositions.get(player) - playerPositions.get(player);

      double[] tileCenter = getTileCenter(newPlayerPositions.get(player) - 1);

      gc.setFill(Color.BLACK);
      gc.fillOval(tileCenter[0] - tileSize / 8, tileCenter[1] - tileSize / 8, tileSize / 4,  tileSize / 4);

      playerPositions = newPlayerPositions;
    });
  }

  private double[] getTilePos(int tileId) {
    double yPos = (tileSize * rows) - tileSize;

    int col = tileId % columns;
    int row = tileId / columns;

    boolean leftToRight = (row % 2 == 0);
    double xPos = leftToRight ? col * tileSize : (columns - 1 - col) * tileSize;
    double currentYPos = yPos - (row * tileSize);

    return new double[] {xPos + 30, currentYPos + 9};
  }

  private double[] getTileCenter(int tileId) {
    double yPos = (tileSize * rows) - tileSize;

    int col = tileId % columns;
    int row = tileId / columns;

    boolean leftToRight = (row % 2 == 0);
    double xPos = leftToRight ? col * tileSize : (columns - 1 - col) * tileSize;
    double currentYPos = yPos - (row * tileSize);

    return new double[] {xPos + (tileSize / 2) + 30, currentYPos + (tileSize / 2) + 9};
  }
}
