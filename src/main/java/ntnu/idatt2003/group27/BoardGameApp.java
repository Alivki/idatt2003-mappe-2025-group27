package ntnu.idatt2003.group27;

import ntnu.idatt2003.group27.view.BoardGameGUI;
import ntnu.idatt2003.group27.view.BoardGameMenu;
import ntnu.idatt2003.group27.view.MainMenuGUI;

public class BoardGameApp {
  public static void main(String[] args) {
    // to start the GUI for the application. Run main or mvn javafx:run for no error
    try {
      MainMenuGUI.launchGui(args);
      //BoardGameGUI.launchGui(args);
    } catch (Exception e) {
      System.err.println("Failed to start: " + e.getMessage());
      System.exit(1);
    }
  }
}
