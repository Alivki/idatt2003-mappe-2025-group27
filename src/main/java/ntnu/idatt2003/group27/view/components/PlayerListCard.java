package ntnu.idatt2003.group27.view.components;

import java.util.List;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import ntnu.idatt2003.group27.models.Player;
import ntnu.idatt2003.group27.models.Tile;

/**
 * A card representing a player list.
 * Displays player information, like icon, name, and current {@link Tile} position.
 *
 * @author Amadeus Berg
 */
public class PlayerListCard extends Card {

  private final ScrollPane playerList;

  /**
   * Constructs a {@link Card} with the specified title, description and height.
   *
   * @param title       The title text to display on the card.
   * @param description The description text to display on the card, or null if no
   *                    description is needed.
   * @param height      The minimum height of the card in pixels.
   */
  public PlayerListCard(String title, String description, double height) {
    super(title, description, height);

    //Initializes playerList scrollpane
    playerList = new ScrollPane();
    playerList.setPadding(new Insets(10, 0, 0, 0));
    playerList.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
    playerList.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
    playerList.setMaxHeight(height);
    VBox.setVgrow(playerList, Priority.ALWAYS);
    playerList.setFitToWidth(true);
    playerList.getStyleClass().add("player-scroll-pane");
    getChildren().add(playerList);
  }

  /**
   * Populates this {@link Card}'s {@link ScrollPane} with players.
   *
   * @param players The list of players to display in the player list.
   */
  public void populatePlayerList(List<Player> players) {
    VBox playerContainer = new VBox(5);

    players.forEach(player -> {
      HBox playerRow = new HBox(8);
      playerRow.setAlignment(Pos.CENTER);
      playerRow.getStyleClass().add("player-row");
      playerRow.getStyleClass().add("player-row-inGame");

      Label playerName = new Label(player.getName());
      playerName.getStyleClass().add("p");

      Region spacer = new Region();
      HBox.setHgrow(spacer, Priority.ALWAYS);

      String playerPosition = String.valueOf(player.getCurrentTile().getTileId());
      Label playerPositionLabel = new Label(playerPosition);
      playerPositionLabel.getStyleClass().add("h2");

      StackPane playerIconStack = new StackPane();

      String iconPath = player.getPiece().getIconFilePath();
      ImageView playerIcon = new ImageView(new Image(getClass().getResourceAsStream(iconPath)));
      playerIcon.setFitHeight(20);
      playerIcon.setFitWidth(20);

      Circle colorCircle = new Circle(18);
      colorCircle.setFill(player.getColor());

      //Positions nodes
      playerIconStack.getChildren().addAll(colorCircle, playerIcon);

      playerRow.getChildren().addAll(playerIconStack, playerName, spacer, playerPositionLabel);
      playerContainer.getChildren().addAll(playerRow);
    });

    playerList.setContent(playerContainer);
  }

}
