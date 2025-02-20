package ntnu.idatt2003.group27.models;

/**
 * A class that represents a tile on the game board.
 */
public class Tile {
  public Tile nextTile;
  public int tileId;
  public TileAction landAction;

  public Tile(int tileId) {
    this.tileId = tileId;
  }

  public int getTileId() {
    return tileId;
  }

  public void landPlayer(Player player) {
    player.placeOnTile(this);
  }

  public void leavePlayer(Player player) {

  }

  public void setNextTile(Tile nextTile) {

  }
}
