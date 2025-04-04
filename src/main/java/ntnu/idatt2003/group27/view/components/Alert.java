package ntnu.idatt2003.group27.view.components;

import java.util.function.Consumer;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
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
  private final Consumer<Boolean> onResponse;

  public Alert(String title, String message, String confirmationText, String denyText, Consumer<Boolean> onResponse) {
    this.title = title;
    this.message = message;
    this.confirmationText = confirmationText;
    this.denyText = denyText;
    this.onResponse = onResponse;
    this.alertStage = new Stage();

    alertStage.initModality(Modality.APPLICATION_MODAL);
    alertStage.initStyle(StageStyle.UNDECORATED);
    alertStage.initStyle(StageStyle.TRANSPARENT);
    alertStage.setResizable(false);

    setupUI();
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
    titleLabel.getStyleClass().add("alert-title-text");

    Label messageLabel = new Label(message);
    messageLabel.getStyleClass().add("alert-text");
    messageLabel.setWrapText(true);

    HBox buttonBox = new HBox(10);
    buttonBox.setAlignment(Pos.BOTTOM_RIGHT);
    VBox.setVgrow(buttonBox, Priority.ALWAYS);

    CustomButton confirmationButton = new CustomButton(confirmationText, CustomButton.ButtonType.CONFIRM, e -> {
      onResponse.accept(true);
      alertStage.close();
    });

    CustomButton denyButton = new CustomButton(denyText, CustomButton.ButtonType.PRIMARY, e -> {
      onResponse.accept(false);
      alertStage.close();
    });

    header.getChildren().addAll(titleLabel);
    main.getChildren().addAll(messageLabel, buttonBox);
    buttonBox.getChildren().addAll(denyButton, confirmationButton);
    layout.getChildren().addAll(header, main);

    Scene scene = new Scene(layout, 350, 150);
    scene.setFill(null);
    scene.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());
    alertStage.setScene(scene);
  }

  public void show() {
    alertStage.showAndWait();
  }
}