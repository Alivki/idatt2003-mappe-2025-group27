package ntnu.idatt2003.group27.view.components;

import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;

public class CustomButton extends Button {
  public enum ButtonType {
    PRIMARY,
    PRIMARY_ICON,
    CONFIRM,
    DESTRUCTIVE,
    GHOST,
    ICON;
  }

  public CustomButton(String text, ButtonType type, EventHandler<ActionEvent> action) {
    super(text);
    initialize(type, null, action);
  }

  public CustomButton(String text, ButtonType type, Node icon, EventHandler<ActionEvent> action) {
    super(text);
    initialize(type, icon, action);
  }

  public CustomButton(Node icon, ButtonType type, EventHandler<ActionEvent> action) {
    super();
    initialize(type, icon, action);
  }

  private void initialize(ButtonType type, Node icon, EventHandler<ActionEvent> action) {
    getStyleClass().add("button");

    switch (type) {
      case PRIMARY:
        getStyleClass().add("button");
        break;
      case PRIMARY_ICON:
        getStyleClass().add("icon-primary-button");
        if (icon != null) {
          if (icon instanceof ImageView) {
            ((ImageView) icon).setFitWidth(14);
            ((ImageView) icon).setFitHeight(14);
          }
          setGraphic(icon);
        }
        break;
      case CONFIRM:
        getStyleClass().add("confirm-button");
        break;
      case DESTRUCTIVE:
        getStyleClass().add("destructive-button");
        break;
      case GHOST:
        getStyleClass().add("ghost-button");
        break;
      case ICON:
        getStyleClass().add("icon-button");
        if (icon != null) {
          if (icon instanceof ImageView) {
            ((ImageView) icon).setFitWidth(14);
            ((ImageView) icon).setFitHeight(14);
          }
          setGraphic(icon);
        }
        setMinSize(32, 32);
        setPrefSize(32, 32);
        setMaxSize(32, 32);
        break;
    }

    if (action != null) {
      setOnAction(action);
    }
  }

  public void setIcon(Node icon) {
    setGraphic(icon);
  }
}
