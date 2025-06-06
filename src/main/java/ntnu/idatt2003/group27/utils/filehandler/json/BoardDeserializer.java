package ntnu.idatt2003.group27.utils.filehandler.json;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;
import ntnu.idatt2003.group27.models.actions.BackToStartAction;
import ntnu.idatt2003.group27.models.Board;
import ntnu.idatt2003.group27.models.actions.LadderAction;
import ntnu.idatt2003.group27.models.actions.ThrowNewDiceAction;
import ntnu.idatt2003.group27.models.Tile;

/**
 * Deserializes a JSON object into a {@link Board} instance, constructing a game board with tiles
 * and their associated actions based on the provided JSON structure. This class implements the
 * {@link JsonDeserializer} interface for custom deserialization logic.
 *
 * @author Iver Lindholm
 * @version 1.1
 * @since 1.0
 */
public class BoardDeserializer implements JsonDeserializer<Board> {
  /**
   * Logger instance for the {@link BoardDeserializer} class.
   * Used for logging informational messages and errors related to class operations.
   */
  private static final Logger logger = Logger.getLogger(BoardDeserializer.class.getName());

  /**
   * Deserializes a JSON object into a {@link Board} object. The JSON is expected to contain an
   * array of tile definitions, including tile IDs, connections to next tiles, and optional actions
   * such as ladders.
   *
   * @param json    The {@link JsonElement} representing the JSON data to deserialize.
   * @param typeOfT The type of the object to deserialize to. In this case, a {@link Board} object.
   * @param context The {@link JsonDeserializationContext} for deserializing nested objects.
   * @return A new {@link Board} instance populated with tiles and actions based on the JSON data.
   * @throws JsonParseException       if the JSON structure is invalid or cannot be parsed.
   * @throws IllegalArgumentException if an unknown action type is encountered in the JSON data.
   */
  @Override
  public Board deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
      throws JsonParseException, IllegalArgumentException {
    logger.fine("Deserializing JsonElement: " + json + ", to a Board instance.");
    JsonObject jsonObject = json.getAsJsonObject();
    JsonArray tilesJsonArray = jsonObject.getAsJsonArray("tiles");

    Map<Integer, Tile> tiles = new HashMap<>();

    tilesJsonArray.forEach(tile -> {
      JsonObject tileObject = tile.getAsJsonObject();
      int id = tileObject.get("id").getAsInt();

      Tile currentTile = tiles.computeIfAbsent(id, Tile::new);

      if (tileObject.has("nextTile")) {
        int nextTileId = tileObject.get("nextTile").getAsInt();
        Tile nextTile = tiles.computeIfAbsent(nextTileId, Tile::new);
        currentTile.setNextTile(nextTile);
        currentTile.setPreviousTile(tiles.get(currentTile.getTileId() - 1));
      }

      if (tileObject.has("action")) {
        JsonObject actionObject = tileObject.getAsJsonObject("action");
        String type = actionObject.get("type").getAsString();

        switch (type) {
          case "LadderAction":
            logger.fine("Adding LadderAction to tile: " + id);
            int targetTile = actionObject.get("targetTile").getAsInt();
            String ladderDescription = actionObject.get("description").getAsString();
            currentTile.setLandAction(new LadderAction(targetTile, ladderDescription));
            break;
          case "BackToStartAction":
            logger.fine("Adding BackToStartAction to tile: " + id);
            String backToStartDescription = actionObject.get("description").getAsString();
            currentTile.setLandAction(new BackToStartAction(backToStartDescription));
            break;
          case "ThrowNewDiceAction":
            logger.fine("Adding ThrowNewDiceAction to tile: " + id);
            String throwNewDiceDescription = actionObject.get("description").getAsString();
            int numberOfDice = actionObject.get("numberOfDice").getAsInt();
            int numberOfSides = actionObject.get("numberOfSides").getAsInt();
            currentTile.setLandAction(new ThrowNewDiceAction(throwNewDiceDescription, numberOfDice, numberOfSides));
            break;
          default:
            logger.warning("Could not add action to board: Unknown action type: " + type);
            throw new IllegalArgumentException("Unknown action type: " + type);
        }
      }

    });

    return new Board(tiles);
  }
}
