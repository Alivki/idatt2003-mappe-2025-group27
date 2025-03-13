package ntnu.idatt2003.group27;

import java.io.IOException;
import ntnu.idatt2003.group27.filehandler.JsonFileReader;
import ntnu.idatt2003.group27.models.Board;
import ntnu.idatt2003.group27.models.BoardGame;
import ntnu.idatt2003.group27.models.BoardGameFactory;
import ntnu.idatt2003.group27.models.Player;

public class BoardGameApp {
  public static void main(String[] args) {
    // to start the GUI for the application. Run main or mvn javafx:run for no error
    // BoardgameGUI.main(args);

    BoardGame game = BoardGameFactory.createGameFromJson(
        "src/main/java/ntnu/idatt2003/group27/resources/boards/Board.json");

    game.addPlayer(new Player("Alice", game));
    game.addPlayer(new Player("Test", game));
    game.setUpGame();
    game.play();
  }
}
