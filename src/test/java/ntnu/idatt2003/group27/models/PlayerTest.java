package ntnu.idatt2003.group27.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;

import org.junit.jupiter.api.Assertions;
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
  @Test
  @DisplayName("Test that creating a new player works.")
  public void testCreatePlayer() {
    BoardGame game = new BoardGame(90, 1, 6);
    Player player = new Player("Player_1");

    assertNotNull(player);
    assertNull(player.getCurrentTile());
    assertEquals("Player_1", player.getName());
  }

  @Test
  @DisplayName("Test that the startGame method places player on correct tile.")
  public void startGame() {
    BoardGame game = new BoardGame(90, 1, 6);
    Player player = new Player("Player_1");
    game.addPlayer(player);
    game.setUpGame();

    assertSame(player.getCurrentTile(), game.getBoard().getTile(0));
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
    BoardGame game = new BoardGame(90, 1, 6);
    Player player = new Player("Player_1");
    game.addPlayer(player);
    game.setUpGame();
    player.move(2);

    assertSame(player.getCurrentTile(), game.getBoard().getTile(2));
  }

  @Test
  @DisplayName("Test that player Move method function properly when moving past the end.")
  public void testMovePastEnd() {
    BoardGame game = new BoardGame(10, 1, 6);
    Player player = new Player("Player_1");
    game.addPlayer(player);
    game.setUpGame();
    player.move(7);
    player.move(3);

    assertSame(player.getCurrentTile(), game.getBoard().getTile(8));
  }
}
