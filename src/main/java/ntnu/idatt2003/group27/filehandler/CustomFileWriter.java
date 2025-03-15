package ntnu.idatt2003.group27.filehandler;

import java.io.IOException;

/**
 * Defines a contract for writing objects of a specified type to a file. Implementations of this
 * interface are responsible for serializing data and saving it to the specified file location.
 *
 * @param <T> The type of object to write to file.
 * @author Iver Lindholm
 * @version 1.0
 * @since 1.0
 */
public interface CustomFileWriter<T> {

  /**
   * Writes the provided data to a file at the specified file path.
   *
   * @param filePath The path to the file where the data will be written.
   * @param data The object of type T to be serialized and written to the file.
   * @throws IOException if an error occurs wile writing the file.
   */
  void writeFile(String filePath, T data) throws  IOException;
}
