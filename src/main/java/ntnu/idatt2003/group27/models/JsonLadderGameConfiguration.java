package ntnu.idatt2003.group27.models;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;
import ntnu.idatt2003.group27.models.enums.LadderGameType;
import ntnu.idatt2003.group27.models.interfaces.GameConfiguration;
import ntnu.idatt2003.group27.models.interfaces.TileAction;
import ntnu.idatt2003.group27.utils.filehandler.json.JsonFileReader;

/**
 * A configuration class for ladder games loaded from a JSON file. Implements the
 * {@link GameConfiguration} interface to provide game settings, including the number of dice, die
 * sides, total tiles, and tile actions, based on the JSON configuration.
 *
 * @author Iver Lindholm, Amadeus Berg
 * @version 1.0
 * @since 2.0
 */
public class JsonLadderGameConfiguration extends LadderGameConfiguration {
  /**
   * Logger instance for the {@link JsonLadderGameConfiguration} class.
   * Used for logging informational messages and errors related to class operations.
   */
  private static final Logger logger = Logger.getLogger(JsonLadderGameConfiguration.class.getName());
  /** The file path to the JSON configuration file. */
  private final String jsonPath;
  /** The game board loaded from the JSON file. */
  private Board board;

  /**
   * Constructs a {@link JsonLadderGameConfiguration} by reading the game board configuration from
   * the specified JSON file.
   *
   * @param jsonPath The file path to the JSON configuration file.
   * @throws IOException if the JSON file cannot be read or is invalid.
   */

  public JsonLadderGameConfiguration(String jsonPath) throws IOException {
    super(LadderGameType.JSON);
    logger.fine("Initializing JsonLadderGameConfiguration for path " + jsonPath);
    this.jsonPath = jsonPath;

    try {
      this.board = new JsonFileReader().readFile(jsonPath);
    } catch (IOException e) {
      logger.severe("Error reading JsonLadderGameConfiguration for path " + jsonPath);
      throw new IOException(e.getMessage());
    }
  }

  /**
   * Returns the game board loaded from the JSON file.
   *
   * @return The {@link Board} instance configured from the JSON file.
   * @throws IllegalArgumentException if the board has not been initialized.
   */
  public Board getBoard() {
    logger.fine("Getting board.");
    if (board == null) {
      logger.severe("Board is null!");
      throw new IllegalArgumentException("Board has not been initialized.");
    }

    return board;
  }
}
