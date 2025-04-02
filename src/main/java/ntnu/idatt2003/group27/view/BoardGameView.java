package ntnu.idatt2003.group27.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import ntnu.idatt2003.group27.models.BoardGame;
import ntnu.idatt2003.group27.models.BoardGameController;
import ntnu.idatt2003.group27.models.BoardGameObserver;
import ntnu.idatt2003.group27.models.Player;

/**
 * This class represent the game board in the GUI for our game.
 */
public class GameScreen implements BoardGameObserver {
  private final StackPane root;
  private BoardGame game;
  private BoardGameController controller;

  /**
   *.
   */
  public GameScreen() {
    root = new StackPane();
    root.setAlignment(Pos.TOP_CENTER);
    root.setPadding(new Insets(20, 10, 10, 10));
    root.setStyle("-fx-background-color: #f1f1f1");

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

    gameArea.getChildren().addAll(title);
    root.getChildren().add(gameArea);
  }

  @Override
  public void onPlayerMoved(Player player) {

  }

  @Override
  public void onPlayerWon(Player player) {
    root.setStyle("-fx-background-color: #f1f1f1");
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
