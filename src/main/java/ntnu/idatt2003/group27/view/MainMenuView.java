package ntnu.idatt2003.group27.view;

import java.util.ArrayList;
import java.util.List;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import ntnu.idatt2003.group27.controllers.MainMenuController;
import ntnu.idatt2003.group27.models.Piece;
import ntnu.idatt2003.group27.models.Player;
import ntnu.idatt2003.group27.view.components.AppLayout;
import ntnu.idatt2003.group27.view.components.Card;
import ntnu.idatt2003.group27.view.components.CustomButton;
import ntnu.idatt2003.group27.view.components.CustomToggleButton;
import ntnu.idatt2003.group27.view.components.MainMenuBoardButton;
import ntnu.idatt2003.group27.view.components.PlayerListCardEditable;

public class MainMenuView {
  private final StackPane root;

  private MainMenuController mainMenuController;

  //Header buttons
  private CustomButton ladderGameMainMenuButton;
  private CustomButton mathGameMainMenuButton;
  private CustomButton applicationQuitButton;

  //title label
  private Label title;

  //Other buttons
  private CustomButton addPlayerButton;
  private CustomButton exportPlayersCsvButton;
  private CustomButton importPlayersCsvButton;

  //Player icon selection buttons
  private ArrayList<ToggleButton> playerIconButtons = new ArrayList<>();
  private CustomButton colorPicker;
  private Color pickedColor;

  //Game difficulty button grid
  private GridPane gameDifficultyGrid;

  //Game difficulty buttons
  private MainMenuBoardButton normalBoardButton;
  private MainMenuBoardButton crazyBoardButton;
  private MainMenuBoardButton impossibleBoardButton;
  private MainMenuBoardButton jsonBoardButton;
  private MainMenuBoardButton mathEasyButton;
  private MainMenuBoardButton mathMediumButton;
  private MainMenuBoardButton mathHardButton;

  //Cards
  private PlayerListCardEditable playerListCardEditable;
  private TextField playerNameTextField;

  public MainMenuView() {
    root = new StackPane();
    root.setAlignment(Pos.TOP_CENTER);
    root.getStyleClass().add("root");
  }

