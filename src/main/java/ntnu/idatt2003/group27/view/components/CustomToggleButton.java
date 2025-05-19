package ntnu.idatt2003.group27.view.components;

import java.util.logging.Logger;
import javafx.scene.Node;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.ImageView;

/**
 * A customizable togglebutton component extending the JavaFX ToggleButton class.
 */
public class CustomToggleButton extends ToggleButton {
  /**
   * Logger instance for the {@code } class.
   * Used for logging informational messages and errors related to class operations.
   */
  private static final Logger logger = Logger.getLogger(ToggleButton.class.getName());
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
