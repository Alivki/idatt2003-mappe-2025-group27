package ntnu.idatt2003.group27.models.interfaces;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import ntnu.idatt2003.group27.models.Player;
import ntnu.idatt2003.group27.view.components.LadderCanvas;

import java.util.List;

public interface TileAction {
  /**
   * Executes the custom action associated with a tile, affecting the specified player.
   *
   * @param player The {@link Player} on whom the action is performed.
   */
  void perform(Player player);

  /**
   * Returns the animation path for the player's movement when this action is triggered.
   *
   * @param startTileId The starting tile ID.
   * @param actionTileId The tile ID where the action is performed.
   * @return A list of tile IDs representing the path of the animation.
   */
  List<Integer> getAnimationPath(int startTileId, int actionTileId);

  /**
   * Return the color to use when drawing the tile associated with this action.
   *
   * @return The {@link Color} for the tile.
   */
  Color getTileColor(int tileId);

  /**
   * Returns the path to the icon image for this action, or null if no icon is needed.
   *
   * @return The icon resource path, or null.
   */
  String getIconPath();

  /**
   * Draws custom elements specific to this actio.
   *
   * @param gc The {@link GraphicsContext} used for drawing.
   * @param tileId The zero based ID of the tile.
   * @param ladderCanvas The {@link LadderCanvas} instance for accessing tile positions.
   */
  void drawCustom(GraphicsContext gc, int tileId, LadderCanvas ladderCanvas);

  /**
   * Draw the destination tile for this action, if applicable.
   *
   * @param gc The {@link GraphicsContext} used for drawing.
   * @param tileId The zero based ID of the tile.
   * @param ladderCanvas The {@link LadderCanvas} instance for accessing tile positions.
   */
  void drawDestinationTile(GraphicsContext gc, int tileId, LadderCanvas ladderCanvas);
}
