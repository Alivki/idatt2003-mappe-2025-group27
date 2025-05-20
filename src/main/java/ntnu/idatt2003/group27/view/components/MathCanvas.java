package ntnu.idatt2003.group27.view.components;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import ntnu.idatt2003.group27.models.Board;
import ntnu.idatt2003.group27.models.Player;

/**
 * A JavaFX canvas component for rendering a tile-based game board. The canvas displays tiles,
 * players positions, tile actions, and visual indicators like arrows and icons. The board is
 * dynamically sized and supports various tile actions defined in the game model.
 *
 * @author Iver Lindholm
 * @version 1.8
 * @since 2.0
 */
public class MathCanvas extends javafx.scene.canvas.Canvas {
  /**
   * Logger instance for the {@link MathCanvas} class.
   * Used for logging informational messages and errors related to class operations.
   */
  private static final Logger logger = Logger.getLogger(MathCanvas.class.getName());

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
  private final int columns = 5;
  /**
   * The number of rows in the board grid
   */
  private int rows = 0;
  /**
   * The list of players currently on the board.
   */
  private List<Player> players;
  private Map<Player, Board> playersBoard;
  /**
   * The positions of players on the board, mapped to their respective tile IDs.
   */
  private Map<Player, Integer> playerPositions;
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
   * Constructs a {@link MathCanvas} for rendering the game board with the specified tile actions,
   * players, and board size.
   *
   * @param players     A {@link List} of {@link Player} objects representing the players on the board.
   */
  public MathCanvas(List<Player> players, Map<Player, Board> boards) {
    logger.fine("Initializing MathCanvas with players: " + players + " and boards: " + boards);
    this.tileSize = 0;
    this.players = new ArrayList<>(players);
    this.playersBoard = new LinkedHashMap<>(boards);
    this.boardSize = boards.values().stream().findFirst().get().getTiles().size();
    this.rows = players.size();
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
    logger.fine("Getting board size.");
    return boardSize;
  }

  /**
   * Updates the board with a new list of players and redraws the canvas to reflect the changes in
   * their positions.
   *
   * @param players The updated {@link List} of {@link Player} objects.
   */
  public void updateBoard(List<Player> players) {
    logger.fine("Updating board with players: " + players);
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
    logger.fine("Resizing board with tile size: " + tileSize);
    this.tileSize = tileSize;
    redrawBoard();
  }

  /**
   * Redraws the entire board, including tiles, players, tile actions, and visual indicators.
   */
  public void redrawBoard() {
    logger.fine("Redrawing board");
    GraphicsContext gc = getGraphicsContext2D();
    gc.clearRect(0, 0, getWidth(), getHeight());

    drawTiles(gc);
    drawPlayers(gc);
    drawFlag(gc);
  }

  /**
   * Draws the flag on the board, indicating the finish line.
   *
   * @param gc The {@link GraphicsContext} used for drawing.
   */
  private void drawFlag(GraphicsContext gc) {
    logger.fine("Drawing flag.");
    double flagSize = tileSize / 4.3;
    int numberOfFlag = (int) ((getHeight() - 35) / flagSize) + 1;
    double [] tilePos  = getTilePos(1, 4);

    IntStream.range(0, numberOfFlag).forEach(i -> {
      Image flag = new Image(Objects.requireNonNull(
          getClass().getResourceAsStream("/texture/finish" + i % 2 + ".png")));
      double y = i * flagSize + 10;
      gc.drawImage(flag, tilePos[0] + 2, y, flagSize, flagSize);
    });
  }

  /**
   * Draws the grid of tiles on the canvas, including tile borders and tile numbers.
   *
   * @param gc The {@link GraphicsContext} used for drawing.
   */
  private void drawTiles(GraphicsContext gc) {
    logger.fine("Drawing tiles.");
    List<Player> playerList =  new ArrayList<>(playersBoard.keySet());

    playersBoard.entrySet().stream().toList().forEach(entry -> {
      int playerIndex = playerList.indexOf(entry.getKey());
      entry.getValue().getTiles().keySet().forEach(tileId -> {
        double [] tilePosition = getTilePos(playerIndex, tileId - 1);

        gc.setStroke(Color.BLACK);
        gc.setLineWidth(3.0);
        if (tileId == 5) {
          tilePosition = new double[]{tilePosition[0] + (tileSize / 4), tilePosition[1]};
          gc.setFill(Color.YELLOW);
          gc.fillRect(tilePosition[0], tilePosition[1], tileSize, tileSize);
        }
        gc.strokeRect(tilePosition[0], tilePosition[1], tileSize, tileSize);
      });
    });
  }

