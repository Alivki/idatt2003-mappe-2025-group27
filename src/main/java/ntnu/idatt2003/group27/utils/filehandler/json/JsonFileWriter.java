package ntnu.idatt2003.group27.utils.filehandler.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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
 * @version 1.1
 */
public class JsonFileWriter implements CustomFileWriter<Board> {
  /**
   * The Gson instance used for serializing the {@link Board} object to JSON.
   * Public to allow access to the Gson instance for testing purposes.
   * Prioritizing testability over encapsulation.
   */
  public final Gson gson;

  /**
   * Constructs a {@link JsonFileWriter} instance with a configured Gson object. The Gson instance
   * is set up to use a custom {@link BoardSerializer} to handle the serialization of {@link Board}
   * object to Json.
   */
  public JsonFileWriter() {
    this.gson = new GsonBuilder()
        .registerTypeAdapter(Board.class, new BoardSerializer())
        .setPrettyPrinting()
        .create();
  }

  /**
   * Writes the provided {@link Board} object to a file in JSON format to the specified file path.
   *
   * @param filePath The path to the file where the data will be written.
   * @param data The {@link Board} object to be written to the file.
   * @throws IOException If an error occurs while writing to the file.
   */
  @Override
  public void writeFile(String filePath, Board data) throws IOException {
    try (FileWriter file = new FileWriter(filePath)) {
      gson.toJson(data, file);
      file.flush();
    } catch (Exception e) {
      throw new IOException("Error writing to file: " + e.getMessage(), e);
    }
  }
}
