package ntnu.idatt2003.group27.models;

/**
 * A class that represents a tile on the game board.
 */
public class Tile {
  public Tile nextTile;
  private final int tileId;
  public TileAction landAction;

  public Tile(int tileId) {
    this.tileId = tileId;
  }

  public int getTileId() {
    return tileId;
  }

  public Tile getNextTileId() {
    return nextTile;
  }

  public TileAction getLandAction() {
    return landAction;
  }

  public void landPlayer(Player player) {
    if (landAction != null) {
      landAction.Perform(player);
    } else {
      player.placeOnTile(this);
    }
  }

  public void leavePlayer(Player player) {

  }

  public void setLandAction(TileAction landAction) {
    this.landAction = landAction;
  }

  public void setNextTile(Tile nextTile) {
    this.nextTile = nextTile;
  }
}
