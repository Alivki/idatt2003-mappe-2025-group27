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
import ntnu.idatt2003.group27.models.Board;
import ntnu.idatt2003.group27.models.LadderAction;
import ntnu.idatt2003.group27.models.Tile;
import ntnu.idatt2003.group27.models.TileAction;

/**
 * A class that deserializes a JSON object to a Board object.
 */
public class BoardDeserializer implements JsonDeserializer<Board> {

  @Override
  public Board deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
      throws JsonParseException {
    JsonObject jsonObject = json.getAsJsonObject();
    JsonArray tilesJsonArray = jsonObject.getAsJsonArray("tiles");

    Map<Integer, Tile> tiles = new HashMap<>();
    Tile newTile = null;

    for (JsonElement tileElement : tilesJsonArray) {
      JsonObject tileObject = tileElement.getAsJsonObject();
      int id = tileObject.get("id").getAsInt();

      if (id == 1) {
        newTile = new Tile(id);
        continue;
      }

      int nextTileId = -1;
      if (id > 90) {
        nextTileId = tileObject.get("nextTile").getAsInt();
      }

      TileAction action = null;
      if (tileObject.has("action")) {
        JsonObject actionObject = tileObject.getAsJsonObject("action");
        String type = actionObject.get("type").getAsString();

        switch (type) {
          case "LadderAction":
            int targetTile = actionObject.get("targetTile").getAsInt();
            String description = actionObject.get("description").getAsString();
            action = new LadderAction(targetTile, description);
            break;
          default:
            throw new IllegalArgumentException("Unknown action type: " + type);
        }
      }

      Tile nextTile = new Tile(nextTileId);

      newTile.setNextTile(nextTile);
      newTile.setLandAction(action);

      if (id == 1) {
        tiles.put(id, newTile);
      }

      tiles.put(nextTileId, nextTile);
    }

    return new Board(tiles);
  }
}
