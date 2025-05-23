package ntnu.idatt2003.group27.models.interfaces;

import java.util.ArrayList;
import java.util.Map;

import ntnu.idatt2003.group27.models.Board;
import ntnu.idatt2003.group27.models.LadderBoardGame;
import ntnu.idatt2003.group27.models.Player;


/**
 * Defines a contract for observing significant events in a {@link LadderBoardGame}. Implementations of
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
   * @param tileAction The action performed on the tile.
   */
  void onRoundPlayed(ArrayList<Player> players, Player currentPlayer, int roll, TileAction tileAction);

  /**
   * Invoked when a player has won the game.
   *
   * @param player The {@link Player} who has won.
   */
  void onPlayerWon(Player player);

  /**
   * Invoked when the game is set up and ready to start.
   *
   * @param players The list of players participating in the game.
   * @param boards The list of boards for each player.
   */
  void onGameSetup(ArrayList<Player> players, Map<Player, Board> boards);

  /**
   * Invoked when the game is restarted.
   * @param players The list of players participating in the game.
   * @param boards The list of boards for each player.
   */
  void onGameRestart(ArrayList<Player> players, Map<Player, Board> boards);
}
