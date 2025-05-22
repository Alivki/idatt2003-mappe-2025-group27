package ntnu.idatt2003.group27.utils.filehandler.json;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.lang.reflect.Type;
import ntnu.idatt2003.group27.models.Board;
import ntnu.idatt2003.group27.models.Tile;
import ntnu.idatt2003.group27.models.actions.BackToStartAction;
import ntnu.idatt2003.group27.models.actions.LadderAction;
import ntnu.idatt2003.group27.models.actions.ThrowNewDiceAction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Unit test for board deserializer class.
 */
public class BoardDeserializerTest {
  private BoardDeserializer boardDeserializer;
  private JsonDeserializationContext context;

  /**
   * Sets up the test environment before each test case.
   */
  @BeforeEach
  void setUp() {
    boardDeserializer = new BoardDeserializer();
    context = new JsonDeserializationContext() {
      @Override
      public <T> T deserialize(JsonElement json, Type typeOfT) {
        return null;
      }
    };
  }

  /**
   * Test deserialization of a board with a single tile without action or next tile.
   */
  @Test
  void deserializerBoardWithSingleTile() {
    JsonObject json = new JsonObject();
    JsonArray tilesArray = new JsonArray();
    JsonObject tileJson = new JsonObject();
    tileJson.addProperty("id", 1);
    tilesArray.add(tileJson);
    json.add("tiles", tilesArray);

    Board board = boardDeserializer.deserialize(json, Board.class, context);

    assertNotNull(board);
    assertEquals(1, board.getTiles().size());
    Tile tile = board.getTiles().get(1);
    assertEquals(1, tile.getTileId());
    assertNull(tile.getNextTile());
    assertNull(tile.getLandAction());
  }

  /**
   * Test deserialization of a board with a single tile with a next tile.
   */
  @Test
  void deserializerBoardWithNextTile() {
    JsonObject json = new JsonObject();
    JsonArray tilesArray = new JsonArray();
    JsonObject tileJson = new JsonObject();
    tileJson.addProperty("id", 1);
    tileJson.addProperty("nextTile", 2);
    tilesArray.add(tileJson);
    JsonObject tile2Json = new JsonObject();
    tile2Json.addProperty("id", 2);
    tilesArray.add(tile2Json);
    json.add("tiles", tilesArray);

    Board board = boardDeserializer.deserialize(json, Board.class, context);

    assertEquals(2, board.getTiles().size());
    Tile tile1 = board.getTiles().get(1);
    Tile tile2 = board.getTiles().get(2);
    assertEquals(tile2, tile1.getNextTile());
  }

  /**
   * Test deserialization of a board with a single tile with a ladder action.
   */
  @Test
  void deserializerBoardWithLadder() {
    JsonObject json = new JsonObject();
    JsonArray tilesArray = new JsonArray();
    JsonObject tileJson = new JsonObject();
    tileJson.addProperty("id", 1);
    JsonObject actionJson = new JsonObject();
    actionJson.addProperty("type", "LadderAction");
    actionJson.addProperty("targetTile", 2);
    actionJson.addProperty("description", "Go to tile 2");
    tileJson.add("action", actionJson);
    tilesArray.add(tileJson);
    json.add("tiles", tilesArray);

    Board board = boardDeserializer.deserialize(json, Board.class, context);

    Tile tile = board.getTiles().get(1);
    assertTrue(tile.getLandAction() instanceof LadderAction);
    LadderAction ladderAction = (LadderAction) tile.getLandAction();
    assertEquals(2, ladderAction.getDestinationTileId());
    assertEquals("Go to tile 2", ladderAction.getDescription());
  }

  /**
   * Test deserialization of a board with a single tile with a back to start action.
   */
  @Test
  void deserializerBoardWithBackToStart() {
    JsonObject json = new JsonObject();
    JsonArray tilesArray = new JsonArray();
    JsonObject tileJson = new JsonObject();
    tileJson.addProperty("id", 1);
    JsonObject actionJson = new JsonObject();
    actionJson.addProperty("type", "BackToStartAction");
    actionJson.addProperty("description", "Go back to start");
    tileJson.add("action", actionJson);
    tilesArray.add(tileJson);
    json.add("tiles", tilesArray);

    Board board = boardDeserializer.deserialize(json, Board.class, context);

    Tile tile = board.getTiles().get(1);
    assertTrue(tile.getLandAction() instanceof BackToStartAction);
    BackToStartAction action = (BackToStartAction) tile.getLandAction();
    assertEquals("Go back to start", action.getDescription());
  }

  /**
   * Test deserialization of a board with a single tile with a throw new dice action.
   */
  @Test
  void deserializerBoardWithThrowNewDice() {
    JsonObject json = new JsonObject();
    JsonArray tilesArray = new JsonArray();
    JsonObject tileJson = new JsonObject();
    tileJson.addProperty("id", 1);
    JsonObject actionJson = new JsonObject();
    actionJson.addProperty("type", "ThrowNewDiceAction");
    actionJson.addProperty("description", "Roll again");
    actionJson.addProperty("numberOfDice", 2);
    actionJson.addProperty("numberOfSides", 6);
    tileJson.add("action", actionJson);
    tilesArray.add(tileJson);
    json.add("tiles", tilesArray);

    Board board = boardDeserializer.deserialize(json, Board.class, context);

    Tile tile = board.getTiles().get(1);
    assertTrue(tile.getLandAction() instanceof ThrowNewDiceAction);
    ThrowNewDiceAction action = (ThrowNewDiceAction) tile.getLandAction();
    assertEquals("Roll again", action.getDescription());
    assertEquals(2, action.getNumberOfDice());
    assertEquals(6, action.getNumberOfSides());
  }

  /**
   * Tests deserialization with empty tiles array.
   */
  @Test
  void deserializeEmptyTilesArray() {
    JsonObject json = new JsonObject();
    JsonArray tilesArray = new JsonArray();
    json.add("tiles", tilesArray);

    Board board = boardDeserializer.deserialize(json, Board.class, context);

    assertNotNull(board);
    assertTrue(board.getTiles().isEmpty());
  }

  /**
   * Test deserialization with missing tiles array.
   */
  @Test
  void deserializeMissingTilesArrayThrowsException() {
    JsonObject json = new JsonObject();

    assertThrows(NullPointerException.class,
        () -> boardDeserializer.deserialize(json, Board.class, context));
  }

  /**
   * Test deserialization with unknown action type.
   */
  @Test
  void deserializeUnknownActionTypeThrowsException() {
    JsonObject json = new JsonObject();
    JsonArray tilesArray = new JsonArray();
    JsonObject tileJson = new JsonObject();
    tileJson.addProperty("id", 1);
    JsonObject actionJson = new JsonObject();
    actionJson.addProperty("type", "InvalidAction");
    tileJson.add("action", actionJson);
    tilesArray.add(tileJson);
    json.add("tiles", tilesArray);

    assertThrows(IllegalArgumentException.class,
        () -> boardDeserializer.deserialize(json, Board.class, context));
  }

  /**
   * Test deserialization with null JSON element.
   */
  @Test
  void deserializeNullJsonThrowsException() {
    JsonElement json = null;

    assertThrows(NullPointerException.class,
        () -> boardDeserializer.deserialize(json, Board.class, context));
  }
}
