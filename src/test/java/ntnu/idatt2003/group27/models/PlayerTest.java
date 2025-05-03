package ntnu.idatt2003.group27.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;

import java.util.HashMap;
import java.util.Map;

import ntnu.idatt2003.group27.models.exceptions.NotEnoughPlayersInGameException;
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
  LadderGame game;

  /**
   * Creates a new board game before each test.
   */
  @BeforeEach
  public void setup() {
    Map<Integer, Tile> tiles = new HashMap<>();
    Tile tile1 = new Tile(1);
    Tile tile2 = new Tile(2);

    tile1.setNextTile(tile2);

    tiles.put(1, tile1);
    tiles.put(2, tile2);

    Board board = new Board(tiles);

    game = new LadderGame(board, 1, 6);
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
  public void startGame() throws NotEnoughPlayersInGameException {
    Player player = new Player("Player_1");
    game.addPlayer(player);
    game.setUpGame();

    assertSame(player.getCurrentTile(), game.getBoard().getTile(1));
  }

  @Test
  @DisplayName("Test that placeOnTile method functions properly.")
  public void testPlaceOnTile() {
    Player player = new Player("Player_1");
    Tile tile = new Tile(0);
    player.placeOnTile(tile);

    assertSame(player.getCurrentTile(), tile);
  }

  @Test
  @DisplayName("Test that player Move method function properly.")
  public void testMove() throws NotEnoughPlayersInGameException {
    Player player = new Player("Player_1");
    game.addPlayer(player);
    game.setUpGame();
    player.move(1);

    assertSame(player.getCurrentTile(), game.getBoard().getTile(2));
  }
}