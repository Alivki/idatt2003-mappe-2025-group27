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

public class LadderGameView implements BoardGameObserver {
  private final StackPane root;
  private final BoardGame game;
  private CustomButton homeButton;
  private CustomButton diceButton;
  private CustomButton restartButton;
  private Label statusInfo;
  private Label currentPlayerInfo;
  private Box dice;

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
    diceContainer.setPadding(new Insets(0, 0, 13, 0));
    diceContainer.setAlignment(Pos.CENTER);

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
    Label currentPlayer = new Label("Nåværende spiller:");
    Label grade = new Label("Vansklighetsgrad:");
    Label status = new Label("Status:");
    round.getStyleClass().add("p");
    currentPlayer.getStyleClass().add("p");
    grade.getStyleClass().add("p");
    status.getStyleClass().add("p");

    Label roundInfo = new Label("?");
    currentPlayerInfo = new Label("");
    Label gradeInfo = new Label("?");
    statusInfo = new Label("Pågående");
    roundInfo.getStyleClass().add("p");
    currentPlayerInfo.getStyleClass().add("p");
    gradeInfo.getStyleClass().add("p");
    statusInfo.getStyleClass().add("p");

    Separator separator1 = new Separator();

    dice = new Box(5, 5, 5);
    dice.setMaterial(new PhongMaterial(Color.WHITE));

    PerspectiveCamera camera = new PerspectiveCamera(true);
    camera.getTransforms().addAll(new Rotate(-120, Rotate.X_AXIS), new Rotate(60, Rotate.Y_AXIS), new Translate(0, 0, -20));

    Group root3D = new Group(camera,dice);

    SubScene subScene = new SubScene(root3D, 150, 150, true, SceneAntialiasing.BALANCED);
    subScene.setCamera(camera);

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
    gameInfoContainer2.getChildren().addAll(currentPlayer, dots2, currentPlayerInfo);
    gameInfoContainer3.getChildren().addAll(grade, dots3, gradeInfo);
    gameInfoContainer4.getChildren().addAll(status, dots4, statusInfo);
    gameInfo.getChildren().addAll(gameInfoContainer1, gameInfoContainer2, gameInfoContainer3, gameInfoContainer4);
    diceContainer.getChildren().addAll(subScene, diceButton);
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

  @Override
  public void onRoundPlayed(ArrayList<Player> players, Player currentPlayer, int roll) {
    currentPlayerInfo.setText(currentPlayer.getName());
    rotateDice(roll);
  }

  @Override
  public void onPlayerWon(Player player) {
    statusInfo.setText("Avsluttet");
    showToast(Toast.ToastVariant.SUCCESS, "Spiller vant", player.getName() + " vant spillet!");
  }

  public StackPane getRoot() {
    return root;
  }
}
