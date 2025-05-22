package ntnu.idatt2003.group27.models;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import ntnu.idatt2003.group27.models.actions.BackToStartAction;
import ntnu.idatt2003.group27.models.actions.LadderAction;
import ntnu.idatt2003.group27.models.interfaces.TileAction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for the {@link BoardFactory} class.
 *
 * <p>Verifies creation of boards with correct tile linking and action assignment.</p>
 *
 * @author Amadeus Berg
 */
public class BoardFactoryTest {

  private BoardFactory boardFactory;
  private Map<Integer, TileAction> tileActions;

  /**
   * Initializes the BoardFactory and a sample tile action map.
   */
  @BeforeEach
  public void setUp() {
    boardFactory = new BoardFactory();
    tileActions = new HashMap<Integer, TileAction>(10);
    tileActions.put(3, new BackToStartAction("Back to start"));
    tileActions.put(5, new LadderAction(8, "Ladder to tile 8"));
  }

  /**
   * Verifies that a board is created with the correct number of tiles.
   */
  @Test
  @DisplayName("should create board with correct number of tiles")
  public void testCreateBoardWithCorrectTileCount() {
    Board board = boardFactory.createBoard(10, tileActions);

    assertEquals(10, board.getTiles().size(), "Board should contain 10 tiles");
  }

  /**
   * Verifies that tiles are linked correctly (next and previous).
   */
  @Test
  @DisplayName("should link tiles correctly")
  public void testTileLinking() {
    Board board = boardFactory.createBoard(5, tileActions);

    Tile tile2 = board.getTile(2);
    Tile tile3 = board.getTile(3);
    Tile tile4 = board.getTile(4);

    assertEquals(tile3, tile2.getNextTile(), "Tile 2 should link to Tile 3");
    assertEquals(tile2, tile3.getPreviousTile(), "Tile 3 should link back to Tile 2");
    assertEquals(tile4, tile3.getNextTile(), "Tile 3 should link to Tile 4");
  }

  /**
   * Verifies that tile actions are assigned to the correct tiles.
   */
  @Test
  @DisplayName("should assign tile actions correctly")
  public void testTileActionAssignment() {
    Board board = boardFactory.createBoard(10, tileActions);

    assertNotNull(board.getTile(3).getLandAction(), "Tile 3 should have an action");
    assertNotNull(board.getTile(5).getLandAction(), "Tile 5 should have an action");
    assertNull(board.getTile(4).getLandAction(), "Tile 4 should not have an action");
  }

  /**
   * Verifies that creating a board with zero tiles results in an empty board.
   */
  @Test
  @DisplayName("should create empty board when tile count is zero")
  public void testCreateBoardWithZeroTiles() {
    Board board = boardFactory.createBoard(0, tileActions);

    assertTrue(board.getTiles().isEmpty(), "Board should be empty");
  }

  /**
   * Verifies that creating a board with negative tile count throws an exception.
   */
  @Test
  @DisplayName("should throw exception when tile count is negative")
  public void testCreateBoardWithNegativeTileCount() {
    Board board = boardFactory.createBoard(-5, tileActions);

    assertTrue(board.getTiles().isEmpty(), "Board should be empty");
  }

  /**
   * Verifies that a board is created correctly when tileActions is null.
   */
  @Test
  @DisplayName("should create board with correct number of tiles when tileActions is null")
  public void testCreateBoardWithNullTileActions() {
    Board board = boardFactory.createBoard(10, null);

    assertNotNull(board, "Board should not be null");
    assertEquals(10, board.getTiles().size(), "Board should contain 10 tiles");
    assertTrue(board.getTiles().values().stream().allMatch(tile -> tile.getLandAction() == null),
        "No tile should have a land action assigned");
  }

  /**
   * Verifies that a board is created correctly when tileActions is empty.
   */
  @Test
  @DisplayName("should create board with correct number of tiles when tileActions is empty")
  public void testCreateBoardWithEmptyTileActions() {
    Board board = boardFactory.createBoard(10, Collections.emptyMap());

    assertNotNull(board, "Board should not be null");
    assertEquals(10, board.getTiles().size(), "Board should contain 10 tiles");
    assertTrue(board.getTiles().values().stream().allMatch(tile -> tile.getLandAction() == null),
        "No tile should have a land action assigned");
  }
}
