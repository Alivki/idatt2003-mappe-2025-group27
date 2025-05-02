package ntnu.idatt2003.group27.view.components;

import javafx.scene.Node;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.ImageView;

/**
 * A customizable togglebutton component extending the JavaFX ToggleButton class.
 */
public class CustomToggleButton extends ToggleButton {
  public CustomToggleButton(ImageView icon, double size) {
    super();
    getStyleClass().add("button");
    getStyleClass().add("icon-button");
    icon.setFitWidth(size * .5f);
    icon.setFitHeight(size * .5f);
    setGraphic(icon);

    setMinSize(size, size);
    setPrefSize(size, size);
    setMaxSize(size, size);
  }
}
