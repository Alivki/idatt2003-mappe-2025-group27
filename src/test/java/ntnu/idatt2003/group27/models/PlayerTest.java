package ntnu.idatt2003.group27.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;

import java.io.IOException;
import ntnu.idatt2003.group27.filehandler.JsonFileReader;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Test class for the Player class.
 *
 * <p>Testing the createPlayer method,<br>
 * testing the placeOnTile method,<br>
 * testing the move method</p>
 */
public class PlayerTest {
  BoardGame game;

  /**
   * Creates a new board game before each test.
   */
  @BeforeEach
  public void setup() {
    Board board = null;
    try {
      board = new JsonFileReader().readFile("src/main/java/ntnu/idatt2003/group27/resources/boards/Board.json");
    } catch (IOException e) {
      System.err.println(e.getMessage());
    }

    game = new BoardGame(board, 1, 6);
  }

  @Test
  @DisplayName("Test that creating a new player works.")
  public void testCreatePlayer() {
    Player player = new Player("Player_1");

    assertNotNull(player);
    assertNull(player.getCurrentTile());
    assertEquals("Player_1", player.getName());
  }

  @Test
  @DisplayName("Test that currentTile is null by default")
  public void testCurrentTileIsNull() {
    Player player = new Player("Player_1");

    assertNull(player.getCurrentTile());
  }

  @Test
  @DisplayName("Test that the startGame method places player on correct tile.")
  public void startGame() {
    Player player = new Player("Player_1");
    game.addPlayer(player);
    game.setUpGame();

    assertSame(player.getCurrentTile(), game.getBoard().getTile(1));
  }

  @Test
  @DisplayName("Test that placeOnTile method functions properly.")
  public void testPlaceOnTile() {
    BoardGame game = new BoardGame(90, 1, 6);
    Player player = new Player("Player_1");
    Tile tile = new Tile(0);
    player.placeOnTile(tile);

    assertSame(player.getCurrentTile(), tile);
  }

  @Test
  @DisplayName("Test that player Move method function properly.")
  public void testMove() {
    Player player = new Player("Player_1");
    game.addPlayer(player);
    game.setUpGame();
    player.move(2);

    assertSame(player.getCurrentTile(), game.getBoard().getTile(3));
  }
}
