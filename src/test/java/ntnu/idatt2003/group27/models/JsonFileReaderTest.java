package ntnu.idatt2003.group27.models;

import java.io.IOException;
import ntnu.idatt2003.group27.filehandler.JsonCustomFileReader;
import org.junit.jupiter.api.Test;

public class JsonCustomFileReaderTest {
  @Test
  public void ReadBoardFileToBoardTest(){
    JsonCustomFileReader jsonFileReader = new JsonCustomFileReader();
    Board board = null;
    try {
      board = jsonFileReader.readBoardFile("Board.json");
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    System.out.println(board.getTiles().size());
  }
}
