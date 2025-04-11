package ntnu.idatt2003.group27.view;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ntnu.idatt2003.group27.controllers.BoardGameController;
import ntnu.idatt2003.group27.models.BoardGame;
import ntnu.idatt2003.group27.models.BoardGameFactory;
import ntnu.idatt2003.group27.models.Player;
import ntnu.idatt2003.group27.models.enums.LadderGameType;
import ntnu.idatt2003.group27.models.exceptions.NotEnoughPlayersInGameException;
import ntnu.idatt2003.group27.models.exceptions.UnknownLadderGameTypeExceptions;

/**
 *.
 */
public class MainMenuGUI extends Application {

  /**
   *.
   *
   * @param primaryStage .
   */
  @Override
  public void start(Stage primaryStage) {
    MainMenuMenu view = new MainMenuMenu();
    MainMenuView mainMenuView = new MainMenuView();

    //BoardGameController controller = new BoardGameController(game, view, mainMenuView);

    // set up scene

    Scene scene = new Scene(mainMenuView.getRoot(), 1280, 690);

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