  public void initializeLayout(List<Piece> pieces) {
    AppLayout layout = new AppLayout();

    //Initializes header
    HBox headerContainer = new HBox(20);
    headerContainer.setAlignment(Pos.CENTER);

    //Initializes header buttons
    ladderGameMainMenuButton = new CustomButton("Stigespill", CustomButton.ButtonVariant.GHOST, null);
    mathGameMainMenuButton = new CustomButton("Matte spill", CustomButton.ButtonVariant.GHOST, null);
    applicationQuitButton = new CustomButton("Avslutt", CustomButton.ButtonVariant.DESTRUCTIVE,
        actionEvent -> Platform.exit());

    //Initializes main content title
    title = new Label("Stigespill");
    title.getStyleClass().add("h1");

    //Initializes board button grid
    gameDifficultyGrid = new GridPane(5,5);
    gameDifficultyGrid.setAlignment(Pos.CENTER);
    gameDifficultyGrid.getStyleClass().add("card");

    //Initializes board buttons
    int boardButtonMinSize = 100;
    int boardButtonPrefSize = 170;
    int boardButtonMaxSize = 200;
    int boardButtonImageSize = 120;
    Insets boardButtonInsets = new Insets(5, 5, 5, 5);

    normalBoardButton = new MainMenuBoardButton(
        boardButtonPrefSize, boardButtonMinSize, boardButtonMaxSize, boardButtonImageSize,
        boardButtonInsets, "Vanlig", "Helt vanlig norsk stigespill med 90 ruter",
        new Image("icons/ladder_game_normal_board.png"));
    crazyBoardButton = new MainMenuBoardButton(
        boardButtonPrefSize, boardButtonMinSize, boardButtonMaxSize, boardButtonImageSize,
        boardButtonInsets, "Crazy", "Stigespill med tileAction!",
        new Image("icons/ladder_game_normal_board.png"));
    impossibleBoardButton = new MainMenuBoardButton(
        boardButtonPrefSize, boardButtonMinSize, boardButtonMaxSize, boardButtonImageSize,
        boardButtonInsets, "Impossible", "Veldig vanskelig stigespill",
        new Image("icons/ladder_game_normal_board.png"));
    jsonBoardButton = new MainMenuBoardButton(
        boardButtonPrefSize, boardButtonMinSize, boardButtonMaxSize, boardButtonImageSize,
        boardButtonInsets, "Vanlig (Json)", "Last inn eget spill fra Json fil",
        new Image("icons/ladder_game_normal_board.png"));

    mathEasyButton = new MainMenuBoardButton(
        boardButtonPrefSize, boardButtonMinSize, boardButtonMaxSize, boardButtonImageSize,
        boardButtonInsets, "Enkel", "Matte spill med enkel matte",
        new Image("icons/math_game.png"));
    mathMediumButton = new MainMenuBoardButton(
        boardButtonPrefSize, boardButtonMinSize, boardButtonMaxSize, boardButtonImageSize,
        boardButtonInsets, "Medium", "Matte spill med litt vanskligere matte",
        new Image("icons/math_game.png"));
    mathHardButton = new MainMenuBoardButton(
        boardButtonPrefSize, boardButtonMinSize, boardButtonMaxSize, boardButtonImageSize,
        boardButtonInsets, "Vansklig", "Matte spill med vansklig matte",
        new Image("icons/math_game.png"));

    //Positions board buttons on grid
    gameDifficultyGrid.add(normalBoardButton, 0, 0);
    gameDifficultyGrid.add(crazyBoardButton, 1, 0);
    gameDifficultyGrid.add(impossibleBoardButton, 0, 1);
    gameDifficultyGrid.add(jsonBoardButton, 1, 1);

    HBox nameAndColorInputContainer = new HBox(10);

    //Initializes player name input field
    playerNameTextField = new TextField();
    playerNameTextField.setPromptText("Spiller navn...");
    playerNameTextField.setPrefHeight(31);
    HBox.setHgrow(playerNameTextField, Priority.ALWAYS);

    ImageView colorPickerIcon = new ImageView("icons/picker-button.png");
    colorPicker = new CustomButton(null, CustomButton.ButtonVariant.GHOST_ICON, colorPickerIcon, null);
    colorPickerIcon.setFitWidth(20);
    colorPickerIcon.setFitHeight(20);

    //Initializes add player button
    addPlayerButton = new CustomButton("Legg til spiller", CustomButton.ButtonVariant.PRIMARY, null);

    //Initializes player list card to display player information
    playerListCardEditable = new PlayerListCardEditable("Spillere", "En oversikt over spillerne i spillet. Her kan du legge til og redigere spillere.", 382);
    playerListCardEditable.setSpacing(10);

    //Initializes piece selection buttons
    HBox pieceSelectionButtonContainer = new HBox(7);
    pieceSelectionButtonContainer.setAlignment(Pos.CENTER);

    ToggleGroup pieceSelectionButtonGroup = new ToggleGroup();
    pieces.forEach(piece -> {
      ImageView playerIcon = new ImageView(new Image(piece.getIconFilePath()));
      //CustomButton playerIconButton = new CustomButton(playerIcon, CustomButton.ButtonVariant.ICON, null);
      CustomToggleButton playerIconButton = new CustomToggleButton(playerIcon, 34);
      playerIconButton.setToggleGroup(pieceSelectionButtonGroup);
      playerIconButtons.add(playerIconButton);
    });

    //Initializes player csv cards
    Card playerExportCsvCard = new Card("Eksporter spillere", "Last ned csv fil med spillerdata", 100);
    playerExportCsvCard.setSpacing(5);
    ImageView downloadImageView = new ImageView("icons/download.png");
    exportPlayersCsvButton = new CustomButton("Last ned spillere", CustomButton.ButtonVariant.PRIMARY_ICON, downloadImageView, null);

    Card playerImportCsvCard = new Card("Importer spillere", "Last opp spillerdata fra csv", 200);
    playerImportCsvCard.setSpacing(5);
    ImageView uploadImageView = new ImageView("icons/upload.png");
    importPlayersCsvButton = new CustomButton("Last opp spillere", CustomButton.ButtonVariant.PRIMARY_ICON, uploadImageView, null);

    VBox csvExample = new VBox(5);
    csvExample.getStyleClass().add("info-container");

    Label csvExampleInfoHeaderLabel = new Label("CSV-fil eksempel:");
    Label csvExampleInfoDescriptionLabel = new Label("navn, brikke, farge\nSpiller 1, bil, red\nSpiller 2, sjakk, blue");
    csvExampleInfoDescriptionLabel.getStyleClass().add("info-text");

    //Positions nodes correctly in each container
    pieceSelectionButtonContainer.getChildren().addAll(playerIconButtons);
    nameAndColorInputContainer.getChildren().addAll(playerNameTextField, colorPicker);
    playerListCardEditable.getChildren().addAll(pieceSelectionButtonContainer, nameAndColorInputContainer, addPlayerButton);
    playerExportCsvCard.getChildren().addAll(exportPlayersCsvButton);
    csvExample.getChildren().addAll(csvExampleInfoDescriptionLabel);
    playerImportCsvCard.getChildren().addAll(importPlayersCsvButton, csvExampleInfoHeaderLabel, csvExample);

    headerContainer.getChildren().addAll(ladderGameMainMenuButton, mathGameMainMenuButton, applicationQuitButton);
    layout.getHeader().getChildren().addAll(headerContainer);
    layout.getMainContainer().getChildren().addAll(title, gameDifficultyGrid);
    layout.getRightContainer().getChildren().addAll(playerExportCsvCard, playerImportCsvCard);
    layout.getLeftContainer().getChildren().addAll(playerListCardEditable);
    root.getChildren().add(layout);
  }

