package ntnu.idatt2003.group27.controllers;

import ntnu.idatt2003.group27.models.BoardGame;
import ntnu.idatt2003.group27.view.components.ConfirmationPopup;

public class BoardGameController {
  private BoardGame game;

  public BoardGameController(BoardGame game) {
    this.game = game;
  }

  public void play() {
    game.play();
  }
}
