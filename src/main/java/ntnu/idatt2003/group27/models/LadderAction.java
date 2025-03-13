package ntnu.idatt2003.group27.models;

/**
 * A class that represents a ladder's behaviour on the game board.
 */
public class LadderAction implements TileAction {
  public int destinationTileId;
  public String description;

  public LadderAction(int destinationTileId, String description) {
    this.destinationTileId = destinationTileId;
    this.description = description;
  }

  @Override
  public void Perform(Player player) {

  }
}
