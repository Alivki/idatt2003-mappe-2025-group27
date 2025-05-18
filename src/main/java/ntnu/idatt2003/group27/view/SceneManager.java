package ntnu.idatt2003.group27.view;

import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import ntnu.idatt2003.group27.controllers.MainController;
import ntnu.idatt2003.group27.controllers.MainMenuController;

/**
 * A class representing a scene manager. Extends {@link Application}.
 * Implements logic for switching scenes.
 */
public class SceneManager extends Application {
  /** The root container for the scene manager. */
  private StackPane rootContainer;
  /** The current pane used by the scene manager. */
  private StackPane currentPane = null;
  /** The view for the main menu. */
  private MainMenuView mainMenuView;
  /** The main controller instance used by this scene manager. */
  private MainController mainController;


  /**
   * The start method to launch the application.
   */
  @Override
  public void start(Stage primaryStage) throws Exception {
    //Initializes the main controller for the application
    mainController = new MainController(this);

    System.out.println("Starting SceneManager");
    rootContainer = new StackPane();
    rootContainer.setAlignment(Pos.TOP_CENTER);
    rootContainer.getStyleClass().add("root");

    // set up scene
    Scene scene = new Scene(rootContainer, 1260, 600);

    // configure stage
    primaryStage.setTitle("Boardgames");

    primaryStage.setScene(scene);

    primaryStage.setResizable(true);
    primaryStage.setFullScreen(true);
    primaryStage.setFullScreenExitHint("");
    scene.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());
    primaryStage.show();

    mainMenuView = new MainMenuView();
    MainMenuController mainMenuController = new MainMenuController(mainController, mainMenuView);
    switchSceneImmediate(mainMenuView.getRoot());
  }

  public static void launchGui(String[] args) {
    launch(args);
  }

  /**
   * Instantly switches "scene" to the specified {@link StackPane}
   * @param newPane
   */
  public void switchSceneImmediate(StackPane newPane) {
    System.out.println("switchSceneImmediate");
    rootContainer.getChildren().clear();
    rootContainer.getChildren().add(newPane);
  }

  /**
   * Switch scenes with a fade transition.
   * @param newPane
   */
  public void switchScene(StackPane newPane) {
    if(currentPane == newPane) {
      return;
    }

    currentPane = newPane;

    FadeTransition fadeOut = new FadeTransition(Duration.millis(150), rootContainer);
    fadeOut.setFromValue(1.0);
    fadeOut.setToValue(0.7);

    fadeOut.setOnFinished(e -> {
      rootContainer.getChildren().clear();
      rootContainer.getChildren().add(newPane);

      FadeTransition fadeIn = new FadeTransition(Duration.millis(150), rootContainer);
      fadeIn.setFromValue(0.7);
      fadeIn.setToValue(1.0);
      fadeIn.play();
    });

    fadeOut.play();
  }

  public void switchToMainMenu() {
    switchSceneImmediate(mainMenuView.getRoot());
  }
}
