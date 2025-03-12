package ntnu.idatt2003.group27.filehandler;

import java.io.IOException;

/**
 *.
 *
 * @param <T> .
 */
public interface CustomFileReader<T> {

  /**
   *.
   *
   * @param filePath .
   * @return .
   * @throws IOException .
   */
  T readFile(String filePath) throws IOException;
}
