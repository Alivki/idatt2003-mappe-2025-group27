package ntnu.idatt2003.group27.view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import ntnu.idatt2003.group27.models.BoardGame;
import ntnu.idatt2003.group27.models.Player;
import ntnu.idatt2003.group27.models.interfaces.BoardGameObserver;
import ntnu.idatt2003.group27.view.components.*;

public class LadderGameView implements BoardGameObserver {
  private final StackPane root;
  private final BoardGame game;
  private CustomButton homeButton;

  public LadderGameView(BoardGame game) {
    this.game = game;
    game.addObserver(this);

    root = new StackPane();
    root.setAlignment(Pos.TOP_CENTER);
    root.getStyleClass().add("root");

    initializeLayout();
  }

  private void initializeLayout() {
    AppLayout layout = new AppLayout();

    homeButton = new CustomButton("Hjem", CustomButton.ButtonVariant.GHOST, null);

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
      double tileSize = (newWidth.doubleValue() - 100) / ((double) game.getBoard().getTiles().size() / 9);
      canvas.setWidth(newWidth.doubleValue() - 100);
      canvas.setHeight(tileSize * 9);
      gameBoardCanvas.redrawBoard(tileSize);
    });

    Card test = new Card("Spill info", null, 450);

    layout.getHeader().getChildren().add(homeButton);
    layout.getLeftContainer().getChildren().add(playerList);
    layout.getRightContainer().getChildren().add(test);
    root.getChildren().add(layout);
  }

  public void setHomeButtonHandler(EventHandler<ActionEvent> action) {
    homeButton.setOnAction(action);
  }

  public void setRollDiceHandler(EventHandler<ActionEvent> action) {
    //diceButton.setOnAction(action);
  }

  public void showToast(Toast.ToastVariant variant, String title, String message) {
    Toast toast = new Toast(root, variant, title, message);
    toast.show();
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
