package ntnu.idatt2003.group27.models;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

import ntnu.idatt2003.group27.models.enums.LadderGameType;
import ntnu.idatt2003.group27.utils.filehandler.json.JsonFileReader;
import ntnu.idatt2003.group27.models.actions.BackToStartAction;
import ntnu.idatt2003.group27.models.actions.LadderAction;
import ntnu.idatt2003.group27.models.interfaces.TileAction;
import ntnu.idatt2003.group27.models.exceptions.UnknownLadderGameTypeExceptions;

/**
 * A factory class responsible for creating {@link BoardGame} instances with predefined number of
 * tiles or with file-based configuration.
 *
 * @author Iver Lindholm
 * @version 1.0
 * @since 1.0
 */
public class BoardGameFactory {
  /**
   * Creates a {@link BoardGame} instance with defined configuration.
   * The game includes a board with tiles and set number of dice.
   *
   * @return A new {@link BoardGame} instance with a configuration.
   */
  public static BoardGame createLadderGame(LadderGameType ladderGameType) throws UnknownLadderGameTypeExceptions {
    int numberOfDice = 1;
    int numberOfDieSides = 6;

    int totalTiles = 90;
    Map<Integer, TileAction> tileActions = new HashMap<>();

    switch (ladderGameType) {
      case NORMAL:
        tileActions.put(4, new LadderAction(15, "description"));
        tileActions.put(10, new BackToStartAction("description"));
        tileActions.put(69, new LadderAction(47, "description"));
        tileActions.put(18, new LadderAction(43, "description"));
        tileActions.put(65, new LadderAction(88, "description"));
        tileActions.put(63, new LadderAction(45, "description"));
        tileActions.put(32, new LadderAction(12, "description"));
        break;
      case CRAZY:
        tileActions.put(4, new LadderAction(15, "description"));
        tileActions.put(10, new BackToStartAction("description"));
        break;
      case IMPOSSIBLE:
        tileActions.put(4, new LadderAction(15, "description"));
        tileActions.put(10, new BackToStartAction("description"));
        break;
      default:
        throw new UnknownLadderGameTypeExceptions("Unknown ladder game type: " + ladderGameType);
    }

    Board board = createBoard(totalTiles, tileActions);
    return new BoardGame(board, numberOfDice, numberOfDieSides);
  }

  /**
   * Creates a {@link BoardGame} instance based on a board configuration loaded from a JSON file.
   * The game uses a single six sided die. If file cannot be read, and error message is logged.
   *
   * @param path The file path to the JSON configuration file.
   * @return A new {@link BoardGame} instance configured based on the JSON file.
   */
  public static BoardGame createLadderGameFromJson(String path) throws IOException {
    Board board = null;
    try {
      board = new JsonFileReader().readFile(path);
    } catch (IOException e) {
      throw new IOException(e.getMessage());
    }

    return new BoardGame(board, 1, 6);
  }

  /**
   * Creates a {@link Board} object with a specified number of tiles and tile actions.
   *
   * @param totalTiles  The total number of tiles on the board.
   * @param tileActions A map of tile actions, where keys are tile IDs and values are {@link TileAction} objects.
   * @return A new {@link Board} object with the specified tiles and actions.
   */
  private static Board createBoard(int totalTiles, Map<Integer, TileAction> tileActions) {
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