package ntnu.idatt2003.group27.view.components;

import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;

public class CustomButton extends Button {
  public enum ButtonVariant {
    PRIMARY,
    SECONDARY,
    PRIMARY_ICON,
    CONFIRM,
    DESTRUCTIVE,
    GHOST,
    GHOST_ICON,
    ICON;
  }

  public CustomButton(String text, ButtonVariant type, EventHandler<ActionEvent> action) {
    super(text);
    initialize(type, null, action);
  }

  public CustomButton(String text, ButtonVariant type, Node icon, EventHandler<ActionEvent> action) {
    super(text);
    initialize(type, icon, action);
  }

  public CustomButton(Node icon, ButtonVariant type, EventHandler<ActionEvent> action) {
    super();
    initialize(type, icon, action);
  }

  private void initialize(ButtonVariant variant, Node icon, EventHandler<ActionEvent> action) {
    getStyleClass().add("button");

    if (variant != ButtonVariant.ICON) {
      setMaxWidth(Double.MAX_VALUE);
    }

    int iconButtonSize = 28;
    switch (variant) {
      case PRIMARY:
        getStyleClass().add("primary-button");
        break;
      case SECONDARY:
        break;
      case PRIMARY_ICON:
        getStyleClass().add("icon-primary-button");
        if (icon != null) {
          if (icon instanceof ImageView) {
            ((ImageView) icon).setFitWidth(12);
            ((ImageView) icon).setFitHeight(12);
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
            ((ImageView) icon).setFitWidth(12);
            ((ImageView) icon).setFitHeight(12);
          }
          setGraphic(icon);
        }
        setMinSize(iconButtonSize, iconButtonSize);
        setPrefSize(iconButtonSize, iconButtonSize);
        setMaxSize(iconButtonSize, iconButtonSize);
        break;
      case GHOST_ICON:
        getStyleClass().add("ghost-icon-button");
        if (icon != null) {
          if (icon instanceof ImageView) {
            ((ImageView) icon).setFitWidth(12);
            ((ImageView) icon).setFitHeight(12);
          }
          setGraphic(icon);
        }
        setMinSize(iconButtonSize, iconButtonSize);
        setPrefSize(iconButtonSize, iconButtonSize);
        setMaxSize(iconButtonSize, iconButtonSize);
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
