package ntnu.idatt2003.group27.models;

import java.io.IOException;
import ntnu.idatt2003.group27.filehandler.JsonFileReader;

/**
 * A factory class responsible for creating {@link BoardGame} instances with predefined number of
 * tiles or with file-based configuration.
 *
 * @author Iver Lindholm
 * @since 1.0
 * @version 1.0
 */
public class BoardGameFactory {
  /**
   * Creates a default {@link BoardGame} instance with a standards configuration.
   * The default game includes a board with 90 tiles and a single six sieded die.
   *
   * @return A new {@link BoardGame} instance with default configuration.
   */
  public static BoardGame createDefaultGame() {
    Board board = new Board(90);

    return new BoardGame(board, 1, 6);
  }

  /**
   * Creates a {@link BoardGame} instance based on a board configuration loaded from a JSON file.
   * The game uses a single six sided die. If file cannot be read, and error message is logged.
   *
   * @param path The file path to the JSON configuration file.
   * @return A new {@link BoardGame} instance configured based on the JSON file.
   */
  public static BoardGame createGameFromJson(String path) {
    Board board = null;
    try {
      board = new JsonFileReader().readFile(path);
    } catch (IOException e) {
      System.err.println(e.getMessage());
    }

    return new BoardGame(board, 1, 6);
  }
}
