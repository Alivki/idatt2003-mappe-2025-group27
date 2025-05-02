package ntnu.idatt2003.group27.view.components;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javax.swing.Icon;

/**
 * A class representing board buttons to be used in the main menu.
 * These buttons have a title and description explaining the board type, and an icon to show a preview of the board.
 *
 * @author Amadeus Berg
 * @version 1.0
 * @since 2.0
 */
public class MainMenuBoardButton extends Button {

  /**
   * Constructs a {@link MainMenuBoardButton} with the specified size, padding, title, description
   * and a preview image.
   *
   * @param prefSize The preferred size (width and height) for the preview image in pixels.
   * @param insets The {@link Insets} defining the padding around the buttons's content.
   * @param title The title text to display on the button.
   * @param description The description text to display on the button.
   * @param image The {@link Image} to display as a preview of the board.
   */
  public MainMenuBoardButton(int prefSize, int minSize, int maxSize, int imageSize, Insets insets, String title, String description, Image image) {
    super();

    //Creates button content to display button information correctly.
    VBox buttonContent = new VBox(5);
    buttonContent.setPadding(insets);

    setPrefSize(prefSize, prefSize + 60);
    setMinSize(minSize, minSize + 60);
    setMaxSize(maxSize, maxSize + 60);

    //Initializes labels
    Label titleLabel = new Label(title);
    titleLabel.getStyleClass().add("h1");
    Label descriptionLabel = new Label(description);
    descriptionLabel.setWrapText(true);

    //Initializes image
    ImageView imageView = new ImageView(image);
    imageView.setPreserveRatio(true);
    imageView.setFitWidth(imageSize);

    //Applies changes to button.
    buttonContent.getChildren().addAll(titleLabel, descriptionLabel, imageView);
    super.setGraphic(buttonContent);
  }
}
