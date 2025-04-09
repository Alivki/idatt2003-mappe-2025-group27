package ntnu.idatt2003.group27.models.interfaces;

import java.util.ArrayList;
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
   * Invoked when a round has been played with a list of players.
   *
   * @param players List of all {@link Player} in the game.
   * @param currentPlayer The {@link Player} next player to play.
   * @param roll The result of the dice roll for the round.
   */
  void onRoundPlayed(ArrayList<Player> players, Player currentPlayer, int roll);

  /**
   * Invoked when a player has won the game.
   *
   * @param player The {@link Player} who has won.
   */
  void onPlayerWon(Player player);
}
