package ntnu.idatt2003.group27;

import java.util.logging.Logger;
import ntnu.idatt2003.group27.controllers.MainController;
import ntnu.idatt2003.group27.view.SceneManager;

public class BoardGameApp {
  /**
   * Logger instance for the {@code } class.
   * Used for logging informational messages and errors related to class operations.
   */
  private static final Logger logger = Logger.getLogger(BoardGameApp.class.getName());

  /**
   * The main method that serves as the entry point for the application.
   * It initializes the application and starts the GUI.
   * Constructs a new {@code BoardGameApp} instance.
   * <p>This class is not meant to be instantiated.</p>
   *
   * @param args Command-line arguments passed to the application.
   */
   static void main(String[] args) {
    logger.info("Starting BoardGameApp");
    // to start the GUI for the application. Run main or mvn javafx:run for no error
    SceneManager.launchGui(args);
  }
}
