package ntnu.idatt2003.group27.view;

import java.util.*;

import javafx.animation.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Point3D;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.util.Duration;
import ntnu.idatt2003.group27.models.Player;
import ntnu.idatt2003.group27.models.Tile;
import ntnu.idatt2003.group27.view.components.*;
import org.fxyz3d.shapes.primitives.CuboidMesh;

public class LadderGameView {
  private final StackPane root;

  private CustomButton homeButton;
  private CustomButton diceButton;
  private CustomButton restartButton;

  private Canvas canvas;

  private ScrollPane playerList;

  private VBox canvasContainer;

  private Label roundInfo;
  private Label currentPlayerInfo;
  private Label gradeInfo;
  private Label statusInfo;

  private CuboidMesh dice;
  private static final Point3D[] DICE_ROTATION = {
    new Point3D(0, 0, 0),
    new Point3D(90, 0, 0),
    new Point3D(0, -90, 0),
    new Point3D(0, 90, 0),
    new Point3D(-90, 0, 0),
    new Point3D(0, 180, 90)
  };

  private Label lastPlayer;
  private Label movedTo;
  private Label lastRoll;
  private Label tileAction;

  private Random random = new Random();

  public LadderGameView() {
    root = new StackPane();
    root.setAlignment(Pos.TOP_CENTER);
    root.getStyleClass().add("root");

    initializeLayout();
  }

