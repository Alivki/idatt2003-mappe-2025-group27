package ntnu.idatt2003.group27.view.components;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import ntnu.idatt2003.group27.controllers.MainController;
import ntnu.idatt2003.group27.models.Player;

/**
 * A class to represent player list cells.
 * Gives access to appropraite player information and player-handling functionality.
 */
public class PlayerButtonListCell extends ListCell<Player> {
  private final HBox mainContainer;
  private final Label playerLabel;
  private final CustomButton removePlayerButton;

  public PlayerButtonListCell() {
    //Initializes main content
    AnchorPane mainAnchorPane = new AnchorPane();
    mainContainer = new HBox(10);
    HBox leftContainer = new HBox(10);
    HBox rightContainer = new HBox(10);
    HBox middleContainer = new HBox(10);
    leftContainer.setAlignment(Pos.CENTER);
    leftContainer.setPadding(new Insets(0, 0, 0, 5));
    HBox.setHgrow(middleContainer, Priority.ALWAYS);
    //mainContainer.setAlignment(Pos.CENTER_LEFT);

    //Initializes player label
    playerLabel = new Label("New player");

    //Initializes button to remove player from list
    removePlayerButton = new CustomButton("X", CustomButton.ButtonVariant.SECONDARY, null);
    removePlayerButton.setOnAction(e -> {
      MainController.getInstance().removePlayer(getItem());
    });

    mainContainer.getChildren().addAll(leftContainer, middleContainer, rightContainer);
    mainAnchorPane.getChildren().addAll(mainContainer);
    leftContainer.getChildren().addAll(playerLabel);
    rightContainer.getChildren().addAll(removePlayerButton);
  }

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
