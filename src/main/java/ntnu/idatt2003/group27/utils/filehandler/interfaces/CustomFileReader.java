package ntnu.idatt2003.group27.utils.filehandler.interfaces;

import java.io.IOException;

/**
 * Defines a contract for reading and parsing files into objects of a specific type. Implementations
 * of this interface are responsible for handling file input and converting it into a usable
 * data structure.
 *
 * @param <T> The type of object to be returned by the file reading process.
 * @author Iver Lindholm
 * @version 1.0
 * @since 1.0
 */
public interface CustomFileReader<T> {

  /**
   * Reads a file from the specified path and converts its contents into a object of type T.
   *
   * @param filePath The path to the file to be read.
   * @return An object of type T representing the parsed file contents.
   * @throws IOException if an error occurs while reading the file.
   */
  T readFile(String filePath) throws IOException;
}
