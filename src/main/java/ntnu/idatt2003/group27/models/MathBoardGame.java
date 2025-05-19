package ntnu.idatt2003.group27.models;

import ntnu.idatt2003.group27.models.exceptions.NotEnoughPlayersInGameException;
import ntnu.idatt2003.group27.models.interfaces.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MathBoardGame implements BoardGame {
  private final List<BoardGameObserver> observers = new ArrayList<>();
  private Map<Player, Board> playerBoards = new HashMap<>();
  private Player currentPlayer;
  private ArrayList<Player> players =  new ArrayList<>();
  private TileAction action;
  private String mathQuestion;

  public MathBoardGame(List<Board> boards, List<Player> players) {
    addPlayers(boards, players);
  }

  @Override
  public void addObserver(BoardGameObserver observer) {
    observers.add(observer);
  }

  @Override
  public void setUpGame() throws NotEnoughPlayersInGameException {
    if (players.isEmpty()) {
      throw new NotEnoughPlayersInGameException("Not enough players to start the game.");
    }
    players.forEach(player -> player.placeOnTile(playerBoards.get(player).getTile(1)));
    currentPlayer = players.getFirst();
    notifyGameSetup();
  }

  @Override
  public void restartGame() {
    players.forEach(player -> player.placeOnTile(playerBoards.get(player).getTile(1)));
    currentPlayer = players.getFirst();
    notifyGameRestart();
  }

  @Override
  public void play() throws NotEnoughPlayersInGameException {
    if (players.size() < 2) {
      throw new NotEnoughPlayersInGameException("Not enough players to play the game.");
    }

    currentPlayer.move(1);
    action = currentPlayer.getCurrentTile().getLandAction();
    if (action instanceof MathTileAction mathAction) {
      mathQuestion = mathAction.getQuestion();
    }
    notifyRoundPlayed();
  }

  public void checkAnswer(String answer) {
    if (action instanceof MathTileAction mathAction) {
      mathAction.isCorrect(currentPlayer, answer);
    }
    currentPlayer = players.get((players.indexOf(currentPlayer) + 1) % players.size());
    notifyRoundPlayed();

    if (getWinner() != null) {
      notifyPlayerWon(getWinner());
    }
  }

  @Override
  public Player getCurrentPlayer() {
    return currentPlayer;
  }

  @Override
  public ArrayList<Player> getPlayers() {
    return new ArrayList<>(players);
  }

  @Override
  public Player getWinner() {
    for (Player player : players) {
      if (player.getCurrentTile().getTileId() == playerBoards.get(player).getTiles().size()) {
        return player;
      }
    }
    return null;
  }

  @Override
  public Map<Player, Board> getBoards() {
    return new HashMap<>(playerBoards);
  }

  public void addPlayers(List<Board> boards, List<Player> players) throws IllegalArgumentException {
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
    observers.forEach(observer -> observer.onRoundPlayed(players, currentPlayer, 0, action));
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
   */
  public void notifyGameSetup() {
    observers.forEach(observer -> observer.onGameSetup(players, getBoards()));
  }

  /**
   * Notifies all observers that the game has been restarted.
   */
  public void notifyGameRestart() {
    observers.forEach(observer -> observer.onGameRestart(players, playerBoards));
  }

  public String getMathQuestion() {
    return mathQuestion;
  }
}
