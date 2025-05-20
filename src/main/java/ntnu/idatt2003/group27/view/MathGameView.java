package ntnu.idatt2003.group27.view;

import java.util.ArrayList;
import java.util.Map;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import ntnu.idatt2003.group27.models.Board;
import ntnu.idatt2003.group27.models.Player;
import ntnu.idatt2003.group27.models.Tile;
import ntnu.idatt2003.group27.models.interfaces.TileAction;
import ntnu.idatt2003.group27.view.components.*;

import java.util.List;

/**
 * A class representing the view for the ladder game.
 * Displays all necessary GUI for interacting with and playing the ladder game.
 */
public class MathGameView {
  /**
   * Logger instance for the {@link MathGameView} class.
   * Used for logging informational messages and errors related to class operations.
   */
  private static final Logger logger = Logger.getLogger(MathGameView.class.getName());

  /**
   * Root container for the Ladder Game view.
   */
  private final StackPane root;

  /**
   * Button to return to the home screen.
   */
  private CustomButton homeButton;

  /**
   * Canvas for rendering the game board.
   */
  private MathCanvas mathCanvas;

  /**
   * VBox for showing playing buttons and input field
   */
  private VBox playMenuBox;

  /**
   * Button to play the round.
   */
  private CustomButton playButton;

  /**
   * Text field to input math answer
   */
  private TextField answerField;

  /**
   * Button to submit answer.
   */
  private CustomButton answerButton;

  /**
   * Button to restart the game.
   */
  private CustomButton restartButton;

  /**
   * Card displaying the list of players.
   */
  private PlayerListCard playerListCard;

  /**
   * Container for the canvas and related UI elements.
   */
  private VBox canvasContainer;

  /**
   * Label showing the current round number.
   */
  private Label roundInfo;

  /**
   * Label showing the current player's name.
   */
  private Label currentPlayerInfo;

  /**
   * Label showing the current player's grade.
   */
  private Label gradeInfo;

  /**
   * Label showing the current game status.
   */
  private Label statusInfo;

  private int playerAmount;

  /**
   * Constructs a new {@code MathGameView} and initializes the root layout.
   * The root is a {@link StackPane} aligned to the top center and styled with the "root" CSS class.
   * This constructor sets up the foundational layout for this interface.
   */
  public MathGameView() {
    logger.fine("Initializing MathGameView.");
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
    logger.fine("Initialize layout.");
    AppLayout layout = new AppLayout();

    homeButton = new CustomButton("Avlutt spill/hjem", CustomButton.ButtonVariant.GHOST, null);

    playerListCard = new PlayerListCard("Spillere", "Spillerne i spillet", 382);

    Card settingsCard = new Card("Innstillinger", "Restart eller slutt spill", 115);
    VBox settingsButtonContainer = new VBox(12);
    settingsButtonContainer.setPadding(new Insets(12, 0, 0, 0));
    restartButton = new CustomButton("Restart", CustomButton.ButtonVariant.SECONDARY, null);

    Label title = new Label("Mattespill");
    title.getStyleClass().add("h1");

    canvasContainer = new VBox();
    canvasContainer.setAlignment(Pos.CENTER);
    canvasContainer.getStyleClass().add("gameAreaCard");

    layout.getMainContainer().getChildren().addAll(title, canvasContainer);

    layout.getMainContainer().widthProperty().addListener((obs, oldWidth, newWidth) -> {
      double availableWidth = newWidth.doubleValue() - 60;
      double tileSize = (availableWidth - 90) / 5;
      double height = tileSize * playerAmount + 70 + ((playerAmount - 1) *  20);
      mathCanvas.setWidth(availableWidth);
      mathCanvas.setHeight(height);
      mathCanvas.resizeBoard(tileSize);
    });

    Card rightCard = new Card("Spill info", null, 350);

    VBox gameInfo = new VBox(5);
    gameInfo.setPadding(new Insets(0, 0, 20, 0));

    roundInfo = new Label("1");
    currentPlayerInfo = new Label("");
    gradeInfo = new Label("");
    statusInfo = new Label("Pågående");

    Separator separator = new Separator();

    playButton = new CustomButton("Start runden", CustomButton.ButtonVariant.PRIMARY, null);

    playMenuBox = new VBox(5);
    answerField = new TextField();
    answerField.setPromptText("Skriv inn svaret her");
    answerButton = new CustomButton("Send svar", CustomButton.ButtonVariant.PRIMARY, null);

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
    playMenuBox.getChildren().add(playButton);
    rightCard.getChildren()
        .addAll(gameInfo, separator, playMenuBox);
    layout.getRightContainer().getChildren().addAll(rightCard);

    root.getChildren().add(layout);
  }

