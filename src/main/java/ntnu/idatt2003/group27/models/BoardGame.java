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

  public void addPlayer(Player player) {
    if (players.contains(player)) {
      throw new IllegalArgumentException("Player already exists");
    }

    players.add(player);

    if (player == null) {
      currentPlayer = player;
    }
  }

  public void createBoard() {
    board = new Board();
  }

  public void createDice() {
    dice = new Dice(1, 6);
  }

  public void play() {
    if (currentPlayer == null) {
      throw new IllegalStateException("No players in the game");
    }

    int roll = dice.roll();
    System.out.println(currentPlayer.getName() + " rolled a" + roll);
    currentPlayer = players.get((players.indexOf(currentPlayer) + 1) % players.size());
  }

  public Player getWinner() {
    throw new UnsupportedOperationException("Not implemented yet.");
  }
}
