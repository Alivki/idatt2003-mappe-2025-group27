package ntnu.idatt2003.group27.view.components;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import ntnu.idatt2003.group27.models.Player;

/**
 * A card representing an editable player list.
 *
 * @author Amadeus Berg
 */
public class PlayerListCardEditable extends Card {
  /**
   * Logger instance for the {@code } class.
   * Used for logging informational messages and errors related to class operations.
   */
  private static final Logger logger = Logger.getLogger(PlayerListCardEditable.class.getName());

  private final ScrollPane playerList;

  /** An arrayList storing all player used by this list*/
  private ArrayList<PlayerListCell> playerListCells = new ArrayList<>();

  /** A map connecting a player to their corresponding playerHBox */
  private Map<Player, PlayerListCell> playerListCellMap = new HashMap<>();

  /**
   * Constructs a {@link Card} with the specified title, description and height.
   *
   * @param title       The title text to display on the card.
   * @param description The description text to display on the card, or null if no
   *                    description is needed.
   * @param height      The minimum height of the card in pixels.
   */
  public PlayerListCardEditable(String title, String description, double height) {
    super(title, description, height);

    //Initializes playerList scrollpane
    playerList = new ScrollPane();
    playerList.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
    playerList.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
    playerList.setMaxHeight(height);
    VBox.setVgrow(playerList, Priority.ALWAYS);
    playerList.setFitToWidth(true);
    playerList.setFitToHeight(true);
    playerList.getStyleClass().add("player-scroll-pane");
    getChildren().add(playerList);
  }

  /**
   * Populates this {@link Card}'s {@link ScrollPane} with players.
   * @param players
   */
  public void populatePlayerList(List<Player> players) {
    VBox playerContainer = new VBox(5);

    playerListCellMap.clear();

    players.forEach(player -> {
      PlayerListCell playerListCell = new PlayerListCell(player, 8);
      playerListCells.add(playerListCell);
      playerContainer.getChildren().addAll(playerListCell);
      playerListCellMap.put(player, playerListCell);
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

  public void setRemovePlayerButtonHandler(Player player, EventHandler<ActionEvent> action) {
    playerListCellMap.get(player).setRemovePlayerButtonHandler(action);
  }

}
