package ntnu.idatt2003.group27.models.actions;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.IntStream;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import ntnu.idatt2003.group27.models.Player;
import ntnu.idatt2003.group27.models.interfaces.LadderTileAction;
import ntnu.idatt2003.group27.view.components.LadderCanvas;

/**
 * Represents the behavior of a ladder on the game board. This class implements the 
 * {@link LadderTileAction} interface to define an action that moves a player to a specified
 * destination tile when triggered.
 *
 * @author Iver Lindholm
 * @version 1.0
 * @since 1.0
 */
public class BackToStartAction implements LadderTileAction {
  /**
   * Logger instance for the {@link BackToStartAction} class.
   * Used for logging informational messages and errors related to class operations.
   */
  private static final Logger logger = Logger.getLogger(BackToStartAction.class.getName());

  /** Description for this {@link LadderTileAction} */
  public String description;

  /**
   * Constructs a ladder action with the specified destination tile and description.
   *
   * @param description A textual description of the ladder action.
   */
  public BackToStartAction(String description) {
    logger.fine("Initializing BackToStartAction");
    this.description = description;
  }

  /**
   * Executes the ladder action by moving the specified player to the destination tile.
   *
   * @param player The {@link Player} affected by this ladder action.
   */
  @Override
  public void perform(Player player) {
    logger.info("Performing BackToStartAction on player: " + player.getName());
    int currentTile = player.getCurrentTile().getTileId();

    if (currentTile <= 1) {
      return;
    }

    int steps = currentTile - 1;

    logger.fine("moving player " + player.getName() + " " + -steps + "steps.");
    player.move(-steps);
  }

  @Override
  public List<Integer> getAnimationPath(int startTileId, int actionTileId) {
    logger.fine("Getting Animation Path for " + startTileId + " and " + actionTileId);
    List<Integer> path = new ArrayList<>();
    IntStream.range(startTileId, actionTileId).forEach(path::add);
    path.add(1);
    return path;
  }

  @Override
  public Color getTileColor(int tileId) {
    logger.fine("Getting tile color for " + tileId);
    return Color.LIGHTPINK;
  }

  @Override
  public String getIconPath() {
    logger.fine("Getting icon path.");
    return "/icons/home.png";
  }

  @Override
  public void drawCustom(GraphicsContext gc, int tileId, LadderCanvas ladderCanvas) {
    // No custom drawing needed for this action
  }

  @Override
  public void drawDestinationTile(GraphicsContext gc, int tileId, LadderCanvas ladderCanvas) {
    // No custom drawing needed for this action
  }

  /**
   * Retrieves the description of this ladder action.
   *
   * @return the description of the ladder action.
   */
  public String getDescription() {
    logger.fine("Getting description.");
    return description;
  }
}
