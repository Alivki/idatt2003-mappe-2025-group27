package ntnu.idatt2003.group27.models;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;
import java.util.NoSuchElementException;

/**
 * A class representing the game board in a tile-based game.
 * The board consists of a number of tiles, each with a unique ID.
 * Tiles are stored in a Map with Integer as tile id key and Tile object as value.
 *
 * @author Iver Lindholm, Amadeus Berg
 * @since 0.0
 * @version 1.1
 */
public class Board {
  /**
   * Logger instance for the {@link Board} class.
   * Used for logging informational messages and errors related to class operations.
   */
  private static final Logger logger = Logger.getLogger(Board.class.getName());

  /**
   * The map storing the tiles on the board,
   * with tile IDs as keys and {@link Tile} objects as values.
   */
  private final Map<Integer, Tile> tiles;

  /**
   * Constructs a new Board using a pre-existing Map of tiles.
   * This constructor allows for custom tile configurations to be passed directly.
   *
   * @param inputTiles The map of tiles to initialize the board with, where keys are tile IDs
   *                   and values are Tile objects. Must not be null.
   * @throws IllegalArgumentException if {@code inputTiles} is null.
   */
  public Board(Map<Integer, Tile> inputTiles) throws IllegalArgumentException {
    logger.fine("Creating board.");
    if (inputTiles == null) {
      logger.warning("Input tiles is null.");
      throw new IllegalArgumentException("Input tiles cannot be null");
    }

    this.tiles = inputTiles;
  }

  /**
   * Retrieves the map of tiles on the board.
   * The returned map is a copy of the internal map,
   * so changes to the returned map will not affect the board.
   *
   * @return A {@link Map} containing the tiles on the board, with tile IDs as keys and {@link Tile}
   *     objects as values.
   */
  public Map<Integer, Tile> getTiles() {
    logger.fine("Getting tiles.");
    return new HashMap<>(tiles);
  }

  /**
   * Retrieves the tile associated with the specified ID.
   *
   * @param tileId The ID of the tile to retrieve.
   * @return The {@link Tile} object corresponding to the give {@code tileId},
   *      or {@code null} if no tile with the specified ID exists.
   */
  public Tile getTile(int tileId) {
    logger.fine("Getting tile " + tileId);
    if (!tiles.containsKey(tileId)) {
      logger.warning("Tile does not exist!");
      throw new NoSuchElementException("Tile with ID " + tileId + " does not exist");
    }
    return tiles.get(tileId);
  }

  /**
   * Adds a tile to the board.
   *
   * @param tile The tile to add to the board.
   */
  public void addTile(Tile tile) {
    logger.fine("Adding tile " + tile);
    tiles.put(tile.getTileId(), tile);
  }
}
