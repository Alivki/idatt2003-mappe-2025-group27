package ntnu.idatt2003.group27.view.components;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
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

/**
 * A JavaFX canvas component for rendering a tile-based game board. The canvas displays tiles,
 * players positions, tile actions, and visual indicators like arrows and icons. The board is
 * dynamically sized and supports various tile actions defined in the game model.
 *
 * @author Iver Lindholm
 * @version 1.8
 * @since 2.0
 */
public class Canvas extends javafx.scene.canvas.Canvas {
  /**
   * The total number of tiles on the board
   */
  private final int boardSize;
  /**
   * The size of each tile in pixels
   */
  private double tileSize;
  /**
   * The number of columns in the board grid
   */
  private final int columns = 10;
  /**
   * The number of rows in the board grid
   */
  private final int rows = 9;
  /**
   * The list of players currently on the board.
   */
  private List<Player> players;
  /**
   * The positions of players on the board, mapped to their respective tile IDs.
   */
  private Map<Player, Integer> playerPositions;
  /**
   * A map of tile IDs to their corresponding {@link Tile} objects, containing tile actions
   */
  private final Map<Integer, Tile> tileActions;
  /**
   * The time of the player animation
   */
  private final double ANIMATION_DURATION = 100;
  /**
   * The position of the player being animated
   */
  private double[] animatingPlayerPosition;
  /**
   * The player currently being animated
   */
  private Player animatingPlayer;

