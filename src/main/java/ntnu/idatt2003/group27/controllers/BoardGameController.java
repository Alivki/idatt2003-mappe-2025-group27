package ntnu.idatt2003.group27.controllers;

import ntnu.idatt2003.group27.models.BoardGame;

public class BoardGameController {
  private BoardGame game;

  public BoardGameController(BoardGame game) {
    this.game = game;
  }

  public void play() {
    game.play();
  }
}
