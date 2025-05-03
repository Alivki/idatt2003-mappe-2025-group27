package ntnu.idatt2003.group27.view;

import java.util.ArrayList;
import java.util.List;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import ntnu.idatt2003.group27.controllers.MainMenuController;
import ntnu.idatt2003.group27.models.Piece;
import ntnu.idatt2003.group27.models.Player;
import ntnu.idatt2003.group27.view.components.AppLayout;
import ntnu.idatt2003.group27.view.components.Card;
import ntnu.idatt2003.group27.view.components.CustomButton;
import ntnu.idatt2003.group27.view.components.CustomToggleButton;
import ntnu.idatt2003.group27.view.components.MainMenuBoardButton;
import ntnu.idatt2003.group27.view.components.PlayerButtonListCell;
import ntnu.idatt2003.group27.view.components.PlayerListCardEditable;

public class MainMenuView {
  private final StackPane root;

  private MainMenuController mainMenuController;

  //Header buttons
  private CustomButton ladderGameMainMenuButton;
  private CustomButton secondGameMainMenuButton;
  private CustomButton thirdGameMainMenuButton;
  private CustomButton applicationQuitButton;

  //Other buttons
  private CustomButton addPlayerButton;
  private CustomButton exportPlayersCsvButton;
  private CustomButton importPlayersCsvButton;

  //Player icon selection buttons
  private ArrayList<ToggleButton> playerIconButtons = new ArrayList<>();

  //Board buttons
  private MainMenuBoardButton normalBoardButton;
  private MainMenuBoardButton crazyBoardButton;
  private MainMenuBoardButton impossibleBoardButton;
  private MainMenuBoardButton jsonBoardButton;

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
    secondGameMainMenuButton = new CustomButton("Spill 2", CustomButton.ButtonVariant.GHOST, null);
    thirdGameMainMenuButton = new CustomButton("Spill 3", CustomButton.ButtonVariant.GHOST, null);
    applicationQuitButton = new CustomButton("Avslutt", CustomButton.ButtonVariant.DESTRUCTIVE,
        actionEvent -> Platform.exit());

    //Initializes main content
    VBox menuContainer = new VBox(20);
    menuContainer.setAlignment(Pos.TOP_CENTER);

    //Initializes main content title
    VBox titleContainer = new VBox(0);
    Label title = new Label("Stigespill");
    title.getStyleClass().add("h1");

    //Initializes board button grid
    GridPane boardGrid = new GridPane(5,5);
    boardGrid.setAlignment(Pos.CENTER);
    boardGrid.getStyleClass().add("card");

    //Initializes board buttons
    int boardButtonMinSize = 100;
    int boardButtonPrefSize = 170;
    int boardButtonMaxSize = 200;
    int boardButtonImageSize = 120;
    Insets boardButtonInsets = new Insets(5, 5, 5, 5);


    normalBoardButton = new MainMenuBoardButton(boardButtonPrefSize, boardButtonMinSize, boardButtonMaxSize, boardButtonImageSize, boardButtonInsets, "Vanlig", "Helt vanlig norsk stigespill med 90 ruter", new Image("icons/ladder_game_normal_board.png"));

    crazyBoardButton = new MainMenuBoardButton(boardButtonPrefSize, boardButtonMinSize, boardButtonMaxSize, boardButtonImageSize, boardButtonInsets, "Crazy", "Stigespill med tileAction!", new Image("icons/ladder_game_normal_board.png"));

    impossibleBoardButton = new MainMenuBoardButton(boardButtonPrefSize, boardButtonMinSize, boardButtonMaxSize, boardButtonImageSize, boardButtonInsets, "Impossible", "Veldig vanskelig stigespill", new Image("icons/ladder_game_normal_board.png"));

    jsonBoardButton = new MainMenuBoardButton(boardButtonPrefSize, boardButtonMinSize, boardButtonMaxSize, boardButtonImageSize, boardButtonInsets, "Vanlig (Json)", "Last inn eget spill fra Json fil", new Image("icons/ladder_game_normal_board.png"));

    //Positions board buttons on grid
    boardGrid.add(normalBoardButton, 0, 0);
    boardGrid.add(crazyBoardButton, 1, 0);
    boardGrid.add(impossibleBoardButton, 0, 1);
    boardGrid.add(jsonBoardButton, 1, 1);

