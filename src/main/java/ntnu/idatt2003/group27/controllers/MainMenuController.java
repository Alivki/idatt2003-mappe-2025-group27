package ntnu.idatt2003.group27.controllers;

import java.io.IOException;
import ntnu.idatt2003.group27.models.Player;
import ntnu.idatt2003.group27.models.enums.LadderGameType;
import ntnu.idatt2003.group27.utils.filehandler.csv.PlayerCsvFileReader;
import ntnu.idatt2003.group27.utils.filehandler.csv.PlayerCsvFileWriter;
import ntnu.idatt2003.group27.view.MainMenuView;
import ntnu.idatt2003.group27.view.components.Alert;

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

    // change player piece button



    // change to ladder game selection
    // change to game 2 selection
    // change to game 3 selection

    // start game with specified difficulty use factory to create game

    //Sets handler for normal board button
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
      MainController.getInstance().switchToBoardGame(LadderGameType.NORMAL);
    });

    //Sets handler for crazy board button
    mainMenuView.setCrazyBoardButtonHandler(e -> {
      System.out.println("Crazy board button clicked");
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
      MainController.getInstance().switchToBoardGame(LadderGameType.CRAZY);
    });

    //Sets handler for IMPOSSIBLE board button
    mainMenuView.setImpossibleBoardButtonHandler(e -> {
      System.out.println("IMPOSSIBLE board button clicked");
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
      MainController.getInstance().switchToBoardGame(LadderGameType.IMPOSSIBLE);
    });

    //Sets handler for JSON board button
    mainMenuView.setJsonBoardButtonHandler(e -> {
      System.out.println("Json board button clicked");
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
      MainController.getInstance().switchToBoardGame(LadderGameType.NORMAL);
    });

    //Set export players csv file handler
    mainMenuView.setExportPlayersCsvButtonHandler(e -> {
      System.out.println("Export players csv button clicked");
      if (MainController.getInstance() != null){      PlayerCsvFileWriter csvFileWriter = new PlayerCsvFileWriter();
      try {
        csvFileWriter.writeFile("src/main/resources/csv/Exported_Players.csv",
            MainController.getInstance().getPlayers().toArray(new Player[0]));
      } catch (IOException ex) {
        System.out.println(ex.getMessage());

      }
      }
    });

    //Set import players csv file handler
    mainMenuView.setImportPlayersCsvButtonHandler(e -> {
      System.out.println("Import players csv button clicked");
      PlayerCsvFileReader fileReader = new PlayerCsvFileReader();
      try {
        Player[] players = fileReader.readFile("src/main/resources/csv/Exported_Players.csv");
        if (MainController.getInstance() != null){
          MainController.getInstance().setPlayers(players);
        }
      }
      catch (IOException ex) {
        System.out.println(ex.getMessage());
      }
    });
  }
}
