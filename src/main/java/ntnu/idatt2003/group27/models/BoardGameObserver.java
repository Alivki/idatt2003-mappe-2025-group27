package ntnu.idatt2003.group27.models;

/**
 * An interface for observing the state of a board game.
 */
public interface BoardGameObserver {
  /**
   * Called when a player has moved.
   *
   * @param player The player that has moved.
   */
  void onPlayerMoved(Player player);

  /**
   * Called when a player has won the game.
   *
   * @param player The player that has won.
   */
  void onPlayerWon(Player player);
}
