package ntnu.idatt2003.group27.models.interfaces;

import ntnu.idatt2003.group27.models.Player;

/**
 * Defines a contract for custom actions that can be executed when a player interacts with a 
 * tile on the game board. Implementations of this interface specify behavior triggered by 
 * a player's presence on a tile.
 *
 * @author Iver Lindholm
 * @version 1.0
 * @since 1.0
 */
public interface TileAction {

  /**
   * Executes the custom action associated with a tile, affecting the specified player.
   *
   * @param player The {@link Player} on whom the action is performed.
   */
  void perform(Player player);
}
