package ntnu.idatt2003.group27.view.components;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javax.swing.Icon;

/**
 * A class representing board buttons to be used in the main menu.
 * These buttons have a title and description explaining the board type, and an icon displaying how to board looks.
 */
public class MainMenuBoardButton extends Button {
  public MainMenuBoardButton(int prefSize, String title, String description, Image image) {
    super();
    super.setPrefSize(prefSize, prefSize);
    VBox buttonContent = new VBox(10);
    Label titleLabel = new Label(title);
    Label descriptionLabel = new Label(description);
    ImageView imageView = new ImageView(image);
    buttonContent.getChildren().addAll(titleLabel, descriptionLabel, imageView);
    super.setGraphic(buttonContent);
  }
}
