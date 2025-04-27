package ntnu.idatt2003.group27.controllers;

import java.io.IOException;
import ntnu.idatt2003.group27.models.Player;
import ntnu.idatt2003.group27.models.enums.LadderGameType;
import ntnu.idatt2003.group27.utils.filehandler.csv.PlayerCsvFileReader;
import ntnu.idatt2003.group27.utils.filehandler.csv.PlayerCsvFileWriter;
import ntnu.idatt2003.group27.view.MainMenuView;
import ntnu.idatt2003.group27.view.components.Alert;

/**
 * A controller class for managing the main menu of the ladder game application. It handles user
 * interactions with the {@link MainMenuView}, such as adding players, exporting/importing player
 * data, and starting games with different difficult levels.
 */
public class MainMenuController {
  /** The main menu view associated with this controller */
  private MainMenuView mainMenuView;

  /** A reference to the mainController */
  private final MainController mainController;

  /**
   * Constructs a  {@link MainMenuController} witht the specified {@link MainMenuView} and sets up
   * events handlers for menu interactions.
   *
   * @param mainMenuView The {@link MainMenuView} to be controlled.
   */
  public MainMenuController(MainController mainController, MainMenuView mainMenuView) {
    this.mainController = mainController;
    this.mainMenuView = mainMenuView;
    mainMenuView.setMainMenuController(this);
    mainMenuView.initializeLayout();
    setupMenuViewEventHandler();
  }

  /**
   * Sets up event handlers for the main menu view, including buttons for adding players, exporting/
   * importing player data, and starting games with different difficulty levels.
   */
  private void setupMenuViewEventHandler() {
    //Sets handler for add player button
    mainMenuView.setAddPlayerButtonHandler(e -> {
      System.out.println("Add player button clicked");

      if (mainController.getPlayers().size() >= 5) {
        System.out.println("Cannot add player, max player limit reached!");
        Alert alert = new Alert(
            this.mainMenuView.getRoot(),
            "Kan ikke legge til spiller",
            "Du har nådd maksgrensen på 5 spillere. Fjern en tidligere spiller dersom du vil endre på spillerene.",
            "Ok",
            "Lukk",
            response -> {
            }
        );
        alert.show();
        return;
      }

      String playerName = mainMenuView.getPlayerNameTextFieldValue();
      if (playerName.equals("")) {
        playerName = "Spiller " + (mainController.getPlayers().size() + 1);
      }

      if(mainController.getPlayers().stream().map(Player::getName).anyMatch(playerName::equals)) {
        System.out.println("Cannot add player, player name already in use!");
        Alert alert = new Alert(
            this.mainMenuView.getRoot(),
            "Kan ikke legge til spiller",
            "En spiller med samme navn finnes allerede!",
            "Ok",
            "Lukk",
            response -> {
            }
        );
        alert.show();
        return;
      }

      mainController.addPlayer(new Player(playerName));
    });

    //Sets handler for normal board button
    mainMenuView.setNormalBoardButtonHandler(e -> {
      System.out.println("Normal board button clicked");
      if (mainController.getPlayers().size() < 2) {
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
      mainController.switchToBoardGame(LadderGameType.NORMAL);
    });

    //Sets handler for crazy board button
    mainMenuView.setCrazyBoardButtonHandler(e -> {
      System.out.println("Crazy board button clicked");
      if (mainController.getPlayers().size() < 2) {
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
      mainController.switchToBoardGame(LadderGameType.CRAZY);
    });

    //Sets handler for IMPOSSIBLE board button
    mainMenuView.setImpossibleBoardButtonHandler(e -> {
      System.out.println("IMPOSSIBLE board button clicked");
      if (mainController.getPlayers().size() < 2) {
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
      mainController.switchToBoardGame(LadderGameType.IMPOSSIBLE);
    });

    //Sets handler for JSON board button
    mainMenuView.setJsonBoardButtonHandler(e -> {
      System.out.println("Json board button clicked");
      if (mainController.getPlayers().size() < 2) {
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
      mainController.switchToBoardGame(LadderGameType.NORMAL);
    });

    //Set export players csv file handler
    mainMenuView.setExportPlayersCsvButtonHandler(e -> {
      System.out.println("Export players csv button clicked");
      if (mainController != null){      PlayerCsvFileWriter csvFileWriter = new PlayerCsvFileWriter();
      try {
        csvFileWriter.writeFile("src/main/resources/csv/Exported_Players.csv",
            mainController.getPlayers().toArray(new Player[0]));
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
        if (mainController != null){
          mainController.setPlayers(players);
        }
      }
      catch (IOException ex) {
        System.out.println(ex.getMessage());
      }
    });

    mainMenuView.setSelectIconButtonHandlers(e -> {
      //gets an int from the specific button, alternatively
      //selectedPiece = MainController.pieceMap.get(i);
      //selected piece is used on creation
    }
    );
  }

  public MainController getMainController(){
    return mainController;
  }
}
