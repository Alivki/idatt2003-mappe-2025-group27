package ntnu.idatt2003.group27.controllers;

import ntnu.idatt2003.group27.models.BoardGame;
import ntnu.idatt2003.group27.models.Player;
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

    setupMenuViewEventHandler();
    setupLadderViewEventHandlers();
  }

  private void setupMenuViewEventHandler(){
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

    });

    ladderView.setHomeButtonHandler(e -> {
      Alert popup = new Alert(
        this.ladderView.getRoot(),
        "Bekreft avslutning",
        "Er du sikker på at du vil avslutte spillet?",
        "Ja",
        "Nei",
        response -> {
          if (response) {
            ladderView.showToast(
              Toast.ToastType.DEFAULT,
              "Avsluttet",
              "Spillet er avsluttet, og du vil bli sendt tilbake til hovedmenyen"
            );
          }
        }
      );
      popup.show();
    });
  }

  @Override
  public void onPlayerMoved(Player player) {
  }

  @Override
  public void onPlayerWon(Player player) {
  }
}
