package ntnu.idatt2003.group27.controllers;

import java.util.HashMap;
import java.util.Map;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Stage;
import ntnu.idatt2003.group27.models.Piece;
import ntnu.idatt2003.group27.models.Player;
import ntnu.idatt2003.group27.models.enums.LadderGameType;
import ntnu.idatt2003.group27.view.SceneManager;

/**
 * A singleton controller class for managing the core functionality of the ladder game application.
 * It handles player management, scene switching, and game initialization.
 */
public class MainController {
  /** The observable list of players in the game. */
  private ObservableList<Player> playersObservableList = FXCollections.observableArrayList();

  /** The HashMap for retrieving piece instances from an integer */
  private Map<Integer, Piece> pieceMap = new HashMap<Integer, Piece>();

  /** The scene manager responsible for handling screen transitions */
  private SceneManager sceneManager;
  /** The controller for the board game. */
  private BoardGameController boardGameController;

  /**
   * Constructs a {@link MainController} and initializes it as the singleton instance of nine exists.
   */
  public MainController(SceneManager sceneManager) {
    this.sceneManager = sceneManager;
    InitializePieces();
  }

  /**
   * Switches the application to a board game scene with the specified game type and players.
   *
   * @param ladderGameType The {@link LadderGameType} defining the type of game to be initialized.
   */
  public void switchToBoardGame(LadderGameType ladderGameType) {
    boardGameController = new BoardGameController(this);
    boardGameController.InitializeGame(ladderGameType, getPlayerArray());
    sceneManager.switchSceneImmediate(boardGameController.getView().getRoot());
  }

  /**
   * Switches the application to the main menu scene.
   */
  public void switchToMainMenu(){
    sceneManager.switchToMainMenu();
  }

  /**
   * Retrieves and array of players currently in the game.
   *
   * @return An array of {@link Player} objects.
   */
  public ObservableList<Player> getPlayers(){
    return playersObservableList;
  }

  /**
   * Retrieves an array of players currently in the game.
   *
   * @return An array of {@link Player} objects.
   */
  public Player[] getPlayerArray(){
    Player[] players = new Player[playersObservableList.size()];
    players = playersObservableList.toArray(players);
    return players;
  }

  /**
   * Adds a player to the game.
   *
   * @param player The {@link Player} object to be added.
   */
  public void addPlayer(Player player){
    playersObservableList.add(player);
    System.out.println("Player added: " + player.getName());
  }

  /**
   * Removes a player from the game.
   *
   * @param player The {@link Player} to add to the game.
   */
  public void removePlayer(Player player){
    playersObservableList.remove(player);
  }

  /**
   * Sets the list of players, replacing the current list with the provided array.
   *
   * @param players An array of {@link Player} objects to set as the new player list.
   */
  public void setPlayers(Player[] players){
    playersObservableList.clear();
    playersObservableList.addAll(players);
  }


  /**
   * Initializes the game pieces and puts them into a hashmap with the correct key
   */
  private void InitializePieces(){
    pieceMap.put(0, new Piece("Car", "icons/player_icons/jeep.png"));
    pieceMap.put(1, new Piece("Chicken", "icons/player_icons/chicken.png"));
    pieceMap.put(2, new Piece("Frisbee", "icons/player_icons/frisbee.png"));
    pieceMap.put(3, new Piece("Pawn", "icons/player_icons/chess-pawn.png"));
    pieceMap.put(4, new Piece("Tophat", "icons/player_icons/top-hat.png"));
  }
}
