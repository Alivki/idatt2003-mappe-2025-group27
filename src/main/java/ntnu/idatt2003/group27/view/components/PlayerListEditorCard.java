package ntnu.idatt2003.group27.view.components;

import java.util.ArrayList;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import ntnu.idatt2003.group27.models.Player;
import ntnu.idatt2003.group27.models.actionEvents.PlayerActionEvent;

/**
 * A card representing an editable player list.
 *
 * @author Amadeus Berg
 */
public class PlayerListEditorCard extends Card {

  private final ScrollPane playerList;

  private EventHandler<PlayerActionEvent> playerActionEvent;

  /** An arrayList storing all player used by this list*/
  private ArrayList<PlayerHBox> playerHBoxes = new ArrayList<>();

  /**
   * Constructs a {@link Card} with the specified title, description and height.
   *
   * @param title       The title text to display on the card.
   * @param description The description text to display on the card, or null if no
   *                    description is needed.
   * @param height      The minimum height of the card in pixels.
   */
  public PlayerListEditorCard(String title, String description, double height) {
    super(title, description, height);

    //Initializes playerList scrollpane
    playerList = new ScrollPane();
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
   * @param players
   */
  public void populatePlayerList(List<Player> players) {
    VBox playerContainer = new VBox(0);

    players.forEach(player -> {
      PlayerHBox playerHBox = new PlayerHBox(player, 8);
      playerHBoxes.add(playerHBox);
      playerContainer.getChildren().addAll(playerHBox);
    });

    if (players.size() == 0) {
      HBox playerRow = new HBox(8);
      playerRow.getStyleClass().add("player-row");
      playerRow.setAlignment(Pos.CENTER);

      Label infoLabel = new Label("Ingen spillere funnet");
      infoLabel.getStyleClass().add("p");

      //Positions nodes
      playerRow.getChildren().addAll(infoLabel);
      playerContainer.getChildren().add(playerRow);
    }

    playerList.setContent(playerContainer);
  }

  public void setRemovePlayerButtonHandler(EventHandler<PlayerActionEvent> action) {
    playerHBoxes.forEach(playerHBox -> {
      playerHBox.setRemovePlayerButtonHandler(action);
    });
  }

}
