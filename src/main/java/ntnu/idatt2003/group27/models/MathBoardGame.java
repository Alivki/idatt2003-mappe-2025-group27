package ntnu.idatt2003.group27.models;

import java.util.logging.Logger;
import ntnu.idatt2003.group27.models.exceptions.NotEnoughPlayersInGameException;
import ntnu.idatt2003.group27.models.exceptions.WrongMathAnswerException;
import ntnu.idatt2003.group27.models.interfaces.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MathBoardGame implements BoardGame {
  /**
   * Logger instance for the {@link MathBoardGame} class.
   * Used for logging informational messages and errors related to class operations.
   */
  private static final Logger logger = Logger.getLogger(MathBoardGame.class.getName());

  private final List<BoardGameObserver> observers = new ArrayList<>();
  private Map<Player, Board> playerBoards = new HashMap<>();
  private Player currentPlayer;
  private ArrayList<Player> players =  new ArrayList<>();
  private TileAction action;
  private String mathQuestion;

  public MathBoardGame(List<Board> boards, List<Player> players) {
    logger.fine("Initializing MathBoardGame.");
    addPlayers(boards, players);
  }

  @Override
  public void addObserver(BoardGameObserver observer) {
    logger.fine("Adding observer: " + observer);
    observers.add(observer);
  }

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

  @Override
  public void restartGame() {
    logger.fine("Restarting game.");
    players.forEach(player -> player.placeOnTile(playerBoards.get(player).getTile(1)));
    currentPlayer = players.getFirst();
    notifyGameRestart();
  }

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

  public void checkAnswer(String answer) throws WrongMathAnswerException {
    logger.fine("Checking answer: " + answer);
    if (action instanceof MathTileAction mathAction) {
      try {
        mathAction.isCorrect(currentPlayer, answer);
      } catch (WrongMathAnswerException e) {
        throw new WrongMathAnswerException(e.getMessage());
      }
    }
    currentPlayer = players.get((players.indexOf(currentPlayer) + 1) % players.size());
    notifyRoundPlayed();

    if (getWinner() != null) {
      notifyPlayerWon(getWinner());
    }
  }

  @Override
  public Player getCurrentPlayer() {
    logger.fine("Getting current player: " + currentPlayer);
    return currentPlayer;
  }

  @Override
  public ArrayList<Player> getPlayers() {
    logger.fine("Getting players: " + players);
    return new ArrayList<>(players);
  }

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

  @Override
  public Map<Player, Board> getBoards() {
    logger.fine("Getting boards.");
    return new HashMap<>(playerBoards);
  }

  public void addPlayers(List<Board> boards, List<Player> players) throws IllegalArgumentException {
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
