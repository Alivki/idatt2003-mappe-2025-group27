package ntnu.idatt2003.group27.view;

import java.util.*;

import java.util.logging.Logger;
import javafx.animation.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Point3D;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.util.Duration;
import ntnu.idatt2003.group27.models.Player;
import ntnu.idatt2003.group27.models.Tile;
import ntnu.idatt2003.group27.models.interfaces.LadderTileAction;
import ntnu.idatt2003.group27.models.interfaces.TileAction;
import ntnu.idatt2003.group27.view.components.*;
import org.fxyz3d.shapes.primitives.CuboidMesh;

/**
 * A class representing the view for the ladder game.
 * Displays all necessary GUI for interacting with and playing the ladder game.
 */
public class LadderGameView {
  /**
   * Logger instance for the {@link LadderGameView} class.
   * Used for logging informational messages and errors related to class operations.
   */
  private static final Logger logger = Logger.getLogger(LadderGameView.class.getName());

  /** Root container for the Ladder Game view. */
  private final StackPane root;

  /** Button to return to the home screen. */
  private CustomButton homeButton;

  /** Button to roll the dice. */
  private CustomButton diceButton;

  /** Button to restart the game. */
  private CustomButton restartButton;

  /** Canvas for rendering the game board. */
  private LadderCanvas ladderCanvas;

  /** Card displaying the list of players. */
  private PlayerListCard playerListCard;

  /** Container for the canvas and related UI elements. */
  private VBox canvasContainer;

  /** Label showing the current round number. */
  private Label roundInfo;

  /** Label showing the current player's name. */
  private Label currentPlayerInfo;

  /** Label showing the current player's grade. */
  private Label gradeInfo;

  /** Label showing the current game status. */
  private Label statusInfo;

  /** Array representing the 3D dice mesh. */
  private CuboidMesh[] dice;

  /** Predefined 3D rotation values for each dice face. */
  private static final Point3D[] DICE_ROTATION = {
      new Point3D(0, 0, 0),
      new Point3D(90, 0, 0),
      new Point3D(0, -90, 0),
      new Point3D(0, 90, 0),
      new Point3D(-90, 0, 0),
      new Point3D(0, 180, 90)
  };

  /** Label showing the last player who moved. */
  private Label lastPlayer;

  /** Label showing the tile the player moved to. */
  private Label movedTo;

  /** Label showing the result of the last dice roll. */
  private Label lastRoll;

  /** Label describing the action triggered by the tile. */
  private Label tileAction;

  /** Random number generator for dice rolls. */
  private Random random = new Random();

  /**
   * Constructs a new {@code LadderGameView} and initializes the root layout.
   * The root is a {@link StackPane} aligned to the top center and styled with the "root" CSS class.
   * This constructor sets up the foundational layout for this interface.
   */
  public LadderGameView() {
    logger.info("Initializing LadderGameView");
    root = new StackPane();
    root.setAlignment(Pos.TOP_CENTER);
    root.getStyleClass().add("root");

    initializeLayout();
  }

  /**
   * Initializes the layout of the view.
   * Creates and organizes the all required UI components.
   */
  private void initializeLayout() {
    logger.fine("Initializing layout.");
    AppLayout layout = new AppLayout();

    homeButton = new CustomButton("Avslutt spill / Hjem", CustomButton.ButtonVariant.GHOST, null);

    playerListCard = new PlayerListCard("Spillere", "Spillerne i spillet", 382);

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
      double tileSize = (newWidth.doubleValue() - 120) / ((double) ladderCanvas.getBoardSize() / 9);
      ladderCanvas.setWidth(newWidth.doubleValue() - 60);
      ladderCanvas.setHeight(tileSize * 9 + 18);
      ladderCanvas.resizeBoard(tileSize);
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

    dice = new CuboidMesh[2];
    dice[0] = new CuboidMesh(3f, 3f, 3f);
    dice[1] = new CuboidMesh(3f, 3f, 3f);
    dice[0].setTextureModeImage(getClass().getResource("/texture/dice.png").toExternalForm());
    dice[1].setTextureModeImage(getClass().getResource("/texture/dice.png").toExternalForm());

    dice[0].setTranslateX(-3);
    dice[1].setTranslateX(3);

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

    Group root3D = new Group(camera, dice[0], dice[1], pointLight, ambientLight);

    SubScene subScene = new SubScene(root3D, 150, 150, true, SceneAntialiasing.BALANCED);
    subScene.setCamera(camera);

    diceButton = new CustomButton("Rull terninger", CustomButton.ButtonVariant.PRIMARY, null);

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
    layout.getLeftContainer().getChildren().addAll(playerListCard, settingsCard);

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
    rightCard.getChildren()
        .addAll(gameInfo, separator1, diceContainer, separator2, lastRoundContainer);
    layout.getRightContainer().getChildren().addAll(rightCard);

    root.getChildren().add(layout);
  }

