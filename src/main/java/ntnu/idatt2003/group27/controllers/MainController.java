package ntnu.idatt2003.group27.controllers;

import java.util.ArrayList;
import java.util.Collections;
import ntnu.idatt2003.group27.models.Piece;
import ntnu.idatt2003.group27.models.Player;
import ntnu.idatt2003.group27.models.enums.LadderGameType;
import ntnu.idatt2003.group27.models.enums.MathGameType;
import ntnu.idatt2003.group27.view.SceneManager;
import java.util.logging.Logger;

/**
 * A controller class for managing the core functionality of the ladder game application.
 * It handles player management, swapping scenes, and game initialization.
 */
public class MainController {
  /**
   * Logger instance for the {@link MainController} class.
   * Used for logging informational messages and errors related to class operations.
   */
  private static final Logger logger = Logger.getLogger(MainController.class.getName());

  /** The lost of players in the game. */
  private final ArrayList<Player> players = new ArrayList<>();

  /** A list of all pieces available in the application*/
  private final ArrayList<Piece> pieces = new ArrayList<>();

  /** The scene manager responsible for handling screen transitions */
  private final SceneManager sceneManager;
  /** The controller for the snakes and ladder board game. */
  private LadderGameController ladderGameController;

  /** The controller for the math game. */
  private MathGameController mathGameController;

  /** The max players for the game. */
  private final int maxPlayers = 5;

  /**
   * Constructs a {@link MainController} and initializes it as the singleton instance of nine exists.
   *
   * @param sceneManager The {@link SceneManager} instance used for managing scene transitions.
   */
  public MainController(SceneManager sceneManager) {
    logger.info("Initializing Main Controller");
    this.sceneManager = sceneManager;
    InitializePieces();
  }

  /**
   * Switches the application to a board game scene with the specified game type and players.
   *
   * @param ladderGameType The {@link LadderGameType} defining the type of game to be initialized.
   */
  public void switchToLadderBoardGame(LadderGameType ladderGameType) {
    logger.info("Switching to ladder board game.");
    ladderGameController = new LadderGameController(this);
    ladderGameController.InitializeGame(ladderGameType, getPlayerArray());
    sceneManager.switchSceneImmediate(ladderGameController.getView().getRoot());
  }

  /**
   * Switches the application to a math board game scene with the specified game type and players.
   *
   * @param mathGameType The {@link MathGameType} defining the type of game to be initialized.
   */
  public void switchToMathBoardGame(MathGameType mathGameType) {
    logger.info("Switching to math board game.");
    mathGameController = new MathGameController(this);
    mathGameController.InitializeGame(mathGameType, getPlayerArray());
    sceneManager.switchSceneImmediate(mathGameController.getView().getRoot());
  }

  /**
   * Switches the application to the main menu scene.
   */
  public void switchToMainMenu(){
    logger.info("Switching to main menu.");
    sceneManager.switchToMainMenu();
  }

  /**
   * Retrieves and array of players currently in the game.
   *
   * @return An array of {@link Player} objects.
   */
  public ArrayList<Player> getPlayers(){
    logger.fine("Getting players.");
    return players;
  }

  /**
   * Retrieves an array of players currently in the game.
   *
   * @return An array of {@link Player} objects.
   */
  public Player[] getPlayerArray(){
    logger.fine("Getting player array.");
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
    logger.fine("Adding player to list.");
    players.add(player);
    System.out.println("Player added: " + player.getName());
  }

  /**
   * Removes a player from the game.
   *
   * @param player The {@link Player} to add to the game.
   */
  public void removePlayer(Player player){
    logger.fine("Removing player from list.");
    players.remove(player);
  }

  /**
   * Retrieves the list of pieces available in the game.
   *
   * @return An {@link ArrayList} of {@link Piece} objects.
   */
  public ArrayList<Piece> getPieces(){
    logger.fine("Getting pieces.");
    return pieces;
  }

  /**
   * Sets the list of players, replacing the current list with the provided array.
   *
   * @param players An array of {@link Player} objects to set as the new player list.
   */
  public void setPlayers(Player[] players){
    logger.info("Setting players.");
    this.players.clear();
    Collections.addAll(this.players, players);
  }

  /**
   * Initializes the game pieces and puts them into a list.
   */
  private void InitializePieces(){
    logger.info("Initializing pieces.");
    pieces.clear();
    pieces.add(new Piece("Car", "/icons/player_icons/jeep.png"));
    pieces.add(new Piece("Chicken", "/icons/player_icons/chicken.png"));
    pieces.add(new Piece("Frisbee", "/icons/player_icons/frisbee.png"));
    pieces.add(new Piece("Pawn", "/icons/player_icons/chess-pawn.png"));
    pieces.add(new Piece("Tophat", "/icons/player_icons/top-hat.png"));
  }

  /**
   * Returns the maximum number of players allowed.
   *
   * @return the maximum number of players
   */
  public int getMaxPlayers() {
    logger.info("Getting max players.");
    return this.maxPlayers;
  }

}
