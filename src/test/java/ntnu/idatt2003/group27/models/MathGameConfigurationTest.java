package ntnu.idatt2003.group27.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Map;
import ntnu.idatt2003.group27.models.enums.LadderGameType;
import ntnu.idatt2003.group27.models.enums.MathGameType;
import ntnu.idatt2003.group27.models.interfaces.TileAction;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for the {@link MathGameConfiguration} class.
 *
 * <p>Verifies config initialization for all {@link MathGameConfiguration}s.</p>
 *
 * @author Amadeus Berg
 */
public class MathGameConfigurationTest {

  /** Instance of the EASY MathGameConfiguration to be tested. */
  private static MathGameConfiguration easyConfig;

  /** Instance of the MEDIUM MathGameConfiguration to be tested. */
  private static MathGameConfiguration mediumConfig;

  /** Instance of the HARD MathGameConfiguration to be tested. */
  private static MathGameConfiguration hardConfig;

  /**
   * Sets up the test environment by initializing the MathGameConfiguration with a valid JSON
   * path.
   */
  @BeforeAll
  public static void setUp() {
    easyConfig = new MathGameConfiguration(MathGameType.EASY);
    mediumConfig = new MathGameConfiguration(MathGameType.MEDIUM);
    hardConfig = new MathGameConfiguration(MathGameType.HARD);
  }

  /// Tests for EASY board configuration

  /**
   * Verifies that the number of initialized dice matches the expected output.
   */
  @Test
  @DisplayName("EASY CONFIG: should return correct number of dice")
  public void testEasyGetNumberOfDice() {
    assertEquals(0, easyConfig.getNumberOfDice(), "Expected 1 die");
  }

  /**
   * Verifies that the number of initialized die sides matches the expected output.
   */
  @Test
  @DisplayName("EASY CONFIG: should return correct number of die sides")
  public void testEasyGetNumberOfDieSides() {
    assertEquals(0, easyConfig.getNumberOfDieSides(), "Expected 0 sides per die");
  }

  /**
   * Verifies that the number of initialized tiles matches the expected output.
   */
  @Test
  @DisplayName("EASY CONFIG: should return total number of tiles from board")
  public void testEasyGetTotalTiles() {
    assertEquals(5, easyConfig.getTotalTiles(), "Total tiles should match board size");
  }

  /**
   * Verifies that the number of initialized tile actions of the easyConfig is not null.
   */
  @Test
  @DisplayName("EASY CONFIG: should return non-null tile actions map")
  public void testEasyGetTileActions() {
    Map<Integer, TileAction> tileActions = easyConfig.getTileActions();
    assertNotNull(tileActions, "Tile actions map should not be null");
  }

  /// Tests for MEDIUM board configuration

  /**
   * Verifies that the number of initialized dice matches the expected output.
   */
  @Test
  @DisplayName("MEDIUM CONFIG: should return correct number of dice")
  public void testMediumGetNumberOfDice() {
    assertEquals(0, mediumConfig.getNumberOfDice(), "Expected 1 die");
  }

  /**
   * Verifies that the number of initialized die sides matches the expected output.
   */
  @Test
  @DisplayName("MEDIUM CONFIG: should return correct number of die sides")
  public void testMediumGetNumberOfDieSides() {
    assertEquals(0, mediumConfig.getNumberOfDieSides(), "Expected 0 sides per die");
  }

  /**
   * Verifies that the number of initialized tiles matches the expected output.
   */
  @Test
  @DisplayName("MEDIUM CONFIG: should return total number of tiles from board")
  public void testMediumGetTotalTiles() {
    assertEquals(5, mediumConfig.getTotalTiles(), "Total tiles should match board size");
  }

  /**
   * Verifies that the number of initialized tile actions of the mediumConfig is not null.
   */
  @Test
  @DisplayName("MEDIUM CONFIG: should return non-null tile actions map")
  public void testMediumGetTileActions() {
    Map<Integer, TileAction> tileActions = mediumConfig.getTileActions();
    assertNotNull(tileActions, "Tile actions map should not be null");
  }

  /// Tests for HARD board configuration

  /**
   * Verifies that the number of initialized dice matches the expected output.
   */
  @Test
  @DisplayName("HARD CONFIG: should return correct number of dice")
  public void testHardGetNumberOfDice() {
    assertEquals(0, hardConfig.getNumberOfDice(), "Expected 1 die");
  }

  /**
   * Verifies that the number of initialized die sides matches the expected output.
   */
  @Test
  @DisplayName("HARD CONFIG: should return correct number of die sides")
  public void testHardGetNumberOfDieSides() {
    assertEquals(0, hardConfig.getNumberOfDieSides(), "Expected 0 sides per die");
  }

  /**
   * Verifies that the number of initialized tiles matches the expected output.
   */
  @Test
  @DisplayName("HARD CONFIG: should return total number of tiles from board")
  public void testHardGetTotalTiles() {
    assertEquals(5, hardConfig.getTotalTiles(), "Total tiles should match board size");
  }

  /**
   * Verifies that the number of initialized tile actions of the hardConfig is not null.
   */
  @Test
  @DisplayName("HARD CONFIG: should return non-null tile actions map")
  public void testHardGetTileActions() {
    Map<Integer, TileAction> tileActions = hardConfig.getTileActions();
    assertNotNull(tileActions, "Tile actions map should not be null");
  }
}