  /**
   * Sets the event handler for the home button.
   *
   * @param action the action to perform when the button is clicked.
   */
  public void setHomeButtonHandler(EventHandler<ActionEvent> action) {
    logger.fine("Setting home button handler.");
    homeButton.setOnAction(action);
  }

  /**
   * Sets the event handler for the roll dice button.
   *
   * @param action the action to perform when the button is clicked.
   */
  public void setRollDiceHandler(EventHandler<ActionEvent> action) {
    logger.fine("Setting roll dice handler.");
    diceButton.setOnAction(action);
  }

  /**
   * Sets the event handler for the restart button.
   *
   * @param action the action to perform when the button is clicked.
   */
  public void setRestartButtonHandler(EventHandler<ActionEvent> action) {
    logger.fine("Setting restart button handler.");
    restartButton.setOnAction(action);
  }

  /**
   * Toggles the dice button to allow of disallow user interaction.
   * @param enable true allows interaction while false disallows.
   */
  public void toggleDiceButton(boolean enable) {
    logger.fine("Toggle dice button. Enable: " + enable);
    diceButton.setDisable(!enable);
  }

  /**
   * Sets the round label text.
   * @param round the round number to display.
   */
  public void updateRoundLabel(String round) {
    logger.fine("Updating round label to: " + round);
    roundInfo.setText(round);
  }

  /**
   * Sets the text for the currentPlayer label.
   */
  public void updateCurrentPlayerLabel(String playerName) {
    logger.fine("Updating current player label to: " + playerName);
    currentPlayerInfo.setText(playerName);
  }

  /**
   * Sets the text for the grade label to display the difficulty of the game.
   * @param grade the difficulty of the game.
   */
  public void updateGradeLabel(String grade) {
    logger.fine("Updating grade label to: " + grade);
    gradeInfo.setText(grade);
  }

  /**
   * Sets the text for the status label.
   * @param status the status of the game.
   */
  public void updateStatusLabel(String status) {
    logger.fine("Updating status label to: " + status);
    statusInfo.setText(status);
  }

  /**
   * Sets the text for the last player label.
   * @param playerName the name of the last player who moved.
   */
  public void updateLastPlayerLabel(String playerName) {
    logger.fine("Updating last player label to: " + playerName);
    lastPlayer.setText(playerName);
  }

  /**
   * Sets the text for the moved to label informing the user of which tile the player has moved to.
   * @param tileName the name of the tile the player has moved to.
   */
  public void updateMovedToLabel(String tileName) {
    logger.fine("Updating moved to label to: " + tileName);
    movedTo.setText(tileName);
  }

  /**
   * Sets teh text for the last roll label informing the player the value of the previous roll.
   * @param roll the value of the last roll.
   */
  public void updateLastRollLabel(String roll) {
    logger.fine("Updating last roll label to: " + roll);
    lastRoll.setText(roll);
  }

  /**
   * Sets the tile action label informing the player which tile action was triggered.
   * @param action the action triggered by the tile.
   */
  public void updateTileActionLabel(String action) {
    logger.fine("Updating tile action to: " + action);
    tileAction.setText(action);
  }

  /**
   * Gets the integer value from the round label.
   * @return Integer value of the round label text
   */
  public int getRoundLabel() {
    logger.fine("Getting round label integer value.");
    return Integer.parseInt(roundInfo.getText());
  }

  /**
   * Populates the player list card with the players from the specified player list.
   * @param players a list of players.
   */
  public void populatePlayerList(List<Player> players) {
    logger.fine("Populating player list with players: " + players);
    playerListCard.populatePlayerList(players);
  }

  /**
   * Animates the movement of a player's piece on the board.
   * @param player the player whose piece is being moved.
   * @param newTileId the ID of the tile to which the player is moving.
   * @param tileAction the action triggered by the tile.
   * @param roll the value of the dice roll.
   * @param players the list of players in the game.
   * @param onComplete a callback to be executed after the animation is complete.
   */
  public void animatePlayerMovement(Player player, int newTileId, TileAction tileAction, int roll,
                                    List<Player> players, Runnable onComplete) {
    logger.fine("Animating player movement for player " + player + ", with tile: " + newTileId + ", and tileAction:" + tileAction + ", roll: " + roll + ", players: " + players);
    ladderCanvas.animatePlayerMovement(player, newTileId, tileAction, roll, () -> {
      updateBoard(players);
      toggleDiceButton(true);
      if (onComplete != null) {
        onComplete.run();
      }
    });
  }

