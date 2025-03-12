package ntnu.idatt2003.group27.filehandler;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import ntnu.idatt2003.group27.models.Board;
import ntnu.idatt2003.group27.models.Tile;

/**
 * A class that reads a JSON file and creates a Board object from the contents of the file.
 */
public class JsonCustomFileReader implements CustomFileReader<Board> {

  private final Gson gson = new Gson();

  public JsonFileReaer() {
    
  }

  /**
   * Reads the board.json file at filepath and returns a new board object with the contents of the
   * file.
   *
   * @param filePath The path to the file to read.
   * @return Board
   * @throws IOException If the file is not found or if the file is not a valid JSON file.
   */
  @Override
  public Board readFile(String filePath) throws IOException {
    try (FileReader reader = new FileReader(filePath)) {
      JsonElement jsonElement = JsonParser.parseReader(reader);

      JsonObject jsonObject = jsonElement.getAsJsonObject();

      return gson.fromJson(jsonObject, Board.class);
    } catch (Exception e) {
      throw new IOException(e.getMessage());
    }
  }

  public Board BoardDeserializer(String jsonString) {
    JsonObject jsonObject = JsonParser.parseString(jsonString).getAsJsonObject();
    JsonObject tilesJsonObject = jsonObject.getAsJsonObject("tiles");

    List<Tile> tiles = new ArrayList<>();
  }
}