  public void setLadderGameButtonHandler(EventHandler<ActionEvent> action) {
    ladderGameMainMenuButton.setOnAction(action);
  }

  public void setMathGameButtonHandler(EventHandler<ActionEvent> action) {
    mathGameMainMenuButton.setOnAction(action);
  }

  public void setAddPlayerButtonHandler(EventHandler<ActionEvent> action) {
    addPlayerButton.setOnAction(action);
  }

  public void setNormalBoardButtonHandler(EventHandler<ActionEvent> action) {
    normalBoardButton.setOnAction(action);
  }

  public void setCrazyBoardButtonHandler(EventHandler<ActionEvent> action) {
    crazyBoardButton.setOnAction(action);
  }

  public void setImpossibleBoardButtonHandler(EventHandler<ActionEvent> action) {
    impossibleBoardButton.setOnAction(action);
  }

  public void setJsonBoardButtonHandler(EventHandler<ActionEvent> action) {
    jsonBoardButton.setOnAction(action);
  }

  public void setEasyMathButtonHandler(EventHandler<ActionEvent> action) {
    normalBoardButton.setOnAction(action);
  }

  public void setMediumMathButtonHandler(EventHandler<ActionEvent> action) {
    crazyBoardButton.setOnAction(action);
  }

  public void setHardMathButtonHandler(EventHandler<ActionEvent> action) {
    impossibleBoardButton.setOnAction(action);
  }

  public void setExportPlayersCsvButtonHandler(EventHandler<ActionEvent> action) {
    exportPlayersCsvButton.setOnAction(action);
  }

  public void setImportPlayersCsvButtonHandler(EventHandler<ActionEvent> action) {
    importPlayersCsvButton.setOnAction(action);
  }

  public void setPlayerPieceButtonHandlers(int buttonIndex, EventHandler<ActionEvent> action) {
    playerIconButtons.get(buttonIndex).setOnAction(action);
  }

  public void setRemovePlayerButtonHandler(Player player, EventHandler<ActionEvent> action) {
    playerListCardEditable.setRemovePlayerButtonHandler(player, action);
  }

