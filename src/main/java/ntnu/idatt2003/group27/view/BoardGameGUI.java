package ntnu.idatt2003.group27.view;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *.
 */
public class BoardgameGUI extends Application {
  @Override
  public void start(Stage primaryStage) {
    GameScreen gameScreen = new GameScreen();
    Scene scene = new Scene(gameScreen.getRoot(), 1920, 1080);
    primaryStage.setTitle("Boardgames");
    primaryStage.setScene(scene);
    primaryStage.show();
  }

  /**
   *.
   *
   * @param args .
   */
  public static void main(String[] args) {
    launch(args);
  }
}
