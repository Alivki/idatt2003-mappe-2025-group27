package ntnu.idatt2003.group27.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.util.Map;
import ntnu.idatt2003.group27.models.enums.LadderGameType;
import ntnu.idatt2003.group27.models.interfaces.TileAction;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for the {@link LadderGameConfiguration} class.
 *
 * <p>Verifies initialization of all non-json {@link LadderGameConfiguration}s</p>
 *
 * @author Amadeus Berg
 */
public class LadderGameConfigurationTest {

  /** Instance of the NORMAL LadderGameConfiguration to be tested. */
  private static LadderGameConfiguration normalConfig;
  
  /** Instance of the CRAZY LadderGameConfiguration to be tested. */
  private static LadderGameConfiguration crazyConfig;

  /** Instance of the IMPOSSIBLE LadderGameConfiguration to be tested. */
  private static LadderGameConfiguration impossibleConfig;

  /**
   * Sets up the test environment by initializing the LadderGameConfiguration with a valid JSON
   * path.
   */
  @BeforeAll
  public static void setUp() {
    normalConfig = new LadderGameConfiguration(LadderGameType.NORMAL);
    crazyConfig = new LadderGameConfiguration(LadderGameType.CRAZY);
    impossibleConfig = new LadderGameConfiguration(LadderGameType.IMPOSSIBLE);
  }


  /// Tests for NORMAL board configuration

  /**
   * Verifies that the number of initialized dice matches the expected output.
   */
  @Test
  @DisplayName("NORMAL CONFIG: should return correct number of dice")
  public void testNormalGetNumberOfDice() {
    assertEquals(2, normalConfig.getNumberOfDice(), "Expected 1 die");
  }

  /**
   * Verifies that the number of initialized die sides matches the expected output.
   */
  @Test
  @DisplayName("NORMAL CONFIG: should return correct number of die sides")
  public void testNormalGetNumberOfDieSides() {
    assertEquals(6, normalConfig.getNumberOfDieSides(), "Expected 6 sides per die");
  }

  /**
   * Verifies that the number of initialized tiles matches the expected output.
   */
  @Test
  @DisplayName("NORMAL CONFIG: should return total number of tiles from board")
  public void testNormalGetTotalTiles() {
    assertEquals(90, normalConfig.getTotalTiles(), "Total tiles should match board size");
  }

  /**
   * Verifies that the number of initialized tile actions of the normalConfig is not null.
   */
  @Test
  @DisplayName("NORMAL CONFIG: should return non-null tile actions map")
  public void testNormalGetTileActions() {
    assertNotNull(normalConfig.getTileActions(), "Tile actions map should not be null");
  }

  /// Tests for CRAZY board configuration

  /**
   * Verifies that the number of initialized dice matches the expected output.
   */
  @Test
  @DisplayName("CRAZY CONFIG: should return correct number of dice")
  public void testCrazyGetNumberOfDice() {
    assertEquals(2, crazyConfig.getNumberOfDice(), "Expected 1 die");
  }

  /**
   * Verifies that the number of initialized die sides matches the expected output.
   */
  @Test
  @DisplayName("CRAZY CONFIG: should return correct number of die sides")
  public void testCrazyGetNumberOfDieSides() {
    assertEquals(6, crazyConfig.getNumberOfDieSides(), "Expected 6 sides per die");
  }

  /**
   * Verifies that the number of initialized tiles matches the expected output.
   */
  @Test
  @DisplayName("CRAZY CONFIG: should return total number of tiles from board")
  public void testCrazyGetTotalTiles() {
    assertEquals(90, crazyConfig.getTotalTiles(), "Total tiles should match board size");
  }

  /**
   * Verifies that the number of initialized tile actions of the crazyConfig is not null.
   */
  @Test
  @DisplayName("CRAZY CONFIG: should return non-null tile actions map")
  public void testCrazyGetTileActions() {
    Map<Integer, TileAction> tileActions = crazyConfig.getTileActions();
    assertNotNull(tileActions, "Tile actions map should not be null");
  }

  /// Tests for IMPOSSIBLE board configuration

  /**
   * Verifies that the number of initialized dice matches the expected output.
   */
  @Test
  @DisplayName("IMPOSSIBLE CONFIG: should return correct number of dice")
  public void testImpossibleGetNumberOfDice() {
    assertEquals(2, impossibleConfig.getNumberOfDice(), "Expected 1 die");
  }

  /**
   * Verifies that the number of initialized die sides matches the expected output.
   */
  @Test
  @DisplayName("IMPOSSIBLE CONFIG: should return correct number of die sides")
  public void testImpossibleGetNumberOfDieSides() {
    assertEquals(6, impossibleConfig.getNumberOfDieSides(), "Expected 6 sides per die");
  }

  /**
   * Verifies that the number of initialized tiles matches the expected output.
   */
  @Test
  @DisplayName("IMPOSSIBLE CONFIG: should return total number of tiles from board")
  public void testImpossibleGetTotalTiles() {
    assertEquals(90, impossibleConfig.getTotalTiles(), "Total tiles should match board size");
  }

  /**
   * Verifies that the number of initialized tile actions of the impossibleConfig is not null.
   */
  @Test
  @DisplayName("IMPOSSIBLE CONFIG: should return non-null tile actions map")
  public void testImpossibleGetTileActions() {
    assertNotNull(impossibleConfig.getTileActions(), "Tile actions map should not be null");
  }

}
