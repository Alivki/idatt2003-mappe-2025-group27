package ntnu.idatt2003.group27.controllers;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.paint.Color;
import ntnu.idatt2003.group27.models.Piece;
import ntnu.idatt2003.group27.models.Player;
import ntnu.idatt2003.group27.models.enums.LadderGameType;
import ntnu.idatt2003.group27.models.enums.MathGameType;
import ntnu.idatt2003.group27.models.RandomColor;
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
  /** The main menu view associated with this controller. */
  private MainMenuView mainMenuView;

  /** A reference to the mainController */
  private final MainController mainController;

  /** The currently selected piece from the icon menu. */
  private Piece selectedPiece;

  /**
   * Constructs a  {@link MainMenuController} witht the specified {@link MainMenuView} and sets up
   * events handlers for menu interactions.
   *
   * @param mainMenuView The {@link MainMenuView} to be controlled.
   */
  public MainMenuController(MainController mainController, MainMenuView mainMenuView) {
    this.mainController = mainController;
    this.mainMenuView = mainMenuView;
    mainMenuView.initializeLayout(mainController.getPieces());
    setupMenuViewEventHandler();
    mainMenuView.populatePlayerList(mainController.getPlayers());
  }

  /**
   * Sets up event handlers for the main menu view, including buttons for adding players, exporting/
   * importing player data, and starting games with different difficulty levels.
   */
  private void setupMenuViewEventHandler() {
    setColorPickerButtonHandler();
    setAddPlayerButtonHandler();
    setImportPlayerCsvButtonHandler();
    setExportPlayerCsvButtonHandler();
    setLadderGameSelectionMenuButtonHandler();
    setMathGameSelectionMenuButtonHandler();
    setNormalLadderGameBoardButtonHandler();
    setCrazyLadderGameBoardButtonHandler();
    setImpossibleLadderGameBoardButtonHandler();
    setJsonLadderGameBoardButtonHandler();
    setEasyMathGameBoardButtonHandler();
    setMediumMathGameBoardButtonHandler();
    setHardMathGameBoardButtonHandler();
    setJsonLadderGameBoardButtonHandler();
    //Dynamically sets appropritate piece button handlers.
    for(int i = 0; i < mainController.getPieces().size(); i++) {
      setSelectPieceButtonHandler(i);
    }
  }

  /**
   * Shows an alert to the user requiring more players to start a game.
   * @param requiredAmountOfPlayers
   */
  private void showNotEnoughPlayersAlert(int requiredAmountOfPlayers){
    System.out.println("Not enough players to start game!");
    Alert alert = new Alert(
        this.mainMenuView.getRoot(),
        "Ikke nok spillere",
        String.format("Minst %d spillere trengs for å starte spillet!", requiredAmountOfPlayers),
        "Ok",
        "Lukk",
        response -> {}
    );
    alert.show();
  }

  private void setLadderGameSelectionMenuButtonHandler(){
    mainMenuView.setLadderGameSelectionMenuButtonHandler(e -> {
      mainMenuView.switchToGameLadder();
    });
  }

  private void setMathGameSelectionMenuButtonHandler(){
    mainMenuView.setMathGameSelectionMenuButtonHandler(e -> {
      mainMenuView.switchToGameMath();
    });
  }

  private void setNormalLadderGameBoardButtonHandler(){
    //Sets handler for normal board button
    mainMenuView.setNormalLadderGameBoardButtonHandler(e -> {
      System.out.println("Normal board button clicked");
      if (mainController.getPlayers().size() < 2) {
        showNotEnoughPlayersAlert(2);
        return;
      }
      mainController.switchToLadderBoardGame(LadderGameType.NORMAL);
    });
  }

  private void setCrazyLadderGameBoardButtonHandler(){
    //Sets handler for crazy board button
    mainMenuView.setCrazyLadderGameBoardButtonHandler(e -> {
      System.out.println("Crazy board button clicked");
      if (mainController.getPlayers().size() < 2) {
        showNotEnoughPlayersAlert(2);
        return;
      }
      mainController.switchToLadderBoardGame(LadderGameType.CRAZY);
    });
  }

  private void setImpossibleLadderGameBoardButtonHandler(){
    //Sets handler for IMPOSSIBLE board button
    mainMenuView.setImpossibleLadderGameBoardButtonHandler(e -> {
      System.out.println("IMPOSSIBLE board button clicked");
      if (mainController.getPlayers().size() < 2) {
        showNotEnoughPlayersAlert(2);
        return;
      }
      mainController.switchToLadderBoardGame(LadderGameType.IMPOSSIBLE);
    });
  }

  /**
   * Sets up handlers for Json ladderGame board button.
   */
  private void setJsonLadderGameBoardButtonHandler(){
    //Sets handler for JSON board button
    mainMenuView.setJsonLadderGameBoardButtonHandler(e -> {
      System.out.println("Json board button clicked");
      if (mainController.getPlayers().size() < 2) {
        showNotEnoughPlayersAlert(2);
        return;
      }
      mainController.switchToLadderBoardGame(LadderGameType.JSON);
    });
  }

  private void setEasyMathGameBoardButtonHandler(){
    mainMenuView.setEasyMathButtonHandler(e -> {
      if (mainController.getPlayers().size() < 2) {
        showNotEnoughPlayersAlert(2);
        return;
      }
      mainController.switchToMathBoardGame(MathGameType.EASY);
    });
  }

  private void setMediumMathGameBoardButtonHandler(){
    mainMenuView.setMediumMathButtonHandler(e -> {
      if (mainController.getPlayers().size() < 2) {
        showNotEnoughPlayersAlert(2);
        return;
      }
      mainController.switchToMathBoardGame(MathGameType.MEDIUM);
    });
  }

  private void setHardMathGameBoardButtonHandler(){
    mainMenuView.setHardMathButtonHandler(e -> {
      if (mainController.getPlayers().size() < 2) {
        showNotEnoughPlayersAlert(2);
        return;
      }
      mainController.switchToMathBoardGame(MathGameType.HARD);
    });
  }

  /**
   * Sets up handlers for add player button.
   */
  private void setAddPlayerButtonHandler(){
    mainMenuView.setAddPlayerButtonHandler(e -> {
      System.out.println("Add player button clicked");

      //Show alert if adding player crosses player limit.
      if (mainController.getPlayers().size() >= mainController.getMaxPlayers()) {
        System.out.println("Cannot add player, max player limit reached!");
        Alert alert = new Alert(
            this.mainMenuView.getRoot(),
            "Kan ikke legge til spiller",
            "Du har nådd maksgrensen på "+ mainController.getMaxPlayers() +" spillere. Fjern en tidligere spiller dersom du vil endre på spillerene.",
            "Ok",
            "Lukk",
            response -> {
            }
        );
        alert.show();
        return;
      }

      String playerName = mainMenuView.getPlayerNameTextFieldValue();

      //Set a unique default player name if player is already added.
      if (playerName.equals("")) {
        for(int i = 0; i < mainController.getPlayers().size() + 1; i++) {
          String generatedPlayerName = "Spiller " + (i + 1);
          playerName = generatedPlayerName;
          if (mainController.getPlayers().stream()
              .noneMatch(player -> player.getName().equals(generatedPlayerName))) {
            break;
          }
        }
      }

      //Show alert if player with name already exists
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

      Piece piece = selectedPiece;

      //Show alert if no piece is selected.
      if (selectedPiece == null) {
        System.out.println("Cannot add player, no piece selected!");
        Alert alert = new Alert(
            this.mainMenuView.getRoot(),
            "Kan ikke legge til spiller",
            "Vennligst velg en brikke!",
            "Ok",
            "Lukk",
            response -> {
            }
        );
        alert.show();
        return;
      }

      //Show alert if piece is already selected by another player.
      if (mainController.getPlayers().stream().anyMatch(p -> p.getPiece() == piece)){
        System.out.println("Cannot add player, player piece already in use!");
        Alert alert = new Alert(
            this.mainMenuView.getRoot(),
            "Kan ikke legge til spiller",
            "En spiller med samme brikke finnes allerede!",
            "Ok",
            "Lukk",
            response -> {
            }
        );
        alert.show();
        return;
      }

      Color color = mainMenuView.getPickedColor();

      if (color == null) {
        color = RandomColor.generateRandomColor().getColor();
      }

      Player newPlayer = new Player(playerName, piece, color);
      System.out.println("New player created: " + newPlayer.getColor());
      mainController.addPlayer(newPlayer);
      UpdatePlayerListDisplay();
      mainMenuView.setDisablePlayerPieceButton(mainController.getPieces().indexOf(selectedPiece), true);
      mainMenuView.setPickedColor(null);
      mainMenuView.removePickedColor();
    });
  }

  /**
   * Sets up actionEvent handlers for color picker button.
   */
  private void setColorPickerButtonHandler() {
    mainMenuView.setColorPickerButtonHandler(event -> {
      mainMenuView.showColorPicker();
    });
  }

  /**
   * Sets up actionEvent handler for export players button.
   */
  private void setExportPlayerCsvButtonHandler(){
    //Set export players csv file handler
    mainMenuView.setExportPlayersCsvButtonHandler(e -> {
      System.out.println("Export players csv button clicked");

      if (mainController.getPlayers().isEmpty()) {
        Alert alert = new Alert(
            this.mainMenuView.getRoot(),
            "Ingen spillere",
            "Ingen spillere å eksportere",
            "Ok",
            "Lukk",
            response -> {
            }
        );
        alert.show();
        return;
      }

      //Open fileChooser for saving
      FileChooser fileChooser = new FileChooser();
      fileChooser.setTitle("Save player csv file");
      fileChooser.getExtensionFilters().add(
          new FileChooser.ExtensionFilter("CSV file", "*.csv"));
      Stage stage = (Stage)mainMenuView.getRoot().getScene().getWindow();
      File selectedFile = fileChooser.showSaveDialog(stage);

      //Save file to selected path
      if (selectedFile != null && mainController != null){
        PlayerCsvFileWriter csvFileWriter = new PlayerCsvFileWriter();
        try {
          csvFileWriter.writeFile(selectedFile.getAbsolutePath(),
              mainController.getPlayers().toArray(new Player[0]));
        } catch (IOException ex) {
          System.out.println(ex.getMessage());
        }
      }
    });
  }

  /**
   * Sets up actionEvent handler for import players button.
   */
  private void setImportPlayerCsvButtonHandler(){
    //Set import players csv file handler
    mainMenuView.setImportPlayersCsvButtonHandler(e -> {
      System.out.println("Import players csv button clicked");

      //Opens an explorer window to select a file from the system.
      FileChooser fileChooser = new FileChooser();
      fileChooser.setTitle("Import player csv file");
      fileChooser.getExtensionFilters().addAll(
          new FileChooser.ExtensionFilter("Csv files","*.csv")
      );

      Stage stage = (Stage)mainMenuView.getRoot().getScene().getWindow();
      File selectedFile = fileChooser.showOpenDialog(stage);
      if (selectedFile != null) {
        System.out.println("File selected: " + selectedFile.getAbsolutePath());

        //Reads the csv file and adds players
        PlayerCsvFileReader fileReader = new PlayerCsvFileReader(mainController.getPieces().toArray(new Piece[0]));
        try {
          Player[] players = fileReader.readFile(selectedFile.getAbsolutePath());
          //Shortens array to the max player amount
          players = Arrays.copyOf(players, Math.min(players.length, mainController.getMaxPlayers()));

          mainMenuView.setDisableAllPlayerPieceButtons(false);
          if (mainController != null){
            mainController.setPlayers(players);
            for(int i = 0; i < players.length; i++) {
              Player player = players[i];
              if (player != null) {
                System.out.println(
                    "Adding player: " + player.getName() + ", piece: " + player.getPiece());

                Piece piece = player.getPiece();
                if (piece != null && mainController.getPieces().contains(piece)) {
                  mainMenuView.setDisablePlayerPieceButton(
                      mainController.getPieces().indexOf(piece),
                      true);
                }
              }
            }
          }
          UpdatePlayerListDisplay();
        }
        catch (Exception ex) {
          Alert alert = new Alert(
              this.mainMenuView.getRoot(),
              "Kan ikke legge til spillere",
              ex.getMessage(),
              "Ok",
              "Lukk",
              response -> {
              }
          );
          alert.show();
        }
      }
    });
  }

  /**
   * Sets up actionEvent handlers for select piece button.
   *
   * @param i The index of the piece in the list of pieces.
   */
  private void setSelectPieceButtonHandler(int i){
    Piece piece = mainController.getPieces().get(i);
    mainMenuView.setPlayerPieceButtonHandlers(i, e -> {
          System.out.println("Selected piece: " + piece.getName());
          selectedPiece = piece;
        }
    );
  }

  /**
   * Sets up actionEvent handlers for remove player buttons.
   */
  private void setRemovePlayerButtonHandlers(){
    mainController.getPlayers().forEach(player -> {
      mainMenuView.setRemovePlayerButtonHandler(player, e -> {
        System.out.println("Remove player button clicked for player: " + player.getName());
        mainController.removePlayer(player);
        UpdatePlayerListDisplay();
        mainMenuView.setDisablePlayerPieceButton(mainController.getPieces().indexOf(player.getPiece()), false);
      });
    });
  }

  /**
   * Updates the player list to display correct player information.
   */
  private void UpdatePlayerListDisplay(){
    mainMenuView.populatePlayerList(mainController.getPlayers());
    setRemovePlayerButtonHandlers();
  }

  public MainController getMainController(){
    return mainController;
  }
}
