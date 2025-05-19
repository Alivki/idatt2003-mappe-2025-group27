package ntnu.idatt2003.group27.models.interfaces;

import java.util.List;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import ntnu.idatt2003.group27.models.Player;
import ntnu.idatt2003.group27.view.components.Canvas;

/**
 * Defines a contract for custom actions that can be executed when a player interacts with a 
 * tile on the game board. Implementations of this interface specify behavior triggered by 
 * a player's presence on a tile.
 *
 * @author Iver Lindholm
 * @version 1.0
 * @since 1.0
 */
public interface LadderTileAction extends TileAction {
  // no more methods needed
}
