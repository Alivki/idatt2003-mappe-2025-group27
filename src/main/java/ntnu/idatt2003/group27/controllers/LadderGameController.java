package ntnu.idatt2003.group27.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.logging.Logger;
import ntnu.idatt2003.group27.models.*;
import ntnu.idatt2003.group27.models.enums.LadderGameType;
import ntnu.idatt2003.group27.models.exceptions.NotEnoughPlayersInGameException;
import ntnu.idatt2003.group27.models.interfaces.BoardGameObserver;
import ntnu.idatt2003.group27.models.interfaces.TileAction;
import ntnu.idatt2003.group27.view.LadderGameView;
import ntnu.idatt2003.group27.view.components.Alert;
import ntnu.idatt2003.group27.view.components.Toast;
import javafx.animation.PauseTransition;
import javafx.util.Duration;

/**
 * A controller class for managing the ladder game, implementing the MVC pattern. It handles game
 * logic, user interactions with the {@link LadderGameView}, and updates the view based on the game
 * event as an observer of the {@link LadderBoardGame}.
 *
 * @author Iver Lindholm, Amadeus Berg
 * @version 1.3
 * @since 2.0
 */
public class LadderGameController implements BoardGameObserver {
  /**
   * Logger instance for the {@link BoardGameController} class.
   * Used for logging informational messages and errors related to class operations.
   */
  private static final Logger logger = Logger.getLogger(LadderGameController.class.getName());
  /** The game model managing the ladder game logic */
  private LadderBoardGame game;
  /** The view for displaying the ladder game. */
  private LadderGameView ladderGameView;
  /** The last player who made a move */
  private Player lastPlayer;
  /** A boolean indicating whether the play button should be disabled */
  private boolean diablePlayButton = false;

  /** The main controller for coordinating application-wide actions */
  private final MainController mainController;

  /** The type of ladder game being played */
  private LadderGameType ladderGameType;

  private final BoardGameFactory boardGameFactory;

  /**
   * Constructs a {@link LadderGameController} with the specified {@link MainController}
   *
   * @param mainController The {@link MainController} for coordinating application-wide actions
   */
  public LadderGameController(MainController mainController) {
    logger.info("Initializing LadderGameController.");
    this.mainController = mainController;
    this.boardGameFactory = new BoardGameFactory(new BoardFactory());
  }