  public void switchToGameLadder() {
    title.setText("Stigespill");

    gameDifficultyGrid.getChildren().clear();
    gameDifficultyGrid.add(normalBoardButton, 0, 0);
    gameDifficultyGrid.add(crazyBoardButton, 1, 0);
    gameDifficultyGrid.add(impossibleBoardButton, 0, 1);
    gameDifficultyGrid.add(jsonBoardButton, 1, 1);
  }

  public void switchToGameMath() {
    title.setText("Matte spill");

    gameDifficultyGrid.getChildren().clear();
    gameDifficultyGrid.add(mathEasyButton, 0, 0);
    gameDifficultyGrid.add(mathMediumButton, 1, 0);
    gameDifficultyGrid.add(mathHardButton, 2, 0);
  }

  /**
   * Sets a new icon for the color picker button to indicate what color was picked by the user.
   *
   * @param color The {@link Color} object representing the color to be displayed.
   */
  public void showPickedColor(Color color) {
    Circle colorCircle = new Circle(10);
    colorCircle.setFill(color);
    colorPicker.setGraphic(colorCircle);
  }

  /**
   * Resets the icon back to the default color picker icon when a player has been added.
   */
  public void removePickedColor() {
    ImageView colorPickerIcon = new ImageView("icons/picker-button.png");
    colorPickerIcon.setFitWidth(20);
    colorPickerIcon.setFitHeight(20);
    colorPicker.setGraphic(colorPickerIcon);
  }

  /**
   * Displays the color picker for the user to select a color.
   */
  public void showColorPicker() {
    ColorPicker tempColorPicker = new ColorPicker(pickedColor != null ? pickedColor : Color.WHITE);
    tempColorPicker.setStyle("-fx-color-label-visible: false; -fx-background-color: transparent; -fx-pref-width: 0; -fx-pref-height: 0;");
    tempColorPicker.setOnAction(colorPickEvent -> {
      pickedColor = tempColorPicker.getValue();
      showPickedColor(pickedColor);
    });
    StackPane tempContainer = new StackPane(tempColorPicker);
    tempContainer.setVisible(false);
    playerListCardEditable.getChildren().add(tempContainer);
    tempContainer.setLayoutX(colorPicker.getLayoutX());
    tempContainer.setLayoutY(colorPicker.getLayoutY() + colorPicker.getHeight());
    tempColorPicker.show();
    tempColorPicker.getStyleClass().add("color-palette");
    tempColorPicker.setOnHidden(colorPickerHideEvent -> playerListCardEditable.getChildren().remove(tempContainer));
  }

  /**
   * Sets the picked color to the given color.
   *
   * @param color the {@link Color} object representing the color to be set.
   */
  public void setPickedColor(Color color) {
    this.pickedColor = color;
  }

  /**
   * Retrieves the color picked by the user.
   *
   * @return the {@link Color} object representing the picked color.
   */
  public Color getPickedColor() {
    return pickedColor;
  }

  /**
   * Set the action for the color picker button.
   *
   * @param action the action to set.
   */
  public void setColorPickerButtonHandler(EventHandler<ActionEvent> action) {
    colorPicker.setOnAction(action);
  }

  public void populatePlayerList(List<Player> players){
    playerListCardEditable.populatePlayerList(players);
  }

  /**
   * Set the state of the selection button to enabled or disabled. Updates the display and prevents / allows button interaction.
   * @param buttonIndex
   * @param disable
   */
  public void setDisablePlayerPieceButton(int buttonIndex, boolean disable){
    playerIconButtons.get(buttonIndex).setDisable(disable);
  }

  /**
   * Set the state of all player piece selection buttons.
   * @param disable
   */
  public void setDisableAllPlayerPieceButtons(boolean disable){
    playerIconButtons.forEach(button -> button.setDisable(disable));
  }

  /**
   * Get the name from the player name text input field.
   * @return
   */
  public String getPlayerNameTextFieldValue() {
    return playerNameTextField.getText();
  }

  /**
   * Sets the controller of the view in the MVC pattern
   * @return
   */
  public void setMainMenuController(MainMenuController mainMenuController) {
    this.mainMenuController = mainMenuController;
  }

  public StackPane getRoot() {
    return root;
  }
}
