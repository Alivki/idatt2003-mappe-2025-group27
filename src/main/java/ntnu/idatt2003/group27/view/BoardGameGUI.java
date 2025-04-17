package ntnu.idatt2003.group27.view;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import ntnu.idatt2003.group27.controllers.MainController;
import ntnu.idatt2003.group27.models.exceptions.NotEnoughPlayersInGameException;
import ntnu.idatt2003.group27.models.exceptions.UnknownLadderGameTypeExceptions;
import ntnu.idatt2003.group27.models.enums.LadderGameType;
import ntnu.idatt2003.group27.models.BoardGame;
import ntnu.idatt2003.group27.controllers.BoardGameController;
import ntnu.idatt2003.group27.models.BoardGameFactory;
import ntnu.idatt2003.group27.models.Player;

/**
 *.
 */
public class BoardGameGUI {

  public LadderGameView ladderGameView;

  public BoardGameGUI() {
    // initialize mvc
    BoardGame game = null;
    try {
      game = BoardGameFactory.createLadderGame(LadderGameType.NORMAL);
    } catch (UnknownLadderGameTypeExceptions e) {
      System.err.println(e.getMessage());
    }

    if (game == null) {
      return;
    }

    ladderGameView = new LadderGameView();


    BoardGameController controller = new BoardGameController(game, ladderGameView);

    //Add players to game
    MainController.getInstance().getPlayers().forEach(game::addPlayer);


    // change when being done in controller on game difficult select
    try {
      game.setUpGame();
    } catch (NotEnoughPlayersInGameException e) {
      System.err.println(e.getMessage());
    }
  }
}
