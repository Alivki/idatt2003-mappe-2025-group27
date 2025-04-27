package ntnu.idatt2003.group27.view.components;

import java.util.List;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import ntnu.idatt2003.group27.models.Player;

/**
 * A card representing a player list.
 * Is useful for displaying player lists uniformly across different views.
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
    playerList = new ScrollPane();
    getChildren().add(playerList);
  }

  /**
   * Populates this {@link Card}'s {@link ScrollPane} with players.
   * @param players
   */
  public void populatePlayerList(List<Player> players) {
    VBox playerContainer = new VBox(5);

    players.forEach(player -> {
      HBox playerRow = new HBox(8);
      playerRow.getStyleClass().add("player-row");

      Label playerName = new Label(player.getName());
      playerName.getStyleClass().add("p");

      Region spacer = new Region();
      HBox.setHgrow(spacer, Priority.ALWAYS);

      String playerPosition = String.valueOf(player.getCurrentTile().getTileId());
      Label playerPositionLabel = new Label(playerPosition);
      playerPositionLabel.getStyleClass().add("p");

      // change to actual player icons once that is implemented
      ImageView playerIcon = new ImageView(new Image(getClass().getResourceAsStream("/icons/home.png")));
      playerIcon.setFitHeight(20);
      playerIcon.setFitWidth(20);

      playerRow.getChildren().addAll(playerIcon, playerName, spacer, playerPositionLabel);
      playerContainer.getChildren().addAll(playerRow);
    });

    playerList.setContent(playerContainer);
  }

}
