package ntnu.idatt2003.group27;

import ntnu.idatt2003.group27.controllers.MainController;
import ntnu.idatt2003.group27.view.BoardGameGUI;
import ntnu.idatt2003.group27.view.BoardGameMenu;
import ntnu.idatt2003.group27.view.MainMenuGUI;

public class BoardGameApp {
  public static void main(String[] args) {
    // to start the GUI for the application. Run main or mvn javafx:run for no error
    MainController mainController = new MainController(args);
    mainController.initializeMainMenu();
  }
}
