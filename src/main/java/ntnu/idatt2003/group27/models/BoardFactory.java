package ntnu.idatt2003.group27.models;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;
import ntnu.idatt2003.group27.models.interfaces.LadderTileAction;
import ntnu.idatt2003.group27.models.interfaces.TileAction;

/**
 * A factory class responsible for creating {@link Board} instances with a specified number of tiles
 * and associated tile actions. Configures the board by linking tiles and assigning actions as
 * needed.
 *
 * @author Iver Lindholm
 * @version 1.0
 * @since 2.0
 */
public class BoardFactory {

  /**
   * Creates a {@link Board} instance with the specified number of tiles and tile actions. Tiles are
   * linked sequentially, and actions are assigned to tiles based on the provided map.
   *
   * @param totalTiles The total number of tiles on the board.
   * @param tileActions A map of tiles IDs to their corresponding {@link LadderTileAction} objects.
   * @return A new {@link Board} instance configured with the specified tiles and actions.
   */
  public Board createBoard(int totalTiles, Map<Integer, TileAction> tileActions) {
    Map<Integer, Tile> tiles = new HashMap<>();

    IntStream.rangeClosed(1, totalTiles).forEach(index -> {
      Tile currentTile = tiles.computeIfAbsent(index, Tile::new);

      if (index < totalTiles) {
        Tile nextTile = tiles.computeIfAbsent(index + 1, Tile::new);
        currentTile.setNextTile(nextTile);
      }

      if (index > 1) {
        currentTile.setPreviousTile(tiles.get(currentTile.getTileId() - 1));
      }

      if (tileActions.containsKey(index)) {
        currentTile.setLandAction(tileActions.get(index));
      }
    });

    return new Board(tiles);
  }
}