  private void initializeLayout() {
    AppLayout layout = new AppLayout();

    homeButton = new CustomButton("Hjem", CustomButton.ButtonVariant.GHOST, null);

    Card playerCard = new Card("Spillere", "Spillerne i spillet", 382);

    playerList = new ScrollPane();
    playerList.setPadding(new Insets(10, 0, 0, 0));
    playerList.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
    playerList.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
    playerList.setMaxHeight(305);
    VBox.setVgrow(playerList, Priority.ALWAYS);
    playerList.setFitToWidth(true);
    playerList.getStyleClass().add("player-scroll-pane");

    Card settingsCard = new Card("Innstillinger", "Restart eller slutt spill", 115);
    VBox settingsButtonContainer = new VBox(12);
    settingsButtonContainer.setPadding(new Insets(12, 0, 0, 0));
    restartButton = new CustomButton("Restart", CustomButton.ButtonVariant.SECONDARY, null);

    Label title = new Label("Stigespillet");
    title.getStyleClass().add("h1");

    canvasContainer = new VBox();
    canvasContainer.setAlignment(Pos.CENTER);
    canvasContainer.getStyleClass().add("gameAreaCard");

    layout.getMainContainer().getChildren().addAll(title, canvasContainer);

    layout.getMainContainer().widthProperty().addListener((obs, oldWidth, newWidth) -> {
      double tileSize = (newWidth.doubleValue() - 120) / ((double) canvas.getBoardSize() / 9);
      canvas.setWidth(newWidth.doubleValue() - 60);
      canvas.setHeight(tileSize * 9 + 18);
      canvas.resizeBoard(tileSize);
    });

    Card rightCard = new Card("Spill info", null, 350);

    VBox gameInfo = new VBox(5);
    gameInfo.setPadding(new Insets(0, 0, 20, 0));

    roundInfo = new Label("1");
    currentPlayerInfo = new Label("");
    gradeInfo = new Label("");
    statusInfo = new Label("Pågående");

    Separator separator1 = new Separator();

    VBox diceContainer = new VBox();
    diceContainer.setPadding(new Insets(0, 0, 20, 0));
    diceContainer.setAlignment(Pos.CENTER);

    dice = new CuboidMesh(5f, 5f, 5f);
    dice.setTextureModeImage(getClass().getResource("/texture/dice.png").toExternalForm());

    PerspectiveCamera camera = new PerspectiveCamera(true);
    camera.getTransforms().addAll(
      new Rotate(20, Rotate.X_AXIS),
      new Rotate(160, Rotate.Y_AXIS),
      new Rotate(0, Rotate.Z_AXIS),
      new Translate(0, 0, -20)
    );

    PointLight pointLight = new PointLight();
    pointLight.setColor(Color.rgb(55, 55, 55));
    pointLight.setTranslateX(-8);
    pointLight.setTranslateY(10);
    pointLight.setTranslateZ(15);

    AmbientLight ambientLight = new AmbientLight(Color.rgb(244, 244, 244));

    Group root3D = new Group(camera, dice, pointLight, ambientLight);

    SubScene subScene = new SubScene(root3D, 150, 150, true, SceneAntialiasing.BALANCED);
    subScene.setCamera(camera);

    diceButton = new CustomButton("Rull terning", CustomButton.ButtonVariant.PRIMARY, null);

    Separator separator2 = new Separator();

    VBox lastRoundContainer = new VBox();
    lastRoundContainer.setPadding(new Insets(12, 0, 4, 0));

    Label lastRoundTitleLabel = new Label("Siste runde");
    lastRoundTitleLabel.getStyleClass().add("h3");

    VBox lastRoundInfo = new VBox(5);

    lastPlayer = new Label("Ingen");
    movedTo = new Label("Start");
    lastRoll = new Label("Ikke kastet");
    tileAction = new Label("Ingen");

    layout.getHeader().getChildren().add(homeButton);

    settingsButtonContainer.getChildren().addAll(restartButton);
    settingsCard.getChildren().addAll(settingsButtonContainer);
    playerCard.getChildren().add(playerList);
    layout.getLeftContainer().getChildren().addAll(playerCard, settingsCard);

    gameInfo.getChildren().addAll(
      createGameInfoRow("Runde:", roundInfo),
      createGameInfoRow("Nåværende spiller:", currentPlayerInfo),
      createGameInfoRow("Vanskelighetsgrad  :", gradeInfo),
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

  public void toggleDiceButton(boolean enable) {
    diceButton.setDisable(!enable);
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

  public int getRoundLabel() {
    return Integer.parseInt(roundInfo.getText());
  }

  public void populatePlayerList(List<Player> players) {
    VBox playerContainer = new VBox(5);

    players.forEach(player -> {
      HBox playerRow = new HBox(8);
      playerRow.getStyleClass().add("player-row");

      Label playerName = new Label(player.getName());
      playerName.getStyleClass().add("p");

      Region spacer = new Region();
      HBox.setHgrow(spacer, Priority.ALWAYS);

      String playerPosition = String.valueOf(player.getCurrentTile().getTileId());
      Label playerPositionLabel = new Label(playerPosition);
      playerPositionLabel.getStyleClass().add("p");

      // change to actual player icons once that is implemented
      ImageView playerIcon = new ImageView(new Image(getClass().getResourceAsStream("/icons/home.png")));
      playerIcon.setFitHeight(20);
      playerIcon.setFitWidth(20);

      playerRow.getChildren().addAll(playerIcon, playerName, spacer, playerPositionLabel);
      playerContainer.getChildren().addAll(playerRow);
    });

    playerList.setContent(playerContainer);
  }

  public void createBoard(ArrayList<Player> players, Map<Integer, Tile> tiles) {
    canvas = new Canvas(tiles, players, tiles.size());
    canvasContainer.getChildren().add(canvas);

    canvas.redrawBoard();
    canvas.updateBoard(players);
  }

  public void updateBoard(ArrayList<Player> players) {
    canvas.updateBoard(players);
  }

  public void rotateDice(int roll) {
    Timeline timeline = new Timeline();
    dice.getTransforms().clear();

    Point3D targetRotation = DICE_ROTATION[roll - 1];

    Rotate rotateX = new Rotate(0, Rotate.X_AXIS);
    Rotate rotateY = new Rotate(0, Rotate.Y_AXIS);
    Rotate rotateZ = new Rotate(0, Rotate.Z_AXIS);
    dice.getTransforms().addAll(rotateX, rotateY, rotateZ);

    double tumbleBase = 360 + random.nextDouble() * 180;
    double[] tumbleAngles = {
      tumbleBase * (random.nextBoolean() ? 1 : -1),
      tumbleBase * (random.nextBoolean() ? 1 : -1),
      tumbleBase * (random.nextBoolean() ? 1 : -1)
    };

    double finalX = targetRotation.getX() + Math.round(tumbleAngles[0] / 360) * 360;
    double finalY = targetRotation.getY() + Math.round(tumbleAngles[1] / 360) * 360;
    double finalZ = targetRotation.getZ() + Math.round(tumbleAngles[2] / 360) * 360;

    KeyValue kvTumbleX = new KeyValue(rotateX.angleProperty(), tumbleAngles[0], Interpolator.LINEAR);
    KeyValue kvTumbleY = new KeyValue(rotateY.angleProperty(), tumbleAngles[1], Interpolator.LINEAR);
    KeyValue kvTumbleZ = new KeyValue(rotateZ.angleProperty(), tumbleAngles[2], Interpolator.LINEAR);
    KeyFrame kfTumble = new KeyFrame(Duration.millis(250), kvTumbleX, kvTumbleY, kvTumbleZ);

    KeyValue kvFinalX = new KeyValue(rotateX.angleProperty(), finalX, Interpolator.EASE_OUT);
    KeyValue kvFinalY = new KeyValue(rotateY.angleProperty(), finalY, Interpolator.EASE_OUT);
    KeyValue kvFinalZ = new KeyValue(rotateZ.angleProperty(), finalZ, Interpolator.EASE_OUT);
    KeyFrame kfFinal = new KeyFrame(Duration.millis(350), kvFinalX, kvFinalY, kvFinalZ);

    timeline.getKeyFrames().addAll(kfTumble, kfFinal);
    timeline.setCycleCount(1);
    timeline.play();
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
