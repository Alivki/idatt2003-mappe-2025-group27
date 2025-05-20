package ntnu.idatt2003.group27.models.interfaces;

import ntnu.idatt2003.group27.models.Board;
import ntnu.idatt2003.group27.models.Player;
import ntnu.idatt2003.group27.models.exceptions.NotEnoughPlayersInGameException;

import java.util.List;
import java.util.Map;

public interface BoardGame {
  void setUpGame() throws NotEnoughPlayersInGameException;
  void play() throws NotEnoughPlayersInGameException;
  void restartGame();
  void addObserver(BoardGameObserver observer);
  Player getCurrentPlayer();
  List<Player> getPlayers();
  Player getWinner();
  Map<Player, Board> getBoards();
}
