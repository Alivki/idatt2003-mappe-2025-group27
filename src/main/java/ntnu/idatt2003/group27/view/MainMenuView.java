package ntnu.idatt2003.group27.view;

import java.util.ArrayList;
import java.util.List;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javax.swing.Action;
import javax.swing.Icon;
import ntnu.idatt2003.group27.controllers.MainController;
import ntnu.idatt2003.group27.models.Player;
import ntnu.idatt2003.group27.view.components.AppLayout;
import ntnu.idatt2003.group27.view.components.Card;
import ntnu.idatt2003.group27.view.components.CustomButton;
import ntnu.idatt2003.group27.view.components.MainMenuBoardButton;
import ntnu.idatt2003.group27.view.components.PlayerButtonListCell;

public class MainMenuView {
  private final StackPane root;

  //Header buttons
  private CustomButton ladderGameMainMenuButton;
  private CustomButton secondGameMainMenuButton;
  private CustomButton thirdGameMainMenuButton;
  private CustomButton applicationQuitButton;

  //Other buttons
  private CustomButton addPlayerButton;
  private CustomButton exportPlayersCsvButton;
  private CustomButton importPlayersCsvButton;

  //Board buttons
  private MainMenuBoardButton normalBoardButton;
  private MainMenuBoardButton crazyBoardButton;
  private MainMenuBoardButton impossibleBoardButton;
  private MainMenuBoardButton jsonBoardButton;

  private TextField playerNameTextField;

  public MainMenuView() {
    root = new StackPane();
    root.setAlignment(Pos.TOP_CENTER);
    root.getStyleClass().add("root");

    initializeLayout();
  }

  private void initializeLayout() {
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
    Label title = new Label("Stigespill");
    title.getStyleClass().add("h1");

    //Initializes board button grid
    GridPane boardGrid = new GridPane(10, 10);
    boardGrid.setAlignment(Pos.CENTER);

    //Initializes board buttons
    int boardButtonSize = 210;
    Insets boardButtonInsets = new Insets(10, 10, 10, 10);


    normalBoardButton = new MainMenuBoardButton(boardButtonSize,boardButtonInsets, "Vanlig", "Helt vanlig norsk stigespill med 90 ruter", new Image("icons/ladder_game_normal_board.png"));

    crazyBoardButton = new MainMenuBoardButton(boardButtonSize,boardButtonInsets, "Crazy", "Stigespill med tileAction!", new Image("icons/ladder_game_normal_board.png"));

    impossibleBoardButton = new MainMenuBoardButton(boardButtonSize,boardButtonInsets, "Impossible", "Veldig vanskelig stigespill", new Image("icons/ladder_game_normal_board.png"));

    jsonBoardButton = new MainMenuBoardButton(boardButtonSize,boardButtonInsets, "Vanlig (Json)", "Last inn eget spill fra Json fil", new Image("icons/ladder_game_normal_board.png"));

    //Positions board buttons on grid
    boardGrid.add(normalBoardButton, 0, 0);
    boardGrid.add(crazyBoardButton, 1, 0);
    boardGrid.add(impossibleBoardButton, 0, 1);
    boardGrid.add(jsonBoardButton, 1, 1);

    //Initializes player information card
    Card playerCard = new Card("Spillere", null, 200);
    playerCard.setSpacing(10);

    //Initializes list view to display player information
    ListView<Player> playerListView = new ListView<>();
    playerListView.setCellFactory(list -> new PlayerButtonListCell());
    if (MainController.getInstance() != null) {
      playerListView.setItems(MainController.getInstance().getPlayers());
    }
    playerListView.setPrefSize(playerCard.widthProperty().intValue(), 150);

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

    //Initializes player name input field
    playerNameTextField = new TextField();
    playerNameTextField.setPromptText("Spiller navn...");

    //Initializes add player button
    addPlayerButton = new CustomButton("Legg til spiller", CustomButton.ButtonVariant.PRIMARY, null);


    //Positions nodes correctly in each container
    playerCard.getChildren().addAll(playerListView, playerNameTextField, addPlayerButton);
    playerExportCsvCard.getChildren().addAll(exportPlayersCsvButton);
    playerImportCsvCard.getChildren().addAll(importPlayersCsvButton, csvExampleInfoHeaderLabel, csvExampleInfoDescriptionLabel);
    headerContainer.getChildren().addAll(ladderGameMainMenuButton, secondGameMainMenuButton, thirdGameMainMenuButton, applicationQuitButton);
    menuContainer.getChildren().addAll(title, boardGrid);
    layout.getHeader().getChildren().addAll(headerContainer);
    layout.getMainContainer().getChildren().addAll(menuContainer);
    layout.getLeftContainer().getChildren().addAll(playerCard, playerExportCsvCard, playerImportCsvCard);
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
  /**
   * Get the name from the player name text input field.
   * @return
   */
  public String getPlayerNameTextFieldValue() {
    return playerNameTextField.getText();
  }

  public StackPane getRoot() {
    return root;
  }
}
