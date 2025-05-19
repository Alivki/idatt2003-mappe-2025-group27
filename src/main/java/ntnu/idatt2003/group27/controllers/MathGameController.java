package ntnu.idatt2003.group27.controllers;

import ntnu.idatt2003.group27.models.*;
import ntnu.idatt2003.group27.models.enums.LadderGameType;
import ntnu.idatt2003.group27.models.enums.MathGameType;
import ntnu.idatt2003.group27.models.exceptions.NotEnoughPlayersInGameException;
import ntnu.idatt2003.group27.models.interfaces.BoardGameObserver;
import ntnu.idatt2003.group27.models.interfaces.TileAction;
import ntnu.idatt2003.group27.view.LadderGameView;
import ntnu.idatt2003.group27.view.MathGameView;
import ntnu.idatt2003.group27.view.components.Alert;
import ntnu.idatt2003.group27.view.components.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * A controller class for managing the ladder game, implementing the MVC pattern. It handles game
 * logic, user interactions with the {@link LadderGameView}, and updates the view based on the game
 * event as an observer of the {@link LadderBoardGame}.
 *
 * @author Iver Lindholm, Amadeus Berg
 * @version 1.3
 * @since 2.0
 */
public class MathGameController implements BoardGameObserver {
  /** The game model managing the ladder game logic */
  private MathBoardGame game;
  /** The view for displaying the ladder game. */
  private MathGameView mathGameView;

  /** The main controller for coordinating application-wide actions */
  private final MainController mainController;

  /** The type of ladder game being played */
  private MathGameType mathGameType;

  private final BoardGameFactory boardGameFactory;

  /**
   * Constructs a {@link MathGameController} with the specified {@link MainController}
   *
   * @param mainController The {@link MainController} for coordinating application-wide actions
   */
  public MathGameController(MainController mainController) {
    this.mainController = mainController;
    this.boardGameFactory = new BoardGameFactory(new BoardFactory());
  }

  /**
   * Initializes the ladder game with the specified game type and players, setting up the MVC pattern
   * and event handlers.
   *
   * @param mathGameType The {@link LadderGameType} defining the game difficulty.
   * @param players An array of {@link Player} objects participating in the game.
   */
  public void InitializeGame(MathGameType mathGameType, Player[] players) {
    this.mathGameType = mathGameType;
    // initializes mvc pattern
    game = null;
    try {
      game = boardGameFactory.createMathGame(mathGameType, players);
    } catch (IllegalArgumentException e) {
      System.err.println("Error creating game: " + e.getMessage());
    }

    if (game == null) {
      return;
    }

    mathGameView = new MathGameView();

    game.addObserver(this);

    setupMathViewEventHandlers();

    List<Board> boards = game.getBoards().values().stream().toList();
    game.addPlayers(boards, Arrays.stream(players).toList());

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
    game.restartGame();
  }

  /**
   * Sets up events handlers for the ladder game view, including actions for rolling the dice,
   * restarting the game, and returning to the main menu.
   */
  private void setupMathViewEventHandlers() {
    mathGameView.setPlayButtonHandler(e -> {
      try {
        game.play();
      } catch (NotEnoughPlayersInGameException error) {
        mathGameView.showToast(
            Toast.ToastVariant.ERROR,
            "Feil",
            "Det oppstod en feil under spillingen: " + error.getMessage()
        );
      }
      String question = game.getMathQuestion();
      mathGameView.roundPlay(question);
    });

    mathGameView.setAnswerButtonHandler(e -> {
      String answer = mathGameView.getAnswer();
      try {
        Integer.parseInt(answer);
      } catch (NumberFormatException error) {
        mathGameView.showToast(
            Toast.ToastVariant.ERROR,
            "Feil",
            "Ugyldig svar. Vennligst skriv inn et gyldig tall."
        );
        return;
      }
      game.checkAnswer(answer);
      mathGameView.betweenRounds();
    });

    mathGameView.setRestartButtonHandler(e -> {
      Alert alert = new Alert(
          this.mathGameView.getRoot(),
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

    mathGameView.setHomeButtonHandler(e -> {
      Alert alert = new Alert(
          this.mathGameView.getRoot(),
          "Bekreft avslutning",
          "Er du sikker på at du vil avslutte spillet?",
          "Ja",
          "Nei",
          response -> {
            if (response) {
              //Loads main menu
              System.out.println("Home button clicked");
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
    int round = mathGameView.getRoundLabel() + 1;

    int currentPlayerIndex = players.indexOf(currentPlayer);
    int lastPlayerIndex = (currentPlayerIndex - 1 + players.size()) % players.size();

    mathGameView.updateCurrentPlayerLabel(currentPlayer.getName());
  }

  /**
   * Handles the event when a player wins the game, updating the view and showing a victory alert.
   *
   * @param player The {@link Player} who has won.
   */
  @Override
  public void onPlayerWon(Player player) {
    mathGameView.updateStatusLabel("Avsluttet");
    mathGameView.showToast(Toast.ToastVariant.SUCCESS, "Spiller vant",
        player.getName() + " vant spillet! Gratulerer!");
    Alert alert = new Alert(
        this.mathGameView.getRoot(),
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
    mathGameView.populatePlayerList(players);
    mathGameView.updateCurrentPlayerLabel(players.getFirst().getName());

    mathGameView.updateGradeLabel(mathGameType.name());
  }

  /**
   * Handles the event when the game is restarted, resetting the view to the initial game state.
   *
   * @param players The list of {@link Player} objects in the game.
   */
  @Override
  public void onGameRestart(ArrayList<Player> players, Map<Player, Board> boards){
    mathGameView.updateCurrentPlayerLabel(players.getFirst().getName());
    mathGameView.updateRoundLabel("1");
    mathGameView.populatePlayerList(players);
    mathGameView.updateStatusLabel("Pågående");
  }

  /**
   * Retrievers the ladder game view associated with this controller.
   *
   * @return The {@link LadderGameView} instance.
   */
  public MathGameView getView() {
    return mathGameView;
  }
}
