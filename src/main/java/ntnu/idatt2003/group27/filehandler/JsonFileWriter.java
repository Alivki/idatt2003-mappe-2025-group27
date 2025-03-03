package ntnu.idatt2003.group27.filehandler;

import com.google.gson.JsonObject;
import java.io.FileWriter;
import java.io.IOException;
import ntnu.idatt2003.group27.models.Board;
import ntnu.idatt2003.group27.models.Tile;

/**
 * A class for writing data to files using the JSON format.
 */
public class JsonFileWriter {
  /**
   * Serializes the board object given as a parameter.
   * @param board
   * @return The JsonObject for the board.
   */
  public JsonObject serializeBoard(Board board){
    JsonObject boardJson = new JsonObject();

    int numberOfTiles = board.getTiles().size();
    boardJson.addProperty("numberOfTiles", numberOfTiles);

    /* Started working on tileaction compatibility, but having some trouble deciding on how TileAction information should be structured.
    //Store information for each tileAction.
    for(int i = 0; i < numberOfTiles; i++){
      Tile tile = board.getTiles().get(i);
      JsonObject tileActionObject = new JsonObject();
      //tileActionObject.addProperty("type", );
    }
    */

    return boardJson;
  }

  /**
   * Writes JsonObject to file at specified filepath.
   * @param filePath
   * @param data
   * @throws IOException
   */
  public void writeFile(String filePath, JsonObject data) throws IOException {
    try (FileWriter file = new FileWriter(filePath)) {
      file.write(data.toString());
      file.flush();
    } catch (Exception e) {
      throw new IOException(e.getMessage());
    }
  }
}
