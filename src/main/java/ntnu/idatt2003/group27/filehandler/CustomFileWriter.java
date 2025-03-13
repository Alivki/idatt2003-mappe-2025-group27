package ntnu.idatt2003.group27.filehandler;

import java.io.IOException;

/**
 *.
 *
 * @param <T> .
 */
public interface CustomFileWriter<T> {

  /**
   *.
   *
   * @param filePath .
   * @return .
   * @throws IOException .
   */
  T writeFile(String filePath, Object data) throws IOException;
}
