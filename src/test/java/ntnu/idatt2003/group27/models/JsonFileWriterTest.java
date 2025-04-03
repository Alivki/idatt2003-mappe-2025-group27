package ntnu.idatt2003.group27.models;

import com.google.gson.JsonObject;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

import ntnu.idatt2003.group27.filehandler.JsonFileWriter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class JsonFileWriterTest {
  Map<Integer, Tile> tiles;


  /**
   * Creates a map of tiles for the board.
   */
  @BeforeEach
  public void createTiles() {
    tiles = new HashMap<>();
    IntStream.range(0, 10).forEach(i -> tiles.put(i, new Tile(i)));
  }

  @Test
  public void WriteBoardToJsonTest(){
    Board board = new Board(tiles);
    JsonFileWriter jsonFileWriter = new JsonFileWriter();
    JsonObject jsonObject = jsonFileWriter.serializeBoard(board);
    try {
      jsonFileWriter.writeFile("Board.Json", jsonObject);
    }
    catch (IOException e){
      System.out.println(e.getMessage());
    }
  }
}
