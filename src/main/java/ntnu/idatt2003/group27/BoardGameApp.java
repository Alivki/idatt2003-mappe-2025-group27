package ntnu.idatt2003.group27;

import java.io.IOException;
import ntnu.idatt2003.group27.filehandler.JsonFileReader;
import ntnu.idatt2003.group27.models.Board;
import ntnu.idatt2003.group27.models.BoardGame;
import ntnu.idatt2003.group27.models.Player;
import ntnu.idatt2003.group27.view.BoardgameGUI;

public class BoardGameApp {
  public static void main(String[] args) {
    // to start the GUI for the application. Run main or mvn javafx:run for no error
    BoardgameGUI.main(args);

    //Read board from file
    JsonFileReader jsonFileReader = new JsonFileReader();
    Board board;
    try {
      board = jsonFileReader.readBoardFile("Board.Json");
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    //Initialize boardgame with board if defined, otherwise with amount of tiles.
    BoardGame game;
    if (board != null) {
      game = new BoardGame(board, 1, 6);
    }
    else{
      game = new BoardGame(90, 1, 6);
    }
    game.addPlayer(new Player("Alice", game));
    game.addPlayer(new Player("Test", game));
    game.setUpGame();
    game.play();
  }
}
