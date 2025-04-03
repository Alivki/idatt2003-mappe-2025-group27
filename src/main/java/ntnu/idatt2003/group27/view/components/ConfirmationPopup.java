package ntnu.idatt2003.group27.view.components;

import java.util.function.Consumer;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.stage.StageStyle;

public class ConfirmationPopup {
  private final Stage popupStage;
  private final String title;
  private final String message;
  private final String confirmationText;
  private final String denyText;
  private final Consumer<Boolean> onResponse;

  public ConfirmationPopup(String title, String message, String confirmationText, String denyText, Consumer<Boolean> onResponse) {
    this.title = title;
    this.message = message;
    this.confirmationText = confirmationText;
    this.denyText = denyText;
    this.onResponse = onResponse;
    this.popupStage = new Stage();

    popupStage.initModality(Modality.APPLICATION_MODAL);
    popupStage.initStyle(StageStyle.UNDECORATED);
    popupStage.setResizable(false);

    setupUI();
  }

  private void setupUI() {
    VBox layout = new VBox(0);
    layout.getStyleClass().add("popup");
    layout.setFillWidth(true);

    StackPane header = new StackPane();
    header.setAlignment(Pos.CENTER_LEFT);

    VBox main = new VBox(0);
    main.setAlignment(Pos.CENTER_LEFT);
    VBox.setVgrow(main, Priority.ALWAYS);

    Label titleLabel = new Label(title);
    titleLabel.getStyleClass().add("popup-title-text");

    Label messageLabel = new Label(message);
    messageLabel.getStyleClass().add("popup-text");
    messageLabel.setWrapText(true);

    HBox buttonBox = new HBox(10);
    buttonBox.setAlignment(Pos.BOTTOM_RIGHT);
    VBox.setVgrow(buttonBox, Priority.ALWAYS);

    Button confirmationButton = new Button(confirmationText);
    confirmationButton.getStyleClass().add("action-button");
    confirmationButton.setOnAction(e -> {
      onResponse.accept(true);
      popupStage.close();
    });

    Button denyButton = new Button(denyText);
    denyButton.setOnAction(e -> {
      onResponse.accept(false);
      popupStage.close();
    });

    header.getChildren().addAll(titleLabel);
    main.getChildren().addAll(messageLabel, buttonBox);
    buttonBox.getChildren().addAll(denyButton, confirmationButton);
    layout.getChildren().addAll(header, main);

    Scene scene = new Scene(layout, 300, 150);
    scene.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());
    popupStage.setScene(scene);
  }

  public void show() {
    popupStage.showAndWait();
  }
}