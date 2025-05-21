package ntnu.idatt2003.group27.models;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.google.gson.Gson;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import ntnu.idatt2003.group27.models.actions.LadderAction;
import ntnu.idatt2003.group27.utils.filehandler.json.JsonFileWriter;
import org.junit.jupiter.api.BeforeEach;
import java.io.StringWriter;
import org.junit.jupiter.api.Test;

/**
 * Test class for the {@link JsonFileWriter} class.
 */
public class JsonFileWriterTest {
  private JsonFileWriter jsonFileWriter;
  private StringWriter stringWriter;

  /**
   * Sets up the test environment by initializing a {@link JsonFileWriter} instance,
   * creating a {@link Board} with 10 tiles,
   * and preparing a {@link StringWriter} for output.
   */
  @BeforeEach
  public void setUp() {
    jsonFileWriter = new JsonFileWriter();
    stringWriter = new StringWriter();
  }

  /**
   * Test serialization of a board with a single tile to JSON string.
   */
  @Test
  public void writeFileWithSingleTileSerializesCorrectly() throws IOException {
    Map<Integer, Tile> tiles = new HashMap<>();
    Tile tile = new Tile(1);
    tiles.put(1, tile);

    Board board = new Board(tiles);

    jsonFileWriter.writeFile("path", board);
    Gson gson = new Gson();
    gson.toJson(jsonFileWriter.gson.toJsonTree(board), stringWriter);

    String jsonOutput = stringWriter.toString();
    assertTrue(jsonOutput.contains("\"tiles\""));
    assertTrue(jsonOutput.contains("\"id\":1"));
    assertFalse(jsonOutput.contains("\"nextTileId\""));
    assertFalse(jsonOutput.contains("\"action\""));
  }

  /**
   * Test serialization of a board with a tile and ladderAction.
   */
  @Test
  void writeFileWithLadderActionSerializesCorrectly() throws IOException {
    Map<Integer, Tile> tiles = new HashMap<>();
    Tile tile = new Tile(1);
    tile.setLandAction(new LadderAction(5, "Go to tile 5"));
    tiles.put(1, tile);

    Board board = new Board(tiles);

    jsonFileWriter.writeFile("path", board);
    Gson gson = new Gson();
    gson.toJson(jsonFileWriter.gson.toJsonTree(board), stringWriter);
    System.out.println(stringWriter.toString());

    String jsonOutput = stringWriter.toString();
    assertTrue(jsonOutput.contains("\"type\":\"LadderAction\""));
    assertTrue(jsonOutput.contains("\"targetTile\":5"));
    assertTrue(jsonOutput.contains("\"description\":\"Go to tile 5\""));
  }

  /**
   * Test serialization of a board with no tiles.
   */
  @Test
  void writeFileWithEmptyBoardSerializesCorrectly() throws IOException {
    Map<Integer, Tile> tiles = new HashMap<>();
    Board board = new Board(tiles);

    jsonFileWriter.writeFile("path", board);
    Gson gson = new Gson();
    gson.toJson(jsonFileWriter.gson.toJsonTree(board), stringWriter);

    String jsonOutput = stringWriter.toString();
    assertTrue(jsonOutput.contains("\"tiles\""));
    assertTrue(jsonOutput.contains("[]"));
  }

  /**
   * Test serialization of a tile with null action.
   */
  @Test
  void writeFileWithNullActionSerializesCorrectly() throws IOException {
    Map<Integer, Tile> tiles = new HashMap<>();
    Tile tile = new Tile(1);
    tile.setLandAction(null);
    tiles.put(1, tile);

    Board board = new Board(tiles);

    jsonFileWriter.writeFile("path", board);
    Gson gson = new Gson();
    gson.toJson(jsonFileWriter.gson.toJsonTree(board), stringWriter);

    String jsonOutput = stringWriter.toString();
    assertTrue(jsonOutput.contains("\"tiles\""));
    assertTrue(jsonOutput.contains("\"id\":1"));
    assertFalse(jsonOutput.contains("\"nextTileId\""));
    assertFalse(jsonOutput.contains("\"action\""));
  }

}
