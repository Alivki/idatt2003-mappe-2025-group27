package ntnu.idatt2003.group27.controllers;

import java.util.ArrayList;
import ntnu.idatt2003.group27.models.Player;
import ntnu.idatt2003.group27.view.MainMenuView;
import ntnu.idatt2003.group27.view.components.Alert;
import ntnu.idatt2003.group27.view.components.Toast;

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
      String playerName = mainMenuView.getPlayerNameTextFieldValue();
      if (playerName.equals("")) {
        playerName = "Spiller " + (MainController.getInstance().getPlayers().size() + 1);
      }
      MainController.getInstance().addPlayer(new Player(playerName));

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
      if (MainController.getInstance().getPlayers().size() < 2) {
        System.out.println("Not enough players to start game!");
        Alert alert = new Alert(
            this.mainMenuView.getRoot(),
            "Ikke nok spillere",
            "Minst 2 spillere trengs for å starte spillet!",
            "Ok",
            "Lukk",
            response -> {}
        );
        alert.show();
        return;
      }
      MainController.getInstance().switchToBoardGame();
    });
  }
}
