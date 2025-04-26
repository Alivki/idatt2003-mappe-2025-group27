package ntnu.idatt2003.group27.view.components;

import javafx.animation.PauseTransition;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.stage.StageStyle;

/**
 * A JavaFX component representing a toast notification. that appears temporarily on the screen. The
 * toast displays a title and message, supports different variants (default, error, success), and
 * automatically closes after a short delay. It is styled using an external CSS stylesheet.
 *
 * @author Iver Lindholm
 * @version 1.0
 * @since 2.0
 */
public class Toast {
  /** Enum defining the available toast notification variants. */
  public enum ToastVariant {
    DEFAULT,
    ERROR,
    SUCCESS,
  }

  /** The stage used to display the toast notification */
  private final Stage toastStage;
  /** The title of the toast notification */
  private final String title;
  /** The message of the toast notification */
  private final String message;
  /** The parent StackPane that owns the toast */
  private final StackPane owner;
  /** The minimum width of the toast notification */
  private static final double MIN_TOAST_WIDTH = 250;
  /** The height of the toast notification */
  private static final double TOAST_HEIGHT = 101;
  /** The offset from the screen edge */
  private static final double EDGE_OFFSET = 35;
  /** The estimated width of a character in pixels for width calculation */
  private static final double CHAR_WIDTH = 5.5;
  /** The estimated width of the toast based on content lenght */
  private double estimatedWidth;

  /**
   * Constructs a {@link Toast} notification with the specified owner, variant, title, and message.
   * The toast is initialized as a non-model, undecorated, and transparent stage.
   *
   * @param owner The {@link StackPane} that owns the toast notification.
   * @param type The {@link ToastVariant} defining the toast's style.
   * @param title The title text to display in the toast.
   * @param message The message text to display in the toast.
   */
  public Toast(StackPane owner, ToastVariant type, String title, String message) {
    this.owner = owner;
    this.title = title;
    this.message = message;
    this.toastStage = new Stage();

    toastStage.initOwner(owner.getScene().getWindow());
    toastStage.initModality(Modality.NONE);
    toastStage.initStyle(StageStyle.UNDECORATED);
    toastStage.initStyle(StageStyle.TRANSPARENT);
    toastStage.setAlwaysOnTop(true);
    toastStage.setResizable(false);

    setupUI(type);
  }

  /**
   * Sets up the user interface of the toast notification, including the layout, title, message, and
   * styling based on the specified variant. The width is estimated based on the content length.
   *
   * @param type The {@link ToastVariant} defining the toast's style.
   */
  private void setupUI(ToastVariant type) {
    VBox layout = new VBox(0);
    layout.getStyleClass().add("toast");
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

    header.getChildren().addAll(titleLabel);
    main.getChildren().addAll(messageLabel);
    layout.getChildren().addAll(header, main);

    switch (type) {
      case ERROR:
        titleLabel.setStyle("-fx-text-fill: #721c24;");
        messageLabel.setStyle("-fx-text-fill: #431116;");
        layout.getStyleClass().add("error-toast");
        break;
      case SUCCESS:
        titleLabel.setStyle("-fx-text-fill: #0f3e1a;");
        messageLabel.setStyle("-fx-text-fill: #0d3516;");
        layout.getStyleClass().add("success-toast");
        break;
    }

    estimatedWidth = Math.max(title.length() * CHAR_WIDTH, message.length() * CHAR_WIDTH) + 40;
    estimatedWidth = Math.max(MIN_TOAST_WIDTH, Math.min(estimatedWidth, 600));

    Scene scene = new Scene(layout, estimatedWidth, TOAST_HEIGHT);
    scene.setFill(null);
    scene.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());
    toastStage.setScene(scene);
  }

  /**
   * Displays the toast notification in the bottom-right corner of the owner window. The toast
   * remains visible for 3.5 seconds before automatically closing.
   */
  public void show() {
    double ownerWidth = owner.getScene().getWidth();
    double ownerHeight = owner.getScene().getHeight();
    double ownerX = owner.getScene().getWindow().getX();
    double ownerY = owner.getScene().getWindow().getY();

    double rightEdgeX = ownerX + ownerWidth - estimatedWidth - EDGE_OFFSET;
    double bottomEdgeY = ownerY + ownerHeight - TOAST_HEIGHT - EDGE_OFFSET + 35;

    toastStage.setX(rightEdgeX);
    toastStage.setY(bottomEdgeY);

    toastStage.show();
    toastStage.toFront();
    toastStage.requestFocus();

    PauseTransition delay = new PauseTransition(javafx.util.Duration.millis(3500));
    delay.setOnFinished(event -> toastStage.close());
    delay.play();
  }
}