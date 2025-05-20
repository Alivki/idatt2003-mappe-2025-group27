package ntnu.idatt2003.group27.view.components;

import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;
import ntnu.idatt2003.group27.models.Player;

/**
 * A class representing an HBox displaying player information.
 */
public class PlayerListCell extends HBox {
  /**
   * Logger instance for the {@link PlayerListCell} class.
   * Used for logging informational messages and errors related to class operations.
   */
  private static final Logger logger = Logger.getLogger(PlayerListCell.class.getName());

  /** The button to remove this {@link Player} from the {@link Player} list. */
  private final CustomButton removePlayerButton;

  /**
   * Constructs a player cell to display {@link Player} information in a {@link PlayerListCard}.
   * @param player The {@link Player} to display.
   * @param spacing The horizontal spacing of the elements within the {@link PlayerListCell}.
   */
  public PlayerListCell(Player player, double spacing) {
    logger.fine("Initializing player list cell for player: " + player + ", with spacing: " + spacing);

    //Initializes base HBox
    setSpacing(spacing);
    getStyleClass().add("player-row");
    setAlignment(Pos.CENTER);

    //Initializes player name label
    Label playerName = new Label(player.getName());
    playerName.getStyleClass().add("p");

    //Initializes spacer
    Region spacer = new Region();
    HBox.setHgrow(spacer, Priority.ALWAYS);

    //Initializes remove player button
    ImageView removePlayerIcon = new ImageView(new Image(getClass().getResourceAsStream("/icons/delete.png")));
    this.removePlayerButton = new CustomButton(removePlayerIcon, CustomButton.ButtonVariant.GHOST_ICON, null);

    //Initializes player icon
    StackPane playerIconStack = new StackPane();
    ImageView playerIcon = new ImageView();
    if (player.getPiece() != null) {
      try {
        playerIcon.setImage(
            new Image(getClass().getResourceAsStream(player.getPiece().getIconFilePath())));
      }
      catch (Exception e) {
        logger.severe("Issue loading player icon: Icon could not be loaded from path: " + player.getPiece().getIconFilePath() + "\nError message: "  + e.getMessage());
      }
    }
    playerIcon.setFitHeight(20);
    playerIcon.setFitWidth(20);

    //Initializing color circle
    Circle colorCircle = new Circle(18);
    colorCircle.setFill(player.getColor());

    //Positions nodes
    playerIconStack.getChildren().addAll(colorCircle, playerIcon);
    getChildren().addAll(playerIconStack, playerName, spacer, removePlayerButton);
  }

  /**
   * Sets the action for the remove player button of this {@link PlayerListCell}
   * @param action The action for the button.
   */
  public void setRemovePlayerButtonHandler(EventHandler<ActionEvent> action) {
    logger.fine("Setting remove player button handler.");
    removePlayerButton.setOnAction(action);
  }

}
