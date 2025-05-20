package ntnu.idatt2003.group27.utils.filehandler.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Logger;
import ntnu.idatt2003.group27.models.Board;
import ntnu.idatt2003.group27.utils.filehandler.interfaces.CustomFileReader;

/**
 * Handles the reading of a JSON file and deserializes it into a {@link Board} object.
 * This class utilizes the Gson library to parse the JSON data and
 * construct a {@link Board} instance.
 *
 * @author Iver Lindholm
 * @version 1.0
 * @since 1.0
 */

public class JsonFileReader implements CustomFileReader<Board> {
  /**
   * Logger instance for the {@code JsonFileReader} class.
   * Used for logging informational messages and errors related to class operations.
   */
  private static final Logger logger = Logger.getLogger(JsonFileReader.class.getName());
  private final Gson gson;

  /**
   * Constructs a {@link JsonFileReader} instance with a configured Gson object. The Gson instance
   * is set up to use a custom {@link BoardDeserializer} to handle the deserialization of the JSON
   * into a {@link Board} object.
   */
  public JsonFileReader() {
    this.gson =
        new GsonBuilder().registerTypeAdapter(Board.class, new BoardDeserializer()).create();
  }

  /**
   * Reads a JSON file from a specified file path and deserializes it into a {@link Board} object.
   *
   * @param filePath The path to the JSON file to read.
   * @return a {@link Board} object representing the data in the JSON file.
   * @throws IOException if an error occurs while reading the file or deserializing the JSON data.
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


