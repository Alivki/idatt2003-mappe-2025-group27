package ntnu.idatt2003.group27.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

import javafx.scene.paint.Color;
import ntnu.idatt2003.group27.models.exceptions.InvalidPlayerMoveException;
import ntnu.idatt2003.group27.models.exceptions.NotEnoughPlayersInGameException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Test class for the {@link Player} class.
 *
 * <p>Testing the createPlayer method,<br>
 * testing the placeOnTile method,<br>
 * testing the move method</p>
 */
public class PlayerTest {

  /** The player to be tested. */
  Player player;

  /**
   * Creates a new board game before each test.
   */
  @BeforeEach
  public void setup() {
    player = new Player("Player_1");
  }

  /**
   * Test that the constructor of the Player class works as expected.
   */
  @Test
  @DisplayName("Test that creating a new player works with just a name parameter.")
  public void testCreatePlayer() {
    assertNotNull(player, "Player should not be null");
    assertNull(player.getCurrentTile(), "Current tile should be null");
    assertNull(player.getPiece(), "Current piece should be null");
    assertNull(player.getColor(), "Current color should be null");
    assertEquals("Player_1", player.getName(), "Name should match");
  }

  /**
   * Test that the constructor of the Player class works as expected.
   */
  @Test
  @DisplayName("Test that creating a new player works when assigning name, piece, and color parameters.")
  public void testCreateNamePieceColorPlayer() {
    Player player = new Player("Player_1", new Piece("Piece_1", "path/to/icon"), Color.RED);
    assertNotNull(player, "Player should not be null");
    assertNull(player.getCurrentTile(), "Current tile should be null");
    assertEquals("Player_1", player.getName(), "Name should match");
    assertEquals(Color.RED, player.getColor(), "Color should match");
    assertNotNull(player.getPiece(), "Piece should not be null");
    assertEquals("Piece_1", player.getPiece().getName(), "Piece name should match");
    assertEquals("path/to/icon", player.getPiece().getIconFilePath(), "Piece icon path should match");
  }


  /**
   * Test that the placeOnTile method of the Player class works as expected.
   */
  @Test
  @DisplayName("Test that placeOnTile method functions properly.")
  public void testPlaceOnTile() {
    Player player = new Player("Player_1");
    Tile tile = new Tile(0);
    player.placeOnTile(tile);

    assertSame(player.getCurrentTile(), tile);
  }

  /**
   * Test that the placeOnTile method of the Player class works as expected with a null value.
   */
  @Test
  @DisplayName("Test that placeOnTile method functions properly with a null tile value.")
  public void testPlaceOnNullTile() {
    Player player = new Player("Player_1");
    Tile tile = null;
    player.placeOnTile(tile);

    assertSame(player.getCurrentTile(), tile);
  }

  /**
   * Test that the move method of the Player class works as expected.
   */
  @Test
  @DisplayName("Test that a single forward player step moves the player to the next tile.")
  public void testPositiveMove() {
    Tile startTile = new Tile(0);
    Tile endTile = new Tile(1);
    startTile.setNextTile(endTile);

    player.placeOnTile(startTile);
    player.move(1);

    assertEquals(player.getCurrentTile(), endTile, "Player's current tile should match endTile");
  }

  /**
   * Test that the move method of the Player class works as expected.
   */
  @Test
  @DisplayName("Test that when a player moves multiple positive steps it is placed on the correct tile.")
  public void testExtendedPositiveMove() {
    Tile startTile = new Tile(0);
    Tile tile1 = new Tile(1);
    Tile tile2 = new Tile(2);
    Tile endTile = new Tile(3);
    startTile.setNextTile(tile1);
    tile1.setNextTile(tile2);
    tile2.setNextTile(endTile);

    player.placeOnTile(startTile);
    player.move(3);

    assertEquals(player.getCurrentTile(), endTile, "Player's current tile should match endTile");
  }

