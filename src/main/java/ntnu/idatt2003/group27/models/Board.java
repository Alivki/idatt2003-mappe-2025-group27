package ntnu.idatt2003.group27.models;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

/**
 * A class representing the game board.
 */
public class Board {
  private final Map<Integer, Tile> tiles;

  /**
   * Constructor for the Board class.
   *
   * @param numberOfTiles The number of tiles on the board.
   */
  public Board(int numberOfTiles) {
    if (numberOfTiles < 0) {
      throw new IllegalArgumentException("Number of tiles cannot be negative");
    }

    this.tiles = new HashMap<Integer, Tile>();

    addTile(numberOfTiles);
  }

  /**
   * getTiles method to get the tiles of the board.
   *
   * @return The tiles of the board.
   */
  public Map<Integer, Tile> getTiles() {
    return tiles;
  }

  /**
   * Adds the specified number of tiles to the board.
   *
   * @param numberOfTiles The number of tiles to add.
   */
  private void addTile(int numberOfTiles) {
    IntStream.range(0, numberOfTiles).forEach(i -> {
      if (tiles.containsKey(i)) {
        throw new IllegalArgumentException("Tile with ID " + i + " already exists");
      }
      tiles.put(i, new Tile(i));
    });
  }

  /**
   * Returns the tile with the specified ID.
   *
   * @param tileId The ID of the tile to return.
   * @return The tile with the specified ID.
   */
  public Tile getTile(int tileId) {
    return tiles.get(tileId);
  }
}
