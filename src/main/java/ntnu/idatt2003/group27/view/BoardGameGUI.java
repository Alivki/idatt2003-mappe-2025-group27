package ntnu.idatt2003.group27.view;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
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
public class BoardGameGUI extends Application {

  /**
   *.
   *
   * @param primaryStage .
   */
  @Override
  public void start(Stage primaryStage) {
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

    BoardGameMenu view = new BoardGameMenu();
    LadderGameView ladderView = new LadderGameView();

    BoardGameController controller = new BoardGameController(game, view, ladderView);

    // move to controller when ready to implement
    game.addPlayer(new Player("Alice"));
    game.addPlayer(new Player("Test"));

    // change when being done in controller on game difficult select
    try {
      game.setUpGame();
    } catch (NotEnoughPlayersInGameException e) {
      System.err.println(e.getMessage());
    }

    // set up scene
    Scene scene = new Scene(ladderView.getRoot(), 1280, 690);

    // configure stage
    primaryStage.setTitle("Boardgames");
    primaryStage.setResizable(true);
    primaryStage.setFullScreen(true);
    primaryStage.setScene(scene);
    scene.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());
    primaryStage.show();
  }

  /**
   *.
   *
   * @param args .
   */
  public static void launchGui(String[] args) {
    launch(args);
  }
}
