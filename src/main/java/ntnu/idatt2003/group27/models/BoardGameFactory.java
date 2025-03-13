package ntnu.idatt2003.group27.models;

import java.io.IOException;
import ntnu.idatt2003.group27.filehandler.JsonFileReader;

/**
 * A factory class for creating BoardGame objects.
 */
public class BoardGameFactory {
  /**
   * Creates a default BoardGame object.
   *
   * @return A default BoardGame object.
   */
  public static BoardGame createDefaultGame() {
    Board board = new Board(90);

    return new BoardGame(board, 1, 6);
  }

  /**
   * Creates a BoardGame object from a JSON file.
   *
   * @param path The path to the JSON file.
   * @return A BoardGame object created from the JSON file.
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
