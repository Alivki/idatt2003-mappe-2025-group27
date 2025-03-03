package ntnu.idatt2003.group27.models;

import java.io.IOException;
import ntnu.idatt2003.group27.filehandler.JsonFileReader;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class JsonFileReaderTest {
  @Test
  public void ReadBoardFileToBoardTest(){
    JsonFileReader jsonFileReader = new JsonFileReader();
    Board board = null;
    try {
      board = jsonFileReader.readBoardFile("Board.json");
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    System.out.println(board.getTiles().size());
  }
}
