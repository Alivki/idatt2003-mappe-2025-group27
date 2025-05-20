package ntnu.idatt2003.group27.view.components;

import java.util.function.Consumer;

import java.util.logging.Logger;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.stage.StageStyle;

/**
 * A class representing a customizable alert dialog. The alert displays a title, message, and
 * two buttons (confirmation and deny) with user-defined text. It includes and overlay to dim the
 * background and supports a callback to handle user responses.
 *
 * @author Iver Lindholm
 * @version 1.2
 * @since 2.0
 */
public class Alert {
  /**
   * Logger instance for the {@code Alert} class.
   * Used for logging informational messages and errors related to class operations.
   */
  private static final Logger logger = Logger.getLogger(Alert.class.getName());
  /** The stage to display the alert dialog. */
  private final Stage alertStage;
  /** The title of the alert dialog. */
  private final String title;
  /** The message to display in the alert dialog. */
  private final String message;
  /** The text for the confirmation button. */
  private final String confirmationText;
  /** The text for the deny button. */
  private final String denyText;
  /** The owner of the alert dialog, used for positioning and overlay. */
  private final StackPane owner;
  /** The overlay rectangle to dim the background. */
  private Rectangle overlay;
  /** The callback to handle user responses. */
  private final Consumer<Boolean> onResponse;
  /** The width of the alert dialog. */
  private final double ALERT_WIDTH = 350;
  /** The height of the alert dialog. */
  private final double ALERT_HEIGHT = 150;

  /**
   * Constructs an {@link Alert} dialog with the specified parameters. The alert is initialized as a
   * modal, undecorated, and transparent stage that appears over the owner {@link StackPane}.
   *
   * @param owner The {@link StackPane} that owns the alert and hosts the overlay.
   * @param title The title of the alert dialog.
   * @param message The message to display in the alert dialog.
   * @param confirmationText The text for the confirmation button.
   * @param denyText The text for the deny button.
   * @param onResponse The {@link Consumer} callback to handle the user's response
   *                   (true for confirmation, false for dney).
   */
  public Alert(StackPane owner, String title, String message, String confirmationText, String denyText, Consumer<Boolean> onResponse) {
    this.owner = owner;
    this.title = title;
    this.message = message;
    this.confirmationText = confirmationText;
    this.denyText = denyText;
    this.onResponse = onResponse;
    this.alertStage = new Stage();

    alertStage.initOwner(owner.getScene().getWindow());
    alertStage.initModality(Modality.APPLICATION_MODAL);
    alertStage.initStyle(StageStyle.UNDECORATED);
    alertStage.initStyle(StageStyle.TRANSPARENT);
    alertStage.setAlwaysOnTop(true);
    alertStage.setResizable(false);

    setupUI();
    setupOverlay();
  }

  /**
   * Sets up the user interface of the alert dialog, including the layout, title, message, and
   * buttons.
   * The UI is styled using external CSS stylesheets.
   */
  private void setupUI() {
    VBox layout = new VBox(0);
    layout.getStyleClass().add("alert");
    layout.setFillWidth(true);

    StackPane header = new StackPane();
    header.setAlignment(Pos.CENTER_LEFT);

    VBox main = new VBox(0);
    main.setAlignment(Pos.CENTER_LEFT);
    VBox.setVgrow(main, Priority.ALWAYS);

    Label titleLabel = new Label(title);
    titleLabel.getStyleClass().add("h3");

    Label messageLabel = new Label(message);
    messageLabel.getStyleClass().add("p");
    messageLabel.setWrapText(true);

    HBox buttonBox = new HBox(10);
    buttonBox.setAlignment(Pos.BOTTOM_RIGHT);
    VBox.setVgrow(buttonBox, Priority.ALWAYS);

    CustomButton confirmationButton = new CustomButton(confirmationText, CustomButton.ButtonVariant.CONFIRM, e -> {
      onResponse.accept(true);
      removeOverlay();
      alertStage.close();
    });

    CustomButton denyButton = new CustomButton(denyText, CustomButton.ButtonVariant.SECONDARY, e -> {
      onResponse.accept(false);
      removeOverlay();
      alertStage.close();
    });

    header.getChildren().addAll(titleLabel);
    main.getChildren().addAll(messageLabel, buttonBox);
    buttonBox.getChildren().addAll(denyButton, confirmationButton);
    layout.getChildren().addAll(header, main);

    Scene scene = new Scene(layout, ALERT_WIDTH, ALERT_HEIGHT);
    scene.setFill(null);
    scene.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());
    alertStage.setScene(scene);
  }

  /**
   * Configures the overlay rectangle that dims the background of the owner StackPane. The overlay
   * size is bound to the owner's dimensions.
   */
  private void setupOverlay() {
    overlay = new Rectangle(owner.getWidth(), owner.getHeight(), Color.rgb(0, 0, 0, 0.5));
    overlay.setMouseTransparent(true);
    overlay.widthProperty().bind(owner.widthProperty());
    overlay.heightProperty().bind(owner.heightProperty());
  }

  /**
   * Adds the overlay to the owner StackPane if it is not already present.
   */
  private void addOverlay() {
    if (!owner.getChildren().contains(overlay)) {
      owner.getChildren().add(overlay);
    }
  }

  /**
   * Removes the overlay from the owner StackPane.
   */
  private void removeOverlay() {
    owner.getChildren().remove(overlay);
  }

  /**
   * Displays the alert dialog, centering it over the owner window and adding the overlay to dim the
   * background. The dialog remains open until the user clicks a button.
   */
  public void show() {
    addOverlay();

    double ownerWidth = owner.getScene().getWidth();
    double ownerHeight = owner.getScene().getHeight();
    double ownerX = owner.getScene().getWindow().getX();
    double ownerY = owner.getScene().getWindow().getY();

    double centerX = ownerX + (ownerWidth - ALERT_WIDTH) / 2;
    double centerY = (ownerY + (ownerHeight - ALERT_HEIGHT) / 2);

    alertStage.setX(centerX);
    alertStage.setY(centerY);

    alertStage.showAndWait();
  }
}