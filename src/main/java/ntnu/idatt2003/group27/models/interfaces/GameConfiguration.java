package ntnu.idatt2003.group27.models.interfaces;

import java.util.Map;

/**
 * An interface defining the configuration settings for a board game. Implementations provide
 * details about the number of dice, die sides, total tiles, and tile actions required to configure
 * a game.
 *
 * @author Iver Lindholm
 * @version 1.0
 * @since 2.0
 */
public interface GameConfiguration {
  /**
   * Retrieves the number of dice used in the game.
   *
   * @return the number of dice
   */
  int getNumberOfDice();

  /**
   * Retrieves the number of sides on each die used in the game.
   *
   * @return the number of sides on each die
   */
  int getNumberOfDieSides();

  /**
   * Retrieves the total number of tiles on the board.
   *
   * @return the total number of tiles
   */
  int getTotalTiles();

  /**
   * Retrieves a map of tile actions for the game board, where keys are tile IDs and values are
   * {@link LadderTileAction} objects.
   *
   * @return A map of tile IDs to their corresponding {@link LadderTileAction} objects.
   */
  Map<Integer, TileAction> getTileActions();
}