  /**
   * Test that the move method of the Player class works as expected.
   */
  @Test
  @DisplayName("Test that when a player moves multiple negative steps it is placed on the correct tile.")
  public void testExtendedNegativeMove() {
    Tile startTile = new Tile(0);
    Tile tile1 = new Tile(1);
    Tile tile2 = new Tile(2);
    Tile endTile = new Tile(3);
    tile1.setPreviousTile(startTile);
    tile2.setPreviousTile(tile1);
    endTile.setPreviousTile(tile2);

    player.placeOnTile(endTile);
    player.move(-3);

    assertEquals(player.getCurrentTile(), startTile, "Player's current tile should match endTile");
  }

  /**
   * Test that the move method of the Player class works as expected with a negative value.
   */
  @Test
  @DisplayName("Test that a single backward player step moves the player to the previous tile.")
  public void testNegativeMove() {
    Tile startTile = new Tile(0);
    Tile endTile = new Tile(1);
    endTile.setPreviousTile(startTile);

    player.placeOnTile(endTile);
    player.move(-1);

    assertEquals(player.getCurrentTile(), startTile, "Player's current tile should match startTile");
  }

  /**
   * Test that the move method of the Player class works as expected when the move is invalid.
   */
  @Test
  @DisplayName("Test that InvalidPlayerMoveException is thrown when moving from an invalid tile.")
  public void testInvalidMove() {
    player.placeOnTile(null);
    assertThrows(InvalidPlayerMoveException.class, () -> {player.move(-1);},
        "Should throw InvalidPlayerMoveException as the player is not on a tile.");
  }

  /**
   * Test that the move method of the Player class works as expected when the move is invalid.
   */
  @Test
  @DisplayName("Test that InvalidPlayerMoveException is thrown when moving forwards from an invalid tile.")
  public void testInvalidPositiveMove() {
    Tile startTile = new Tile(0);

    player.placeOnTile(startTile);
    assertThrows(InvalidPlayerMoveException.class, () -> {player.move(1);},
        "Should throw InvalidPlayerMoveException as the next tile is null (invalid move).");
  }

  /**
   * Test that the move method of the Player class works as expected when the move is invalid.
   */
  @Test
  @DisplayName("Test that InvalidPlayerMoveException is thrown when moving backwards from an invalid tile.")
  public void testInvalidNegativeMove() {
    Tile startTile = new Tile(0);

    player.placeOnTile(startTile);
    assertThrows(InvalidPlayerMoveException.class, () -> {player.move(-1);},
        "Should throw InvalidPlayerMoveException as the previous tile is null (invalid move).");
  }

  /**
   * Test that the move method of the Player class works as expected.
   */
  @Test
  @DisplayName("Test that player Move method function properly when moving 0 steps.")
  public void testZeroMove() {
    Tile startTile = new Tile(0);
    player.placeOnTile(startTile);
    player.move(0);

    assertEquals(player.getCurrentTile(), startTile, "Player's current tile should match endTile");
  }

  /**
   * Test that the getPiece method returns the correct piece.
   */
  @Test
  @DisplayName("Test that getPiece method returns the correct piece.")
  public void testGetPiece(){
    assertEquals(null, player.getPiece(), "Piece should be null");
  }

  /**
   * Test that the getName method returns the correct name.
   */
  @Test
  @DisplayName("Test that getName method returns the correct name.")
  public void testGetName() {
    assertEquals("Player_1", player.getName(), "Name should be 'Player_1'");
  }


  /**
   * Test that the getColor method returns null.
   */
  @Test
  @DisplayName("Test that getColor method returns null.")
  public void testGetColor() {
    assertEquals(null, player.getColor(), "Color should be null");
  }

  /**
   * Test that the setColor method sets the correct color.
   */
  @Test
  @DisplayName("Test that getColor method returns null.")
  public void testSetColor() {
    player.setColor(Color.PINK);
    assertEquals(Color.PINK, player.getColor(), "Color should match");
  }

  /**
   * Test that the setPiece method sets the correct piece.
   */
  @Test
  @DisplayName("Test that setPiece method sets the correct piece.")
  public void testSetPiece() {
    Piece piece = new Piece("Piece_1", "path/to/icon");
    player.setPiece(piece);
    assertEquals(piece, player.getPiece(), "Piece should match");
  }
}