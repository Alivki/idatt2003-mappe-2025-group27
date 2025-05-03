package ntnu.idatt2003.group27.models;

/**
 * Represents a player in a board game, tracking their name and current position on the board. This
 * class provides methods to manage the player's location and movement across tiles.
 *
 * @author Iver Lindholm og Amadeus Berg
 * @version 1.1
 * @since 0.0
 */
public class Player {
  /** The name of the player. */
  private final String name;

  /** The piece the player will have on the board.  */
  private Piece piece;

  /** The tile currently occupied by the player. */
  private Tile currentTile;

  /**
   * Constructs a player with the specified name and associates them with the game.
   *
   * @param name The name of the player.
   */
  public Player(String name) {
    this.name = name;
    this.piece = null;
  }

  public Player(String name, Piece piece) {
    this.name = name;
    this.piece = piece;
  }

  /**
   * Retrieves the name of the player.
   *
   * @return The player's name.
   */
  public String getName() {
    return name;
  }

  /**
   * getPiece method to get the current player piece.
   * @return The current {@Link Piece} used by this player.
   */
  public Piece getPiece() {
    return piece;
  }

  /**
   * getCurrentTile method to get the current tile of the player.
   * Retrieves the tile currently occupied by the player.
   *
   * @return The current {@link Tile} occupied by the player, or null if not places on a tile yet.
   */
  public Tile getCurrentTile() {
    return currentTile;
  }

  /**
   * Places the player on the specified tile, updating their current position.
   *
   * @param tile The {@link Tile} where the player is to be placed.
   */
  public void placeOnTile(Tile tile) {
    currentTile = tile;
  }

  /**
   * Moves the player a specified number of steps across the board, either forward or backward.
   * The player's position is updated by traversing the tile sequence, and the landing action of the
   * final tile is triggered.
   *
   * @param steps the number of steps to move; positive values move the player forward, negative
   *              values move the player backward.
   */
  public void move(int steps) {
    int newPosition = currentTile.getTileId() + steps;

    if (steps < 0) {
      while (currentTile.getTileId() > newPosition) {
        currentTile = currentTile.getPreviousTile();
      }
    }

    while (currentTile.getTileId() < newPosition) {
      currentTile = currentTile.getNextTile();
    }

    currentTile.landPlayer(this);
  }
}
