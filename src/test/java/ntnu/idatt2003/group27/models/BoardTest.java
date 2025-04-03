package ntnu.idatt2003.group27.models;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

import ntnu.idatt2003.group27.utils.filehandler.json.JsonFileReader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Test class for the Board class.
 *
 * <p>Testing initialization of the board class,<br>
 * Getting a tile from the board,
 * Adding a tile to the board</p>
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

  @Test
  @DisplayName("test initialization of the board")
  public void testInitializeBoardConstructor() {
    Board board = new Board(tiles);

    assertEquals(10, board.getTiles().size(), "should be added 10 tiles to the tiles map");
  }

  @Test
  @DisplayName("test tileId return expected tile with the json board initialization")
  public void testGettingTile() {
    Board board = null;
    try {
      board = new JsonFileReader().readFile("src/main/java/ntnu/idatt2003/group27/resources/boards/Board.json");
    } catch (IOException e) {
      System.err.println(e.getMessage());
    }

    Tile tile = board.getTile(9);

    assertNotNull(tile, "should be a tile on the board");
    assertEquals(9, tile.getTileId(), "should be added 10 tiles to the tiles map");
  }

  @Test
  @DisplayName("Test adding a tile to the map of tiles")
  public void testAddTile() {
    Board board = new Board(tiles);
    Tile tile = new Tile(10);
    board.addTile(tile);

    assertEquals(11, board.getTiles().size(), "should be added 11 tiles to the tiles map");
  }
}
