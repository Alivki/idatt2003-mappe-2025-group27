package ntnu.idatt2003.group27;

import ntnu.idatt2003.group27.models.Board;
import ntnu.idatt2003.group27.models.BoardGame;
import ntnu.idatt2003.group27.models.Dice;
import ntnu.idatt2003.group27.models.Player;
import ntnu.idatt2003.group27.view.BoardgameGUI;

public class Main {
  public static void main(String[] args) {
    // to start the GUI for the application. Run main or mvn javafx:run for no error
    //BoardgameGUI.main(args);

    BoardGame game =  new BoardGame(90, 1, 6);
    game.addPlayer(new Player("Alice", game));
    game.addPlayer(new Player("Test", game));
    game.setUpGame();
    game.play();
  }
}