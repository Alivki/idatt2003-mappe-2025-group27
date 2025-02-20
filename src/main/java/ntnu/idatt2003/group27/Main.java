package ntnu.idatt2003.group27;

import ntnu.idatt2003.group27.models.Board;
import ntnu.idatt2003.group27.models.BoardGame;
import ntnu.idatt2003.group27.models.Dice;
import ntnu.idatt2003.group27.models.Player;

public class Main {
  public static void main(String[] args) {
    BoardGame game =  new BoardGame();
    game.addPlayer(new Player("Alice", game));
    game.addPlayer(new Player("Test", game));
    game.play();
  }
}