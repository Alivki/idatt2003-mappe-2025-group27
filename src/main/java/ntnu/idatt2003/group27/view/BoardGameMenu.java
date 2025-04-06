package ntnu.idatt2003.group27.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;
import ntnu.idatt2003.group27.models.BoardGame;
import ntnu.idatt2003.group27.controllers.BoardGameController;
import ntnu.idatt2003.group27.models.interfaces.BoardGameObserver;
import ntnu.idatt2003.group27.models.Player;
import ntnu.idatt2003.group27.view.components.Alert;
import ntnu.idatt2003.group27.view.components.CustomButton;

/**
 * This class represent the game board in the GUI for our game.
 */
public class BoardGameMenu implements BoardGameObserver {
  private final StackPane root;
  private BoardGame game;
  private BoardGameController controller;

  /**
   *.
   */
  public BoardGameMenu(BoardGame game, BoardGameController controller) {
    this.game = game;
    this.controller = controller;
    game.addObserver(this);

    root = new StackPane();
    root.setAlignment(Pos.TOP_CENTER);
    root.setPadding(new Insets(20, 10, 10, 10));
    root.getStyleClass().add("root");

    initializeLayout();
  }

  /**
   *.
   */
  private void initializeLayout() {
    VBox gameArea = new VBox(20);
    gameArea.setMinSize(400, 400);
    gameArea.setMaxSize(400, 400);
    gameArea.setStyle("-fx-background-color: #FFFFFF");
    gameArea.setAlignment(Pos.TOP_CENTER);
    gameArea.setPadding(new Insets(60, 0, 0, 0));

    Label title = new Label("Stigespillet");

    CustomButton button = new CustomButton("Start", CustomButton.ButtonType.PRIMARY, e -> {
      Alert popup = new Alert(
        root,
        "Confirm Start",
        "Are you sure you want to start the game? \nYou might lose after this game",
        "Start game",
        "Cancel",
        response -> {
          if (response) {
            controller.play();
          }
        }
      );
      popup.show();
    });

    gameArea.getChildren().addAll(title, button);
    root.getChildren().add(gameArea);
  }

  @Override
  public void onPlayerMoved(Player player) {

  }

  @Override
  public void onPlayerWon(Player player) {
    root.setStyle("-fx-background-color: #d81414");
  }

  /**
   * Getter for the root in the GUI.
   *
   * @return Pane the screen that should be shown
   */
  public StackPane getRoot() {
    return root;
  }

}
