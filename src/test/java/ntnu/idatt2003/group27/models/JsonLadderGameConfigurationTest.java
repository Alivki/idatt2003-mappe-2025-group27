package ntnu.idatt2003.group27.models;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.Map;

import ntnu.idatt2003.group27.models.interfaces.TileAction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for the {@link JsonLadderGameConfiguration} class.
 *
 * <p>Verifies config initialization.</p>
 *
 * @author Amadeus Berg
 */
public class JsonLadderGameConfigurationTest {

  /** Path to a valid JSON file. */
  private static final String VALID_JSON_PATH = "src/main/resources/boards/Board.json";

  /** Path to a non-existing or invalid JSON file. */
  private static final String INVALID_JSON_PATH = "invalid/path/to/Board.json";

  /** Instance of the JsonLadderGameConfiguration to be tested. */
  private JsonLadderGameConfiguration config;

  /**
   * Sets up the test environment by initializing the JsonLadderGameConfiguration with a valid JSON
   * path.
   *
   * @throws IOException if the JSON file cannot be read or is invalid.
   */
  @BeforeEach
  public void setUp() throws IOException {
    config = new JsonLadderGameConfiguration(VALID_JSON_PATH);
  }

  /**
   * Verifies that the number of initialized dice matches the expected output.
   */
  @Test
  @DisplayName("should return correct number of dice")
  public void testGetNumberOfDice() {
    assertEquals(1, config.getNumberOfDice(), "Expected 1 die");
  }

  /**
   * Verifies that the number of initialized die sides matches the expected output.
   */
  @Test
  @DisplayName("should return correct number of die sides")
  public void testGetNumberOfDieSides() {
    assertEquals(6, config.getNumberOfDieSides(), "Expected 6 sides per die");
  }

  /**
   * Verifies that the number of initialized tiles matches the expected output.
   */
  @Test
  @DisplayName("should return total number of tiles from board")
  public void testGetTotalTiles() {
    int totalTiles = config.getTotalTiles();
    assertEquals(totalTiles, config.getTotalTiles(), "Total tiles should match board size");
  }

  /**
   * Verifies that the number of initialized tile actions of the config is not null.
   */
  @Test
  @DisplayName("should return non-null tile actions map")
  public void testGetTileActions() {
    Map<Integer, TileAction> tileActions = config.getTileActions();
    assertNotNull(tileActions, "Tile actions map should not be null");
  }

  /**
   * Verifies that creating a config with an invalid json path throws an IOException.
   */
  @Test
  @DisplayName("should throw IOException for invalid JSON path")
  public void testInvalidJsonPathThrowsException() {
    assertThrows(IOException.class, () -> new JsonLadderGameConfiguration(INVALID_JSON_PATH));
  }

  /**
   * Verifies that the board of the config is initialized and is not null.
   */
  @Test
  @DisplayName("should return initialized board")
  public void testGetBoard() {
    assertNotNull(config.getBoard(), "Board should be initialized and not null");
  }
}
