package ntnu.idatt2003.group27.models;

/**
 * A class representing a player.
 */
public class Player {
  private final String name;
  private Tile currentTile;
  private final BoardGame game;

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
    if (this.getCurrentTile().getTileId() + steps > game.getBoard().getTiles().size() - 1) {
      int move = -1 * (steps - ((game.getBoard().getTiles().size() - 1) - this.getCurrentTile().getTileId()));
      game.getBoard().getTile(currentTile.getTileId() + move).landPlayer(this);
    }

    game.getBoard().getTile(currentTile.getTileId() + steps).landPlayer(this);
  }
}
