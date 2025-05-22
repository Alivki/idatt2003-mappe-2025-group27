package ntnu.idatt2003.group27.models;

import java.util.logging.Logger;
import javafx.scene.paint.Color;
import ntnu.idatt2003.group27.models.exceptions.InvalidPlayerMoveException;

/**
 * Represents a player in a board game, tracking their name and current position on the board. This
 * class provides methods to manage the player's location and movement across tiles.
 *
 * @author Iver Lindholm
 * @version 1.2
 * @since 0.0
 */
public class Player {
  /**
   * Logger instance for the {@link Player} class.
   * Used for logging informational messages and errors related to class operations.
   */
  private static final Logger logger = Logger.getLogger(Player.class.getName());

  /** The name of the player. */
  private final String name;

  /** The {@link Color} the player will be on the game board. */
  private Color color;

  /** The {@link Tile} currently occupied by the player. */
  private Tile currentTile;

  /** The {@link Piece} of the player. */
  private Piece piece;

  /**
   * Constructs a player with the specified name and associates them with the game.
   *
   * @param name The name of the player.
   */
  // TODO: remove if not needed in the csv reader after new constructor is added.
  public Player(String name) {
    logger.fine("Initializing player with name: " + name + ", piece: null, color: null.");
    this.name = name;
    this.piece = null;
    this.color = null;
  }

  /**
   * Constructs a player with the specified name, color, and piece.
   *
   * @param name The name of the player.
   * @param piece The piece representing the player on the board.
   * @param color The color of the player.
   */
  public Player(String name, Piece piece, Color color) {
    logger.fine("Initializing player with name: " + name + ", piece: " + piece + ", color: " + color);
    this.name = name;
    this.piece = piece;
    this.color = color;
  }

  /**
   * Retrieves the name of the player.
   *
   * @return The player's name.
   */
  public String getName() {
    logger.fine("Getting name.");
    return name;
  }

  /**
   * getPiece method to get the current player piece.
   * @return The current {@link Piece} used by this player.
   */
  public Piece getPiece() {
    logger.fine("Getting piece.");
    return piece;
  }

  /**
   * Retrieves the color of the player.
   *
   * @return {@link Color} The color of the player.
   */
  public Color getColor() {
    logger.fine("Getting color.");
    return color;
  }

  /**
   * Replaces the current piece with a new one.
   *
   * @param piece The new {@link Piece} to be assigned to the player.
   */
  public void setPiece(Piece piece) {
    logger.fine("Setting piece.");
    this.piece = piece;
  }

  /**
   * Replaces the current color with a new one.
   *
   * @param color The new {@link Color} to be assigned to the player.
   */
  public void setColor(Color color) {
    logger.fine("Setting color to color: " + color);
    this.color = color;
  }

  /**
   * getCurrentTile method to get the current tile of the player.
   * Retrieves the tile currently occupied by the player.
   *
   * @return The current {@link Tile} occupied by the player, or null if not places on a tile yet.
   */
  public Tile getCurrentTile() {
    logger.fine("Getting current tile: " + currentTile);
    return currentTile;
  }

  /**
   * Places the player on the specified tile, updating their current position.
   *
   * @param tile The {@link Tile} where the player is to be placed.
   */
  public void placeOnTile(Tile tile) {
    logger.fine("Placing player on tile: " + tile);
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
    // Ensure the current tile is not null before moving
    if (currentTile == null) {
      logger.warning("Invalid move: current tile is null.");
      throw new InvalidPlayerMoveException("Trying to move from a null tile!");
    }

    logger.fine("Moving player " + steps + " steps.");
    int newPosition = currentTile.getTileId() + steps;

    // Handles backward movement
    if (steps < 0) {
      while (currentTile.getTileId() > newPosition) {
        if (currentTile.getPreviousTile() == null) {
          logger.warning("Invalid move: previous tile is null.");
          throw new InvalidPlayerMoveException("Trying to move to a null tile!");
        }
        logger.fine("Moving player from " + currentTile.getTileId() + " to " + currentTile.getPreviousTile().getTileId());
        placeOnTile(currentTile.getPreviousTile());
      }
    }

    // Handles forward movement
    while (currentTile.getTileId() < newPosition) {
      if (currentTile.getNextTile() == null) {
        logger.warning("Invalid move: next tile is null.");
        throw new InvalidPlayerMoveException("Trying to move to a null tile!");
      }
      logger.fine("Moving player from " + currentTile.getTileId() + " to " + currentTile.getNextTile().getTileId());
      placeOnTile(currentTile.getNextTile());
    }

    // Places the player on the new tile
    currentTile.landPlayer(this);
  }

}