    //Initializes player information card
    Card playerCard = new Card("Spillere", null, 200);
    playerCard.setSpacing(10);

    //Initializes player name input field
    playerNameTextField = new TextField();
    playerNameTextField.setPromptText("Spiller navn...");

    //Initializes add player button
    addPlayerButton = new CustomButton("Legg til spiller", CustomButton.ButtonVariant.PRIMARY, null);

    //Initializes player list card to display player information
    playerListCardEditable = new PlayerListCardEditable("Spillere", "En oversikt over spillerne i spillet. Her kan du legge til og redigere spillere.", 300);
    playerListCardEditable.setSpacing(10);

    //Initializes piece selection buttons
    HBox pieceSelectionButtonContainer = new HBox(7);
    pieceSelectionButtonContainer.setAlignment(Pos.CENTER);

    ToggleGroup pieceSelectionButtonGroup = new ToggleGroup();
    for(int i = 0; i < pieces.size(); i++){
      ImageView playerIcon = new ImageView(new Image(pieces.get(i).getIconFilePath()));
      //CustomButton playerIconButton = new CustomButton(playerIcon, CustomButton.ButtonVariant.ICON, null);
      CustomToggleButton playerIconButton = new CustomToggleButton(playerIcon, 28);
      playerIconButton.setToggleGroup(pieceSelectionButtonGroup);
      playerIconButtons.add(playerIconButton);
    }

    //Initializes player csv cards
    Card playerExportCsvCard = new Card("Eksporter spillere", "Last ned csv fil med spillerdata", 100);
    playerExportCsvCard.setSpacing(5);
    ImageView downloadImageView = new ImageView("icons/download.png");
    exportPlayersCsvButton = new CustomButton("Last ned spillere", CustomButton.ButtonVariant.PRIMARY_ICON, downloadImageView, null);

    Card playerImportCsvCard = new Card("Importer spillere", "Last opp spillerdata fra csv", 200);
    playerImportCsvCard.setSpacing(5);
    ImageView uploadImageView = new ImageView("icons/upload.png");
    importPlayersCsvButton = new CustomButton("Last opp spillere", CustomButton.ButtonVariant.PRIMARY_ICON, uploadImageView, null);

    Label csvExampleInfoHeaderLabel = new Label("CSV-fil eksempel:");
    Label csvExampleInfoDescriptionLabel = new Label("navn, brikke\nSpiller 1, bil\nSpiller 2, sjakk");
    csvExampleInfoDescriptionLabel.getStyleClass().add("info-text");

    //Positions nodes correctly in each container
    pieceSelectionButtonContainer.getChildren().addAll(playerIconButtons);
    playerListCardEditable.getChildren().addAll(pieceSelectionButtonContainer, playerNameTextField, addPlayerButton);
    playerExportCsvCard.getChildren().addAll(exportPlayersCsvButton);
    playerImportCsvCard.getChildren().addAll(importPlayersCsvButton, csvExampleInfoHeaderLabel, csvExampleInfoDescriptionLabel);

    headerContainer.getChildren().addAll(ladderGameMainMenuButton, secondGameMainMenuButton, thirdGameMainMenuButton, applicationQuitButton);
    titleContainer.getChildren().add(title);
    menuContainer.getChildren().addAll(titleContainer, boardGrid);
    layout.getHeader().getChildren().addAll(headerContainer);
    layout.getMainContainer().getChildren().addAll(menuContainer);
    layout.getRightContainer().getChildren().addAll(playerExportCsvCard, playerImportCsvCard);
    layout.getLeftContainer().getChildren().addAll(playerListCardEditable);
    root.getChildren().add(layout);
  }

  public void setHomeButtonHandler(EventHandler<ActionEvent> action) {
    ladderGameMainMenuButton.setOnAction(action);
  }

  public void setStartButtonHandler(EventHandler<ActionEvent> action) {
    secondGameMainMenuButton.setOnAction(action);
  }

  public void setSettingsButtonHandler(EventHandler<ActionEvent> action) {
    thirdGameMainMenuButton.setOnAction(action);
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
