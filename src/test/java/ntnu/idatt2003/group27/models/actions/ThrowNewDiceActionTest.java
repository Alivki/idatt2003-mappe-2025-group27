package ntnu.idatt2003.group27.models.actions;

import javafx.scene.paint.Color;
import ntnu.idatt2003.group27.models.Player;
import ntnu.idatt2003.group27.models.Tile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit test for the {@link ThrowNewDiceAction} class.
 *
 * <p>Verifies initialization, action execution, and animation path</p>
 * @author Amadeus Berg & Iver Lindholm
 */
class ThrowNewDiceActionTest {
  private ThrowNewDiceAction action;
  private Player player;
  private List<Tile> tiles;

  /**
   * Sets up the test environment with a ThrowNewDice and sample player and tiles.
   */
  @BeforeEach
  void setUp() {
    action = new ThrowNewDiceAction("Roll again", 1,3);
    player = new Player("Player 1");
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
   * Verifies that the action is correctly initialized with description, number of dice and number of die sides.
   */
  @Test
  @DisplayName("test initializing of ThrowNewDiceAction")
  void testInitializeThrowNewDiceAction() {
    assertEquals(1, action.numberOfDice, "The number of dice should be 1");
    assertEquals(3, action.numberOfDieSides, "The number of die sides should be 3");
    assertEquals("Roll again", action.description, "The description should be 'Roll again'");
  }

  /**
   * Verifies that perform moves the player based on dice roll.
   */
  @Test
  @DisplayName("")
  void testPerformAction() {
    action.perform(player);

    int newPosition = player.getCurrentTile().getTileId();
    assertTrue(newPosition >= 2 && newPosition <= 6,
        "Player should be on tile between 2 and 6 after action");
  }

  /**
   *  Verifies that getAnimationPath return correct path including roll.
   */
  @Test
  @DisplayName("test animation path includes rolled steps")
  void testGetAnimationPath() {
    player.placeOnTile(tiles.get(3));
    System.out.println("tset");
    action.perform(player);
    int roll = action.getNumberOfDice() * action.getNumberOfSides();

    List<Integer> path = action.getAnimationPath(2, 4);

    assertFalse(path.isEmpty(), "Path should not be empty");
    assertEquals(2, path.getFirst(), "path should start at tile 3");
    assertTrue(path.size() <= roll + 2, "path length should account for roll");
  }

  /**
   * Verifies that getTileColor return BLUE.
   */
  @Test
  @DisplayName("test tile color return BLUE")
  void testGetTileColor() {
    int tileId = 4;

    Color color = action.getTileColor(tileId);

    assertEquals(Color.BLUE, color, "Tile color should be BLUE for ThrowNewDiceAction");
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