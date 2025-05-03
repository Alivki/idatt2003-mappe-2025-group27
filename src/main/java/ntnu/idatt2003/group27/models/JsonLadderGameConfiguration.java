package ntnu.idatt2003.group27.models;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import ntnu.idatt2003.group27.models.interfaces.GameConfiguration;
import ntnu.idatt2003.group27.models.interfaces.TileAction;
import ntnu.idatt2003.group27.utils.filehandler.json.JsonFileReader;

/**
 * A configuration class for ladder games loaded from a JSON file. Implements the
 * {@link GameConfiguration} interface to provide game settings, including the number of dice, die
 * sides, total tiles, and tile actions, based on the JSON configuration.
 *
 * @author Iver Lindholm
 * @version 1.0
 * @since 2.0
 */
public class JsonLadderGameConfiguration implements GameConfiguration {
  /** The file path to the JSON configuration file. */
  private final String jsonPath;
  /** The game board loaded from the JSON file. */
  private Board board;

  /**
   * Constructs a {@code JsonLadderGameConfiguration} by reading the game board configuration from
   * the specified JSON file.
   *
   * @param jsonPath The file path to the JSON configuration file.
   * @throws IOException if the JSON file cannot be read or is invalid.
   */
  public JsonLadderGameConfiguration(String jsonPath) throws IOException {
    this.jsonPath = jsonPath;

    try {
      this.board = new JsonFileReader().readFile(jsonPath);
    } catch (IOException e) {
      throw new IOException(e.getMessage());
    }
  }

  @Override
  public int getNumberOfDice() {
    return 1;
  }

  @Override
  public int getNumberOfDieSides() {
    return 6;
  }

  @Override
  public int getTotalTiles() {
    return board.getTiles().size();
  }

  @Override
  public Map<Integer, TileAction> getTileActions() {
    Map<Integer, TileAction> tileActions = new HashMap<>();
    board.getTiles().forEach((id, tile) -> {
      if (tile.getLandAction() != null) {
        tileActions.put(id, tile.getLandAction());
      }
    });
    return tileActions;
  }

  /**
   * Returns the game board loaded from the JSON file.
   *
   * @return The {@link Board} instance configured from the JSON file.
   * @throws IllegalArgumentException if the board has not been initialized.
   */
  public Board getBoard() throws IllegalArgumentException {
    if (board == null) {
      throw new IllegalArgumentException("Board has not been initialized.");
    }

    return board;
  }
}
