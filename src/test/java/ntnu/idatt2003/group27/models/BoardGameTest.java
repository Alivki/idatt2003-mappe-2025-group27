package ntnu.idatt2003.group27.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.beans.Transient;
import java.io.IOException;
import ntnu.idatt2003.group27.filehandler.JsonFileReader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Test class for the BoardGame class.
 *
 * <p>Testing initialization of the boardGame class with default board,<br>
 * Testing initialization of the boardGame class with json board,<br>
 * Adding players correctly and wrong,<br>
 * Rolling the dice and getting a valid output,<br>
 * Moving the player the correct amount of steps,<br>
 * Testing winner detection</p>
 */
public class BoardGameTest {
  BoardGame gameDefault;
  BoardGame gameJson;

  @Test
  @DisplayName("Test the initialization of the BoardGame class")
  public void testBoardGameDefaultInitialization() {
    BoardGame game = new BoardGame(10, 1, 6);
    assertNotNull(game.getBoard(), "The board should exist when board game is initialized");
    assertEquals(10, game.getBoard().getTiles().size(), "There should be 10 tiles on this board");
  }

  @Test
  @DisplayName("Test the initialization of the BoardGame class")
  public void testBoardGameJsonInitialization() {
    Board board = null;
    try {
      board = new JsonFileReader().readFile("src/main/java/ntnu/idatt2003/group27/resources/boards/Board.json");
    } catch (IOException e) {
      System.err.println(e.getMessage());
    }
    BoardGame game = new BoardGame(board, 1, 6);
    assertNotNull(game.getBoard(), "The board should exist when board game is initialized");
    assertEquals(90, game.getBoard().getTiles().size(), "There should be 10 tiles on this board");
  }

  /**
   * Creates two games to test with both default and json boards.
   */
  @BeforeEach
  public void createGames() {
    Board board = null;
    try {
      board = new JsonFileReader().readFile("src/main/java/ntnu/idatt2003/group27/resources/boards/Board.json");
    } catch (IOException e) {
      System.err.println(e.getMessage());
    }

    gameDefault = new BoardGame(10, 1, 6);
    gameJson = new BoardGame(board, 1, 6);
  }

  @Test
  @DisplayName("test adding players to the game")
  public void testAddPlayers() {
    Player player1 = new Player("player1");
    Player player2 = new Player("player2");

    gameDefault.addPlayer(player1);
    gameDefault.addPlayer(player2);

    gameJson.addPlayer(player1);
    gameJson.addPlayer(player2);

    assertEquals(2, gameDefault.getPlayers().size(), "Should be two players in the game");
    assertEquals(2, gameJson.getPlayers().size(), "Should be two players in the game");
  }

  @Test
  @DisplayName("test adding duplicate players to the game")
  public void testAddingDuplicatePlayers() {
    Player player = new Player("player");

    gameDefault.addPlayer(player);
    gameJson.addPlayer(player);

    assertThrows(IllegalArgumentException.class, () -> gameDefault.addPlayer(player),
        "Should throw and exception when adding a duplicate player");
    assertThrows(IllegalArgumentException.class, () -> gameJson.addPlayer(player),
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
    Player player = new Player("player");

    gameJson.addPlayer(player);

    gameJson.setUpGame();

    player.move(6);
    assertEquals(7, player.getCurrentTile().getTileId(),
        "Player should have moved to tile 7 after move method");
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
    assertEquals(1, game.getCurrentPlayer().getCurrentTile().getTileId(),
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
    Player player1 = new Player("player1");
    Player player2 = new Player("player2");
    Player player3 = new Player("player3");

    gameJson.addPlayer(player1);
    gameJson.addPlayer(player2);
    gameJson.addPlayer(player3);

    gameJson.setUpGame();
    gameJson.play();

    assertNotNull(gameJson.getWinner(), "A winner should be set after game has been played");
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
