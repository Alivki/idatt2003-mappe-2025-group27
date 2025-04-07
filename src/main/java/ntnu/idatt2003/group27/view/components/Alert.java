package ntnu.idatt2003.group27.view.components;

import java.util.function.Consumer;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.stage.StageStyle;

public class Alert {
  private final Stage alertStage;
  private final String title;
  private final String message;
  private final String confirmationText;
  private final String denyText;
  private final StackPane owner;
  private Rectangle overlay;
  private final Consumer<Boolean> onResponse;
  private final double ALERT_WIDTH = 350;
  private final double ALERT_HEIGHT = 150;

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

    CustomButton confirmationButton = new CustomButton(confirmationText, CustomButton.ButtonType.CONFIRM, e -> {
      onResponse.accept(true);
      removeOverlay();
      alertStage.close();
    });

    CustomButton denyButton = new CustomButton(denyText, CustomButton.ButtonType.PRIMARY, e -> {
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

  private void setupOverlay() {
    overlay = new Rectangle(owner.getWidth(), owner.getHeight(), Color.rgb(0, 0, 0, 0.5));
    overlay.setMouseTransparent(true);
    overlay.widthProperty().bind(owner.widthProperty());
    overlay.heightProperty().bind(owner.heightProperty());
  }

  private void addOverlay() {
    if (!owner.getChildren().contains(overlay)) {
      owner.getChildren().add(overlay);
    }
  }

  private void removeOverlay() {
    owner.getChildren().remove(overlay);
  }

  public void show() {
    addOverlay();

    double ownerWidth = owner.getScene().getWidth();
    double ownerHeight = owner.getScene().getHeight();
    double ownerX = owner.getScene().getWindow().getX();
    double ownerY = owner.getScene().getWindow().getY();

    double centerX = ownerX + (ownerWidth - ALERT_WIDTH) / 2;
    double centerY = (ownerY + (ownerHeight - ALERT_HEIGHT) / 2) - 25;

    alertStage.setX(centerX);
    alertStage.setY(centerY);

    alertStage.showAndWait();
  }
}