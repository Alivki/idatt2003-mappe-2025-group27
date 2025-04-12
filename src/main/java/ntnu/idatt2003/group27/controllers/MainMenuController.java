package ntnu.idatt2003.group27.controllers;

import java.util.ArrayList;
import ntnu.idatt2003.group27.models.Player;
import ntnu.idatt2003.group27.view.MainMenuView;

public class MainMenuController {
  private MainMenuView mainMenuView;

  public MainMenuController(MainMenuView mainMenuView) {
    this.mainMenuView = mainMenuView;
    setupMenuViewEventHandler();
  }

  private void setupMenuViewEventHandler() {
    // add player button
    mainMenuView.setAddPlayerButtonHandler(e -> {
      System.out.println("Add player button clicked");
      MainController.instance.addPlayer(new Player("New player"));

    });

    // upload player button
    // remove player button



    // download player button
    // change player piece button



    // change to ladder game selection
    // change to game 2 selection
    // change to game 3 selection

    // start game with specified difficulty use factory to create game
    mainMenuView.setNormalBoardButtonHandler(e -> {
      System.out.println("Normal board button clicked");
      MainController.instance.switchToBoardGame();
    });
  }
}
