package ntnu.idatt2003.group27.models.actions;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.StrokeLineCap;
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
public class LadderAction implements LadderTileAction {
  public int destinationTileId;
  public String description;

  /**
   * Constructs a ladder action with the specified destination tile and description.
   *
   * @param destinationTileId The ID of the tile where the player will be moved.
   * @param description       A textual description of the ladder action.
   */
  public LadderAction(int destinationTileId, String description) {
    this.destinationTileId = destinationTileId;
    this.description = description;
  }

  /**
   * Executes the ladder action by moving the specified player to the destination tile.
   *
   * @param player The {@link Player} affected by this ladder action.
   */
  @Override
  public void perform(Player player) {
    player.move(destinationTileId - player.getCurrentTile().getTileId());
  }

  @Override
  public List<Integer> getAnimationPath(int startTileId, int actionTileId) {
    List<Integer> path = new ArrayList<>();
    IntStream.range(startTileId, actionTileId).forEach(path::add);
    path.add(destinationTileId);
    return path;
  }

  @Override
  public Color getTileColor(int tileId) {
    return destinationTileId > tileId ? Color.GREEN : Color.DARKRED;
  }

  @Override
  public String getIconPath() {
    return null;
  }

  @Override
  public void drawCustom(GraphicsContext gc, int tileId, LadderCanvas ladderCanvas) {
    double[] start = ladderCanvas.getTileCenter(tileId - 1);
    double[] end = ladderCanvas.getTileCenter(destinationTileId - 1);

    double dx = end[0] - start[0];
    double dy = end[1] - start[1];
    double angle = Math.atan2(dy, dx);
    double length = Math.sqrt(dx * dx + dy * dy);

    double radius = ladderCanvas.getTileSize() / 4;
    double[] offset = {radius * Math.sin(angle), radius * Math.cos(angle)};

    gc.setStroke(Color.BLACK);
    gc.setLineWidth(5);
    gc.setLineCap(StrokeLineCap.ROUND);
    gc.beginPath();
    gc.moveTo(start[0] + offset[0], start[1] - offset[1]);
    gc.lineTo(end[0] + offset[0], end[1] - offset[1]);
    gc.stroke();

    int numSteps = (int) (length / (ladderCanvas.getTileSize() / 2));
    double stepDx = dx / (numSteps + 1);
    double stepDy = dy / (numSteps + 1);
    double stepRadius = radius - 2;
    double[] stepOffset = {stepRadius * Math.sin(angle), stepRadius * Math.cos(angle)};

    gc.setStroke(Color.BLACK);
    gc.setLineWidth(7);
    gc.beginPath();
    for (int i = 1; i <= numSteps; i++) {
      double x = start[0] + i * stepDx;
      double y = start[1] + i * stepDy;
      gc.moveTo(x + stepOffset[0], y - stepOffset[1]);
      gc.lineTo(x - stepOffset[0], y + stepOffset[1]);
    }
    gc.stroke();

    gc.setStroke(Color.WHITE);
    gc.setLineWidth(3);
    gc.beginPath();
    for (int i = 1; i <= numSteps; i++) {
      double x = start[0] + i * stepDx;
      double y = start[1] + i * stepDy;
      gc.moveTo(x + stepOffset[0], y - stepOffset[1]);
      gc.lineTo(x - stepOffset[0], y + stepOffset[1]);
    }
    gc.stroke();

    gc.setStroke(Color.BLACK);
    gc.setLineWidth(5);
    gc.beginPath();
    gc.moveTo(start[0] - offset[0], start[1] + offset[1]);
    gc.lineTo(end[0] - offset[0], end[1] + offset[1]);
    gc.stroke();

    gc.setFill(Color.WHITE);
    double[] dotPos = (tileId > destinationTileId) ? start : end;
    gc.fillOval(dotPos[0] + offset[0] - 1.5, dotPos[1] - offset[1] - 1.5, 3, 3);
    gc.fillOval(dotPos[0] - offset[0] - 1.5, dotPos[1] + offset[1] - 1.5, 3, 3);
  }

  @Override
  public void drawDestinationTile(GraphicsContext gc, int tileId, LadderCanvas ladderCanvas) {
    double[] tileLandPosition = ladderCanvas.getTilePos(destinationTileId - 1);
    gc.setFill(tileId + 1 < destinationTileId ? Color.LIGHTGREEN : Color.RED);
    gc.fillRect(tileLandPosition[0], tileLandPosition[1], ladderCanvas.getTileSize(), ladderCanvas.getTileSize());
  }

  /**
   * Retrieves the tile id of the destination tile for this ladder action.
   *
   * @return the ID of the destination tile.
   */
  public int getDestinationTileId() {
    return destinationTileId;
  }

  /**
   * Retrieves the description of this ladder action.
   *
   * @return the description of the ladder action.
   */
  public String getDescription() {
    return description;
  }
}
