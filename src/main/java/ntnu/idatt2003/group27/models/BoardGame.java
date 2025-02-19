package ntnu.idatt2003.group27.models;

import java.util.ArrayList;

/**
 * A class representing a board game.
 */
public class BoardGame {
  private Board board;
  private Player currentPlayer;
  private ArrayList<Player> players = new ArrayList<Player>();
  private Dice dice;

  /**
   * Add player method to add a player to the game.
   *
   * @param player The player to add.
   */
  public void addPlayer(Player player) {
    if (players.contains(player)) {
      throw new IllegalArgumentException("Player already exists");
    }

    players.add(player);

    if (player == null) {
      currentPlayer = player;
    }
  }

  /**
   * Create board method to create a new board.
   */
  public void createBoard() {
    this.board = new Board();
  }

  /**
   * Create dice method to create a new set of dice.
   */
  public void createDice() {
    dice = new Dice(1, 6);
  }

  /**
   * Method to start the game.
   */
  public void play() {
    if (currentPlayer == null) {
      throw new IllegalStateException("No players in the game");
    }

    System.out.println("Game has started!");

    while (true) {
      int roll = dice.roll();
      System.out.println(currentPlayer.getName() + " rolled a" + roll);
      // needs to call the player class to move the player. logic has to be implemented in the player class

      if (getWinner() != null) {
        System.out.println(getWinner().getName() + " has won the game!");
        break;
      }

      currentPlayer = players.get((players.indexOf(currentPlayer) + 1) % players.size());
    }

  }

  /**
   * Method to get the winner of the game.
   *
   * @return Player The winner of the game.
   */
  public Player getWinner() {
    for (Player player : players) {
      if (player.getCurrentTile().getTileId() == 90) {
        return player;
      }
    }

    return null;
  }
}
