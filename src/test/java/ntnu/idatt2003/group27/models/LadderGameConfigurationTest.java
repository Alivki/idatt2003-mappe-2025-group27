package ntnu.idatt2003.group27.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.util.Map;
import ntnu.idatt2003.group27.models.enums.LadderGameType;
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
public class LadderGameConfigurationTest {

  /** Instance of the JsonLadderGameConfiguration to be tested. */
  private LadderGameConfiguration config;

  /**
   * Sets up the test environment by initializing the JsonLadderGameConfiguration with a valid JSON
   * path.
   */
  @BeforeEach
  public void setUp() {
    config = new LadderGameConfiguration(LadderGameType.NORMAL);
  }

  /**
   * Verifies that the number of initialized dice matches the expected output.
   */
  @Test
  @DisplayName("should return correct number of dice")
  public void testGetNumberOfDice() {
    assertEquals(2, config.getNumberOfDice(), "Expected 1 die");
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
}
