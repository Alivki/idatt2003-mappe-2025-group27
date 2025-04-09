package ntnu.idatt2003.group27.view.components;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class Card extends VBox {

  public Card(String title, String description, double height) {
    initialize(title, description, height);
  }

  public void initialize(String title, String description, double height) {
    Label titleLabel = new Label(title);
    titleLabel.getStyleClass().add("h3");
    getChildren().addAll(titleLabel);

    if (description != null) {
      Label descriptionLabel = new Label(description);
      descriptionLabel.getStyleClass().add("p");
      descriptionLabel.setWrapText(true);
      getChildren().addAll(descriptionLabel);
    }

    setSpacing(0);
    setMinSize(168, height);
    setMaxWidth(200);
    getStyleClass().add("card");
  }
}
