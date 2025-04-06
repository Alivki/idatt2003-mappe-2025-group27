package ntnu.idatt2003.group27.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import ntnu.idatt2003.group27.controllers.BoardGameController;
import ntnu.idatt2003.group27.models.BoardGame;
import ntnu.idatt2003.group27.models.Player;
import ntnu.idatt2003.group27.models.interfaces.BoardGameObserver;
import ntnu.idatt2003.group27.view.components.*;

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
    root.getStyleClass().add("root");

    initializeLayout();
  }

  private void initializeLayout() {
    AppLayout layout = new AppLayout();

    CustomButton homeButton = new CustomButton("Hjem", CustomButton.ButtonType.GHOST, e -> {
      Alert popup = new Alert(
        root,
        "Bekreft avslutning",
        "Er du sikker på at du vil avslutte spillet?",
        "Ja",
        "Nei",
        response -> {
          if (response) {
            //controller.exitGame();
          }
        }
      );
      popup.show();
    });

    Card playerList = new Card("Spillere", "Spillerne i spillet", 300);

    Label title = new Label("Stigespillet");
    title.getStyleClass().add("h1");

    final GameBoardCanvas gameBoardCanvas = new GameBoardCanvas(0);
    final Canvas canvas = gameBoardCanvas.createBoard();

    VBox canvasContainer = new VBox();
    canvasContainer.setAlignment(Pos.CENTER);
    canvasContainer.getStyleClass().add("gameAreaCard");

    canvasContainer.getChildren().add(canvas);
    layout.getMainContainer().getChildren().addAll(title, canvasContainer);

    layout.getMainContainer().widthProperty().addListener((obs, oldWidth, newWidth) -> {
      double tileSize = (newWidth.doubleValue() - 50) / ((double) game.getBoard().getTiles().size() / 9);
      canvas.setWidth(newWidth.doubleValue() - 50);
      canvas.setHeight(tileSize * 9);
      gameBoardCanvas.redrawBoard(tileSize);
    });

    Card test = new Card("Spill info", null, 450);

    layout.getHeader().getChildren().add(homeButton);
    layout.getLeftContainer().getChildren().add(playerList);
    layout.getRightContainer().getChildren().add(test);
    root.getChildren().add(layout);
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