  /**
   * Constructs a {@link Canvas} for rendering the game board with the specified tile actions,
   * players, and board size.
   *
   * @param tileActions A {@link Map} of tile IDs to {@link Tile} objects defining the board's
   *                    actions.
   * @param players     A {@link List} of {@link Player} objects representing the players on the board.
   * @param boardSize   The total number of tiles on the board.
   */
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
    this.animatingPlayerPosition = null;
    this.animatingPlayer = null;
  }

  /**
   * Retrieves the total number of tiles on the board.
   *
   * @return The board size as an integer.
   */
  public int getBoardSize() {
    return boardSize;
  }

  /**
   * Updates the board with a new list of players and redraws the canvas to reflect the changes in
   * their positions.
   *
   * @param players The updated {@link List} of {@link Player} objects.
   */
  public void updateBoard(List<Player> players) {
    this.players = new ArrayList<>(players);
    this.playerPositions = this.players.stream()
        .collect(Collectors.toMap(
            player -> player,
            player -> player.getCurrentTile().getTileId()
        ));
    redrawBoard();
  }

  /**
   * Resizes the board by setting a new tile size and redraws the canvas to reflect the change.
   *
   * @param tileSize The new size of each tile in pixels.
   */
  public void resizeBoard(double tileSize) {
    this.tileSize = tileSize;
    redrawBoard();
  }

  /**
   * Redraws the entire board, including tiles, players, tile actions, and visual indicators.
   */
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


  /**
   * Draws the grid of tiles on the canvas, including tile borders and tile numbers.
   *
   * @param gc The {@link GraphicsContext} used for drawing.
   */
  private void drawTiles(GraphicsContext gc) {
    IntStream.range(0, (columns * rows)).forEach(i -> {
      double[] tilePosition = getTilePos(i);

      gc.setStroke(Color.BLACK);
      gc.setLineWidth(3.0);
      gc.strokeRect(tilePosition[0], tilePosition[1], tileSize, tileSize);

      gc.setFill(Color.BLACK);
      gc.setFont(Font.font("Inter", 14));
      gc.fillText(String.valueOf(i + 1), tilePosition[0] + 30, tilePosition[1] + 20);
    });
  }


  /**
   * Draws the players positions on the board with their piece centered on their respective tiles.
   *
   * @param gc The {@link GraphicsContext} used for drawing.
   */
  private void drawPlayers(GraphicsContext gc) {
    players.forEach(player -> {
      double[] tileCenter;
      if (player.equals(animatingPlayer) && animatingPlayerPosition != null) {
        tileCenter = animatingPlayerPosition;
      } else {
        tileCenter = getTileCenter(playerPositions.getOrDefault(player, 1) - 1);
      }

      gc.setFill(player.getColor());
      gc.fillOval(tileCenter[0] - tileSize / 4, tileCenter[1] - tileSize / 4,
          tileSize / 2, tileSize / 2);
      Image piece = new Image(Objects.requireNonNull(
          getClass().getResourceAsStream(player.getPiece().getIconFilePath())));
      gc.drawImage(piece, tileCenter[0] - tileSize / 6, tileCenter[1] - tileSize / 6,
          tileSize / 3, tileSize / 3);
    });
  }

  /**
   * Animates the movement of a player from their current tile to a new tile, updating their
   * position on the board and executing a callback when the animation is complete.
   *
   * @param player     The {@link Player} to animate.
   * @param newTileId  The ID of the tile to move the player to.
   * @param onComplete A {@link Runnable} callback to execute when the animation is complete.
   */
  public void animatePlayerMovement(Player player, int newTileId, TileAction tileAction, int roll,
                                    Runnable onComplete) {
    int currentTileId = playerPositions.getOrDefault(player, 1);
    List<Integer> path = null;

    if (tileAction instanceof LadderAction ladderAction) {
      path = calculateActionPath(currentTileId, currentTileId + roll,
          ladderAction.getDestinationTileId());
    } else if (tileAction instanceof BackToStartAction) {
      path = calculateActionPath(currentTileId, currentTileId + roll, 1);
    } else if (tileAction instanceof ThrowNewDiceAction) {
      path = calculatePath(currentTileId, newTileId);
    }

    if (tileAction == null) {
      path = calculatePath(currentTileId, newTileId);
    }

    Timeline timeline = new Timeline();
    animatingPlayer = player;
    double totalDuration = ANIMATION_DURATION * (path.size() - 1);

    for (int i = 1; i < path.size(); i++) {
      double[] startPos = getTileCenter(path.get(i - 1) - 1);
      double[] endPos = getTileCenter(path.get(i) - 1);
      double fraction = (double) i / (path.size() - 1);

      int finalI = i;
      List<Integer> finalPath = path;
      KeyFrame keyFrame = new KeyFrame(
          Duration.millis(totalDuration * fraction),
          e -> {
            animatingPlayerPosition = new double[] {
                startPos[0] +
                    (endPos[0] - startPos[0]) * (fraction - (finalI - 1.0) / (finalPath.size() - 1)) *
                        (finalPath.size() - 1),
                startPos[1] +
                    (endPos[1] - startPos[1]) * (fraction - (finalI - 1.0) / (finalPath.size() - 1)) *
                        (finalPath.size() - 1)
            };
            redrawBoard();
          }
      );
      timeline.getKeyFrames().add(keyFrame);
    }

    timeline.setOnFinished(e -> {
      animatingPlayer = null;
      animatingPlayerPosition = null;
      playerPositions.put(player, newTileId);
      redrawBoard();
      if (onComplete != null) {
        onComplete.run();
      }
    });

    timeline.play();
  }

  /**
   * Calculates the path between two tiles based on their IDs with the tile action movement
   * in the middle. The path is represented as a list.
   *
   * @param startTileId The starting tile Id for the path.
   * @param endTileId   The ending tile Id for the path.
   * @return A list of tile IDs representing the path from start to end.
   */
  private List<Integer> calculateActionPath(int startTileId, int actionTile, int endTileId) {
    List<Integer> path = new ArrayList<>();
    if (startTileId <= endTileId) {
      for (int i = startTileId; i <= actionTile; i++) {
        path.add(i);
      }
      path.add(endTileId);
    } else {
      for (int i = startTileId; i >= actionTile; i--) {
        path.add(i);
      }
      path.add(endTileId);
    }
    return path;
  }

  /**
   * Calculates the path between two tiles based on their IDs. The path is represented as a list.
   *
   * @param startTileId The starting tile Id for the path.
   * @param endTileId   The ending tile Id for the path.
   * @return A list of tile IDs representing the path from start to end.
   */
  private List<Integer> calculatePath(int startTileId, int endTileId) {
    List<Integer> path = new ArrayList<>();
    if (startTileId <= endTileId) {
      for (int i = startTileId; i <= endTileId; i++) {
        path.add(i);
      }
    } else {
      for (int i = startTileId; i >= endTileId; i--) {
        path.add(i);
      }
    }
    return path;
  }

  /**
   * Draws visual representations of tile actions, such as ladders, back-to-start actions, or dice
   * reroll actions, using different colors for each actions type.
   *
   * @param gc The {@link GraphicsContext} used for drawing.
   */
  private void drawTileActions(GraphicsContext gc) {
    gc.setFill(Color.YELLOW);
    gc.fillRect(30, (rows - 1) * tileSize + 9, tileSize, tileSize);
    gc.fillRect((columns - 1) * tileSize + 30, 9, tileSize, tileSize);

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
          default -> {
            break;
          }
        }

        gc.fillRect(tilePosition[0], tilePosition[1], tileSize, tileSize);
      }
    });
  }

  /**
   * Draws icons for the specific tile actions, such as back-to-start or dice reroll actions, on
   * the corresponding tiles.
   *
   * @param gc The {@link GraphicsContext} used for drawing.
   */
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

  /**
   * Draws a single icon on the specified tile, centered within the tile.
   *
   * @param gc       The {@link GraphicsContext} used for drawing.
   * @param tileId   The ID of the tile (zero-based) where the icon will be drawn.
   * @param iconPath The resource path to the icon image file.
   */
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

  /**
   * Draws arrows indicating the direction of movement across rows on the board.
   *
   * @param gc The {@link GraphicsContext} used for drawing.
   */
  private void drawArrows(GraphicsContext gc) {
    double yPos = (tileSize * rows) + tileSize + 9;

    for (int i = 1; i < rows + 1; i++) {
      yPos -= tileSize;
      double xPos;

      if (i % 2 == 0) {
        xPos = this.getWidth() - 20;
        double[] xPoints = {xPos + 10, xPos, xPos + 10};
        double[] yPoints =
            {yPos - tileSize / 2 - 10, yPos - tileSize / 2, yPos - tileSize / 2 + 10};
        gc.setFill(Color.BLACK);
        gc.fillPolygon(xPoints, yPoints, 3);
      } else {
        xPos = 5;
        double[] xPoints = {xPos, xPos + 10, xPos};
        double[] yPoints =
            {yPos - tileSize / 2 - 10, yPos - tileSize / 2, yPos - tileSize / 2 + 10};
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(2.0);
        gc.strokePolygon(xPoints, yPoints, 3);
      }
    }
  }

  /**
   * Draws all ladders connecting tiles as defined by {@link LadderAction} instances in the tile
   * actions map.
   *
   * @param gc The {@link GraphicsContext} used for drawing.
   */
  private void drawAllLadders(GraphicsContext gc) {
    tileActions.entrySet().stream()
        .filter(e -> e.getValue().getLandAction() instanceof LadderAction)
        .forEach(e -> drawLadder(
            gc,
            e.getKey() - 1,
            ((LadderAction) e.getValue().getLandAction()).getDestinationTileId() - 1));
  }

  /**
   * Draws a ladder connecting two tiles, including rungs.
   *
   * @param gc        The {@link GraphicsContext} used for drawing.
   * @param startTile The zero-based ID of the strating tile.
   * @param endTile   The zero-based ID of the ending tile.
   */
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

  /**
   * Calculates the top-left position of a tile based on its ID.
   *
   * @param tileId The zero-based ID of the tile.
   * @return A double array containing the x and y coordinates of the tile's top-left corner.
   */
  private double[] getTilePos(int tileId) {
    double yPos = (tileSize * rows) - tileSize;

    int col = tileId % columns;
    int row = tileId / columns;

    boolean leftToRight = (row % 2 == 0);
    double xPos = leftToRight ? col * tileSize : (columns - 1 - col) * tileSize;
    double currentYPos = yPos - (row * tileSize);

    return new double[] {xPos + 30, currentYPos + 9};
  }

  /**
   * Calculates the center position of a tile based on its ID.
   *
   * @param tileId The zero-based ID of the tile.
   * @return A double array containing the x and y coordinates of the tile's center.
   */
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
