package ntnu.idatt2003.group27.filehandler;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import ntnu.idatt2003.group27.models.BackToStartAction;
import ntnu.idatt2003.group27.models.Board;
import ntnu.idatt2003.group27.models.LadderAction;
import ntnu.idatt2003.group27.models.Tile;
import ntnu.idatt2003.group27.models.TileAction;

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
   * Deserializes a JSON object into a {@link Board} object. The JSON is expected to contain an
   * array of tile definitions, including tile IDs, connections to next tiles, and optional actions
   * such as ladders.
   *
   * @param json The {@link JsonElement} representing the JSON data to deserialize.
   * @param typeOfT The type of the object to deserialize to. In this case, a {@link Board} object.
   * @param context The {@link JsonDeserializationContext} for deserializing nested objects.
   * @return A new {@link Board} instance populated with tiles and actions based on the JSON data.
   * @throws JsonParseException if the JSON structure is invalid or cannot be parsed.
   * @throws IllegalArgumentException if an unknown action type is encountered in the JSON data.
   */
  @Override
  public Board deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
      throws JsonParseException, IllegalArgumentException {
    JsonObject jsonObject = json.getAsJsonObject();
    JsonArray tilesJsonArray = jsonObject.getAsJsonArray("tiles");

    Map<Integer, Tile> tiles = new HashMap<>();

    for (JsonElement tileElement : tilesJsonArray) {
      JsonObject tileObject = tileElement.getAsJsonObject();
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
            int targetTile = actionObject.get("targetTile").getAsInt();
            String ladderDescription = actionObject.get("description").getAsString();
            currentTile.setLandAction(new LadderAction(targetTile, ladderDescription));
            break;
          case "BackToStartAction":
            String backToStartDescription = actionObject.get("description").getAsString();
            currentTile.setLandAction(new BackToStartAction(backToStartDescription));
            break;
          default:
            throw new IllegalArgumentException("Unknown action type: " + type);
        }
      }
    }

    return new Board(tiles);
  }
}
