package ntnu.idatt2003.group27.models;

import java.util.logging.Logger;

/**
 * A class representing a player piece on the board
 *
 * @author Amadeus Berg
 */

public class Piece {
  /**
   * Logger instance for the {@link Piece} class.
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
    logger.fine("Initializing piece: " + name + " with icon filepath: " + iconFilePath);
    this.name = name;
    this.iconFilePath = iconFilePath;
  }

  /**
   * Gets the name of the {@link Piece}.
   * @return The name of the {@link Piece}.
   */
  public String getName() {
    logger.fine("Getting piece name.");
    return name;
  }

  /**
   * Gets the file path for the icon of this {@link Piece}.
   * @return The file path to the icon of this {@link Piece}.
   */
  public String getIconFilePath() {
    logger.fine("Getting icon filepath.");
    return iconFilePath;
  }
}
