package ntnu.idatt2003.group27.models;

import java.util.logging.Logger;
import ntnu.idatt2003.group27.models.exceptions.NotEnoughPlayersInGameException;
import ntnu.idatt2003.group27.models.interfaces.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A class representing a math board game.
 * This class implements the {@link BoardGame} interface and provides functionality for managing
 * players, boards, and game actions.
 *
 * @author Iver Lindholm, Amadeus Berg
 * @since 0.0
 * @version 1.1
 */
public class MathBoardGame implements BoardGame {
  /**
   * Logger instance for the {@link MathBoardGame} class.
   * Used for logging informational messages and errors related to class operations.
   */
  private static final Logger logger = Logger.getLogger(MathBoardGame.class.getName());

  /** The list of observers observing this game */
  private final List<BoardGameObserver> observers = new ArrayList<>();

  /** The map of players and their corresponding boards. */
  private Map<Player, Board> playerBoards = new HashMap<>();

  /** The current player in the game. */
  private Player currentPlayer;

  /** The list of players in the game. */
  private ArrayList<Player> players =  new ArrayList<>();

  /** The action associated with the current tile. */
  private TileAction action;

  /** The math question associated with the current tile. */
  private String mathQuestion;

  /**
   * Constructor for MathBoardGame.
   * @param boards
   * @param players
   */
  public MathBoardGame(List<Board> boards, List<Player> players) {
    logger.fine("Initializing MathBoardGame.");
    addPlayers(boards, players);
  }

  /**
   *  Adds an observer to the list of observers.
   * @param observer
   */
  @Override
  public void addObserver(BoardGameObserver observer) {
    logger.fine("Adding observer: " + observer);
    observers.add(observer);
  }

  /**
   * Sets up the game.
   * @throws NotEnoughPlayersInGameException
   */
  @Override
  public void setUpGame() throws NotEnoughPlayersInGameException {
    logger.fine("Setting up game.");
    if (players.isEmpty()) {
      logger.warning("Error setting up game: No players are in the game!");
      throw new NotEnoughPlayersInGameException("Not enough players to start the game.");
    }
    players.forEach(player -> player.placeOnTile(playerBoards.get(player).getTile(1)));
    currentPlayer = players.getFirst();
    notifyGameSetup();
  }

  /**
   * Restarts the game by placing all players on the first tile of their respective boards.
   */
  @Override
  public void restartGame() {
    logger.fine("Restarting game.");
    players.forEach(player -> player.placeOnTile(playerBoards.get(player).getTile(1)));
    currentPlayer = players.getFirst();
    notifyGameRestart();
  }

  /**
   * Plays a round of the game.
   * Moves the current player and performs the action associated with the tile they land on.
   * @throws NotEnoughPlayersInGameException
   */
  @Override
  public void play() throws NotEnoughPlayersInGameException {
    logger.fine("Playing game.");
    if (players.size() < 2) {
      logger.warning("Cannot start game: Not enough players to start the game.");
      throw new NotEnoughPlayersInGameException("Not enough players to play the game.");
    }

    currentPlayer.move(1);
    action = currentPlayer.getCurrentTile().getLandAction();
    if (action instanceof MathTileAction mathAction) {
      mathQuestion = mathAction.getQuestion();
    }
    notifyRoundPlayed();
  }

  /**
   * Checks the answer provided by the current player.
   * @param answer
   */
  public void checkAnswer(String answer) {
    logger.fine("Checking answer: " + answer);
    if (action instanceof MathTileAction mathAction) {
      mathAction.isCorrect(currentPlayer, answer);
    }
    currentPlayer = players.get((players.indexOf(currentPlayer) + 1) % players.size());
    notifyRoundPlayed();

    if (getWinner() != null) {
      notifyPlayerWon(getWinner());
    }
  }

  /**
   * Returns the current player.
   * @return The current player.
   */
  @Override
  public Player getCurrentPlayer() {
    logger.fine("Getting current player: " + currentPlayer);
    return currentPlayer;
  }

  /**
   * Returns the list of players in the game.
   * @return The list of players.
   */
  @Override
  public ArrayList<Player> getPlayers() {
    logger.fine("Getting players: " + players);
    return new ArrayList<>(players);
  }

  /**
   * Returns the winner of the game.
   * @return The winning player, or null if no player has won yet.
   */
  @Override
  public Player getWinner() {
    logger.fine("Getting winner.");
    for (Player player : players) {
      if (player.getCurrentTile().getTileId() == playerBoards.get(player).getTiles().size()) {
        return player;
      }
    }
    return null;
  }

  /**
   * Returns the map of players and their corresponding boards.
   * @return The map of players and boards.
   */
  @Override
  public Map<Player, Board> getBoards() {
    logger.fine("Getting boards.");
    return new HashMap<>(playerBoards);
  }

  /**
   * Adds players to the game and assigns them to their respective boards.
   * @param boards The list of boards for each player.
   * @param players The list of players to add to the game.
   * @throws IllegalArgumentException if the number of players does not match the number of boards.
   */
  public void addPlayers(List<Board> boards, List<Player> players) throws IllegalArgumentException {
    if (players == null){
      logger.warning("Players list is null.");
      throw new IllegalArgumentException("Players list cannot be null");
    }
    if (boards == null){
      logger.warning("Boards list is null.");
      throw new IllegalArgumentException("Boards list cannot be null");
    }

    logger.fine("Adding players: " + players + ", boards:" + boards);
    this.players = new ArrayList<>(players);
    for (int i = 0; i < players.size(); i++) {
      Player player = players.get(i);
      Board board = boards.get(i);
      playerBoards.put(player, board);
    }
  }

  /**
   * Notifies all observers that a round has been played and provides the list of players to update
   * player positions on the board.
   */
  public void notifyRoundPlayed() {
    logger.fine("Notifying round played.");
    observers.forEach(observer -> observer.onRoundPlayed(players, currentPlayer, 0, action));
  }

  /**
   * Notifies all observers that a player has won.
   *
   * @param player The player that has won.
   */
  public void notifyPlayerWon(Player player) {
    logger.fine("Notifying player won.");
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
    observers.forEach(observer -> observer.onGameRestart(players, playerBoards));
  }

  public String getMathQuestion() {
    logger.fine("Getting math question.");
    return mathQuestion;
  }
}
