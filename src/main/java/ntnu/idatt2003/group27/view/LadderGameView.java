package ntnu.idatt2003.group27.view;

import java.util.ArrayList;
import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.animation.Transition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.SceneAntialiasing;
import javafx.scene.SubScene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.util.Duration;
import ntnu.idatt2003.group27.models.BoardGame;
import ntnu.idatt2003.group27.models.Player;
import ntnu.idatt2003.group27.models.interfaces.BoardGameObserver;
import ntnu.idatt2003.group27.view.components.*;

public class LadderGameView {
  private final StackPane root;
  private CustomButton homeButton;
  private CustomButton diceButton;
  private CustomButton restartButton;
  private Label roundInfo;
  private Label currentPlayerInfo;
  private Label gradeInfo;
  private Label statusInfo;
  private Box dice;
  private Label lastPlayer;
  private Label movedTo;
  private Label lastRoll;
  private Label tileAction;

  public LadderGameView() {
    root = new StackPane();
    root.setAlignment(Pos.TOP_CENTER);
    root.getStyleClass().add("root");

    initializeLayout();
  }

  private void initializeLayout() {
    AppLayout layout = new AppLayout();

    homeButton = new CustomButton("Hjem", CustomButton.ButtonVariant.GHOST, null);

    Card playerList = new Card("Spillere", "Spillerne i spillet", 300);

    Card settingsCard = new Card("Innstillinger", "Restart eller slutt spill", 115);
    VBox settingsButtonContainer = new VBox(12);
    settingsButtonContainer.setPadding(new Insets(12, 0, 0, 0));
    restartButton = new CustomButton("Restart", CustomButton.ButtonVariant.SECONDARY, null);

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

    roundInfo = new Label("?");
    currentPlayerInfo = new Label("");
    gradeInfo = new Label("?");
    statusInfo = new Label("Pågående");

    Separator separator1 = new Separator();

    VBox diceContainer = new VBox();
    diceContainer.setPadding(new Insets(0, 0, 13, 0));
    diceContainer.setAlignment(Pos.CENTER);

    dice = new Box(5, 5, 5);
    dice.setMaterial(new PhongMaterial(Color.WHITE));

    PerspectiveCamera camera = new PerspectiveCamera(true);
    camera.getTransforms().addAll(new Rotate(-120, Rotate.X_AXIS), new Rotate(60, Rotate.Y_AXIS), new Translate(0, 0, -20));

    Group root3D = new Group(camera,dice);

    Separator separator2 = new Separator();

    SubScene subScene = new SubScene(root3D, 150, 150, true, SceneAntialiasing.BALANCED);
    subScene.setCamera(camera);

    diceButton = new CustomButton("Rull terning", CustomButton.ButtonVariant.PRIMARY, null);

    VBox lastRoundContainer = new VBox();
    lastRoundContainer.setPadding(new Insets(12, 0, 0, 0));

    Label lastRoundTitleLabel = new Label("Siste runde");
    lastRoundTitleLabel.getStyleClass().add("h3");

    VBox lastRoundInfo = new VBox(5);

    lastPlayer = new Label("?");
    movedTo = new Label("?");
    lastRoll = new Label("?");
    tileAction = new Label("Ingen");

    layout.getHeader().getChildren().add(homeButton);

    settingsButtonContainer.getChildren().addAll(restartButton);
    settingsCard.getChildren().addAll(settingsButtonContainer);
    layout.getLeftContainer().getChildren().addAll(playerList, settingsCard);

    gameInfo.getChildren().addAll(
        createGameInfoRow("Runde:", roundInfo),
        createGameInfoRow("Nåværende spiller:", currentPlayerInfo),
        createGameInfoRow("Vansklighetsgrad:", gradeInfo),
        createGameInfoRow("Status:", statusInfo)
    );
    diceContainer.getChildren().addAll(subScene, diceButton);
    lastRoundContainer.getChildren().addAll(lastRoundTitleLabel, lastRoundInfo);
    lastRoundInfo.getChildren().addAll(
        createGameInfoRow("Siste spiller:", lastPlayer),
        createGameInfoRow("Flyttet til:", movedTo),
        createGameInfoRow("Kastet:", lastRoll),
        createGameInfoRow("Action", tileAction)
    );
    rightCard.getChildren().addAll(gameInfo, separator1, diceContainer, separator2, lastRoundContainer);
    layout.getRightContainer().getChildren().addAll(rightCard);

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

  public void updateRoundLabel(String round) {
    roundInfo.setText(round);
  }

  public void updateCurrentPlayerLabel(String playerName) {
    currentPlayerInfo.setText(playerName);
  }

  public void updateGradeLabel(String grade) {
    gradeInfo.setText(grade);
  }

  public void updateStatusLabel(String status) {
    statusInfo.setText(status);
  }

  public void updateLastPlayerLabel(String playerName) {
    lastPlayer.setText(playerName);
  }

  public void updateMovedToLabel(String tileName) {
    movedTo.setText(tileName);
  }

  public void updateLastRollLabel(String roll) {
    lastRoll.setText(roll);
  }

  public void updateTileActionLabel(String action) {
    tileAction.setText(action);
  }


  public void rotateDice(int roll) {
    RotateTransition rotatorY = new RotateTransition(Duration.millis(500), dice);
    rotatorY.setAxis(Rotate.Z_AXIS);
    rotatorY.setFromAngle(0);
    rotatorY.setToAngle(360);
    rotatorY.setCycleCount(1);
    rotatorY.setInterpolator(Interpolator.EASE_OUT);
    rotatorY.play();

    RotateTransition rotatorX = new RotateTransition(Duration.millis(500), dice);
    rotatorX.setAxis(Rotate.X_AXIS);
    rotatorX.setFromAngle(0);
    rotatorX.setToAngle(360);
    rotatorX.setCycleCount(1);
    rotatorX.setInterpolator(Interpolator.EASE_OUT);
    rotatorX.play();
  }

  public void showToast(Toast.ToastVariant variant, String title, String message) {
    Toast toast = new Toast(root, variant, title, message);
    toast.show();
  }

  private HBox createGameInfoRow(String labelText, Label infoLabel) {
    Label label = new Label(labelText);
    Region dots = new Region();

    label.getStyleClass().add("p");
    infoLabel.getStyleClass().add("p");
    dots.getStyleClass().add("dotted-separator");
    HBox.setHgrow(dots, Priority.ALWAYS);

    HBox row = new HBox(5, label, dots, infoLabel);
    row.setAlignment(Pos.BOTTOM_LEFT);

    return row;
  }

  public StackPane getRoot() {
    return root;
  }
}
