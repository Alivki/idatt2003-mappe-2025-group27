package ntnu.idatt2003.group27.view;

import java.util.stream.IntStream;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import ntnu.idatt2003.group27.controllers.BoardGameController;
import ntnu.idatt2003.group27.models.BoardGame;
import ntnu.idatt2003.group27.models.Player;
import ntnu.idatt2003.group27.models.interfaces.BoardGameObserver;

public class LadderGameView implements BoardGameObserver {
  private final StackPane root;

  private final BoardGame game;
  private final BoardGameController controller;

  public LadderGameView(BoardGame game, BoardGameController controller) {
    this.game = game;
    this.controller = controller;
    game.addObserver(this);

    root = new StackPane();
    root.setAlignment(Pos.TOP_CENTER);
    root.setPadding(new Insets(20, 10, 10, 10));
    root.getStyleClass().add("root");

    initializeLayout();
  }

  private void initializeLayout() {
    VBox gameArea = new VBox(20);
    gameArea.setMinSize(700, 700);
    gameArea.setMaxSize(700, 700);
    gameArea.setStyle("-fx-background-color: #FFFFFF");
    gameArea.setAlignment(Pos.TOP_LEFT);
    gameArea.setPadding(new Insets(30, 0, 0, 30));

    Label title = new Label("Stigespillet");

    final Canvas canvas = createBoard();

    gameArea.getChildren().addAll(title, canvas);
    root.getChildren().add(gameArea);
  }

  private Canvas createBoard() {
    final Canvas canvas = new Canvas(640,576.2);
    GraphicsContext gc = canvas.getGraphicsContext2D();

    double squareSize = 63.8;
    int squaresPerRows = 10;
    int rows = 9;
    double yPos = squareSize * (squaresPerRows - 1) - 63.8;

    IntStream.range(1, squaresPerRows * rows + 1).forEach(i -> {
      int row = (i - 1) / squaresPerRows;
      int col = (i - 1) % squaresPerRows;

      double xPos;
      boolean leftToRight = (row % 2 == 0);
      if (leftToRight) {
        xPos = col * squareSize;
      } else {
        xPos = (squaresPerRows - 1 - col) * squareSize;
      }

      double currentYPos = yPos - (row * squareSize);

      gc.setStroke(Color.BLACK);
      gc.setLineWidth(3.0);
      gc.strokeRect(xPos, currentYPos, squareSize,squareSize);

      gc.setFill(Color.BLACK);
      gc.setFont(Font.font("Inter", 14));
      gc.fillText(String.valueOf(i), xPos + 40, currentYPos + 25);
    });

    return canvas;
  }

  @Override
  public void onPlayerMoved(Player player) {

  }

  @Override
  public void onPlayerWon(Player player) {
    root.setStyle("-fx-background-color: #d81414");
  }

  public StackPane getRoot() {
    return root;
  }
}
