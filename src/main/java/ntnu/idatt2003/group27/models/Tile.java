package ntnu.idatt2003.group27.models;

/**
 * A class that represents a tile on the game board.
 */
public class Tile {
  public int nextTile;
  private final int tileId;
  public TileAction landAction;

  public Tile(int tileId) {
    this.tileId = tileId;
  }

  public int getTileId() {
    return tileId;
  }

  public int getNextTileId() {
    return nextTile;
  }

  public TileAction getLandAction() {
    return landAction;
  }

  public void landPlayer(Player player) {
    player.placeOnTile(this);
  }

  public void setLandAction(TileAction landAction) {
    this.landAction = landAction;
  }

  public void leavePlayer(Player player) {

  }

  public void setNextTile(int nextTile) {
    this.nextTile = nextTile;
  }
}
