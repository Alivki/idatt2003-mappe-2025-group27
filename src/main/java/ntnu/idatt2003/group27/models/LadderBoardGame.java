package ntnu.idatt2003.group27.models;

import java.util.LinkedHashMap;
import java.util.Map;

import java.util.logging.Logger;
import ntnu.idatt2003.group27.models.exceptions.NotEnoughPlayersInGameException;
import ntnu.idatt2003.group27.models.interfaces.BoardGame;
import ntnu.idatt2003.group27.models.interfaces.BoardGameObserver;
import ntnu.idatt2003.group27.models.interfaces.LadderTileAction;
import ntnu.idatt2003.group27.models.interfaces.TileAction;

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
public class LadderBoardGame implements BoardGame {
  /**
   * Logger instance for the {@code BoardGame} class.
   * Used for logging informational messages and errors related to class operations.
   */
  private static final Logger logger = Logger.getLogger(BoardGame.class.getName());

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
  public LadderBoardGame(Board board, int numberOfDice, int numberOfSides) {
    logger.fine("Initializing BoardGame");
    this.board = board;
    createDice(numberOfDice, numberOfSides);
  }

  /**
   * Adds an observer to receive game event notifications.
   *
   * @param observer The observer to register.
   */
  @Override
  public void addObserver(BoardGameObserver observer) {
    logger.fine("Adding observer " + observer + " to BoardGame" + this);
    observers.add(observer);
  }

  /**
   * Adds players to the game.
   *
   * @param player The player to be added.
   * @throws IllegalArgumentException if the player already exists in the game.
   */
  public void addPlayer(Player player) throws IllegalArgumentException {
    logger.fine("Adding player " + player);
    if (players.contains(player)) {
      logger.warning("Player " + player + " already exists");
      throw new IllegalArgumentException("Player already exists");
    }

    players.add(player);
  }

  /**
   * Prepares the game for play by placing all players on the starting tile and setting the initial
   * player.
   *
   * @throws IllegalArgumentException if no players have been added to the game.
   */
  @Override
  public void setUpGame() throws NotEnoughPlayersInGameException {
    logger.fine("Setting up game");
    if (players.isEmpty()) {
      logger.severe("No players in game");
      throw new NotEnoughPlayersInGameException("Not enough players in the game to start!");
    }

    players.forEach(player -> player.placeOnTile(board.getTile(1)));

    currentPlayer = players.getFirst();

    notifyGameSetup();
  }

  /**
   * Restarts the game.
   */
  @Override
  public void restartGame(){
    logger.fine("Restarting game");
    players.forEach(player -> player.placeOnTile(board.getTile(1)));
    currentPlayer = players.getFirst();
    notifyGameRestart();
  }

  /**
   * Starts and runs the game loop until a winner is determined.
   *
   * @throws IllegalArgumentException if there are less than two players in the game.
   */
  @Override
  public void play() throws NotEnoughPlayersInGameException {
    logger.fine("Playing game.");
    if (players.size() < 2) {
      logger.warning("Not enough players to start the game!");
      throw new NotEnoughPlayersInGameException("Must be two players to start the game");
    }

    int roll = dice.roll();

    int nextPlayerPosition = getNextPlayerPosition(roll);
    TileAction ladderTileAction =
      board.getTiles().get(currentPlayer.getCurrentTile().getTileId() + nextPlayerPosition).getLandAction();

    currentPlayer.move(nextPlayerPosition);

    currentPlayer = players.get((players.indexOf(currentPlayer) + 1) % players.size());
    notifyRoundPlayed(roll, ladderTileAction);
    if (getWinner() != null) {
      notifyPlayerWon(getWinner());
    }
  }

  /**
   * Retrieves the current player. The method is package-private for testing purposes.
   *
   * @return The player whose turn is currently active.
   */
  @Override
  public Player getCurrentPlayer() {
    return currentPlayer;
  }

  /**
   * Retrieves a copy of the list of players in the game.
   * The method is package-private for testing purposes.
   *
   * @return A new {@link ArrayList} containing all players in the game.
   */
  @Override
  public ArrayList<Player> getPlayers() {
    return new ArrayList<>(players);
  }

  /**
   * Determines the winner of the game, if any.
   *
   * @return The winning player, or null if no player has won yet.
   */
  @Override
  public Player getWinner() {
    for (Player player : players) {
      if (player.getCurrentTile().getTileId() == board.getTiles().size()) {
        return player;
      }
    }

    return null;
  }

  @Override
  public Map<Player, Board> getBoards() {
    Map<Player, Board> boards = new LinkedHashMap<>();
    boards.put(null, board);
    return boards;
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
   * Initializes the dice with the specified number of dice and sides on each dice.
   *
   * @param numberOfDice  The number of dice to create.
   * @param numberOfSides The number of sides on each dice.
   */
  private void createDice(int numberOfDice, int numberOfSides) {
    dice = new Dice(numberOfDice, numberOfSides);
  }

  /**
   * Calculates the next position for the current player based on the dice roll, handling
   * overshooting beyond the board's end.
   *
   * @param roll The result of the dice roll.
   * @return The relative movement distance for the player.
   */
  private int getNextPlayerPosition(int roll) {
    logger.fine("Getting Next player position for roll " + roll);
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
   * Notifies all observers that a round has been played and provides the list of players to update
   * player positions on the board.
   *
   * @param roll The result of the dice roll for the round.
   * @param action The action taken on the tile, if any.
   */

  public void notifyRoundPlayed(int roll, TileAction action) {
    observers.forEach(observer -> observer.onRoundPlayed(players, currentPlayer, roll, action));
  }

  /**
   * Notifies all observers that a player has won.
   *
   * @param player The player that has won.
   */
  public void notifyPlayerWon(Player player) {
    logger.fine("Notifying player won: " + player.getName());
    observers.forEach(observer -> observer.onPlayerWon(player));
  }

  /**
   * Notifies all observers that the game has been set up and is ready to start.
   */
  public void notifyGameSetup() {
    logger.fine("Notifying game setup.");
    observers.forEach(observer -> observer.onGameSetup(players, getBoards()));
  }

  /**
   * Notifies all observers that the game has been restarted.
   */
  public void notifyGameRestart() {
    logger.fine("Notifying game restart.");
    observers.forEach(observer -> observer.onGameRestart(players, getBoards()));
  }
}
