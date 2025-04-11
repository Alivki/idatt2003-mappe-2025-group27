package ntnu.idatt2003.group27.view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import ntnu.idatt2003.group27.view.components.AppLayout;
import ntnu.idatt2003.group27.view.components.CustomButton;
import ntnu.idatt2003.group27.view.components.MainMenuBoardButton;

public class MainMenuView {
  private final StackPane root;

  private CustomButton ladderGameMainMenuButton;
  private CustomButton secondGameMainMenuButton;
  private CustomButton thirdGameMainMenuButton;

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
    ladderGameMainMenuButton = new CustomButton("Stigespill", CustomButton.ButtonVariant.GHOST, null);
    secondGameMainMenuButton = new CustomButton("Spill 2", CustomButton.ButtonVariant.GHOST, null);
    thirdGameMainMenuButton = new CustomButton("Spill 3", CustomButton.ButtonVariant.GHOST, null);

    //Initializes main content
    VBox menuContainer = new VBox(20);
    menuContainer.setAlignment(Pos.CENTER);
    menuContainer.setPadding(new Insets(60, 0, 0, 0));

    //Initializes main content title
    Label title = new Label("Stigespill");
    title.getStyleClass().add("h1");

    //Initializes board button grid
    GridPane boardGrid = new GridPane(10, 10);
    boardGrid.setAlignment(Pos.CENTER);

    //Initializes board buttons
    int boardButtonSize = 220;
    Insets boardButtonInsets = new Insets(10, 10, 10, 10);

    MainMenuBoardButton
        normalBoardButton = new MainMenuBoardButton(boardButtonSize,boardButtonInsets, "Vanlig", "Helt vanlig norsk stigespill med 90 ruter", new Image("icons/stigespill.png"));

    MainMenuBoardButton
        crazyBoardButton = new MainMenuBoardButton(boardButtonSize,boardButtonInsets, "Crazy", "Stigespill med tileAction!", new Image("icons/stigespill.png"));

    MainMenuBoardButton
        impossibleBoardButton = new MainMenuBoardButton(boardButtonSize,boardButtonInsets, "Impossible", "Veldig vanskelig stigespill", new Image("icons/stigespill.png"));

    MainMenuBoardButton
        jsonBoardButton = new MainMenuBoardButton(boardButtonSize,boardButtonInsets, "Vanlig (Json)", "Last inn eget spill fra Json fil", new Image("icons/stigespill.png"));

    //Positions board buttons on grid
    boardGrid.add(normalBoardButton, 0, 0);
    boardGrid.add(crazyBoardButton, 1, 0);
    boardGrid.add(impossibleBoardButton, 0, 1);
    boardGrid.add(jsonBoardButton, 1, 1);

    //Positions nodes correctly in each container
    headerContainer.getChildren().addAll(ladderGameMainMenuButton, secondGameMainMenuButton, thirdGameMainMenuButton);
    menuContainer.getChildren().addAll(title, boardGrid);
    layout.getHeader().getChildren().addAll(headerContainer);
    layout.getMainContainer().getChildren().addAll(menuContainer);
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

  public StackPane getRoot() {
    return root;
  }
}
