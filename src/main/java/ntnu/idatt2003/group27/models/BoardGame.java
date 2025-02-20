package ntnu.idatt2003.group27.models;

import java.util.ArrayList;

/**
 * A class representing a board game.
 */
public class BoardGame {
  private Board board;
  private Player currentPlayer;
  private final ArrayList<Player> players = new ArrayList<Player>();
  private Dice dice;

  /**
   * Constructor for the BoardGame class.
   */
  public BoardGame() {
    createBoard();
    createDice();
  }

  /**
   * getBoard method to get the board of the game.
   *
   * @return The current game board.
   */
  public Board getBoard() {
    return board;
  }

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

    if (currentPlayer == null) {
      currentPlayer = player;
    }
  }

  /**
   * Create board method to create a new board.
   */
  public void createBoard() {
    this.board = new Board(90);
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
    int round = 0;

    if (currentPlayer == null) {
      throw new IllegalStateException("No players in the game");
    }

    players.forEach(player -> player.placeOnTile(board.getTile(0)));

    while (true) {
      round++;
      int roll = dice.roll();

      System.out.println(currentPlayer.getName() + " rolled a " + roll);

      if (currentPlayer.currentTile.tileId + roll > board.getTiles().size() - 1) {
        int move = -1 * (roll - ((board.getTiles().size() - 1) - currentPlayer.currentTile.tileId));
        currentPlayer.move(move);
        System.out.println(currentPlayer.getName() + "rolled to much and moved back \n");
      } else {
        currentPlayer.move(roll);
        System.out.println(
            currentPlayer.getName() + "moved to tile " + (currentPlayer.getCurrentTile().getTileId() + 1) + "\n");
      }

      if (getWinner() != null) {
        System.out.println("\n" + getWinner().getName() + " has won the game!");
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
      if (player.getCurrentTile().getTileId() == board.getTiles().size() - 1) {
        return player;
      }
    }

    return null;
  }
}
