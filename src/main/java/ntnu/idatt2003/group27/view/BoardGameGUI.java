package ntnu.idatt2003.group27.view;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
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

    BoardGameController controller = new BoardGameController(game);
    BoardGameMenu view = new BoardGameMenu(game, controller);

    game.addPlayer(new Player("Alice"));
    game.addPlayer(new Player("Test"));
    game.setUpGame();

    // set up scene
    Scene scene = new Scene(view.getRoot(), 1920, 1080);

    // configure stage
    primaryStage.setTitle("Boardgames");
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
