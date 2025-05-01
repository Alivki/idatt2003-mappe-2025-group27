package ntnu.idatt2003.group27.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import java.util.stream.IntStream;
import ntnu.idatt2003.group27.models.exceptions.NotEnoughPlayersInGameException;
import ntnu.idatt2003.group27.utils.filehandler.json.JsonFileReader;
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
public class LadderGameTest {
  LadderGame gameDefault;
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

    gameDefault = new LadderGame(board, 1, 6);
  }

  @Test
  @DisplayName("Test the initialization of the BoardGame class")
  public void testBoardGameDefaultInitialization() {
    LadderGame game = new LadderGame(board, 1, 6);
    assertNotNull(game.getBoard(), "The board should exist when board game is initialized");
    assertEquals(3, game.getBoard().getTiles().size(), "There should be 3 tiles on this board");
  }


  @Test
  @DisplayName("test adding players to the game")
  public void testAddPlayers() {
    Player player1 = new Player("player1");
    Player player2 = new Player("player2");

    gameDefault.addPlayer(player1);
    gameDefault.addPlayer(player2);

    assertEquals(2, gameDefault.getPlayers().size(), "Should be two players in the game");
  }

  @Test
  @DisplayName("test adding duplicate players to the game")
  public void testAddingDuplicatePlayers() {
    Player player = new Player("player");

    gameDefault.addPlayer(player);

    assertThrows(IllegalArgumentException.class, () -> gameDefault.addPlayer(player),
        "Should throw and exception when adding a duplicate player");
  }

  @Test
  @DisplayName("test that dice roll produces valid numbers")
  public void testDiceRoll() {
    LadderGame game = new LadderGame(board, 1, 6);
    int roll = game.getDice().roll();

    assertTrue(roll >= 1 && roll <= 6, "Roll should be within the valid range for 1 six-sided die");
  }

  @Test
  @DisplayName("test moving the player")
  public void testPlayerMove() throws NotEnoughPlayersInGameException {
    Player player = new Player("player");

    gameDefault.addPlayer(player);
    gameDefault.setUpGame();

    player.move(1);
    assertEquals(2, player.getCurrentTile().getTileId(),
        "Player should have moved to tile 7 after move method");
  }

  @Test
  @DisplayName("test the game set up method")
  public void testGameSetUp() throws NotEnoughPlayersInGameException {
    LadderGame game = new LadderGame(board, 1, 6);
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
    LadderGame game = new LadderGame(board, 1, 6);

    assertThrows(NotEnoughPlayersInGameException.class, game::setUpGame, "Should throw error as no players are in the game");
  }

  @Test
  @DisplayName("test to check if winner is correctly detected and the game loop working")
  public void testWinnerDetectionMethod() throws NotEnoughPlayersInGameException {
    Board board = null;

    try {
      board = new JsonFileReader().readFile("src/main/resources/boards/board.json");
    } catch (IOException e) {
      e.printStackTrace();
    }

    LadderGame game = new LadderGame(board, 1, 6);

    Player player1 = new Player("player1");
    Player player2 = new Player("player2");
    Player player3 = new Player("player3");

    game.addPlayer(player1);
    game.addPlayer(player2);
    game.addPlayer(player3);

    game.setUpGame();

    IntStream.range(0, 100).forEach(i -> {
      if(game.getWinner() != null) {
        return;
      }

      try {
        game.play();
      } catch (NotEnoughPlayersInGameException e) {
        throw new RuntimeException(e);
      }
    });

    assertNotNull(game.getWinner(), "A winner should be set after game has been played");
  }

  @Test
  @DisplayName("test if game throws error with to few players when starting")
  public void testStartingGameThrowsError() {
    LadderGame game = new LadderGame(board, 1, 6);
    Player player = new Player("player");

    game.addPlayer(player);

    assertThrows(NotEnoughPlayersInGameException.class, game::play,
        "Game should throw error when starting with to few players");
  }
}