  /**
   * Creates a canvas to display the UI of the game board.
   * @param players the list of players in the game.
   * @param tiles the map of tiles in the game.
   */
  public void createBoard(ArrayList<Player> players, Map<Integer, Tile> tiles) {
    logger.fine("Creating board with players: " + players + ", tiles: " + tiles);
    ladderCanvas = new LadderCanvas(tiles, players, tiles.size());
    canvasContainer.getChildren().add(ladderCanvas);

    ladderCanvas.redrawBoard();
    ladderCanvas.updateBoard(players);
  }

  /**
   * Updates the board canvas with a new list of players and redraws the canvas to reflect the changes in
   * their positions.
   *
   * @param players The updated {@link List} of {@link Player} objects.
   */
  public void updateBoard(List<Player> players) {
    logger.fine("Updating board with players: " + players);
    ladderCanvas.updateBoard(players);
  }

  /**
   * Rotates the 3D representation of the game dice.
   * @param roll the value of the dice roll.
   */
  public void rotateDice(int roll) {
    logger.fine("Rotating dice for roll: " + roll);
    List<int[]> possibleRolls = new ArrayList<>();
    for (int roll1 = 1; roll1 <= 6; roll1++) {
      int roll2 = roll - roll1;
      if (roll2 >= 1 && roll2 <= 6) {
        possibleRolls.add(new int[]{roll1, roll2});
      }
    }

    int[] selectedRolls = possibleRolls.get(random.nextInt(possibleRolls.size()));

    Timeline timeline = new Timeline();
    for (int i = 0; i < 2; i++) {
      CuboidMesh currentDice = dice[i];

      currentDice.getTransforms().clear();

      Point3D targetRotation = DICE_ROTATION[selectedRolls[i] - 1];

      Rotate rotateX = new Rotate(0, Rotate.X_AXIS);
      Rotate rotateY = new Rotate(0, Rotate.Y_AXIS);
      Rotate rotateZ = new Rotate(0, Rotate.Z_AXIS);
      currentDice.getTransforms().addAll(rotateX, rotateY, rotateZ);

      double tumbleBase = 360 + random.nextDouble() * 180;
      double[] tumbleAngles = {
          tumbleBase * (random.nextBoolean() ? 1 : -1),
          tumbleBase * (random.nextBoolean() ? 1 : -1),
          tumbleBase * (random.nextBoolean() ? 1 : -1)
      };

      double finalX = targetRotation.getX() + Math.round(tumbleAngles[0] / 360) * 360;
      double finalY = targetRotation.getY() + Math.round(tumbleAngles[1] / 360) * 360;
      double finalZ = targetRotation.getZ() + Math.round(tumbleAngles[2] / 360) * 360;

      KeyValue kvTumbleX =
          new KeyValue(rotateX.angleProperty(), tumbleAngles[0], Interpolator.LINEAR);
      KeyValue kvTumbleY =
          new KeyValue(rotateY.angleProperty(), tumbleAngles[1], Interpolator.LINEAR);
      KeyValue kvTumbleZ =
          new KeyValue(rotateZ.angleProperty(), tumbleAngles[2], Interpolator.LINEAR);
      KeyFrame kfTumble = new KeyFrame(Duration.millis(250), kvTumbleX, kvTumbleY, kvTumbleZ);

      KeyValue kvFinalX = new KeyValue(rotateX.angleProperty(), finalX, Interpolator.EASE_OUT);
      KeyValue kvFinalY = new KeyValue(rotateY.angleProperty(), finalY, Interpolator.EASE_OUT);
      KeyValue kvFinalZ = new KeyValue(rotateZ.angleProperty(), finalZ, Interpolator.EASE_OUT);
      KeyFrame kfFinal = new KeyFrame(Duration.millis(350), kvFinalX, kvFinalY, kvFinalZ);

      timeline.getKeyFrames().addAll(kfTumble, kfFinal);
    }
    timeline.setCycleCount(1);
    timeline.play();
  }

  /**
   * Displays a toast to the user.
   * @param variant the variant of the toast (success, error, etc.)
   * @param title the title of the toast
   * @param message the message to display in the toast
   */
  public void showToast(Toast.ToastVariant variant, String title, String message) {
    logger.fine("Showing toast with variant: " + variant + ", title: " + title + ", message: " + message);
    Toast toast = new Toast(root, variant, title, message);
    toast.show();
  }

  /**
   * Creates a row for the game info.
   * @param labelText the text for the label
   * @param infoLabel the label to display the info
   * @return a HBox containing the label, separator, and info label
   */
  private HBox createGameInfoRow(String labelText, Label infoLabel) {
    logger.fine("Creating game info row for label: " + labelText + ", infoLabel: " + infoLabel);
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

  /**
   * Gets the root of this view.
   * @return the root {@link StackPane} of this view.
   */
  public StackPane getRoot() {
    logger.fine("Getting root pane.");
    return root;
  }
}
