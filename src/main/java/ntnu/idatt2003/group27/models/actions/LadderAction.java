package ntnu.idatt2003.group27.models.actions;

import ntnu.idatt2003.group27.models.Player;
import ntnu.idatt2003.group27.models.interfaces.TileAction;

/**
 * Represents the behavior of a ladder on the game board. This class implements the 
 * {@link TileAction} interface to define an action that moves a player to a specified
 * destination tile when triggered.
 *
 * @author Iver Lindholm
 * @version 1.0
 * @since 1.0
 */
public class LadderAction implements TileAction {
  public int destinationTileId;
  public String description;

  /**
   * Constructs a ladder action with the specified destination tile and description.
   *
   * @param destinationTileId The ID of the tile where the player will be moved.
   * @param description       A textual description of the ladder action.
   */
  public LadderAction(int destinationTileId, String description) {
    this.destinationTileId = destinationTileId;
    this.description = description;
  }

  /**
   * Retrieves the tile id of the destination tile for this ladder action.
   *
   * @return the ID of the destination tile.
   */
  public int getDestinationTileId() {
    return destinationTileId;
  }

  /**
   * Retrieves the description of this ladder action.
   *
   * @return the description of the ladder action.
   */
  public String getDescription() {
    return description;
  }

  /**
   * Executes the ladder action by moving the specified player to the destination tile.
   *
   * @param player The {@link Player} affected by this ladder action.
   */
  @Override
  public void perform(Player player) {
    player.move(destinationTileId - player.getCurrentTile().getTileId());
  }
}
