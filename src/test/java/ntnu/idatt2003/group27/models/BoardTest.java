package ntnu.idatt2003.group27.models;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Test class for the Board class.
 *
 * <p>Testing initialization of the board class,<br>
 * getting a tile from the board</p>
 */
public class BoardTest {
  @Test
  @DisplayName("test initialization of the board")
  public void testInitializeBoard() {
    Board board = new Board(10);

    assertEquals(10, board.getTiles().size(), "should be added 10 tiles to the tiles map");
  }

  @Test
  @DisplayName("test initialization of the board throws error with negative tiles")
  public void testInitializeBoardThrows() {
    assertThrows(IllegalArgumentException.class, () -> new Board(-10),
        "Negative number of tiles should throw error");
  }

  @Test
  @DisplayName("test tileId return expected tile")
  public void testGettingTile() {
    Board board = new Board(10);
    Tile tile = board.getTile(9);

    assertNotNull(tile, "should be a tile on the board");
    assertEquals(9, tile.getTileId(), "should be added 10 tiles to the tiles map");
  }
}
