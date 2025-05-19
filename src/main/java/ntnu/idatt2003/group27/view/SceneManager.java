package ntnu.idatt2003.group27.view;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import ntnu.idatt2003.group27.controllers.MainController;
import ntnu.idatt2003.group27.controllers.MainMenuController;
import java.util.logging.Logger;

/**
 * A class representing a scene manager and base application. Extends {@link Application}.
 * Implements logic for switching scenes and sets up necessary dependencies to control the application.
 */
public class SceneManager extends Application {

  /**
   * Logger instance for the {@code SceneManager} class.
   * Used for logging informational messages and errors related to scene management operations.
   */
  private static final Logger logger = Logger.getLogger(SceneManager.class.getName());


  /** The root container for the scene manager. */
  private StackPane rootContainer;

  /** The view for the main menu. */
  private MainMenuView mainMenuView;

  /** The main controller instance used by this scene manager. */
  private MainController mainController;

  /**
   * The start method to launch the application.
   */
  @Override
  public void start(Stage primaryStage) throws Exception {
    //Initialized scene manager
    mainController = new MainController(this);
    logger.info("Starting SceneManager.");
    rootContainer = new StackPane();
    rootContainer.setAlignment(Pos.TOP_CENTER);
    rootContainer.getStyleClass().add("root");

    //Initializes new scene
    Scene scene = new Scene(rootContainer, 1260, 600);

    //Initializes primary stage
    primaryStage.setTitle("Boardgames");
    primaryStage.setScene(scene);
    primaryStage.setResizable(true);
    primaryStage.setFullScreen(true);
    primaryStage.setFullScreenExitHint("");
    scene.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());
    primaryStage.show();

    //Initialized main menu view
    mainMenuView = new MainMenuView();
    MainMenuController mainMenuController = new MainMenuController(mainController, mainMenuView);
    switchSceneImmediate(mainMenuView.getRoot());
  }

  /**
   * Launches the application GUI.
   * @param args
   */
  public static void launchGui(String[] args) {
    launch(args);
  }

  /**
   * Instantly switches "scene" to the specified {@link StackPane}
   * @param newPane
   */
  public void switchSceneImmediate(StackPane newPane) {
    rootContainer.getChildren().clear();
    rootContainer.getChildren().add(newPane);
  }

  /**
   * Instantly switches "scene" to the main menu view.
   */
  public void switchToMainMenu() {
    logger.info("Switching to main menu.");
    switchSceneImmediate(mainMenuView.getRoot());
  }
}
