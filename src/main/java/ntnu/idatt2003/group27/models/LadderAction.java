package ntnu.idatt2003.group27.models;

/**
 * A class that represents a ladder's behaviour on the game board.
 */
public class LadderAction implements TileAction {
  public int destinationTileId;
  public String description;

  /**
   * Constructor for the LadderAction class.
   *
   * @param destinationTileId The tile id of the destination tile.
   * @param description       The description of the ladder.
   */
  public LadderAction(int destinationTileId, String description) {
    this.destinationTileId = destinationTileId;
    this.description = description;
  }

  /**
   * Method to perform the action of the tile.
   *
   * @param player The player to perform the action on.
   */
  @Override
  public void perform(Player player) {
    player.move(destinationTileId - player.getCurrentTile().getTileId());
  }
}
