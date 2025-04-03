package ntnu.idatt2003.group27.utils.filehandler.json;

import com.google.gson.JsonObject;
import java.io.FileWriter;
import java.io.IOException;
import ntnu.idatt2003.group27.models.Board;
import ntnu.idatt2003.group27.utils.filehandler.interfaces.CustomFileWriter;

/**
 * Handles the writing of a {@link Board} object and serializes it to a JSON file.
 * This class utilizes the Gson library to write the JSON data
 *
 * @author Iver Lindholm, Amadeus Berg
 * @since 0.0
 * @version 1.0
 */
public class JsonFileWriter implements CustomFileWriter<JsonObject> {
  /**
   * Serializes the board object given as a parameter.
   *
   * @param board .
   * @return The JsonObject for the board.
   */
  public JsonObject serializeBoard(Board board) {
    JsonObject boardJson = new JsonObject();

    int numberOfTiles = board.getTiles().size();
    boardJson.addProperty("numberOfTiles", numberOfTiles);

    return boardJson;
  }

  /**
   * Writes JsonObject to file at specified filepath.
   *
   * @param filePath .
   * @param data .
   * @throws IOException .
   */
  @Override
  public void writeFile(String filePath, JsonObject data) throws IOException {
    try (FileWriter file = new FileWriter(filePath)) {
      file.write(data.toString());
      file.flush();
    } catch (Exception e) {
      throw new IOException(e.getMessage());
    }
  }
}
