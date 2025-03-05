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
    currentTile = tile;
  }

  /**
   * Method to move the player a specified number of steps.
   *
   * @param steps the number of steps to move the player.
   */
  public void move(int steps) {
    int newPosition = currentTile.getTileId() + steps;
    int lastTileIndex = game.getBoard().getTiles().size() - 1;

    if (newPosition > lastTileIndex) {
      int overshoot = newPosition - lastTileIndex;
      newPosition = lastTileIndex - overshoot;
      game.getBoard().getTile(newPosition).landPlayer(this);
      return;
    }

    game.getBoard().getTile(newPosition).landPlayer(this);
  }
}
