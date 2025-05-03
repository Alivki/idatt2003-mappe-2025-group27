package ntnu.idatt2003.group27.view.components;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import ntnu.idatt2003.group27.models.Player;

/**
 * A class representing an HBox displaying player information.
 */
public class PlayerListCell extends HBox {
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

    //Positions nodes
    getChildren().addAll(playerIcon, playerName, spacer, removePlayerButton);
  }

  public void setRemovePlayerButtonHandler(EventHandler<ActionEvent> action) {
    removePlayerButton.setOnAction(action);
  }

}
