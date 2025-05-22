package ntnu.idatt2003.group27.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import ntnu.idatt2003.group27.models.actions.EasyMathQuestion;
import ntnu.idatt2003.group27.models.exceptions.NotEnoughPlayersInGameException;
import ntnu.idatt2003.group27.models.exceptions.WrongMathAnswerException;
import ntnu.idatt2003.group27.models.interfaces.MathTileAction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Test class for the {@link MathBoardGame} class.
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
public class MathBoardGameTest {
  
  /** The game boards used for all tests. */
  List<Board> boards;

  /** The players used for all tests. */
  List<Player> players;

  /**
   * Creates two games to test with both default and json boards.
   */
  @BeforeEach
  public void createGames() {
    // Create map of tiles
    Map<Integer, Tile> board1Tiles = new HashMap<>();
    Map<Integer, Tile> board2Tiles = new HashMap<>();

    // Creates tiles for Board 1
    Tile tile1 = new Tile(1);
    Tile tile2 = new Tile(2);
    Tile tile3 = new Tile(3);
    tile1.setNextTile(tile2);
    tile2.setNextTile(tile3);
    board1Tiles.put(1, tile1);
    board1Tiles.put(2, tile2);
    board1Tiles.put(3, tile3);

    // Add tile actions
    tile1.setLandAction(new EasyMathQuestion());

    // Creates tile for Board 2
    Tile tile4 = new Tile(4);
    Tile tile5 = new Tile(5);
    Tile tile6 = new Tile(6);
    tile4.setNextTile(tile5);
    tile5.setNextTile(tile6);

    board2Tiles.put(4, tile4);
    board2Tiles.put(5, tile5);
    board2Tiles.put(6, tile6);

    // Create two boards
    Board board1 = new Board(board1Tiles);
    Board board2 = new Board(board2Tiles);
    boards = List.of(board1, board2);

    // create two players
    Player player1 = new Player("player1");
    Player player2 = new Player("player2");
    players = List.of(player1, player2);
  }

  /**
   * Test the initialization of the MathBoardGame class with a default board.
   */
  @Test
  @DisplayName("Test that default initialization of the MathBoardGame class works")
  public void testBoardGameDefaultInitialization() {
    MathBoardGame game = new MathBoardGame(boards, players);
    assertEquals(2, game.getBoards().size(), "There should be 1 board in the game");
    assertEquals(game.getPlayers(), players, "The players should be the same as the ones passed in");
    assertNull(game.getCurrentPlayer(), "The current player should be null");
  }

  /**
   * Test the initialization of the MathBoardGame class with null values.
   */
  @Test
  @DisplayName("Test that null value initialization creates empty internal lists")
  public void testBoardGameNullValueInitialization() {
    assertThrows(IllegalArgumentException.class, () -> {MathBoardGame game = new MathBoardGame(null, null);});
  }

  /**
   * Test adding players to the game.
   */
  @Test
  @DisplayName("test that addPlayer adds players and boards and maps them correctly")
  public void testAddPlayers() {
    // Create empty game instance
    List<Board> emptyBoards = new ArrayList<>();
    List<Player> emptyPlayers = new ArrayList<>();
    MathBoardGame game = new MathBoardGame(emptyBoards, emptyPlayers);

    // Create two players
    Player player1 = new Player("player1");
    Player player2 = new Player("player2");
    List<Player> players = List.of(player1, player2);

    // Add players to the game
    game.addPlayers(boards, players);

    // Check if the players and boards were added and mapped correctly
    assertEquals(game.getBoards().get(players.get(0)), boards.get(0), "The first board should be mapped to the first player");
    assertEquals(game.getBoards().get(players.get(1)), boards.get(1), "The second board should be mapped to the second player");
    assertEquals(2, game.getPlayers().size(), "There should be two players in the game");
    assertEquals(2, game.getBoards().size(), "There should be two boards in the game");
    assertEquals(game.getPlayers(), new ArrayList<>(players), "The players should be the same as the one passed in");
  }

  /**
   * Test that the game throws an error for minimum required players.
   */
  @Test
  @DisplayName("test if game throws error with too few players when starting")
  public void testStartingGameThrowsError() {
    // Create a game with only one player
    Player player = new Player("player");
    List<Player> players = List.of(player);
    MathBoardGame game = new MathBoardGame(boards, players);

    assertThrows(NotEnoughPlayersInGameException.class, game::play,
        "Game should throw error when starting with to few players");
  }
}
