package ntnu.idatt2003.group27.models.interfaces;

import ntnu.idatt2003.group27.models.BoardGame;
import ntnu.idatt2003.group27.models.Player;

/**
 * Defines a contract for observing significant events in a {@link BoardGame}. Implementations of
 * this interface receive notifications when a player moves or wins the game.
 *
 * @author Iver Lindholm
 * @since 1.0
 * @version 1.0
 */
public interface BoardGameObserver {
  /**
   * Invoked when a player changes position on the board.
   *
   * @param player The {@link Player} who has moved.
   */
  void onPlayerMoved(Player player);

  /**
   * Invoked when a player has won the game.
   *
   * @param player The {@link Player} who has won.
   */
  void onPlayerWon(Player player);
}
