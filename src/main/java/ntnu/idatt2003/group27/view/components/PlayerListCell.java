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
   * Logger instance for the {@code } class.
   * Used for logging informational messages and errors related to class operations.
   */
  private static final Logger logger = Logger.getLogger(PlayerListCell.class.getName());
  private final CustomButton removePlayerButton;

  public PlayerListCell(Player player, double spacing) {
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

    StackPane playerIconStack = new StackPane();

    //Initializes player icon
    ImageView playerIcon = new ImageView();
    if (player.getPiece() != null) {
      System.out.println(player.getPiece().getIconFilePath());
      try {
        playerIcon.setImage(
            new Image(getClass().getResourceAsStream(player.getPiece().getIconFilePath())));
      }
      catch (Exception e) {
        System.out.println("Icon could not be loaded.");
        System.out.println(e);
      }
    }
    playerIcon.setFitHeight(20);
    playerIcon.setFitWidth(20);

    Circle colorCircle = new Circle(18);
    colorCircle.setFill(player.getColor());

    //Positions nodes
    playerIconStack.getChildren().addAll(colorCircle, playerIcon);
    getChildren().addAll(playerIconStack, playerName, spacer, removePlayerButton);
  }

  public void setRemovePlayerButtonHandler(EventHandler<ActionEvent> action) {
    removePlayerButton.setOnAction(action);
  }

}
