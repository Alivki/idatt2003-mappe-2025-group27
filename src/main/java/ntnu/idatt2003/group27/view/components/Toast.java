package ntnu.idatt2003.group27.view.components;

import javafx.animation.PauseTransition;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.stage.StageStyle;

public class Toast {
  public enum ToastType {
    DEFAULT,
    ERROR,
    SUCCESS,
  }

  private final Stage toastStage;
  private final String title;
  private final String message;
  private final StackPane owner;
  private static final double MIN_TOAST_WIDTH = 250;
  private static final double TOAST_HEIGHT = 101;
  private static final double EDGE_OFFSET = 35;
  private static final double CHAR_WIDTH = 5.5;
  private double estimatedWidth = 0;

  public Toast(StackPane owner, ToastType type, String title, String message) {
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

  private void setupUI(ToastType type) {
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

    estimatedWidth = Math.max(title.length() * CHAR_WIDTH, message.length() * CHAR_WIDTH);
    estimatedWidth = Math.max(MIN_TOAST_WIDTH, Math.min(estimatedWidth, 600));

    Scene scene = new Scene(layout, estimatedWidth, TOAST_HEIGHT);
    scene.setFill(null);
    scene.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());
    toastStage.setScene(scene);
  }

  public void show() {
    double ownerWidth = owner.getScene().getWidth();
    double ownerHeight = owner.getScene().getHeight();
    double ownerX = owner.getScene().getWindow().getX();
    double ownerY = owner.getScene().getWindow().getY();

    double rightEdgeX = ownerX + ownerWidth - estimatedWidth - EDGE_OFFSET;
    double bottomEdgeY = ownerY + ownerHeight - TOAST_HEIGHT - EDGE_OFFSET;

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