package ntnu.idatt2003.group27.models;

import java.util.logging.Logger;
import ntnu.idatt2003.group27.models.actions.EasyMathQuestion;
import ntnu.idatt2003.group27.models.actions.HardMathQuestion;
import ntnu.idatt2003.group27.models.actions.MediumMathQuestion;
import ntnu.idatt2003.group27.models.enums.LadderGameType;
import ntnu.idatt2003.group27.models.enums.MathGameType;
import ntnu.idatt2003.group27.models.interfaces.GameConfiguration;
import ntnu.idatt2003.group27.models.interfaces.TileAction;

import java.util.HashMap;
import java.util.Map;

/**
 * A configuration class for ladder games, providing game settings based on the specified
 * {@link LadderGameType}. Implements the {@link GameConfiguration} interface to define the number
 * of dice, die sides, total tiles, and tile actions for the game.
 *
 * @author Iver Lindholm
 * @version 1.0
 * @since 2.0
 */
public class MathGameConfiguration implements GameConfiguration {
  /**
   * Logger instance for the {@link MathGameConfiguration} class.
   * Used for logging informational messages and errors related to class operations.
   */
  private static final Logger logger = Logger.getLogger(MathGameConfiguration.class.getName());

  /** The type of ladder game to configure */
  private final MathGameType gameType;

  /**
   * Constructs a {@code LadderGameConfiguration} for the specified ladder game type.
   *
   * @param gameType The {@link LadderGameType} defining the game difficulty or style.
   */
  public MathGameConfiguration(MathGameType gameType) {
    logger.fine("Initializing MathGameConfiguration of MathGameType: " + gameType);
    this.gameType = gameType;
  }

  /**
   * Returns the type of ladder game for this configuration.
   * Package-private for testing purposes.
   *
   * @return MathGameType The type of ladder game.
   */
  MathGameType getGameType() {
    return gameType;
  }

  @Override
  public int getNumberOfDice() {
    logger.fine("Getting number of dice.");
    return 0;
  }

  @Override
  public int getNumberOfDieSides() {
    logger.fine("Getting number of die sides.");
    return 0;
  }

  @Override
  public int getTotalTiles() {
    logger.fine("Getting total tiles.");
    return 5;
  }

  @Override
  public Map<Integer, TileAction> getTileActions() {
    logger.fine("Getting tile actions.");
    Map<Integer, TileAction> tileActions = new HashMap<>();
    switch (gameType) {
      case EASY:
        tileActions.put(1, null);
        tileActions.put(2, new EasyMathQuestion());
        tileActions.put(3, new EasyMathQuestion());
        tileActions.put(4, new EasyMathQuestion());
        tileActions.put(5, new EasyMathQuestion());
        break;
      case MEDIUM:
        tileActions.put(1, null);
        tileActions.put(2, new MediumMathQuestion());
        tileActions.put(3, new MediumMathQuestion());
        tileActions.put(4, new MediumMathQuestion());
        tileActions.put(5, new MediumMathQuestion());
        break;
      case HARD:
        tileActions.put(1, null);
        tileActions.put(2, new HardMathQuestion());
        tileActions.put(3, new HardMathQuestion());
        tileActions.put(4, new HardMathQuestion());
        tileActions.put(5, new HardMathQuestion());
        break;
      default:
        throw new IllegalArgumentException("Unknown ladder game type: " + gameType);
    }
    return tileActions;
  }
}
