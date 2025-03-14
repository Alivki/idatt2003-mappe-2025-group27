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
            String description = actionObject.get("description").getAsString();
            currentTile.setLandAction(new LadderAction(targetTile, description));
            break;
          default:
            throw new IllegalArgumentException("Unknown action type: " + type);
        }
      }
    }

    return new Board(tiles);
  }
}