  public void clearTextField() {
    logger.fine("Clearing text field.");
    answerField.clear();
  }

  public void roundPlay(String question) {
    logger.fine("Playing round with question: " + question);
    clearTextField();
    playMenuBox.getChildren().clear();
    Label questionLabel = new Label("Spørsmål: " + question);
    questionLabel.getStyleClass().add("h2");
    playMenuBox.getChildren().addAll(questionLabel, answerField, answerButton);
  }

  public void betweenRounds() {
    logger.fine("Executing between round logic.");
    playMenuBox.getChildren().clear();
    playMenuBox.getChildren().addAll(playButton);
  }

  public String getAnswer() {
    return answerField.getText();
  }

  public void setAnswerButtonHandler(EventHandler<ActionEvent> action) {
    logger.fine("Setting answer button handler.");
    answerButton.setOnAction(action);
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
  public void setPlayButtonHandler(EventHandler<ActionEvent> action) {
    logger.fine("Setting play button handler.");
    playButton.setOnAction(action);
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
   * Sets the round label text.
   *
   * @param round
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
   *
   * @param grade
   */
  public void updateGradeLabel(String grade) {
    logger.fine("Updating grade label to: " + grade);
    gradeInfo.setText(grade);
  }

  /**
   * Sets the text for the status label.
   *
   * @param status
   */
  public void updateStatusLabel(String status) {
    logger.fine("Updating status label to: " + status);
    statusInfo.setText(status);
  }

  /**
   * Gets the integer value from the round label.
   *
   * @return Integer value of the round label text
   */
  public int getRoundLabel() {
    logger.fine("Getting round label value.");
    return Integer.parseInt(roundInfo.getText());
  }

  /**
   * Populates the player list card with the players from the specified player list.
   *
   * @param players a list of players.
   */
  public void populatePlayerList(List<Player> players) {
    logger.fine("Populating player list with players: " + players);
    playerListCard.populatePlayerList(players);
    playerAmount = players.size();
  }

  /**
   * Animates the movement of a player's piece on the board.
   *
   * @param player
   * @param newTileId
   * @param players
   * @param onComplete
   */
  public void animatePlayerMovement(Player player, int newTileId, List<Player> players,
                                    Runnable onComplete) {
    logger.fine("Animating player movement for player: " + player.getName() + ", tile ID: " + newTileId + ", players: " + players);
    mathCanvas.animatePlayerMovement(player, newTileId, () -> {
      updateBoard(players);
      if (onComplete != null) {
        onComplete.run();
      }
    });
  }

  /**
   * Creates a canvas to display the UI of the game board.
   *
   * @param players
   */
  public void createBoard(ArrayList<Player> players, Map<Player, Board> boards) {
    logger.fine("Creating board with players: " + players + ", boards: " + boards);
    mathCanvas = new MathCanvas(players, boards);
    canvasContainer.getChildren().add(mathCanvas);

    mathCanvas.redrawBoard();
    mathCanvas.updateBoard(players);
  }

  /**
   * Updates the board canvas with a new list of players and redraws the canvas to reflect the changes in
   * their positions.
   *
   * @param players The updated {@link List} of {@link Player} objects.
   */
  public void updateBoard(List<Player> players) {
    logger.fine("Updating board.");
    mathCanvas.updateBoard(players);
  }

  /**
   * Displays a toast to the user.
   *
   * @param variant
   * @param title
   * @param message
   */
  public void showToast(Toast.ToastVariant variant, String title, String message) {
    logger.fine("Showing toast of variant: " + variant + ", title: " + title + ", message: " + message);
    Toast toast = new Toast(root, variant, title, message);
    toast.show();
  }

  /**
   * Creates a row for the game info.
   *
   * @param labelText
   * @param infoLabel
   * @return
   */
  private HBox createGameInfoRow(String labelText, Label infoLabel) {
    logger.fine("Creating game info row with labelText: " + labelText + ", infoLabel: " + infoLabel);
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
   *
   * @return the root {@link StackPane} of this view.
   */
  public StackPane getRoot() {
    logger.fine("Getting root.");
    return root;
  }
}
