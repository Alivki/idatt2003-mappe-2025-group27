package ntnu.idatt2003.group27.view.components;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class AppLayout extends VBox {
  private final HBox headerMainContainer;
  private final VBox leftContainer;
  private final VBox mainContainer;
  private final VBox rightContainer;

  public AppLayout() {
    this.setAlignment(Pos.TOP_CENTER);

    HBox header = new HBox();
    header.getStyleClass().add("header");

    HBox headerLeftContainer = new HBox();
    headerLeftContainer.setAlignment(Pos.CENTER_LEFT);
    headerLeftContainer.setMinWidth(250);
    headerLeftContainer.setPrefWidth(250);

    headerMainContainer = new HBox(8);
    HBox.setHgrow(headerMainContainer, Priority.ALWAYS);
    headerMainContainer.setAlignment(Pos.CENTER);

    HBox headerRightContainer = new HBox();
    headerRightContainer.setMinWidth(250);
    headerRightContainer.setPrefWidth(250);

    HBox main = new HBox(23);
    VBox.setVgrow(main, Priority.ALWAYS);
    main.getStyleClass().add("main-container");

    leftContainer = new VBox(20);
    leftContainer.setMinWidth(303);
    HBox.setHgrow(leftContainer, Priority.ALWAYS);
    leftContainer.setAlignment(Pos.TOP_RIGHT);
    //leftContainer.setStyle("-fx-background-color: #bf1616");
    leftContainer.setPadding(new Insets(62,0,0,0));

    mainContainer = new VBox(12);
    mainContainer.setMinWidth(627);
    mainContainer.setMaxWidth(627);
    //mainContainer.setStyle("-fx-background-color: #46bf16");
    mainContainer.setPadding(new Insets(21,0,0,0));

    rightContainer = new VBox(20);
    rightContainer.setMinWidth(303);
    HBox.setHgrow(rightContainer, Priority.ALWAYS);
    rightContainer.setAlignment(Pos.TOP_LEFT);
    //rightContainer.setStyle("-fx-background-color: #10409a");
    rightContainer.setPadding(new Insets(62,0,0,0));

    header.getChildren().addAll(headerLeftContainer, headerMainContainer, headerRightContainer);
    main.getChildren().addAll(leftContainer, mainContainer, rightContainer);
    this.getChildren().addAll(header, main);
  }

  public HBox getHeader() {
    return headerMainContainer;
  }

  public VBox getLeftContainer() {
    return leftContainer;
  }

  public VBox getMainContainer() {
    return mainContainer;
  }

  public VBox getRightContainer() {
    return rightContainer;
  }
}
