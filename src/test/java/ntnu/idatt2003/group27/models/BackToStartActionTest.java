package ntnu.idatt2003.group27.models;

import static org.junit.jupiter.api.Assertions.assertEquals;

import ntnu.idatt2003.group27.models.actions.BackToStartActionLadder;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Test class for the LadderAction class.
 *
 * <p>Testing the constructor of the LadderAction class, <br>
 * Testing the perform method of the LadderAction class </p>
 */
public class BackToStartActionTest {
  @Test
  @DisplayName("Test the initialization of the BoardGame class")
  public void testConstructor() {
    BackToStartActionLadder backToStartAction = new BackToStartActionLadder("Test back to start");

    String expectedDescription = "Test back to start";

    assertEquals(expectedDescription, backToStartAction.description, "The description should be 'Test ladder'");
  }

  @Test
  @DisplayName("Test the perform method of the LadderAction class")
  public void testPerform() {
    BackToStartActionLadder backToStartAction = new BackToStartActionLadder("Test back to start action");
    Player player = new Player("player");
    Tile tile = new Tile(1);
    Tile tile2 = new Tile(2);
    Tile tile3 = new Tile(3);

    tile.setNextTile(tile2);
    tile2.setPreviousTile(tile3);

    tile3.setPreviousTile(tile2);
    tile2.setPreviousTile(tile);

    player.placeOnTile(tile3);

    backToStartAction.perform(player);

    assertEquals(tile, player.getCurrentTile(), "The player should be on the destination tile");
  }
}
