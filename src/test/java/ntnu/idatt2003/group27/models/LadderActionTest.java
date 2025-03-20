package ntnu.idatt2003.group27.models;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Test class for the LadderAction class.
 *
 * <p>Testing the constructor of the LadderAction class, <br>
 * Testing the perform method of the LadderAction class </p>
 */
public class LadderActionTest {
  @Test
  @DisplayName("Test the initialization of the BoardGame class")
  public void testConstructor() {
    LadderAction ladderaction = new LadderAction(10, "Test ladder");

    int expectedDestinationTileId = 10;
    String expectedDescription = "Test ladder";

    assertEquals(expectedDestinationTileId, ladderaction.destinationTileId, "The destination tile id should be 10");
    assertEquals(expectedDescription, ladderaction.description, "The description should be 'Test ladder'");
  }

  @Test
  @DisplayName("Test the perform method of the LadderAction class")
  public void testPerform() {
    LadderAction ladderAction = new LadderAction(5, "Test ladder");
    Player player = new Player("player", new BoardGame(10, 1, 6));
    Tile tile = new Tile(1);
    Tile destinationTile = new Tile(6);
    tile.setNextTile(destinationTile);
    player.placeOnTile(tile);

    ladderAction.perform(player);

    assertEquals(destinationTile, player.getCurrentTile(), "The player should be on the destination tile");
  }
}
