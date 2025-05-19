package ntnu.idatt2003.group27.models;

import java.util.logging.Logger;

/**
 * A class representing a player piece on the board
 *
 * @author Amadeus Berg
 */

public class Piece {
  /**
   * Logger instance for the {@code Board} class.
   * Used for logging informational messages and errors related to class operations.
   */
  private static final Logger logger = Logger.getLogger(Piece.class.getName());
  /* The name of the piece */
  private final String name;
  /* The filepath to the piece's icon */
  private final String iconFilePath;

  /**
   * Constructs a piece with the specified name and icon filepath.
   * @param name
   * @param iconFilePath
   */
  public Piece(String name, String iconFilePath) {
    this.name = name;
    this.iconFilePath = iconFilePath;
  }

  public String getName() {
    return name;
  }

  public String getIconFilePath() {
    return iconFilePath;
  }
}
