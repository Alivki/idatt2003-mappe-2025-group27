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
public class BackToStartAction implements TileAction {
  public String description;

  /**
   * Constructs a ladder action with the specified destination tile and description.
   *
   * @param description A textual description of the ladder action.
   */
  public BackToStartAction(String description) {
    this.description = description;
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
    int currentTile = player.getCurrentTile().getTileId();

    int steps = currentTile - 1;

    player.move(-steps);
  }
}
