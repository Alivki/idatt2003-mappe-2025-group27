package ntnu.idatt2003.group27.models;

import ntnu.idatt2003.group27.models.actions.EasyMathQuestion;
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
  /** The type of ladder game to configure */
  private final MathGameType gameType;

  /**
   * Constructs a {@code LadderGameConfiguration} for the specified ladder game type.
   *
   * @param gameType The {@link LadderGameType} defining the game difficulty or style.
   */
  public MathGameConfiguration(MathGameType gameType) {
    this.gameType = gameType;
  }

  @Override
  public int getNumberOfDice() {
    return 0;
  }

  @Override
  public int getNumberOfDieSides() {
    return 0;
  }

  @Override
  public int getTotalTiles() {
    return 4;
  }

  @Override
  public Map<Integer, TileAction> getTileActions() {
    Map<Integer, TileAction> tileActions = new HashMap<>();
    switch (gameType) {
      case EASY:
        tileActions.put(1, new EasyMathQuestion());
        tileActions.put(2, new EasyMathQuestion());
        tileActions.put(3, new EasyMathQuestion());
        tileActions.put(4, new EasyMathQuestion());
        break;
      case MEDIUM:
        tileActions.put(1, new EasyMathQuestion());
        tileActions.put(2, new EasyMathQuestion());
        tileActions.put(3, new EasyMathQuestion());
        tileActions.put(4, new EasyMathQuestion());
        break;
      case HARD:
        tileActions.put(1, new EasyMathQuestion());
        tileActions.put(2, new EasyMathQuestion());
        tileActions.put(3, new EasyMathQuestion());
        tileActions.put(4, new EasyMathQuestion());
        break;
      default:
        throw new IllegalArgumentException("Unknown ladder game type: " + gameType);
    }
    return tileActions;
  }
}
