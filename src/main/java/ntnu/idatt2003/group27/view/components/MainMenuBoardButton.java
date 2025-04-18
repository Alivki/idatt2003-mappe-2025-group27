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
 */
public class MainMenuBoardButton extends Button {
  public MainMenuBoardButton(int prefSize, Insets insets, String title, String description, Image image) {
    super();

    //Creates button content to display button information correctly.
    VBox buttonContent = new VBox(10);
    buttonContent.setPadding(insets);

    //Initializes labels
    Label titleLabel = new Label(title);
    titleLabel.getStyleClass().add("h1");
    Label descriptionLabel = new Label(description);

    //Initializes image
    ImageView imageView = new ImageView(image);
    imageView.setFitHeight(prefSize);
    imageView.setFitWidth(prefSize);

    //Applies changes to button.
    buttonContent.getChildren().addAll(titleLabel, descriptionLabel, imageView);
    super.setGraphic(buttonContent);
  }
}
