package ntnu.idatt2003.group27.filehandler;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.FileReader;
import java.io.IOException;
import ntnu.idatt2003.group27.models.Board;

/**
 * A class that reads a JSON file and creates a Board object from the contents of the file.
 */
public class JsonFileReader implements CustomFileReader<Board> {

  private final Gson gson;

  /**
   * Creates a new JsonFileReader object.
   */
  public JsonFileReader() {
    this.gson =
        new GsonBuilder().registerTypeAdapter(Board.class, new BoardDeserializer()).create();
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
      return gson.fromJson(reader, Board.class);
    } catch (Exception e) {
      throw new IOException(e.getMessage());
    }
  }
}


