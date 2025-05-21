package ntnu.idatt2003.group27.models;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.IntStream;

import ntnu.idatt2003.group27.utils.filehandler.json.JsonFileReader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for the {@link Board} class.
 *
 * <p>Verifies board initialization, tile retrieval, and tile addition.</p>
 *
 * @author Iver Lindholm, Amadeus Berg
 */
public class BoardTest {

  Map<Integer, Tile> tiles;

  /**
   * Creates a map of tiles for the board.
   */
  @BeforeEach
  public void createTiles() {
    tiles = new HashMap<>();
    IntStream.range(0, 10).forEach(i -> tiles.put(i, new Tile(i)));
  }

  /**
   * Verifies that the board is correctly initialized with the expected number of tiles.
   */
  @Test
  @DisplayName("test initialization of the board")
  public void testInitializeBoardConstructor() {
    Board board = new Board(tiles);
    assertEquals(10, board.getTiles().size(), "should be added 10 tiles to the tiles map");
  }

  /**
   * Verifies that constructing a Board with null tiles throws an IllegalArgumentException.
   */
  @Test
  @DisplayName("should throw exception when initialized with null tiles")
  public void testInitializeBoardWithNullTiles() {
    assertThrows(IllegalArgumentException.class, () -> new Board(null));
  }

  /**
   * Verifies that constructing a Board with no tiles results in an empty board.
   */
  @Test
  @DisplayName("should handle empty tile list")
  public void testInitializeBoardWithEmptyTiles() {
    Board board = new Board(Collections.emptyMap());
    assertTrue(board.getTiles().isEmpty(), "Board should be initialized with no tiles");
  }

  /**
   * Verifies that a tile can be retrieved from a board initialized from a JSON file.
   */
  @Test
  @DisplayName("test tileId return expected tile with the json board initialization")
  public void testGettingTile() {
    Board board = null;
    try {
      board = new JsonFileReader().readFile("src/main/resources/boards/Board.json");
    } catch (IOException e) {
      System.err.println(e.getMessage());
    }

    Tile tile = board.getTile(9);
    assertNotNull(tile, "should be a tile on the board");
    assertEquals(9, tile.getTileId(), "should return tile with ID 9");
  }

  /**
   * Verifies that a tile can be added to the board.
   */
  @Test
  @DisplayName("Test adding a tile to the map of tiles")
  public void testAddTile() {
    Board board = new Board(tiles);
    Tile tile = new Tile(10);
    board.addTile(tile);
    assertEquals(11, board.getTiles().size(), "should be added 11 tiles to the tiles map");
  }

  /**
   * Verifies that retrieving a tile at a valid position returns the correct tile.
   */
  @Test
  @DisplayName("should return correct tile at given position")
  public void testGetTileAtValidPosition() {
    Board board = new Board(tiles);
    Tile tile = board.getTile(5);
    assertNotNull(tile);
    assertEquals(5, tile.getTileId());
  }

  /**
   * Verifies that accessing an invalid tile position throws an exception.
   */
  @Test
  @DisplayName("should throw exception when accessing invalid tile position")
  public void testGetTileAtInvalidPosition() {
    Board board = new Board(tiles);
    assertThrows(NoSuchElementException.class, () -> board.getTile(99));
  }

  /**
   * Verifies that accessing an invalid negative tile position throws an exception.
   */
  @Test
  @DisplayName("should throw exception when accessing invalid tile position")
  public void testGetTileAtNegativePosition() {
    Board board = new Board(tiles);
    assertThrows(NoSuchElementException.class, () -> board.getTile(-1));
  }
}
