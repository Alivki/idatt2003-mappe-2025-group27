package ntnu.idatt2003.group27.models;

import ntnu.idatt2003.group27.models.interfaces.LadderTileAction;
import java.util.logging.Logger;
import ntnu.idatt2003.group27.models.interfaces.TileAction;

/**
 * Represents a tile on the game board, forming part of a linked structure with next and previous
 * tiles. Each tile has a unique identifier and an optional action that triggers when a player
 * lands on it.
 *
 * @author Iver Lindholm
 * @version 1.1
 * @since 1.0
 */
public class Tile {
  /**
   * Logger instance for the {@link Tile} class.
   * Used for logging informational messages and errors related to class operations.
   */
  private static final Logger logger = Logger.getLogger(Tile.class.getName());

  /** The next tile in the board sequence. */
  public Tile nextTile;

  /** The previous tile in the board sequence. */
  public Tile previousTile;

  /** The unique identifier for this tile. */
  private final int tileId;

  /** The action to perform when a player lands on this tile, if any. */
  public TileAction landAction;

  /**
   * Constructs a tile with the specified identifier.
   *
   * @param tileId The unique ID assigned to this tile.
   */
  public Tile(int tileId) {
    logger.fine("Initializing tile with id: " + tileId);
    this.tileId = tileId;
  }

  /**
   * Retrieves the unique identifier of this tile.
   *
   * @return The tile's ID.
   */
  public int getTileId() {
    logger.fine("Getting tile id: " + tileId);
    return tileId;
  }

  /**
   * Retrieves the next tile in the sequence.
   *
   * @return The next {@link Tile}, or null if none exists.
   */
  public Tile getNextTile() {
    logger.fine("Getting next tile: " + nextTile);
    return nextTile;
  }

  /**
   * Retrieves the previous tile in the sequence.
   *
   * @return The previous {@link Tile}, or null if none exists.
   */
  public Tile getPreviousTile() {
    logger.fine("Getting previous tile: " + previousTile);
    return previousTile;
  }

  /**
   * Retrieves the action associated with landing on this tile.
   *
   * @return The {@link LadderTileAction} for this tile, or null if no action is set.
   */
  public TileAction getLandAction() {
    logger.fine("Getting land action: " + landAction);
    return landAction;
  }

  /**
   * Sets the action to be performed when a player lands on this tile.
   *
   * @param landAction The {@link LadderTileAction} to associate with this tile.
   */
  public void setLandAction(TileAction landAction) {
    logger.fine("Setting land action: " + landAction);
    this.landAction = landAction;
  }

  /**
   * Sets the next tile in the sequence.
   *
   * @param nextTile The {@link Tile} to set as the next tile.
   */
  public void setNextTile(Tile nextTile) {
    logger.fine("Setting next tile: " + nextTile);
    this.nextTile = nextTile;
  }

  /**
   * Sets the previous tile in the sequence.
   *
   * @param previousTile The {@link Tile} to set as the previous tile.
   */
  public void setPreviousTile(Tile previousTile) {
    logger.fine("Setting previous tile: " + previousTile);
    this.previousTile = previousTile;
  }

  /**
   * Handles a player landing on this tile. If a {@link LadderTileAction} is defined, it is executed;
   * otherwise, the player is placed on this tile.
   *
   * @param player The {@link Player} landing on this tile.
   */
  public void landPlayer(Player player) {
    logger.fine("Landing player: " + player + ", on this tile.");
    if (landAction != null) {
      landAction.perform(player);
    } else {
      player.placeOnTile(this);
    }
  }

  /**
   * Handles a player leaving this tile.
   *
   * @param player The {@link Player} leaving this tile.
   */
  public void leavePlayer(Player player) {
  }
}