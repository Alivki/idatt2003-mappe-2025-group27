package ntnu.idatt2003.group27.controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import ntnu.idatt2003.group27.models.BoardGame;
import ntnu.idatt2003.group27.models.BoardGameFactory;
import ntnu.idatt2003.group27.models.Player;
import ntnu.idatt2003.group27.models.Tile;
import ntnu.idatt2003.group27.models.enums.LadderGameType;
import ntnu.idatt2003.group27.models.exceptions.NotEnoughPlayersInGameException;
import ntnu.idatt2003.group27.models.exceptions.UnknownLadderGameTypeExceptions;
import ntnu.idatt2003.group27.models.interfaces.BoardGameObserver;
import ntnu.idatt2003.group27.models.interfaces.TileAction;
import ntnu.idatt2003.group27.view.LadderGameView;
import ntnu.idatt2003.group27.view.components.Alert;
import ntnu.idatt2003.group27.view.components.Toast;
import javafx.animation.PauseTransition;
import javafx.util.Duration;

public class BoardGameController implements BoardGameObserver {
  private BoardGame game;
  private LadderGameView ladderGameView;
  private Player lastPlayer;

  public BoardGameController() {
  }

  public void InitializeGame(LadderGameType ladderGameType){
    // initialize mvc
    game = null;
    try {
      game = BoardGameFactory.createLadderGame(ladderGameType);
    } catch (UnknownLadderGameTypeExceptions e) {
      System.err.println(e.getMessage());
    }

    if (game == null) {
      return;
    }

    ladderGameView = new LadderGameView();

    game.addObserver(this);

    setupLadderViewEventHandlers();

    //Add players to game
    MainController.getInstance().getPlayers().forEach(game::addPlayer);


    // change when being done in controller on game difficult select
    try {
      game.setUpGame();
    } catch (NotEnoughPlayersInGameException e) {
      System.err.println(e.getMessage());
    }
  }



  private void setupLadderViewEventHandlers() {
    ladderGameView.setRollDiceHandler(e -> {
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
    });

    ladderGameView.setHomeButtonHandler(e -> {
      Alert alert = new Alert(
          this.ladderGameView.getRoot(),
          "Bekreft avslutning",
          "Er du sikker på at du vil avslutte spillet?",
          "Ja",
          "Nei",
          response -> {
            if (response) {
              //Loads main menu
              System.out.println("Home button clicked");
              MainController.getInstance().switchToMainMenu();
            }
          }
      );
      alert.show();
    });
  }

  @Override
  public void onRoundPlayed(ArrayList<Player> players, Player currentPlayer, int roll, TileAction tileAction) {
    int round = ladderGameView.getRoundLabel() + 1;
    ladderGameView.rotateDice(roll);

    PauseTransition delay = new PauseTransition(Duration.millis(400));
    delay.setOnFinished(event -> {
      ladderGameView.updateCurrentPlayerLabel(currentPlayer.getName());
      ladderGameView.updateBoard(players);
      ladderGameView.populatePlayerList(players);
      ladderGameView.updateRoundLabel(String.valueOf(round));
    });
    delay.play();

    lastPlayer = players.get((players.indexOf(currentPlayer) + 1) % players.size());
    ladderGameView.updateLastPlayerLabel(lastPlayer.getName());
    ladderGameView.updateLastRollLabel(String.valueOf(roll));
    ladderGameView.updateMovedToLabel(String.valueOf(lastPlayer.getCurrentTile().getTileId()));
    if (tileAction != null) {
      String action = tileAction.getClass().getSimpleName();
      String formattedAction = action.replaceAll("(?=\\p{Upper})", " ").trim();
      ladderGameView.updateTileActionLabel(formattedAction);
    } else {
      ladderGameView.updateTileActionLabel("Ingen");
    }
  }

  @Override
  public void onPlayerWon(Player player) {
    ladderGameView.disableDiceButton();
    ladderGameView.updateStatusLabel("Avsluttet");
    ladderGameView.showToast(Toast.ToastVariant.SUCCESS, "Spiller vant",
        player.getName() + " vant spillet! Gratulerer!");
    Alert alert = new Alert(
        this.ladderGameView.getRoot(),
        "Spiller vant",
        player.getName() + " vant spillet!",
        "Tilabeke til hovedmeny",
        "Restart",
        response -> {
          if (response) {
            // restart game
          } else {
            // go to main menu
          }
        }
    );
    alert.show();
  }

  @Override
  public void onGameSetup(ArrayList<Player> players, Map<Integer, Tile> tiles) {
    ladderGameView.createBoard(players, tiles);
    ladderGameView.populatePlayerList(players);
    ladderGameView.updateCurrentPlayerLabel(players.getFirst().getName());

    // set the diffuculty level when there is a way to
    ladderGameView.updateGradeLabel("");
  }

  public LadderGameView getView() {
    return ladderGameView;
  }
}
