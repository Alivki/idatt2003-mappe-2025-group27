package ntnu.idatt2003.group27.controllers;

import java.util.ArrayList;
import java.util.Collections;
import ntnu.idatt2003.group27.models.Piece;
import ntnu.idatt2003.group27.models.Player;
import ntnu.idatt2003.group27.models.enums.LadderGameType;
import ntnu.idatt2003.group27.view.SceneManager;

/**
 * A singleton controller class for managing the core functionality of the ladder game application.
 * It handles player management, scene switching, and game initialization.
 */
public class MainController {
  /** The lost of players in the game. */
  private ArrayList<Player> players = new ArrayList<>();

  /** A list of all pieces available in the application*/
  private ArrayList<Piece> pieces = new ArrayList<>();

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
  public ArrayList<Player> getPlayers(){
    return players;
  }

  /**
   * Retrieves an array of players currently in the game.
   *
   * @return An array of {@link Player} objects.
   */
  public Player[] getPlayerArray(){
    Player[] players = new Player[this.players.size()];
    players = this.players.toArray(players);
    return players;
  }

  /**
   * Adds a player to the game.
   *
   * @param player The {@link Player} object to be added.
   */
  public void addPlayer(Player player){
    players.add(player);
    System.out.println("Player added: " + player.getName());
  }

  /**
   * Removes a player from the game.
   *
   * @param player The {@link Player} to add to the game.
   */
  public void removePlayer(Player player){
    players.remove(player);
  }


  public ArrayList<Piece> getPieces(){
    return pieces;
  }

  /**
   * Sets the list of players, replacing the current list with the provided array.
   *
   * @param players An array of {@link Player} objects to set as the new player list.
   */
  public void setPlayers(Player[] players){
    this.players.clear();
    Collections.addAll(this.players, players);
  }


  /**
   * Initializes the game pieces and puts them into a hashmap with the correct key
   */
  private void InitializePieces(){
    pieces.clear();
    pieces.add(new Piece("Car", "/icons/player_icons/jeep.png"));
    pieces.add(new Piece("Chicken", "/icons/player_icons/chicken.png"));
    pieces.add(new Piece("Frisbee", "/icons/player_icons/frisbee.png"));
    pieces.add(new Piece("Pawn", "/icons/player_icons/chess-pawn.png"));
    pieces.add(new Piece("Tophat", "/icons/player_icons/top-hat.png"));
  }

  /**
   * Gets the first piece not used by another player, returns null if none.
   */
  public Piece getAvailablePiece() {
    return pieces.stream()
        .filter(piece -> players.stream()
        .noneMatch(player -> player.getPiece().equals(piece)))
        .findFirst()
        .orElse(null);
  }
}
