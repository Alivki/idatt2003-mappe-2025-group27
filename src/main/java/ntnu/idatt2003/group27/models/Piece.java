package ntnu.idatt2003.group27.models;
/**
 * A class representing a player piece on the board
 *
 * @author Amadeus Berg
 */

public class Piece {
  /* The name of the piece */
  private final String name;
  /* The filepath to the piece's icon */
  private final String iconFilePath;

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
