package ntnu.idatt2003.group27.view.components;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

/**
 * A class representing the main layout structure for a JavaFX application. The layout consists
 * of a header and a main content area, with the main area divided into left, center, and right
 * containers.
 * The layout is styled using external CSS stylesheets and supports flexible resizing.
 *
 * @author Iver Lindholm
 * @version 1.1
 * @since 2.0
 */
public class AppLayout extends VBox {
  /** The container for the header's main content, centered within the header. */
  private final HBox headerMainContainer;
  /** The container for the left container area in the main section. */
  private final VBox leftContainer;
  /** The container for the central content area in the main section */
  private final VBox mainContainer;
  /** The container for the right content area in the main section. */
  private final VBox rightContainer;

  /**
   * Constructs and {@link AppLayout} instance, initializing the header and main content areas. The
   * layout is divided into a header with left, center, and right sections, and a main area with
   * left, center, and right containers. The layout is styled and configured for flexible resizing.
   */
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
    leftContainer.setPadding(new Insets(63,0,21,0));

    mainContainer = new VBox(12);
    HBox.setHgrow(mainContainer, Priority.ALWAYS);
    mainContainer.setMinWidth(630);
    mainContainer.setMaxWidth(1100);
    mainContainer.setPadding(new Insets(21,0,21,0));

    rightContainer = new VBox(20);
    rightContainer.setMinWidth(303);
    HBox.setHgrow(rightContainer, Priority.ALWAYS);
    rightContainer.setAlignment(Pos.TOP_LEFT);
    rightContainer.setPadding(new Insets(63,0,21,0));

    header.getChildren().addAll(headerLeftContainer, headerMainContainer, headerRightContainer);
    main.getChildren().addAll(leftContainer, mainContainer, rightContainer);
    this.getChildren().addAll(header, main);
  }

  /**
   * Retrieves the header's main content container.
   *
   * @return the {@link HBox} representing the header's main content container.
   */
  public HBox getHeader() {
    return headerMainContainer;
  }

  /**
   * Retrieves the left content container in the main section.
   *
   * @return the {@link VBox} representing the left content container.
   */
  public VBox getLeftContainer() {
    return leftContainer;
  }

  /**
   * Retrieves the central content container in the main section.
   *
   * @return the {@link VBox} representing the central content container.
   */
  public VBox getMainContainer() {
    return mainContainer;
  }

  /**
   * Retrieves the right content container in the main section.
   *
   * @return the {@link VBox} representing the right content container.
   */
  public VBox getRightContainer() {
    return rightContainer;
  }
}
