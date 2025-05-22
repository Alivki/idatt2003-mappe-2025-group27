package ntnu.idatt2003.group27.models.actions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import javafx.scene.paint.Color;
import ntnu.idatt2003.group27.models.Player;
import ntnu.idatt2003.group27.models.Tile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Test class for the BackToStart action class.
 *
 * <p>Verifies initialization, action execution, and animation path generation.</p>
 */
public class BackToStartActionTest {
  private BackToStartAction action;
  private Player player;
  private List<Tile> tiles;

  /**
   * Sets up the test environment with a BackToStartAction and sample player and tiles.
   */
  @BeforeEach
  void setUp() {
    action = new BackToStartAction("Go back to start");
    player = new Player("player 1");
    tiles = new ArrayList<>();
    for (int i = 0; i <= 20; i++) {
      Tile tile = new Tile(i);
      tiles.add(tile);
      if (i > 1) {
        tiles.get(i - 1).setNextTile(tile);
        tile.setPreviousTile(tiles.get(i - 1));
      }
    }
    player.placeOnTile(tiles.get(2));
  }

  /**
   * Verifies that the action is correctly initialized with description.
   */
  @Test
  @DisplayName("test initializing of BackToStartAction")
  void testInitializeBackToStartAction() {
    assertEquals("Go back to start", action.getDescription(), "The description should be 'Roll again'");
  }

  /**
   * Verifies that perform moves the player to the start tile.
   */
  @Test
  @DisplayName("test perform moves player to start")
  void testPerformAction() {
    action.perform(player);

    assertEquals(1, player.getCurrentTile().getTileId(), "Player should be on tile 1");
  }

  /**
   * Verifies that getAnimationPath generates the correct path for animation.
   */
  @Test
  @DisplayName("test animation path to start tile")
  void testGetAnimationPath() {
    int startTileId = 2;
    int actionTileId = 5;
    List<Integer> path = action.getAnimationPath(startTileId, actionTileId);

    assertEquals(List.of(2,3,4,1), path, "The animation path should be correct");
  }

  /**
   * Verifies that getTileColor return LIGHTPINK
   */
  @Test
  @DisplayName("test tile color return LIGHTPINK")
  void testGetTileColor() {
    int tileId = 1;
    Color color = action.getTileColor(tileId);

    assertEquals(Color.LIGHTPINK, color, "The tile color should be LIGHTPINK");
  }

  /**
   * Verifies that perform with null player throws an exception.
   */
  @Test
  @DisplayName("should throw exception with null player")
  void testPerformWithNullPlayer() {
    assertThrows(NullPointerException.class, () -> action.perform(null));
  }

  /**
   * Verifies that perform with player at tile 1 does not move the player.
   */
  @Test
  @DisplayName("test perform with player already at start")
  void testPerformAtStartTile() {
    player.placeOnTile(tiles.getFirst());

    action.perform(player);

    assertEquals(0, player.getCurrentTile().getTileId(), "Player should remain on first tile");
  }
}
