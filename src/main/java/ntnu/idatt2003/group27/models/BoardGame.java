package ntnu.idatt2003.group27.models;

import java.util.Map;
import ntnu.idatt2003.group27.models.exceptions.NotEnoughPlayersInGameException;
import ntnu.idatt2003.group27.models.interfaces.BoardGameObserver;

import java.util.ArrayList;
import java.util.List;

/**
 * A class representing a board game with players, a board, and dice.
 * This class manages game state, player movement, and the game flow using an observer pattern
 * to notify listeners of significant events such as player movement or victory.
 *
 * @author Iver Lindholm, Amadeus Berg
 * @version 1.2
 * @since 0.0
 */
public class BoardGame {
  /**
   * List of observers monitoring game events .
   */
  private List<BoardGameObserver> observers = new ArrayList<>();

  /**
   * The game board storing Tiles.
   *
   * @see Board
   */
  private final Board board;

  /**
   * The current {@link Player} of that round.
   */
  private Player currentPlayer;

  /**
   * {@link ArrayList} storing all the {@link Player} objects in the game.
   */
  private final ArrayList<Player> players = new ArrayList<>();

  /**
   * Instace of {@link Dice} class to roll the dice.
   */
  private Dice dice;

  /**
   * Constructs a new board game with a pre-initialized board and specified dice configuration.
   *
   * @param board         The pre-initialized board to use in the game.
   * @param numberOfDice  The number of dice to use in the game.
   * @param numberOfSides The number of sides on each dice.
   */
  public BoardGame(Board board, int numberOfDice, int numberOfSides) {
    this.board = board;
    createDice(numberOfDice, numberOfSides);
  }

  /**
   * Adds an observer to receive game event notifications.
   *
   * @param observer The observer to register.
   */
  public void addObserver(BoardGameObserver observer) {
    observers.add(observer);
  }

  /**
   * Notifies all observers that a round has been played and provides the list of players to update
   * player positions on the board.
   *
   * @param players The list of players in the game.
   * @param currentPlayer The player whose turn it is after round played.
   * @param roll The result of the dice roll for the round.
   */
  public void notifyRoundPlayed(ArrayList<Player> players, Player currentPlayer, int roll) {
    observers.forEach(observer -> observer.onRoundPlayed(players, currentPlayer, roll));
  }

  /**
   * Notifies all observers that a player has won.
   *
   * @param player The player that has won.
   */
  public void notifyPlayerWon(Player player) {
    observers.forEach(observer -> observer.onPlayerWon(player));
  }

  /**
   * Notifies all observers that the game has been set up and is ready to start.
   *
   * @param players The list of players in the game.
   * @param tiles The map of tiles on the board.
   */
  public void notifyGameSetup(ArrayList<Player> players, Map<Integer, Tile> tiles) {
    observers.forEach(observer -> observer.onGameSetup(players, tiles));
  }

  /**
   * Retrieves the current game board. This method is package-private for testing purposes.
   *
   * @return The current game board.
   */
  public Board getBoard() {
    return board;
  }

  /**
   * Retrieves a copy of the list of players in the game.
   * The method is package-private for testing purposes.
   *
   * @return A new {@link ArrayList} containing all players in the game.
   */
  ArrayList<Player> getPlayers() {
    return new ArrayList<>(players);
  }

  /**
   * Retrieves the current player. The method is package-private for testing purposes.
   *
   * @return The player whose turn is currently active.
   */
  Player getCurrentPlayer() {
    return currentPlayer;
  }

  /**
   * Retrieves the dice used in the game. The method is package-private for testing purposes.
   *
   * @return The dice instance used in the game.
   */
  Dice getDice() {
    return dice;
  }

  /**
   * Adds players to the game.
   *
   * @param player The player to be added.
   * @throws IllegalArgumentException if the player already exists in the game.
   */
  public void addPlayer(Player player) throws IllegalArgumentException {
    if (players.contains(player)) {
      throw new IllegalArgumentException("Player already exists");
    }

    players.add(player);
  }

  /**
   * Initializes the dice with the specified number of dice and sides on each dice.
   *
   * @param numberOfDice  The number of dice to create.
   * @param numberOfSides The number of sides on each dice.
   */
  private void createDice(int numberOfDice, int numberOfSides) {
    dice = new Dice(numberOfDice, numberOfSides);
  }

  /**
   * Prepares the game for play by placing all players on the starting tile and setting the initial
   * player.
   *
   * @throws IllegalArgumentException if no players have been added to the game.
   */
  public void setUpGame() throws NotEnoughPlayersInGameException {
    if (players.isEmpty()) {
      throw new NotEnoughPlayersInGameException("Not enough players in the game to start!");
    }

    players.forEach(player -> player.placeOnTile(board.getTile(1)));

    currentPlayer = players.getFirst();

    notifyGameSetup(players, board.getTiles());
  }

  /**
   * Starts and runs the game loop until a winner is determined.
   *
   * @throws IllegalArgumentException if there are less than two players in the game.
   */
  public void play() throws NotEnoughPlayersInGameException {
    if (players.size() < 2) {
      throw new NotEnoughPlayersInGameException("Must be two players to start the game");
    }

    int roll = dice.roll();

    //System.out.println(currentPlayer.getName() + " rolled a " + roll);

    int nextPlayerPosition = getNextPlayerPosition(roll);

    currentPlayer.move(nextPlayerPosition);

    //System.out.println(
     //   currentPlayer.getName() + "moved to tile "
      //      + (currentPlayer.getCurrentTile().getTileId()) + "\n");

    currentPlayer = players.get((players.indexOf(currentPlayer) + 1) % players.size());
    notifyRoundPlayed(players, currentPlayer, roll);
    if (getWinner() != null) {
      notifyPlayerWon(getWinner());
       // System.out.println("\n" + getWinner().getName() + " has won the game!");
      return;
    }
  }

  /**
   * Calculates the next position for the current player based on the dice roll, handling
   * overshooting beyond the board's end.
   *
   * @param roll The result of the dice roll.
   * @return The relative movement distance for the player.
   */
  private int getNextPlayerPosition(int roll) {
    int lastTileIndex = board.getTiles().size();
    int currentPosition = currentPlayer.getCurrentTile().getTileId();
    int newPosition = currentPosition + roll;

    if (newPosition > lastTileIndex) {
      int overshoot = newPosition - lastTileIndex;
      int finalPosition = lastTileIndex - overshoot;

      return finalPosition - currentPosition;
    }

    return roll;
  }

  /**
   * Determines the winner of the game, if any.
   *
   * @return The winning player, or null if no player has won yet.
   */
  public Player getWinner() {
    for (Player player : players) {
      if (player.getCurrentTile().getTileId() == board.getTiles().size()) {
        return player;
      }
    }

    return null;
  }
}
