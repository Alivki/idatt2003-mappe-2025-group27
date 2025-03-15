package ntnu.idatt2003.group27.models;

/**
 * An interface for custom tile actions.
 */
public interface TileAction {

  /**
   * Method to perform the action of the tile.
   *
   * @param player The player to perform the action on.
   */
  void perform(Player player);
}
