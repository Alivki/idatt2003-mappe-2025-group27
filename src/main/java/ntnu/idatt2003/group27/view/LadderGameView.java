package ntnu.idatt2003.group27.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import ntnu.idatt2003.group27.controllers.BoardGameController;
import ntnu.idatt2003.group27.models.BoardGame;
import ntnu.idatt2003.group27.models.Player;
import ntnu.idatt2003.group27.models.interfaces.BoardGameObserver;
import ntnu.idatt2003.group27.view.components.GameBoardCanvas;

public class LadderGameView implements BoardGameObserver {
  private final StackPane root;

  private final BoardGame game;
  private final BoardGameController controller;

  public LadderGameView(BoardGame game, BoardGameController controller) {
    this.game = game;
    this.controller = controller;
    game.addObserver(this);

    root = new StackPane();
    root.setAlignment(Pos.TOP_CENTER);
    root.setPadding(new Insets(20, 10, 10, 10));
    root.getStyleClass().add("root");

    initializeLayout();
  }

  private void initializeLayout() {
    VBox gameArea = new VBox(20);
    gameArea.setMinSize(700, 700);
    gameArea.setMaxSize(700, 700);
    gameArea.setStyle("-fx-background-color: #FFFFFF");
    gameArea.setAlignment(Pos.TOP_LEFT);
    gameArea.setPadding(new Insets(30, 0, 0, 30));

    Label title = new Label("Stigespillet");

    final Canvas canvas = new GameBoardCanvas().createBoard();

    gameArea.getChildren().addAll(title, canvas);
    root.getChildren().add(gameArea);
  }

  @Override
  public void onPlayerMoved(Player player) {

  }

  @Override
  public void onPlayerWon(Player player) {
    root.setStyle("-fx-background-color: #d81414");
  }

  public StackPane getRoot() {
    return root;
  }
}
