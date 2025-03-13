package ntnu.idatt2003.group27.models;

import java.io.IOException;
import ntnu.idatt2003.group27.filehandler.JsonFileReader;
import org.junit.jupiter.api.Test;

public class JsonFileReaderTest {
  @Test
  public void testReadBoardFileToBoardTest() {
    JsonFileReader jsonFileReader = new JsonFileReader();
    Board board = null;
    try {
      board = jsonFileReader.readFile(
          "src/main/java/ntnu/idatt2003/group27/resources/boards/Board.json");
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    System.out.println(board.getTiles().size());
  }
}
