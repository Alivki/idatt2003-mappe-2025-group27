package ntnu.idatt2003.group27.controllers;

import java.util.ArrayList;
import java.util.Map;
import ntnu.idatt2003.group27.models.BoardGame;
import ntnu.idatt2003.group27.models.Player;
import ntnu.idatt2003.group27.models.Tile;
import ntnu.idatt2003.group27.models.exceptions.NotEnoughPlayersInGameException;
import ntnu.idatt2003.group27.models.interfaces.BoardGameObserver;
import ntnu.idatt2003.group27.view.BoardGameMenu;
import ntnu.idatt2003.group27.view.LadderGameView;
import ntnu.idatt2003.group27.view.components.Alert;
import ntnu.idatt2003.group27.view.components.Toast;

public class BoardGameController implements BoardGameObserver {
  private final BoardGame game;
  private BoardGameMenu view;
  private final LadderGameView ladderView;

  public BoardGameController(BoardGame game, BoardGameMenu view, LadderGameView ladderView) {
    this.game = game;
    this.view = view;
    this.ladderView = ladderView;



    game.addObserver(this);



    setupLadderViewEventHandlers();
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
              //Loads main menu
              MainController.getInstance().switchToMainMenu();
            }
          }
      );
      alert.show();
    });
  }

  @Override
  public void onRoundPlayed(ArrayList<Player> players, Player currentPlayer, int roll) {
    ladderView.updateCurrentPlayerLabel(currentPlayer.getName());
    ladderView.rotateDice(roll);
    ladderView.updateBoard(players);
    ladderView.populatePlayerList(players);
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
  }
}
