package ntnu.idatt2003.group27;

import java.io.IOException;
import javax.swing.BorderFactory;
import javax.swing.BoundedRangeModel;

import ntnu.idatt2003.group27.Exceptions.UnknownLadderGameTypeExceptions;
import ntnu.idatt2003.group27.filehandler.JsonFileReader;
import ntnu.idatt2003.group27.models.Board;
import ntnu.idatt2003.group27.models.BoardGame;
import ntnu.idatt2003.group27.models.BoardGameFactory;
import ntnu.idatt2003.group27.models.Player;
import ntnu.idatt2003.group27.view.BoardgameGUI;
import org.apache.commons.lang3.ObjectUtils;

public class BoardGameApp {
  public static void main(String[] args) {
    // to start the GUI for the application. Run main or mvn javafx:run for no error
    //BoardgameGUI.main(args);

    //BoardGame game = BoardGameFactory.createGameFromJson(
    //   "src/main/java/ntnu/idatt2003/group27/resources/boards/Board.json");
    BoardGame game = null;
    try {
      game = BoardGameFactory.createLadderGame(LadderGameType.NORMAL);
    } catch (UnknownLadderGameTypeExceptions e) {
      System.err.println(e.getMessage());
    }

    if (game == null) {
      return;
    }

    game.addPlayer(new Player("Alice"));
    game.addPlayer(new Player("Test"));
    game.setUpGame();
    game.play();
  }
}
