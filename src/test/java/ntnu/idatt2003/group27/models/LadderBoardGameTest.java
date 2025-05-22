package ntnu.idatt2003.group27.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import java.util.stream.IntStream;
import ntnu.idatt2003.group27.models.enums.LadderGameType;
import ntnu.idatt2003.group27.models.exceptions.NotEnoughPlayersInGameException;
import ntnu.idatt2003.group27.utils.filehandler.json.JsonFileReader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Test class for the {@link LadderBoardGame} class.
 *
 * <p>Testing initialization of the boardGame class with default board,<br>
 * Testing initialization of the boardGame class with json board,<br>
 * Adding players correctly and wrong,<br>
 * Rolling the dice and getting a valid output,<br>
 * Moving the player the correct amount of steps,<br>
 * Testing winner detection</p>
 *
 * @author Iver Lindholm, Amadeus Berg
 */
public class LadderBoardGameTest {
  
  /** The game board used for all tests. */
  Board board;

  /**
   * Creates two games to test with both default and json boards.
   */
  @BeforeEach
  public void createGames() {
    Map<Integer, Tile> tiles = new HashMap<>();
    Tile tile1 = new Tile(1);
    Tile tile2 = new Tile(2);
    Tile tile3 = new Tile(3);

    tile1.setNextTile(tile2);
    tile2.setNextTile(tile3);

    tile2.setPreviousTile(tile1);
    tile3.setPreviousTile(tile2);

    tiles.put(1, tile1);
    tiles.put(2, tile2);
    tiles.put(3, tile3);

    board = new Board(tiles);
  }

  /**
   * Test the initialization of the LadderBoardGame class with a default board.
   */
  @Test
  @DisplayName("Test the initialization of the BoardGame class")
  public void testBoardGameDefaultInitialization() {
    LadderBoardGame game = new LadderBoardGame(board, 1, 6);
    assertTrue(game.getBoards().size() == 1, "There should be 1 board in the game");
    assertTrue(game.getDice().getNumberOfDice() == 1, "There should be a dice object in the game");
  }

  /**
   * Test adding player to the game.
   */
  @Test
  @DisplayName("test adding players to the game")
  public void testAddPlayers() {
    LadderBoardGame game = new LadderBoardGame(board, 1, 6);
    Player player1 = new Player("player1");
    Player player2 = new Player("player2");

    game.addPlayer(player1);
    game.addPlayer(player2);

    assertEquals(2, game.getPlayers().size(), "Should be two players in the game");
  }

  /**
   * Test adding duplicate players to the game.
   */
  @Test
  @DisplayName("test adding duplicate players to the game")
  public void testAddingDuplicatePlayers() {
    LadderBoardGame game = new LadderBoardGame(board, 1, 6);
    Player player = new Player("player");

    game.addPlayer(player);

    assertThrows(IllegalArgumentException.class, () -> game.addPlayer(player),
        "Should throw and exception when adding a duplicate player");
  }

  /**
   * Test dice roll method to check if it produces valid numbers.
   */
  @Test
  @DisplayName("test that dice roll produces valid numbers")
  public void testDiceRoll() {
    LadderBoardGame game = new LadderBoardGame(board, 1, 6);
    int roll = game.getDice().roll();

    assertTrue(roll >= 1 && roll <= 6, "Roll should be within the valid range for 1 six-sided die");
  }

  /**
   * Test that the game is set up correctly.
   *
   * @throws NotEnoughPlayersInGameException
   */
  @Test
  @DisplayName("test the game set up method")
  public void testGameSetUp() throws NotEnoughPlayersInGameException {
    LadderBoardGame game = new LadderBoardGame(board, 1, 6);
    Player player = new Player("player");

    game.addPlayer(player);
    game.setUpGame();

    assertNotNull(game.getCurrentPlayer(),
        "The game is set up with a player and should have a current player");
    assertEquals(1, game.getCurrentPlayer().getCurrentTile().getTileId(),
        "The player should be placed on tile 0");
  }

  /**
   * Test that the game set up method throws an error when no players are in the game.
   */
  @Test
  @DisplayName("test that set up method throws error when no players are in the game")
  public void testGameSetUpThrows() {
    LadderBoardGame game = new LadderBoardGame(board, 1, 6);

    assertThrows(NotEnoughPlayersInGameException.class, game::setUpGame, "Should throw error as no players are in the game");
  }

  /**
   * Tests that the winning player is correctly detected when a win is simulated.
   * @throws NotEnoughPlayersInGameException if the game setup fails due to insufficient players.
   */
  @Test
  @DisplayName("test that the correct winner is detected when a player reaches the winning tile")
  public void testGetWinner() {
    // Initialize a game with a board and a player
    LadderBoardGame game = new LadderBoardGame(board, 1, 6);
    Player player = new Player("Alice");

    game.addPlayer(player);
    try {
      game.setUpGame();
    } catch (NotEnoughPlayersInGameException e) {
      throw new RuntimeException(e);
    }

    // Simulate the player reaching the last tile
    Tile lastTile = board.getTiles().get(board.getTiles().size());
    player.placeOnTile(lastTile);

    assertEquals(player, game.getWinner(), "getWinner should return the player who reached the last tile");
  }

  /**
   * Tests that no winner is detected when no player has reached the last tile.
   * @throws NotEnoughPlayersInGameException if the game setup fails due to insufficient players.
   */
  @Test
  @DisplayName("test that winner is null when no player has reached the last tile")
  public void testGetWinnerWithoutWinner() {
    // Initialize a game with a board and a player
    LadderBoardGame game = new LadderBoardGame(board, 1, 6);
    Player player = new Player("Alice");

    game.addPlayer(player);
    try {
      game.setUpGame();
    } catch (NotEnoughPlayersInGameException e) {
      throw new RuntimeException(e);
    }

    // Simulate the player being on the first tile
    Tile firstTile = board.getTiles().get(1);
    player.placeOnTile(firstTile);

    assertNull(game.getWinner(), "getWinner should return null when no player has reached the last tile");
  }

  /**
   * Test that the game throws an error for minimum required players.
   */
  @Test
  @DisplayName("test if game throws error with too few players when starting")
  public void testStartingGameThrowsError() {
    LadderBoardGame game = new LadderBoardGame(board, 1, 6);
    Player player = new Player("player");

    game.addPlayer(player);

    assertThrows(NotEnoughPlayersInGameException.class, game::play,
        "Game should throw error when starting with to few players");
  }
}
