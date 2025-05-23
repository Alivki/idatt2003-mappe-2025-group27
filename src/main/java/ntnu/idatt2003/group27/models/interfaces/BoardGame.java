package ntnu.idatt2003.group27.models.interfaces;

import ntnu.idatt2003.group27.models.Board;
import ntnu.idatt2003.group27.models.Player;
import ntnu.idatt2003.group27.models.exceptions.NotEnoughPlayersInGameException;

import java.util.List;
import java.util.Map;

/**
 * Defines a contract for a board game. This interface provides methods to set up the game,
 * play the game, restart the game, and manage players and observers.
 *
 * @author Iver Lindholm, Amadeus Berg
 * @since 1.0
 * @version 2.0
 */
public interface BoardGame {

  /**
   * Sets up the game with the specified players. This method should be called before starting the game.
   *
   * @throws NotEnoughPlayersInGameException if there are not enough players to start the game.
   */
  void setUpGame() throws NotEnoughPlayersInGameException;

  /**
   * Plays a round of the game. This method should be called to play a single round.
   *
   * @throws NotEnoughPlayersInGameException if there are not enough players to play the game.
   */
  void play() throws NotEnoughPlayersInGameException;

  /**
   * Restarts the game, resetting all players and boards to their initial state.
   */
  void restartGame();

  /**
   * Adds an observer to the game. Observers will be notified of significant events in the game.
   *
   * @param observer The observer to add.
   */
  void addObserver(BoardGameObserver observer);

  /**
   * Method to retrieve the current player.
   *
   * @return The {@link Player} who is currently playing.
   */
  Player getCurrentPlayer();

  /**
   * Method to retrieve the list of players in the game.
   *
   * @return A list of {@link Player} objects representing all players in the game.
   */
  List<Player> getPlayers();

  /**
   * Method to retrieve the winner of the game.
   *
   * @return The {@link Player} who has won the game.
   */
  Player getWinner();

  /**
   * Method to retrieve the boards of all players in the game.
   *
   * @return A map where the key is a {@link Player} and the value is their corresponding {@link Board}.
   */
  Map<Player, Board> getBoards();
}
