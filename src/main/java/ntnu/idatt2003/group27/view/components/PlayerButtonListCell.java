package ntnu.idatt2003.group27.view.components;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import ntnu.idatt2003.group27.controllers.MainController;
import ntnu.idatt2003.group27.models.Player;

public class PlayerButtonListCell extends ListCell<Player> {
  private final HBox mainContainer;
  private final Label playerLabel;
  private final CustomButton removePlayerButton;

  public PlayerButtonListCell() {
    //Initializes main content
    AnchorPane mainAnchorPane = new AnchorPane();
    mainContainer = new HBox(10);
    mainContainer.setAlignment(Pos.CENTER_LEFT);

    //Initializes player label
    playerLabel = new Label("New player");

    //Initializes button to remove player from list
    removePlayerButton = new CustomButton("X", CustomButton.ButtonVariant.DESTRUCTIVE, null);
    removePlayerButton.setOnAction(e -> {
      MainController.instance.removePlayer(getItem());
    });

    mainAnchorPane.getChildren().addAll(mainContainer);
    mainContainer.getChildren().addAll(playerLabel, removePlayerButton);
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
    }
  }
}