  /**
   * Initializes the ladder game with the specified game type and players, setting up the MVC pattern
   * and event handlers.
   *
   * @param ladderGameType The {@link LadderGameType} defining the game difficulty.
   * @param players An array of {@link Player} objects participating in the game.
   */
  public void InitializeGame(LadderGameType ladderGameType, Player[] players) {
    this.ladderGameType = ladderGameType;
    // initializes mvc pattern
    game = null;
    try {
      game = boardGameFactory.createLadderGame(ladderGameType);
    } catch (IllegalArgumentException e) {
      System.err.println("Error creating game: " + e.getMessage());
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    if (game == null) {
      return;
    }

    ladderGameView = new LadderGameView();

    game.addObserver(this);

    setupLadderViewEventHandlers();

    //Add players to game
    Arrays.stream(players).forEach(player -> {game.addPlayer(player);});


    // change when being done in controller on game difficult select
    try {
      game.setUpGame();
    } catch (NotEnoughPlayersInGameException e) {
      System.err.println(e.getMessage());
    }
  }

  /**
   * Restarts the current game, resetting the game state and updating the view.
   */
  public void RestartGame(){
    logger.fine("Restart game.");
    game.restartGame();
  }

  /**
   * Sets up events handlers for the ladder game view, including actions for rolling the dice,
   * restarting the game, and returning to the main menu.
   */
  private void setupLadderViewEventHandlers() {
    ladderGameView.setRollDiceHandler(e -> {
      logger.fine("Roll dice button clicked.");
      try {
        game.play();
      } catch (NotEnoughPlayersInGameException error) {
        //switch to main menu when possible

        ladderGameView.showToast(
            Toast.ToastVariant.ERROR,
            "Feil",
            "Det oppstod en feil under spillingen: " + error.getMessage()
        );
      }
    });

    ladderGameView.setRestartButtonHandler(e -> {
      logger.fine("Restart button clicked.");
      Alert alert = new Alert(
          this.ladderGameView.getRoot(),
          "Bekreft restart",
          "Er du sikker på at du vil starte spillet på nytt?",
          "Ja",
          "Nei",
          response -> {
            if (response) {
              RestartGame();
            }
          }
      );
      alert.show();
    });

    ladderGameView.setHomeButtonHandler(e -> {
      logger.fine("Home button clicked.");
      Alert alert = new Alert(
          this.ladderGameView.getRoot(),
          "Bekreft avslutning",
          "Er du sikker på at du vil avslutte spillet?",
          "Ja",
          "Nei",
          response -> {
            if (response) {
              //Loads main menu
              mainController.switchToMainMenu();
            }
          }
      );
      alert.show();
    });
  }

  /**
   * Handles the event when a round is played, updating the view with the current player, roll
   * result, and tile action information.
   *
   * @param players List of all {@link Player} in the game.
   * @param currentPlayer The {@link Player} next player to play.
   * @param roll The result of the dice roll for the round.
   * @param tileAction The {@link TileAction} triggered by the player's move, or null if none.
   */
  @Override
  public void onRoundPlayed(ArrayList<Player> players, Player currentPlayer, int roll, TileAction tileAction) {
    logger.fine("On round played.");
    ladderGameView.toggleDiceButton(false);

    int round = ladderGameView.getRoundLabel() + 1;
    ladderGameView.rotateDice(roll);

    int currentPlayerIndex = players.indexOf(currentPlayer);
    int lastPlayerIndex = (currentPlayerIndex - 1 + players.size()) % players.size();
    lastPlayer = players.get(lastPlayerIndex);

    int destinationTileId = lastPlayer.getCurrentTile().getTileId();

    PauseTransition delay = new PauseTransition(Duration.millis(400));
    delay.setOnFinished(event -> {
      ladderGameView.updateCurrentPlayerLabel(currentPlayer.getName());
      ladderGameView.updateLastPlayerLabel(lastPlayer.getName());
      ladderGameView.updateLastRollLabel(String.valueOf(roll));
      ladderGameView.updateMovedToLabel(String.valueOf(destinationTileId));
      if (tileAction != null) {
        String action = tileAction.getClass().getSimpleName();
        String formattedAction = action.replaceAll("(?=\\p{Upper})", " ").trim();
        ladderGameView.updateTileActionLabel(formattedAction);
      } else {
        ladderGameView.updateTileActionLabel("Ingen");
      }
      ladderGameView.animatePlayerMovement(
          lastPlayer,
          destinationTileId,
          tileAction,
          roll,
          players,
          () -> {
            ladderGameView.updateRoundLabel(String.valueOf(round));
            ladderGameView.populatePlayerList(players);
          }
      );
    });
    delay.play();
  }

  /**
   * Handles the event when a player wins the game, updating the view and showing a victory alert.
   *
   * @param player The {@link Player} who has won.
   */
  @Override
  public void onPlayerWon(Player player) {
    logger.fine("On player won. Player: " + player.getName());
    ladderGameView.toggleDiceButton(false);
    ladderGameView.updateStatusLabel("Avsluttet");
    ladderGameView.showToast(Toast.ToastVariant.SUCCESS, "Spiller vant",
        player.getName() + " vant spillet! Gratulerer!");
    Alert alert = new Alert(
        this.ladderGameView.getRoot(),
        "Spiller vant",
        player.getName() + " vant spillet!",
        "Tilbake til hovedmeny",
        "Restart",
        response -> {
          if (response) {
            // go to main menu
            mainController.switchToMainMenu();
          } else {
            // restart game
            RestartGame();
          }
        }
    );
    alert.show();
  }

  /**
   * Handles the event when the game is set up, initializing the view with the initial game state.
   *
   * @param players The list of players participating in the game.
   */
  @Override
  public void onGameSetup(ArrayList<Player> players, Map<Player, Board> boards) {
    logger.fine("On game setup.");
    ladderGameView.createBoard(players, boards.values().stream().findFirst().get().getTiles());
    ladderGameView.populatePlayerList(players);
    ladderGameView.updateCurrentPlayerLabel(players.getFirst().getName());

    // set the diffuculty level when there is a way to
    ladderGameView.updateGradeLabel(ladderGameType.name());
  }

  /**
   * Handles the event when the game is restarted, resetting the view to the initial game state.
   *
   * @param players The list of {@link Player} objects in the game.
   */
  @Override
  public void onGameRestart(ArrayList<Player> players, Map<Player, Board> boards){
    logger.fine("On game restart.");
    ladderGameView.toggleDiceButton(true);
    ladderGameView.updateCurrentPlayerLabel(players.getFirst().getName());
    ladderGameView.updateRoundLabel("1");
    ladderGameView.updateLastPlayerLabel("Ingen");
    ladderGameView.updateLastRollLabel("Ikke kastet");
    ladderGameView.updateMovedToLabel("Start");
    ladderGameView.updateBoard(players);
    ladderGameView.populatePlayerList(players);
    ladderGameView.updateStatusLabel("Pågående");
  }

  /**
   * Retrievers the ladder game view associated with this controller.
   *
   * @return The {@link LadderGameView} instance.
   */
  public LadderGameView getView() {
    logger.fine("Get view.");
    return ladderGameView;
  }
}
