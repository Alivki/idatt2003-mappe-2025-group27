package ntnu.idatt2003.group27.view.components;

import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;
import ntnu.idatt2003.group27.models.Player;

public class PlayerButtonListCell extends ListCell<Player> {
  private final HBox mainContainer;
  private final Label playerLabel;
  private final CustomButton removePlayerButton;

  public PlayerButtonListCell() {
    mainContainer = new HBox();
    playerLabel = new Label("New player");

    removePlayerButton = new CustomButton("X", CustomButton.ButtonVariant.DESTRUCTIVE, null);

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
      setGraphic(mainContainer);
    }
  }
}
