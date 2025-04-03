package ntnu.idatt2003.group27.view.components;

import java.util.function.Consumer;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.stage.StageStyle;

public class ConfirmationPopup {
  private final Stage popupStage;
  private final String message;
  private final String confirmationText;
  private final String denyText;
  private final Consumer<Boolean> onResponse;

  public ConfirmationPopup(String title, String message, String confirmationText, String denyText, Consumer<Boolean> onResponse) {
    this.message = message;
    this.confirmationText = confirmationText;
    this.denyText = denyText;
    this.onResponse = onResponse;
    this.popupStage = new Stage();

    popupStage.initModality(Modality.APPLICATION_MODAL);
    popupStage.initStyle(StageStyle.UNDECORATED);
    popupStage.setTitle(title);
    popupStage.setResizable(false);

    setupUI();
  }

  private void setupUI() {
    Label messageLabel = new Label(message);
    messageLabel.setWrapText(true);

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

    VBox layout = new VBox(10);
    layout.getStyleClass().add("popup");
    layout.getChildren().addAll(messageLabel, confirmationButton, denyButton);

    Scene scene = new Scene(layout, 300, 150);
    scene.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());
    popupStage.setScene(scene);
  }

  public void show() {
    popupStage.showAndWait();
  }
}