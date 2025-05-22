package ntnu.idatt2003.group27.models;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import ntnu.idatt2003.group27.models.actions.LadderAction;
import ntnu.idatt2003.group27.view.components.LadderCanvas;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Unit test for the {@link LadderAction} class.
 *
 * <p>Verifies initialization, action execution, animation path, and drawing logic</p>
 */
public class LadderActionTest {
  private LadderAction action;
  private Player player;
  private List<Tile> tiles;

  /**
   * Sets up the test environment with a LadderAction and sample player and tiles.
   */
  @BeforeEach
  void setUp() {
    action = new LadderAction(4, "Move to tile 10");
    player = new Player("Player 1");
    tiles = new ArrayList<>();
    for (int i = 1; i <= 20; i++) {
      Tile tile = new Tile(i);
      tiles.add(tile);
      tiles.get(i - 1).setNextTile(tile);
    }
    tiles.get(9).setLandAction(action);
    player.placeOnTile(tiles.get(3));
  }

  /**
   * Verifies that the action is correctly initialized with destination tile id and description.
   */
  @Test
  @DisplayName("test initializing of LadderAction")
  void testInitializeLadderAction() {
    assertEquals(4, action.getDestinationTileId(), "The destination tile id should be 10");
    assertEquals("Move to tile 10", action.getDescription(),
        "The description should be 'Move to tile 10'");
  }

  /**
   * Verifies that the action moves the player to the correct tile.
   */
  @Test
  @DisplayName("test perform moves player to desitination")
  public void testPerform() {
    action.perform(player);
    assertEquals(4, player.getCurrentTile().getTileId(),
        "Player should be on tile 10 after action");
  }

  /**
   * Verifies that getAnimationPath return correct path from start to destination tile.
   */
  @Test
  @DisplayName("")
  void testGetAnimationPath() {
    int startTileId = 5;
    int actionTileId = 10;

    List<Integer> path = action.getAnimationPath(startTileId, actionTileId);

    assertEquals(List.of(5, 6, 7, 8, 9, 4), path, "Path should include tiles from 5 to 10");
  }

  /**
   * Verifies that getTileColor return DARKRED for downward ladder.
   */
  @Test
  @DisplayName("test tile color returns DARKRED for downward ladder")
  void testGetTileColorDownward() {
    action = new LadderAction(2, "Move to tile 2");
    int tileId = 5;

    Color color = action.getTileColor(tileId);

    assertEquals(Color.DARKRED, color, "Tile color should be DARKRED for downward ladder");
  }

  /**
   * Verifies that getTileColor return GREEN for upward ladder.
   */
  @Test
  @DisplayName("test tile color returns GREEN for upward ladder")
  void testGetTileColorUpward() {
    int tileId = 2;

    Color color = action.getTileColor(tileId);

    assertEquals(Color.GREEN, color, "Tile color should be GREEN for upward ladder");
  }

  /**
   * Verifies that getIconPath returns null for LadderAction.
   */
  @Test
  @DisplayName("test icon path returns null")
  void testGetIconPath() {
    String iconPath = action.getIconPath();

    assertEquals(null, iconPath, "Icon path should be null");
  }

  /**
   * Verifies that perform with null player throws an exception.
   */
  @Test
  @DisplayName("should throw exception with null player")
  void testPerformWithNullPlayer() {
    assertThrows(NullPointerException.class, () -> action.perform(null));
  }
}
