package ntnu.idatt2003.group27.models;

import java.util.HashMap;
import java.util.Map;
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
    return 1;
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
        tileActions.put(4, new LadderAction(15, "description"));
        tileActions.put(10, new BackToStartAction("description"));
        tileActions.put(69, new LadderAction(47, "description"));
        tileActions.put(58, new ThrowNewDiceAction("description", 1, 6));
        tileActions.put(18, new LadderAction(43, "description"));
        tileActions.put(65, new LadderAction(88, "description"));
        tileActions.put(63, new LadderAction(45, "description"));
        tileActions.put(32, new LadderAction(12, "description"));
        break;
      case CRAZY:
        tileActions.put(4, new LadderAction(15, "description"));
        tileActions.put(10, new BackToStartAction("description"));
        break;
      case IMPOSSIBLE:
        tileActions.put(4, new LadderAction(15, "description"));
        tileActions.put(10, new BackToStartAction("description"));
        break;
      default:
        throw new IllegalArgumentException("Unknown ladder game type: " + gameType);
    }
    return tileActions;
  }
}
