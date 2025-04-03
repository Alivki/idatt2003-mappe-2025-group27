package ntnu.idatt2003.group27.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import ntnu.idatt2003.group27.models.actions.LadderAction;
import ntnu.idatt2003.group27.models.interfaces.TileAction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Test class for the Tile class.
 *
 * <p>Testing the constructor,<br>
 * testing setting and getting next tile,<br>
 * testing setting and getting previous tile,<br>
 * testing setting and getting land action,<br>
 * testing landing the player on the tile,<br>
 * testing setting next tile to null,<br>
 * testing setting previous tile to null,<br>
 * testing setting land action to null</p>
 */
public class TileTest {
  private Tile tile;
  private Player player;
  private TileAction action;

  /**
   * Set up the test environment by creating a new tile, player and action.
   */
  @BeforeEach
  public void setUp() {
    tile = new Tile(1);
    player = new Player("Player 1");
    action = new LadderAction(2, "Ladder");
  }

  @Test
  @DisplayName("Test constructor with valid tileId")
  public void testConstructorValidTileId() {
    assertNotNull(tile);
    assertEquals(1, tile.getTileId());
  }

  @Test
  @DisplayName("Test setting and getting next tile")
  public void testSetAndGetNextTile() {
    Tile nextTile = new Tile(2);
    tile.setNextTile(nextTile);
    assertEquals(nextTile, tile.getNextTile(), "Next tile should be the one set");
  }

  @Test
  @DisplayName("Test setting and getting previous tile")
  public void testSetAndGetPreviousTile() {
    Tile prevTile = new Tile(0);
    tile.setPreviousTile(prevTile);
    assertEquals(prevTile, tile.getPreviousTile(), "Previous tile should be the one set");
  }

  @Test
  @DisplayName("Test setting and getting land action")
  public void testSetAndGetLandAction() {
    tile.setLandAction(action);
    assertEquals(action, tile.getLandAction(), "Land action should be the one set");
  }

  @Test
  @DisplayName("Test that land action is null by default")
  public void testLandActionIsNullByDefault() {
    assertNull(tile.getLandAction(), "Land action should be null when not set");
  }

  @Test
  @DisplayName("Test that next tile is null by default")
  public void testNextTileIsNullByDefault() {
    assertNull(tile.getNextTile(), "Next tile should be null when not set");
  }

  @Test
  @DisplayName("Test that previous tile is null by default")
  public void testPreviousTileIsNullByDefault() {
    assertNull(tile.getPreviousTile(), "Previous tile should be null when not set");
  }

  @Test
  @DisplayName("Test landing the player without any action on tile")
  public void testLandPlayerWithoutAction() {
    tile.landPlayer(player);
    assertEquals(tile, player.getCurrentTile(), "Player should be on correct tile");
  }

  @Test
  @DisplayName("Test landing the player with action on tile")
  public void testLandPlayerWithAction() {
    Tile nextTile = new Tile(2);
    player.placeOnTile(tile);
    tile.setNextTile(nextTile);
    tile.setLandAction(action);
    tile.landPlayer(player);
    assertEquals(nextTile, player.getCurrentTile(), "Player should be on correct tile");
  }

  @Test
  @DisplayName("Test setting next tile to null")
  public void testSetNextTileToNull() {
    tile.setNextTile(null);
    assertNull(tile.getNextTile(), "Next tile should be null when set to null");
  }

  @Test
  @DisplayName("Test setting previous tile to null")
  public void testSetPreviousTileToNull() {
    tile.setPreviousTile(null);
    assertNull(tile.getPreviousTile(), "Previous tile should be null when set to null");
  }

  @Test
  @DisplayName("Test setting land action to null")
  public void testSetLandActionToNull() {
    tile.setLandAction(null);
    assertNull(tile.getLandAction(), "Land action should be null when set to null");
  }
}
