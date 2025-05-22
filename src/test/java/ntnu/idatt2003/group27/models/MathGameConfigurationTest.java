package ntnu.idatt2003.group27.models;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Map;
import ntnu.idatt2003.group27.models.actions.EasyMathQuestion;
import ntnu.idatt2003.group27.models.actions.HardMathQuestion;
import ntnu.idatt2003.group27.models.actions.MediumMathQuestion;
import ntnu.idatt2003.group27.models.enums.MathGameType;
import ntnu.idatt2003.group27.models.interfaces.TileAction;
import org.apache.commons.lang3.ObjectUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Unit test for the {@link MathGameConfiguration} class.
 *
 * <p>This test class verifies the functionality of the MathGameConfiguration class, including
 * the initialization of the game type and the retrieval of game settings.</p>
 */
class MathGameConfigurationTest {
  private MathGameConfiguration config;

  /**
   * Setup method to initialize the MathGameConfiguration object before each test.
   */
  @BeforeEach
  void setUp() {
    config = new MathGameConfiguration(MathGameType.EASY);
  }

  /**
   * Verifies that the MathGameConfiguration is initialized with the correct game type.
   */
  @Test
  @DisplayName("test that the config is initialized with the correct game type")
  void testInitializeMathGameConfiguration() {
    assertEquals(MathGameType.EASY, config.getGameType(),
        "MathGameConfiguration should be initialized");
  }

  /**
   * Verifies that the number of dice is returned correctly based on the game type.
   */
  @Test
  @DisplayName("test that the config returns the correct number of dice")
  void getNumberOfDice() {
    assertEquals(0, config.getNumberOfDice(),
        "Number of dice should be 0 for math game type");
  }

  /**
   * Verifies that the number of die sides is returned correctly based on the game type.
   */
  @Test
  @DisplayName("test that the config returns the correct number of die sides")
  void getNumberOfDieSides() {
    assertEquals(0, config.getNumberOfDieSides(),
        "Number of die sides should be 0 for math game type");
  }

  /**
   * Verifies that the config return the correct amount of tiles based on the game type.
   */
  @Test
  @DisplayName("test that the config returns the correct amount of tiles")
  void getTotalTiles() {
    assertEquals(5, config.getTotalTiles(),
        "Total tiles should be 5 for math game type");
  }

  /**
   * Verifies that getTileActions returns correct action for EASY game type.
   */
  @Test
  @DisplayName("test tile actions for EASY game type")
  void getTileActionsEasy() {
    Map<Integer, TileAction> tileActions = config.getTileActions();

    assertEquals(5, tileActions.size(),
        "Tile actions should contain 5 entries for EASY game type");
    assertNull(tileActions.get(1), "Tile 1 should have no action");
    assertInstanceOf(EasyMathQuestion.class, tileActions.get(2),
        "Tile 2 should have an EasyMathQuestion action");
    assertInstanceOf(EasyMathQuestion.class, tileActions.get(3),
        "Tile 3 should have an EasyMathQuestion action");
    assertInstanceOf(EasyMathQuestion.class, tileActions.get(4),
        "Tile 4 should have an EasyMathQuestion action");
    assertInstanceOf(EasyMathQuestion.class, tileActions.get(5),
        "Tile 5 should have an EasyMathQuestion action");
  }

  /**
   * Verifies that getTileActions returns correct action for MEDIUM game type.
   */
  @Test
  @DisplayName("test tile actions for MEDIUM game type")
  void getTileActionsMedium() {
    config = new MathGameConfiguration(MathGameType.MEDIUM);

    Map<Integer, TileAction> tileActions = config.getTileActions();

    assertEquals(5, tileActions.size(),
        "Tile actions should contain 5 entries for MEDIUM game type");
    assertNull(tileActions.get(1), "Tile 1 should have no action");
    assertInstanceOf(MediumMathQuestion.class, tileActions.get(2),
        "Tile 2 should have an MediumMathQuestion action");
    assertInstanceOf(MediumMathQuestion.class, tileActions.get(3),
        "Tile 3 should have an MediumMathQuestion action");
    assertInstanceOf(MediumMathQuestion.class, tileActions.get(4),
        "Tile 4 should have an MediumMathQuestion action");
    assertInstanceOf(MediumMathQuestion.class, tileActions.get(5),
        "Tile 5 should have an MediumMathQuestion action");
  }

  /**
   * Verifies that getTileActions returns correct action for HARD game type.
   */
  @Test
  @DisplayName("test tile actions for HARD game type")
  void getTileActionsHard() {
    config = new MathGameConfiguration(MathGameType.HARD);

    Map<Integer, TileAction> tileActions = config.getTileActions();

    assertEquals(5, tileActions.size(),
        "Tile actions should contain 5 entries for HARD game type");
    assertNull(tileActions.get(1), "Tile 1 should have no action");
    assertInstanceOf(HardMathQuestion.class, tileActions.get(2),
        "Tile 2 should have an HardMathQuestion action");
    assertInstanceOf(HardMathQuestion.class, tileActions.get(3),
        "Tile 3 should have an HardMathQuestion action");
    assertInstanceOf(HardMathQuestion.class, tileActions.get(4),
        "Tile 4 should have an HardMathQuestion action");
    assertInstanceOf(HardMathQuestion.class, tileActions.get(5),
        "Tile 5 should have an HardMathQuestion action");
  }
}