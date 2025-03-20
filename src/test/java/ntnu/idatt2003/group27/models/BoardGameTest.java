package ntnu.idatt2003.group27.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.beans.Transient;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Test class for the BoardGame class.
 *
 * <p>Testing initialization of the boardGame class,<br>
 * adding players correctly and wrong,<br>
 * rolling the dice and getting a valid output,<br>
 * moving the player the correct amount of steps,<br>
 * and testing winner detection</p>
 */
public class BoardGameTest {
  @Test
  @DisplayName("Test the initialization of the BoardGame class")
  public void testBoardGameInitialization() {
    BoardGame game = new BoardGame(10, 1, 6);
    assertNotNull(game.getBoard(), "The board should exist when board game is initialized");
    assertEquals(10, game.getBoard().getTiles().size(), "There should be 10 tiles on this board");
  }

  @Test
  @DisplayName("test adding players to the game")
  public void testAddPlayers() {
    BoardGame game = new BoardGame(10, 1, 6);
    Player player1 = new Player("player1");
    Player player2 = new Player("player2");

    game.addPlayer(player1);
    game.addPlayer(player2);

    assertEquals(2, game.getPlayers().size(), "Should be two players in the game");
  }

  @Test
  @DisplayName("test adding duplicate players to the game")
  public void testAddingDuplicatePlayers() {
    BoardGame game = new BoardGame(10, 1, 6);
    Player player = new Player("player");

    game.addPlayer(player);

    assertThrows(IllegalArgumentException.class, () -> game.addPlayer(player),
        "Should throw and exception when adding a duplicate player");
  }

  @Test
  @DisplayName("test that dice roll produces valid numbers")
  public void testDiceRoll() {
    BoardGame game = new BoardGame(10, 1, 6);
    int roll = game.getDice().roll();

    assertTrue(roll >= 1 && roll <= 6, "Roll should be within the valid range for 1 six-sided die");
  }

  @Test
  @DisplayName("test moving the player")
  public void testPlayerMove() {
    BoardGame game = new BoardGame(10, 1, 6);
    Player player = new Player("player");

    game.addPlayer(player);

    game.setUpGame();

    player.move(6);
    assertEquals(6, player.getCurrentTile().getTileId(),
        "Player should have moved to tile 6 after move method");
  }

  @Test
  @DisplayName("test moving the player past game board")
  public void testPlayerMoveOvershoot() {
    BoardGame game = new BoardGame(10, 1, 6);
    Player player = new Player("player");

    game.addPlayer(player);

    game.setUpGame();

    player.move(6);
    player.move(6);
    assertEquals(9, player.getCurrentTile().getTileId(),
        "Player should move back after overshooting the game board end");
  }

  @Test
  @DisplayName("test the game set up method")
  public void testGameSetUp() {
    BoardGame game = new BoardGame(10, 1, 6);
    Player player = new Player("player");

    game.addPlayer(player);
    game.setUpGame();

    assertNotNull(game.getCurrentPlayer(),
        "The game is set up with a player and should have a current player");
    assertEquals(0, game.getCurrentPlayer().getCurrentTile().getTileId(),
        "The player should be placed on tile 0");
  }

  @Test
  @DisplayName("test that set up method throws error when no players are in the game")
  public void testGameSetUpThrows() {
    BoardGame game = new BoardGame(10, 1, 6);

    assertThrows(IllegalArgumentException.class, game::setUpGame, "Should throw error as no players are in the game");
  }

  @Test
  @DisplayName("test to check if winner is correctly detected and the game loop working")
  public void testWinnerDetectionAndPlayMethod() {
    BoardGame game = new BoardGame(10, 1, 6);
    Player player1 = new Player("player1");
    Player player2 = new Player("player2");

    game.addPlayer(player1);
    game.addPlayer(player2);

    game.setUpGame();
    game.play();

    assertNotNull(game.getWinner(), "A winner should be set after game has been played");
  }

  @Test
  @DisplayName("test if game throws error with to few players when starting")
  public void testStartingGameThrowsError() {
    BoardGame game = new BoardGame(10, 1, 6);
    Player player = new Player("player");

    game.addPlayer(player);

    assertThrows(IllegalArgumentException.class, game::play,
        "Game should throw error when starting with to few players");
  }
}
