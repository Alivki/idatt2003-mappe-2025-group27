package ntnu.idatt2003.group27.models;

import com.google.gson.JsonObject;
import java.io.IOException;
import ntnu.idatt2003.group27.filehandler.JsonFileWriter;
import org.junit.jupiter.api.Test;

public class JsonFileWriterTest {
  @Test
  public void WriteBoardToJsonTest(){
    Board board = new Board(10);
    JsonFileWriter jsonFileWriter = new JsonFileWriter();
    JsonObject jsonObject = jsonFileWriter.serializeBoard(board);
    try {
      jsonFileWriter.writeFile("Board.Json", jsonObject);
    }
    catch (IOException e){
      System.out.println(e.getMessage());
    }
  }
}
