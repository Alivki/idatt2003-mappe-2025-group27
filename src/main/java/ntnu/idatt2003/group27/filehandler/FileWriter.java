package ntnu.idatt2003.group27.filehandler;

import java.io.IOException;

/**
 *.
 *
 * @param <T> .
 */
public interface FileWriter<T> {

  /**
   *.
   *
   * @param filePath .
   * @return .
   * @throws IOException .
   */
  void writeFile(String filePath, T data) throws  IOException;
}