  /**
   * Draws the players positions on the board with their piece centered on their respective tiles.
   *
   * @param gc The {@link GraphicsContext} used for drawing.
   */
  private void drawPlayers(GraphicsContext gc) {
    logger.fine("Drawing players.");
    players.forEach(player -> {
      double[] tileCenter;
      if (player.equals(animatingPlayer) && animatingPlayerPosition != null) {
        tileCenter = animatingPlayerPosition;
      } else {
        int tileId = playerPositions.getOrDefault(player, 1);
        int playerIndex = players.indexOf(player);
        tileCenter = getTileCenter(playerIndex, tileId - 1);

        tileCenter = new double[] {tileCenter[0], tileCenter[1]};
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
  public void animatePlayerMovement(Player player, int newTileId,
                                    Runnable onComplete) {
    logger.fine("Animating player movement for player: " + player + ", tile: " + newTileId);
    int currentTileId = playerPositions.getOrDefault(player, 1);
    List<Integer> path = calculatePath(currentTileId, newTileId);
    int playerIndex = playerPositions.keySet().stream().toList().indexOf(player);

    Timeline timeline = new Timeline();
    animatingPlayer = player;
    double totalDuration = ANIMATION_DURATION * (path.size() - 1);

    for (int i = 1; i < path.size(); i++) {
      double[] startPos = getTileCenter(playerIndex, path.get(i - 1) - 1);
      double[] endPos = getTileCenter(playerIndex, path.get(i) - 1);
      double fraction = (double) i / (path.size() - 1);

      int finalI = i;
      List<Integer> finalPath = path;
      KeyFrame keyFrame = new KeyFrame(
          Duration.millis(totalDuration * fraction),
          e -> {
            animatingPlayerPosition = new double[] {
                startPos[0] +
                    (endPos[0] - startPos[0]) *
                        (fraction - (finalI - 1.0) / (finalPath.size() - 1)) *
                        (finalPath.size() - 1),
                startPos[1] +
                    (endPos[1] - startPos[1]) *
                        (fraction - (finalI - 1.0) / (finalPath.size() - 1)) *
                        (finalPath.size() - 1)
            };
            redrawBoard();
          }
      );
      timeline.getKeyFrames().add(keyFrame);
    }

    timeline.setOnFinished(e -> {
      logger.fine("timeline finished.");
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
   * Calculates the path between two tiles based on their IDs. The path is represented as a list.
   *
   * @param startTileId The starting tile Id for the path.
   * @param endTileId   The ending tile Id for the path.
   * @return A list of tile IDs representing the path from start to end.
   */
  private List<Integer> calculatePath(int startTileId, int endTileId) {
    logger.fine("Calculating path for startTile: " + startTileId + ", endTile: " + endTileId);
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
   * Calculates the top-left position of a tile based on its ID.
   *
   * @param tileId The zero-based ID of the tile.
   * @return A double array containing the x and y coordinates of the tile's top-left corner.
   */
  public double[] getTilePos(int playerIndex, int tileId) {
    logger.fine("Getting tile position for player: " + playerIndex + ", tile: " + tileId);
    double xPos = 20 + (tileId % columns) * tileSize;
    double yPos = 35 + (playerIndex * tileSize + (playerIndex * 20));

    return new double[] {xPos, yPos};
  }

  /**
   * Calculates the center position of a tile based on its ID.
   *
   * @param tileId The zero-based ID of the tile.
   * @return A double array containing the x and y coordinates of the tile's center.
   */
  public double[] getTileCenter(int playerIndex, int tileId) {
    logger.fine("Getting tile center for player: " + playerIndex + ", tile: " + tileId);
    double[] tilePos = getTilePos(playerIndex, tileId);

    if (tileId == 4) {
      return new double[] {
          tilePos[0] + (tileSize / 2) + (tileSize / 4),
          tilePos[1] + (tileSize / 2)
      };
    }

    return new double[] {tilePos[0] + (tileSize / 2), tilePos[1] + (tileSize / 2)};
  }
}
