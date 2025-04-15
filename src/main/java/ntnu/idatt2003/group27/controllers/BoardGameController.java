package ntnu.idatt2003.group27.controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import ntnu.idatt2003.group27.models.BoardGame;
import ntnu.idatt2003.group27.models.Player;
import ntnu.idatt2003.group27.models.Tile;
import ntnu.idatt2003.group27.models.exceptions.NotEnoughPlayersInGameException;
import ntnu.idatt2003.group27.models.interfaces.BoardGameObserver;
import ntnu.idatt2003.group27.models.interfaces.TileAction;
import ntnu.idatt2003.group27.view.BoardGameMenu;
import ntnu.idatt2003.group27.view.LadderGameView;
import ntnu.idatt2003.group27.view.components.Alert;
import ntnu.idatt2003.group27.view.components.Toast;
import javafx.animation.PauseTransition;
import javafx.util.Duration;

public class BoardGameController implements BoardGameObserver {
  private final BoardGame game;
  private BoardGameMenu view;
  private final LadderGameView ladderView;
  private Player lastPlayer;

  public BoardGameController(BoardGame game, BoardGameMenu view, LadderGameView ladderView) {
    this.game = game;
    this.view = view;
    this.ladderView = ladderView;

    game.addObserver(this);

    setupMenuViewEventHandler();
    setupLadderViewEventHandlers();
  }

  private void setupMenuViewEventHandler() {
    // add player button
    // upload player button
    // remove player button
    // download player button
    // change player piece button

    // change to ladder game selection
    // change to game 2 selection
    // change to game 3 selection

    // start game with specified difficulty use factory to create game
  }

  private void setupLadderViewEventHandlers() {
    ladderView.setRollDiceHandler(e -> {
      try {
        game.play();
      } catch (NotEnoughPlayersInGameException error) {
        //switch to main menu when possible

        view.showToast(
            Toast.ToastVariant.ERROR,
            "Feil",
            "Det oppstod en feil under spillingen: " + error.getMessage()
        );
      }
    });

    ladderView.setRestartButtonHandler(e -> {
    });

    ladderView.setHomeButtonHandler(e -> {
      Alert alert = new Alert(
          this.ladderView.getRoot(),
          "Bekreft avslutning",
          "Er du sikker på at du vil avslutte spillet?",
          "Ja",
          "Nei",
          response -> {
            if (response) {
              ladderView.showToast(
                  Toast.ToastVariant.ERROR,
                  "Avsluttet",
                  "Spillet er avsluttet, og du vil bli sendt tilbake til hovedmenyen"
              );
            }
          }
      );
      alert.show();
    });
  }

  @Override
  public void onRoundPlayed(ArrayList<Player> players, Player currentPlayer, int roll, TileAction tileAction) {
    int round = ladderView.getRoundLabel() + 1;
    ladderView.rotateDice(roll);

    PauseTransition delay = new PauseTransition(Duration.millis(400));
    delay.setOnFinished(event -> {
      ladderView.updateCurrentPlayerLabel(currentPlayer.getName());
      ladderView.updateBoard(players);
      ladderView.populatePlayerList(players);
      ladderView.updateRoundLabel(String.valueOf(round));
    });
    delay.play();

    lastPlayer = players.get((players.indexOf(currentPlayer) + 1) % players.size());
    ladderView.updateLastPlayerLabel(lastPlayer.getName());
    ladderView.updateLastRollLabel(String.valueOf(roll));
    ladderView.updateMovedToLabel(String.valueOf(lastPlayer.getCurrentTile().getTileId()));
    if (tileAction != null) {
      String action = tileAction.getClass().getSimpleName();
      String formattedAction = action.replaceAll("(?=\\p{Upper})", " ").trim();
      ladderView.updateTileActionLabel(formattedAction);
    } else {
      ladderView.updateTileActionLabel("Ingen");
    }
  }

  @Override
  public void onPlayerWon(Player player) {
    ladderView.disableDiceButton();
    ladderView.updateStatusLabel("Avsluttet");
    ladderView.showToast(Toast.ToastVariant.SUCCESS, "Spiller vant",
        player.getName() + " vant spillet! Gratulerer!");
    Alert alert = new Alert(
        this.ladderView.getRoot(),
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
    ladderView.createBoard(players, tiles);
    ladderView.populatePlayerList(players);
    ladderView.updateCurrentPlayerLabel(players.getFirst().getName());

    // set the diffuculty level when there is a way to
    ladderView.updateGradeLabel("");
  }
}
