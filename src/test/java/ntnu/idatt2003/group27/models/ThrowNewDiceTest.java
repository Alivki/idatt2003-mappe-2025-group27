package ntnu.idatt2003.group27.models;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.stream.IntStream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * A test class for the ThrowNewDice TileAction.
 * @author Amadeus Berg
 */
public class ThrowNewDiceTest {
  /**
   * Test the constructor
   */
  @Test
  public void testThrowNewDiceConstructor() {
    ThrowNewDiceAction throwNewDiceAction = new ThrowNewDiceAction("Throw new dice test description", 1, 6);
    String expectedDescription = "Throw new dice test description";
    int expectedNumberOfDice = 1;
    int expectedNumberOfSides = 6;
    Assertions.assertEquals(expectedDescription, throwNewDiceAction.description, "Description does not match expected value!");
    Assertions.assertEquals(expectedNumberOfDice, throwNewDiceAction.numberOfDice, "Number of dice does not match expected value!");
    Assertions.assertEquals(expectedNumberOfSides, throwNewDiceAction.numberOfDieSides, " Number of sides does not match expected value!");
  }

  /**
   * Tests the perform method of the ThrowNewDice TileAction.
   */
  @Test
  public void testThrowNewDicePerform() {
    ThrowNewDiceAction throwNewDiceAction = new ThrowNewDiceAction("Throw new dice test description", 1, 2);
    Player player = new Player("player");
    Tile tile = new Tile(1);
    Tile tile2 = new Tile(2);
    Tile tile3 = new Tile(3);

    tile.setNextTile(tile2);
    tile2.setNextTile(tile3);

    tile3.setPreviousTile(tile2);
    tile2.setPreviousTile(tile);

    player.placeOnTile(tile);

    System.out.println();
    throwNewDiceAction.perform(player);

    System.out.println("Player landed on tile " + player.getCurrentTile().getTileId());
  }
}
