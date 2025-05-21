package ntnu.idatt2003.group27.utils.filehandler.json;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;
import java.util.Comparator;
import java.util.logging.Logger;
import ntnu.idatt2003.group27.models.Board;
import ntnu.idatt2003.group27.models.Tile;
import ntnu.idatt2003.group27.models.actions.BackToStartAction;
import ntnu.idatt2003.group27.models.actions.LadderAction;
import ntnu.idatt2003.group27.models.actions.ThrowNewDiceAction;
import ntnu.idatt2003.group27.models.interfaces.TileAction;

/**
 * Serializes a {@link Board} object into a Json this class implements the {@link JsonSerializer} to
 * handle the serialization of the Board object.
 *
 * @author Iver Lindholm
 * @version 1.0
 * @since 2.0
 */
public class BoardSerializer implements JsonSerializer<Board> {
  /**
   * Logger instance for the {@link BoardSerializer} class.
   * Used for logging informational messages and errors related to class operations.
   */
  private static final Logger logger = Logger.getLogger(BoardSerializer.class.getName());

  @Override
  public JsonElement serialize(Board board, Type typeOfSrc, JsonSerializationContext context) {
    logger.fine("Serializing Board: " + board);
    JsonObject boardJson = new JsonObject();
    JsonArray tilesArray = new JsonArray();

    board.getTiles().values().stream()
        .sorted(Comparator.comparingInt(Tile::getTileId))
        .forEach(tile -> {
          JsonObject tileJson = new JsonObject();
          tileJson.addProperty("id", tile.getTileId());

          if (tile.getNextTile() != null) {
            tileJson.addProperty("nextTileId", tile.getNextTile().getTileId());
          }

          TileAction action = tile.getLandAction();
          if (action != null) {
            JsonObject actionJson = new JsonObject();
            if (action instanceof LadderAction ladder) {
              actionJson.addProperty("type", "LadderAction");
              actionJson.addProperty("targetTile", ladder.getDestinationTileId());
              actionJson.addProperty("description", ladder.getDescription());
            } else if (action instanceof BackToStartAction back) {
              actionJson.addProperty("type", "BackToStartAction");
              actionJson.addProperty("description", back.getDescription());
            } else if (action instanceof ThrowNewDiceAction dice) {
              actionJson.addProperty("type", "ThrowNewDiceAction");
              actionJson.addProperty("description", dice.getDescription());
              actionJson.addProperty("numberOfDice", dice.getNumberOfDice());
              actionJson.addProperty("numberOfSides", dice.getNumberOfSides());
            }
            tileJson.add("action", actionJson);
          }

          tilesArray.add(tileJson);
        });

    boardJson.add("tiles", tilesArray);
    return boardJson;
  }
}
