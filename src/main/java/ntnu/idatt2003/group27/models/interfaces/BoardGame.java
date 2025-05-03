package ntnu.idatt2003.group27.models.interfaces;

import ntnu.idatt2003.group27.models.Board;
import ntnu.idatt2003.group27.models.Player;
import ntnu.idatt2003.group27.models.exceptions.NotEnoughPlayersInGameException;

public interface BoardGame {
  void addPlayer(Player player) throws IllegalArgumentException;
  void setUpGame() throws NotEnoughPlayersInGameException;
  void restartGame() throws NotEnoughPlayersInGameException;
  void play() throws NotEnoughPlayersInGameException;

  Board getBoard();

  void addObserver(BoardGameObserver observer);
}
