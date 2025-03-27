package ntnu.idatt2003.group27.models;

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
   * Tests that the perform method of the tileaction functions.
   */
  @Test
  public void testThrowNewDicePerform() {
    ThrowNewDiceAction throwNewDiceAction = new ThrowNewDiceAction("Throw new dice test description", 1, 6);
    Board board = new Board(10);
    Player player = new Player("Test player");
    int startTileId = 1;
    player.placeOnTile(board.getTile(startTileId)); //place player on start tile
    throwNewDiceAction.perform(player);
    System.out.println("Player landed on tile " + player.getCurrentTile().getTileId());
  }

  @Test
  public void testThrowNewDiceTileAction() {
    Board board = new Board(10);
    for (int i = 1; i < 9; i++) {
      board.getTile(i).setLandAction(new ThrowNewDiceAction("Throw new dice test description", 1, 6));
    }

    board.getTile(2).setLandAction(new ThrowNewDiceAction("Throw new dice test description", 1, 6));
    Player player = new Player("Test player");
    Player player2 = new Player("Test player 2");
    BoardGame game = new BoardGame(board, 1, 6);
    game.addPlayer(player);
    game.addPlayer(player2);
    game.setUpGame();
    game.play();
  }
}
