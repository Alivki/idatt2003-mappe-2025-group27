package ntnu.idatt2003.group27.view.components;

import java.util.logging.Logger;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;

/**
 * A customizable button component extending the JavaFX Button class. Supports different styles and
 * optional icon. The button can be styled using different variants and configured with the option
 * for an action handler. (Not recommended, use the controller instead). Styles are applied using
 * external CSS stylesheets.
 *
 * @author Iver Lindholm
 * @version 1.2
 * @since 2.0
 */
public class CustomButton extends Button {
  /**
   * Logger instance for the {@code } class.
   * Used for logging informational messages and errors related to class operations.
   */
  private static final Logger logger = Logger.getLogger(CustomButton.class.getName());
  /** Enum defining the available button style variants */
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

  /**
   * Constructs a {@link CustomButton} with the specified text, variant, and action handler. Used
   * for buttons with text only.
   *
   * @param text The text to display on the button.
   * @param type The {@link ButtonVariant} defining the buttons style.
   * @param action The {@link EventHandler} to handle button click events.
   */
  public CustomButton(String text, ButtonVariant type, EventHandler<ActionEvent> action) {
    super(text);
    initialize(type, null, action);
  }

  /**
   * Constructs a {@link CustomButton} with the specified text, variant, icon, and action handler.
   * Used for buttons with text and icons.
   *
   * @param text The text to display on the button.
   * @param type The {@link ButtonVariant} defining the buttons style.
   * @param icon The {@link Node} to display as an icon on the button.
   * @param action The {@link EventHandler} to handle button click events.
   */
  public CustomButton(String text, ButtonVariant type, Node icon, EventHandler<ActionEvent> action) {
    super(text);
    initialize(type, icon, action);
  }

  /**
   * Constructs a {@link CustomButton} with the specified icon, variant, and action handler. Used
   * for buttons with a single icons only.
   *
   * @param icon The {@link Node} to display as an icon on the button.
   * @param type The {@link ButtonVariant} defining the buttons style.
   * @param action The {@link EventHandler} to handle button click events.
   */
  public CustomButton(Node icon, ButtonVariant type, EventHandler<ActionEvent> action) {
    super();
    initialize(type, icon, action);
  }

  /**
   * Initializes the button with the specified variant, icon, and action handler. Applies the
   * appropriate CSS styles and configures the button's size and icon properties based on the
   * variants.
   *
   * @param variant The {@link ButtonVariant} defining the buttons style.
   * @param icon The {@link Node} to display as an icon on the button.
   * @param action The {@link EventHandler} to handle button click events.
   */
  private void initialize(ButtonVariant variant, Node icon, EventHandler<ActionEvent> action) {
    getStyleClass().add("button");

    if (variant != ButtonVariant.ICON) {
      setMaxWidth(Double.MAX_VALUE);
    }

    int iconButtonSize = 32;
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

  /**
   * Sets the icon for the button.
   *
   * @param icon The {@link Node} to yse as the button's icon.
   */
  public void setIcon(Node icon) {
    setGraphic(icon);
  }
}
