package ntnu.idatt2003.group27.view.components;

import java.util.logging.Logger;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

/**
 * A JavaFX component representing a card with a title and an optional description, the card is
 * styled using an external CSS stylesheet and supports text wrapping the description.
 *
 * @author Iver Lindholm
 * @version 1.0
 * @since 2.0
 */
public class Card extends VBox {
  /**
   * Logger instance for the {@code Card} class.
   * Used for logging informational messages and errors related to class operations.
   */
  private static final Logger logger = Logger.getLogger(Card.class.getName());
  /**
   * Constructs a {@link Card} with the specified title, description and height.
   *
   * @param title The title text to display on the card.
   * @param description The description text to display on the card, or null if no
   *                    description is needed.
   * @param height The minimum height of the card in pixels.
   */
  public Card(String title, String description, double height) {
    initialize(title, description, height);
  }

  /**
   * Initializes the card with a title, optional description and specified minimum height.
   *
   * @param title The title text to display on the card.
   * @param description The description text to display on the card, or null if no
   *                    description is needed.
   * @param height The minimum height of the card in pixels.
   */
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
    setMaxWidth(230);
    getStyleClass().add("card");
  }
}
