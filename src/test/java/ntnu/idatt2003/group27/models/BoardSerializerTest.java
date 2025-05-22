package ntnu.idatt2003.group27.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.google.gson.JsonArray;
import ntnu.idatt2003.group27.models.actions.BackToStartAction;
import ntnu.idatt2003.group27.models.actions.LadderAction;
import ntnu.idatt2003.group27.models.actions.ThrowNewDiceAction;
import ntnu.idatt2003.group27.utils.filehandler.json.BoardSerializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Unit test for board serializer class.
 */
public class BoardSerializerTest {
  private BoardSerializer serializer;
  private JsonSerializationContext context;

  /**
   * Sets up the test environment before each test case.
   */
  @BeforeEach
  void setUp() {
    serializer = new BoardSerializer();
    context = new JsonSerializationContext() {
      @Override
      public JsonElement serialize(Object src) {
        return null;
      }

      @Override
      public JsonElement serialize(Object src, Type typeOfSrc) {
        return null;
      }
    };
  }

  /**
   * Test serialization of a board with a single tile without action or next tile.
   */
  @Test
  void serializeBoardWithSingleTileNoAction() {
    Map<Integer, Tile> tiles = new HashMap<>();
    Tile tile = new Tile(1);
    tiles.put(1, tile);

    Board board = new Board(tiles);

    JsonElement result = serializer.serialize(board, Board.class, context);

    assertTrue(result.isJsonObject());
    JsonObject boardJson = result.getAsJsonObject();
    assertTrue(boardJson.has("tiles"));
    JsonArray tilesArray = boardJson.getAsJsonArray("tiles");
    assertEquals(1, tilesArray.size());
    JsonObject tileJson = tilesArray.get(0).getAsJsonObject();
    assertEquals(1, tileJson.get("id").getAsInt());
    assertFalse(tileJson.has("nextTileId"));
    assertFalse(tileJson.has("action"));
  }

  /**
   * Test serialization of a board with a single tile without action but with next tile.
   */
  @Test
  void serializeBoardWithTileAndNextTile() {
    Map<Integer, Tile> tiles = new HashMap<>();
    Tile tile1 = new Tile(1);
    Tile tile2 = new Tile(2);
    tile1.setNextTile(tile2);
    tiles.put(1, tile1);
    tiles.put(2, tile2);

    Board board = new Board(tiles);

    JsonElement result = serializer.serialize(board, Board.class, context);

    JsonObject boardJson = result.getAsJsonObject();
    JsonArray tilesArray = boardJson.getAsJsonArray("tiles");
    JsonObject tileJson = tilesArray.get(0).getAsJsonObject();
    assertEquals(1, tileJson.get("id").getAsInt());
    assertEquals(2, tileJson.get("nextTileId").getAsInt());
    assertFalse(tileJson.has("action"));
  }

  /**
   * Test serialization of a board with a single tile with an ladderAction.
   */
  @Test
  void serializeTileWithLadderAction() {
    Map<Integer, Tile> tiles = new HashMap<>();
    Tile tile = new Tile(1);
    tile.setLandAction(new LadderAction(5, "Go to tile 5"));
    tiles.put(1, tile);

    Board board = new Board(tiles);

    JsonElement result = serializer.serialize(board, Board.class, context);

    JsonObject boardJson = result.getAsJsonObject();
    JsonArray tilesArray = boardJson.getAsJsonArray("tiles");
    JsonObject tileJson = tilesArray.get(0).getAsJsonObject();
    JsonObject actionJson = tileJson.getAsJsonObject("action");
    assertEquals("LadderAction", actionJson.get("type").getAsString());
    assertEquals(5, actionJson.get("targetTile").getAsInt());
    assertEquals("Go to tile 5", actionJson.get("description").getAsString());
  }

  /**
   * Test serialization of a board with a single tile with an backToStartAction.
   */
  @Test
  void serializeTileWithBackToStartAction() {
    Map<Integer, Tile> tiles = new HashMap<>();
    Tile tile = new Tile(1);
    tile.setLandAction(new BackToStartAction("Go back to start"));
    tiles.put(1, tile);

    Board board = new Board(tiles);

    JsonElement result = serializer.serialize(board, Board.class, context);

    JsonObject boardJson = result.getAsJsonObject();
    JsonArray tilesArray = boardJson.getAsJsonArray("tiles");
    JsonObject tileJson = tilesArray.get(0).getAsJsonObject();
    JsonObject actionJson = tileJson.getAsJsonObject("action");
    assertEquals("BackToStartAction", actionJson.get("type").getAsString());
    assertEquals("Go back to start", actionJson.get("description").getAsString());
  }

  /**
   * Test serialization of a board with a single tile with an ThrowNewDiceAction.
   */
  @Test
  void serializeTileWithThrowNewDiceAction() {
    Map<Integer, Tile> tiles = new HashMap<>();
    Tile tile = new Tile(1);
    tile.setLandAction(new ThrowNewDiceAction("Throw a new dice", 2, 6));
    tiles.put(1, tile);

    Board board = new Board(tiles);

    JsonElement result = serializer.serialize(board, Board.class, context);

    JsonObject boardJson = result.getAsJsonObject();
    JsonArray tilesArray = boardJson.getAsJsonArray("tiles");
    JsonObject tileJson = tilesArray.get(0).getAsJsonObject();
    JsonObject actionJson = tileJson.getAsJsonObject("action");
    assertEquals("ThrowNewDiceAction", actionJson.get("type").getAsString());
    assertEquals("Throw a new dice", actionJson.get("description").getAsString());
    assertEquals(2, actionJson.get("numberOfDice").getAsInt());
    assertEquals(6, actionJson.get("numberOfSides").getAsInt());
  }

  /**
   * Test serialization of a board with no tiles.
   */
  @Test
  void serializeEmptyBoard() {
    Map<Integer, Tile> tiles = new HashMap<>();

    Board board = new Board(tiles);

    JsonElement result = serializer.serialize(board, Board.class, context);

    JsonObject boardJson = result.getAsJsonObject();
    JsonArray tilesArray = boardJson.getAsJsonArray("tiles");
    assertEquals(0, tilesArray.size());
  }

  /**
   * Test serialization with a tile having null action.
   */
  @Test
  void serializeTileWithNullAction() {
    Map<Integer, Tile> tiles = new HashMap<>();
    Tile tile = new Tile(1);
    tile.setLandAction(null);
    tiles.put(1, tile);
    Board board = new Board(tiles);

    JsonElement result = serializer.serialize(board, Board.class, context);

    JsonObject boardJson = result.getAsJsonObject();
    JsonArray tilesArray = boardJson.getAsJsonArray("tiles");
    JsonObject tileJson = tilesArray.get(0).getAsJsonObject();
    assertFalse(tileJson.has("action"));
  }

}
