package ntnu.idatt2003.group27.models;

/**
 * A class representing a player.
 */
public class Player {
  public String name;
  public Tile currentTile;
  private BoardGame game;

  /**
   * Constructor for the player class.
   *
   * @param name The name of the player.
   * @param game The game the player is playing.
   */
  public Player(String name, BoardGame game) {
    this.name = name;
    this.game = game;
  }

  /**
   * getName method to get the name of the player.
   *
   * @return String The name of the player.
   */
  public String getName() {
    return name;
  }

  /**
   * getCurrentTile method to get the current tile of the player.
   *
   * @return Tile The current tile of the player.
   */
  public Tile getCurrentTile() {
    return currentTile;
  }

  /**
   * placeOnTile method to place the player on a tile.
   *
   * @param tile The tile to place the player on.
   */
  public void placeOnTile(Tile tile) {
    if (currentTile == tile) {
      throw new IllegalArgumentException("Player already on this tile");
    }

    currentTile = tile;
  }

  /**
   * Method to move the player a specified number of steps.
   *
   * @param steps the number of steps to move the player.
   */
  public void move(int steps) {
    game.getBoard().getTile(currentTile.getTileId() + steps).landPlayer(this);
  }
}
