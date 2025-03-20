package ntnu.idatt2003.group27.models;

/**
 * Represents the behavior of a "throw an extra dice" tile on the game board. This class implements the
 * {@link TileAction} interface to define an action with custom behaviour.
 *
 * @author Amadeus Berg
 * @version 1.0
 * @since 1.0
 */
public class ThrowNewDiceAction implements TileAction {
  public String description;

  /**
   * Constructs a ladder action with the specified destination tile and description.
   *
   * @param description A textual description of the ladder action.
   */
  public ThrowNewDiceAction(String description) {
    this.description = description;
  }

  /**
   * Executes the ladder action by moving the specified player to the destination tile.
   *
   * @param player The {@link Player} affected by this ladder action.
   */
  @Override
  public void perform(Player player) {

  }
}
