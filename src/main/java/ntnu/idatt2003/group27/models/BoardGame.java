package ntnu.idatt2003.group27.models;

import java.util.ArrayList;
import java.util.List;

/**
 * A class representing a board game.
 */
public class BoardGame {
  private List<BoardGameObserver> observers = new ArrayList<>();
  private Board board;
  private Player currentPlayer;
  private final ArrayList<Player> players = new ArrayList<>();
  private Dice dice;

  /**
   * Constructor for the BoardGame class.
   *
   * @param numberOfTiles The number of tiles on the board.
   * @param numberOfDice  The number of dice to use in the game.
   * @param numberOfSides The number of sides on each dice.
   */
  public BoardGame(int numberOfTiles, int numberOfDice, int numberOfSides) {
    createBoard(numberOfTiles);
    createDice(numberOfDice, numberOfSides);
  }

  /**
   * Alternative constructor for the BoardGame class. Uses a pre-initialized board as input.
   *
   * @param board         The board to use in the game.
   * @param numberOfDice  The number of dice to use in the game.
   * @param numberOfSides The number of sides on each dice.
   */
  public BoardGame(Board board, int numberOfDice, int numberOfSides) {
    this.board = board;
    createDice(numberOfDice, numberOfSides);
  }

  /**
   * Method to add an observer to the list. Class that wants to listen to the changes.
   *
   * @param observer The observer to add.
   */
  public void addObserver(BoardGameObserver observer) {
    observers.add(observer);
  }

  /**
   * Method to notifo observers that a player has moved.
   *
   * @param player The player that has moved.
   */
  public void notifyPlayerMoved(Player player) {
    observers.forEach(observer -> observer.onPlayerMoved(player));
  }

  /**
   * Method to notify observers that a player has won the game.
   *
   * @param player The player that has won.
   */
  public void notifyPlayerWon(Player player) {
    observers.forEach(observer -> observer.onPlayerWon(player));
  }

  /**
   * method to return the game board. No modifier to make it package-private for unit tests.
   *
   * @return The current game board.
   */
  Board getBoard() {
    return board;
  }

  /**
   * method to return the array of players in the game.
   *
   * @return ArrayList of the players in the game.
   */
  public ArrayList<Player> getPlayers() {
    return new ArrayList<>(players);
  }

  /**
   * method to return the current player of the game.
   *
   * @return Player
   */
  public Player getCurrentPlayer() {
    return currentPlayer;
  }

  /**
   * method to return the current game dice.
   *
   * @return Dice
   */
  public Dice getDice() {
    return dice;
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
  }

  /**
   * CreateBoard method to create a new game board with a specific number of tiles.
   */
  private void createBoard(int numberOfTiles) {
    this.board = new Board(numberOfTiles);
  }

  /**
   * Create dice method to create a new set of dice.
   * With specific number of dice and sides on each dice.
   */
  private void createDice(int numberOfDice, int numberOfSides) {
    dice = new Dice(numberOfDice, numberOfSides);
  }

  /**
   * Method to set up the game and make it ready to play.
   * Removes initialization out from game loop method.
   */
  public void setUpGame() {
    if (players.isEmpty()) {
      throw new IllegalArgumentException("No players on the game");
    }

    players.forEach(player -> player.placeOnTile(board.getTile(0)));

    currentPlayer = players.getFirst();
  }

  /**
   * Method to start the game.
   */
  public void play() {
    if (players.size() < 2) {
      throw new IllegalArgumentException("Must be two players to start the game");
    }

    while (true) {
      int roll = dice.roll();

      System.out.println(currentPlayer.getName() + " rolled a " + roll);

      currentPlayer.move(roll);
      System.out.println(
          currentPlayer.getName() + "moved to tile "
              + (currentPlayer.getCurrentTile().getTileId() + 1) + "\n");

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
