package ntnu.idatt2003.group27.view.components;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import ntnu.idatt2003.group27.controllers.MainController;
import ntnu.idatt2003.group27.models.Player;

/**
 * A class to represent player list cells.
 * Gives access to appropriate player information and player-handling functionality.
 *
 * @author Amadeus Berg
 * @version 1.0
 * @since 2.0
 */
public class PlayerButtonListCell extends ListCell<Player> {
  /**
   * The main container for the cell's content, organizing the icon, player name, and remove button
   */
  private final HBox mainContainer;
  /** The label displaying the player's name */
  private final Label playerLabel;
  /** The button used to remove the player from the list */
  private final CustomButton removePlayerButton;

  /**
   * Constructs a {@link PlayerButtonListCell}, initializing the layout with a player icon, name,
   * label, and remove button. The cell is styled and configured to handle player removal view the
   * {@link MainController}.
   */
  public PlayerButtonListCell() {
    //Initializes main content
    AnchorPane mainAnchorPane = new AnchorPane();
    mainContainer = new HBox(10);
    HBox leftContainer = new HBox(10);
    HBox rightContainer = new HBox(10);
    HBox middleContainer = new HBox(10);
    leftContainer.setAlignment(Pos.CENTER);
    middleContainer.setAlignment(Pos.CENTER_LEFT);
    rightContainer.setAlignment(Pos.CENTER);
    leftContainer.setPadding(new Insets(0, 0, 0, 5));
    HBox.setHgrow(middleContainer, Priority.ALWAYS);

    //Initializes player label
    playerLabel = new Label("New player");

    //Initializes button to remove player from list
    removePlayerButton = new CustomButton("X", CustomButton.ButtonVariant.SECONDARY, null);
    removePlayerButton.setOnAction(e -> {
      //mainController.removePlayer(getItem());
    });

    //Initialize player image
    ImageView playerIcon = new ImageView(new Image(getClass().getResourceAsStream("/icons/home.png")));
    playerIcon.setFitHeight(20);
    playerIcon.setFitWidth(20);

    mainContainer.getChildren().addAll(leftContainer, middleContainer, rightContainer);
    mainAnchorPane.getChildren().addAll(mainContainer);
    leftContainer.getChildren().addAll(playerIcon);
    middleContainer.getChildren().addAll(playerLabel);
    rightContainer.getChildren().addAll(removePlayerButton);
  }

  /**
   * Updates the cell's content based on the provided {@link Player} item. Displays the player's
   * name and the cell's layout if the item is non-null; otherwise, clears the cell.
   *
   * @param player The {@link Player} object to display in the cell, or null if the cell is empty.
   * @param empty A boolean indicating weather the cell is empty.
   */
  @Override
  protected void updateItem(Player player, boolean empty) {
    super.updateItem(player, empty);
    if (empty || player == null) {
      setText(null);
      setGraphic(null);
    }
    else {
      playerLabel.setText(player.getName());
      setGraphic(mainContainer);
      setPadding(new Insets(5, 5, 5, 5));
    }
  }
}
