package ntnu.idatt2003.group27.filehandler;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.FileReader;
import java.io.IOException;
import ntnu.idatt2003.group27.models.Board;

public class JsonFileReader implements ntnu.idatt2003.group27.filehandler.FileReader {
  /**
   * Reads the board.json file at filepath and returns a new board object with the contents of the file.
   * @param filePath
   * @return
   * @throws IOException
   */
  public Board readBoardFile(String filePath) throws IOException{


    try {
      FileReader fileReader = new FileReader(filePath);

      JsonElement jsonElement = JsonParser.parseReader(fileReader);
      JsonObject jsonObject = jsonElement.getAsJsonObject();

      int numberOfTiles = jsonObject.get("numberOfTiles").getAsInt();
      Board board = new Board(numberOfTiles);
      return board;
    }
    catch (Exception e){
      throw new IOException(e.getMessage());
    }

  }

  @Override
  public Object readFile(String filePath) throws IOException {
    try{
      FileReader fileReader = new FileReader(filePath);
      JsonElement jsonElement = JsonParser.parseReader(fileReader);
      JsonObject jsonObject = jsonElement.getAsJsonObject();
      return jsonObject;
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
    return null;
  }
}


