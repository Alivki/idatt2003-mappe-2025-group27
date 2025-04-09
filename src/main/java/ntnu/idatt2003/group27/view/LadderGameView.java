package ntnu.idatt2003.group27.view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.*;
import ntnu.idatt2003.group27.models.BoardGame;
import ntnu.idatt2003.group27.models.Player;
import ntnu.idatt2003.group27.models.interfaces.BoardGameObserver;
import ntnu.idatt2003.group27.view.components.*;

public class LadderGameView implements BoardGameObserver {
  private final StackPane root;
  private final BoardGame game;
  private CustomButton homeButton;
  private CustomButton diceButton;
  private CustomButton restartButton;

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

    Card rightCard = new Card("Spill info", null, 350);

    VBox gameInfo = new VBox(5);
    gameInfo.setPadding(new Insets(0, 0, 13, 0));

    VBox diceContainer = new VBox();
    diceContainer.setPadding(new Insets(12, 0, 13, 0));

    VBox lastRoundInfo = new VBox();
    lastRoundInfo.setPadding(new Insets(12, 0, 0, 0));

    Region dots1 = new Region();
    Region dots2 = new Region();
    Region dots3 = new Region();
    Region dots4 = new Region();
    dots1.getStyleClass().add("dotted-separator");
    dots2.getStyleClass().add("dotted-separator");
    dots3.getStyleClass().add("dotted-separator");
    dots4.getStyleClass().add("dotted-separator");
    HBox.setHgrow(dots1, Priority.ALWAYS);
    HBox.setHgrow(dots2, Priority.ALWAYS);
    HBox.setHgrow(dots3, Priority.ALWAYS);
    HBox.setHgrow(dots4, Priority.ALWAYS);

    HBox gameInfoContainer1 = new HBox(5);
    gameInfoContainer1.setAlignment(Pos.BOTTOM_LEFT);
    HBox gameInfoContainer2 = new HBox(5);
    gameInfoContainer2.setAlignment(Pos.BOTTOM_LEFT);
    HBox gameInfoContainer3 = new HBox(5);
    gameInfoContainer3.setAlignment(Pos.BOTTOM_LEFT);
    HBox gameInfoContainer4 = new HBox(5);
    gameInfoContainer4.setAlignment(Pos.BOTTOM_LEFT);

    Label round = new Label("Runde:");
    Label player = new Label("Nåværende spiller:");
    Label grade = new Label("Vansklighetsgrad:");
    Label status = new Label("Status:");
    round.getStyleClass().add("p");
    player.getStyleClass().add("p");
    grade.getStyleClass().add("p");
    status.getStyleClass().add("p");

    Label roundInfo = new Label("?");
    Label playerInfo = new Label("?");
    Label gradeInfo = new Label("?");
    Label statusInfo = new Label("?");
    roundInfo.getStyleClass().add("p");
    playerInfo.getStyleClass().add("p");
    gradeInfo.getStyleClass().add("p");
    statusInfo.getStyleClass().add("p");

    Separator separator1 = new Separator();

    diceButton = new CustomButton("Rull terning", CustomButton.ButtonVariant.PRIMARY, null);

    Separator separator2 = new Separator();

    Label lastRoundTitleLabel = new Label("Siste runde");
    lastRoundTitleLabel.getStyleClass().add("h3");

    Card settingsCard = new Card("Innstillinger", "Restart eller slutt spill", 115);
    VBox settingsButtonContainer = new VBox(12);
    settingsButtonContainer.setPadding(new Insets(12, 0, 0, 0));
    restartButton = new CustomButton("Restart", CustomButton.ButtonVariant.SECONDARY, null);

    layout.getHeader().getChildren().add(homeButton);

    layout.getLeftContainer().getChildren().add(playerList);

    gameInfoContainer1.getChildren().addAll(round, dots1, roundInfo);
    gameInfoContainer2.getChildren().addAll(player, dots2, playerInfo);
    gameInfoContainer3.getChildren().addAll(grade, dots3, gradeInfo);
    gameInfoContainer4.getChildren().addAll(status, dots4, statusInfo);
    gameInfo.getChildren().addAll(gameInfoContainer1, gameInfoContainer2, gameInfoContainer3, gameInfoContainer4);
    diceContainer.getChildren().addAll(diceButton);
    lastRoundInfo.getChildren().addAll(lastRoundTitleLabel);
    rightCard.getChildren().addAll(gameInfo, separator1, diceContainer, separator2, lastRoundInfo);
    settingsButtonContainer.getChildren().addAll(restartButton);
    settingsCard.getChildren().addAll(settingsButtonContainer);
    layout.getRightContainer().getChildren().addAll(rightCard, settingsCard);

    root.getChildren().add(layout);
  }

  public void setHomeButtonHandler(EventHandler<ActionEvent> action) {
    homeButton.setOnAction(action);
  }

  public void setRollDiceHandler(EventHandler<ActionEvent> action) {
    diceButton.setOnAction(action);
  }

  public void setRestartButtonHandler(EventHandler<ActionEvent> action) {
    restartButton.setOnAction(action);
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
