package ntnu.idatt2003.group27.models;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;
import ntnu.idatt2003.group27.models.actions.BackToStartAction;
import ntnu.idatt2003.group27.models.actions.LadderAction;
import ntnu.idatt2003.group27.models.actions.ThrowNewDiceAction;
import ntnu.idatt2003.group27.models.enums.LadderGameType;
import ntnu.idatt2003.group27.models.interfaces.GameConfiguration;
import ntnu.idatt2003.group27.models.interfaces.TileAction;

/**
 * A configuration class for ladder games, providing game settings based on the specified
 * {@link LadderGameType}. Implements the {@link GameConfiguration} interface to define the number
 * of dice, die sides, total tiles, and tile actions for the game.
 *
 * @author Iver Lindholm
 * @version 1.0
 * @since 2.0
 */
public class LadderGameConfiguration implements GameConfiguration {
  /**
   * Logger instance for the {@code Board} class.
   * Used for logging informational messages and errors related to class operations.
   */
  private static final Logger logger = Logger.getLogger(LadderGameConfiguration.class.getName());
  /** The type of ladder game to configure */
  private final LadderGameType gameType;

  /**
   * Constructs a {@code LadderGameConfiguration} for the specified ladder game type.
   *
   * @param gameType The {@link LadderGameType} defining the game difficulty or style.
   */
  public LadderGameConfiguration(LadderGameType gameType) {
    this.gameType = gameType;
  }

  @Override
  public int getNumberOfDice() {
    return 2;
  }

  @Override
  public int getNumberOfDieSides() {
    return 6;
  }

  @Override
  public int getTotalTiles() {
    return 90;
  }

  @Override
  public Map<Integer, TileAction> getTileActions() {
    Map<Integer, TileAction> tileActions = new HashMap<>();
    switch (gameType) {
      case NORMAL:
        tileActions.put(4, new LadderAction(15, "A ladder that takes you from tile 4 to 15"));
        tileActions.put(18, new LadderAction(43, "A ladder that takes you from tile 18 to 43"));
        tileActions.put(32, new LadderAction(12, "A ladder that drops you from tile 32 to 12"));
        tileActions.put(63, new LadderAction(45, "A ladder that drops you from tile 63 to 45"));
        tileActions.put(65, new LadderAction(88, "A ladder that takes you from tile 65 to 88"));
        tileActions.put(69, new LadderAction(47, "A ladder that drops you from tile 69 to 47"));
        break;
      case CRAZY:
        tileActions.put(4, new LadderAction(15, "A ladder that takes you from tile 4 to 15"));
        tileActions.put(10, new BackToStartAction("You landed on tile 10 and are sent back to the start"));
        tileActions.put(11, new ThrowNewDiceAction("You get to throw another dice this turn!", 1, 6));
        tileActions.put(18, new LadderAction(43, "A ladder that takes you from tile 18 to 43"));
        tileActions.put(32, new LadderAction(12, "A ladder that drops you from tile 32 to 12"));
        tileActions.put(58, new ThrowNewDiceAction("You get to throw another dice this turn!", 1, 6));
        tileActions.put(63, new LadderAction(45, "A ladder that drops you from tile 63 to 45"));
        tileActions.put(65, new LadderAction(88, "A ladder that takes you from tile 65 to 88"));
        tileActions.put(69, new LadderAction(47, "A ladder that drops you from tile 69 to 47"));
        tileActions.put(73, new ThrowNewDiceAction("You get to throw another dice this turn!", 1, 6));
        break;
      case IMPOSSIBLE:
        tileActions.put(2, new BackToStartAction("You landed on tile 2 and are sent back to the start"));
        tileActions.put(5, new LadderAction(24, "A ladder that drops you from tile 5 to 24"));
        tileActions.put(8, new BackToStartAction("You landed on tile 8 and are sent back to the start"));
        tileActions.put(10, new BackToStartAction("You landed on tile 10 and are sent back to the start"));
        tileActions.put(21, new BackToStartAction("You landed on tile 21 and are sent back to the start"));
        tileActions.put(32, new LadderAction(12, "A ladder that drops you from tile 32 to 12"));
        tileActions.put(34, new BackToStartAction("You landed on tile 34 and are sent back to the start"));
        tileActions.put(45, new BackToStartAction("You landed on tile 45 and are sent back to the start"));
        tileActions.put(57, new BackToStartAction("You landed on tile 57 and are sent back to the start"));
        tileActions.put(63, new LadderAction(20, "A ladder that drops you from tile 63 to 20"));
        tileActions.put(69, new LadderAction(33, "A ladder that drops you from tile 69 to 33"));
        tileActions.put(87, new LadderAction(65, "A ladder that drops you from tile 87 to 65"));
        tileActions.put(89, new BackToStartAction("You landed on tile 89 and are sent back to the start"));

        break;
      default:
        throw new IllegalArgumentException("Unknown ladder game type: " + gameType);
    }
    return tileActions;
  }
}
