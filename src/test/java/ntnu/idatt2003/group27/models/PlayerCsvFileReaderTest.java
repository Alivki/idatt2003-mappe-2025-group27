package ntnu.idatt2003.group27.models;

import java.io.IOException;
import ntnu.idatt2003.group27.filehandler.PlayerCsvFileReader;
import org.junit.jupiter.api.Test;

public class PlayerCsvFileReaderTest {
  @Test
  public void testReadPlayerCsvFile(){
    PlayerCsvFileReader reader = new PlayerCsvFileReader();
    try {
      Player[] players =
          reader.readFile("src/main/java/ntnu/idatt2003/group27/resources/Players.csv");
      for(int i = 0; i < players.length; i++){
        System.out.printf(players[i].getName() + "\n");
      }
    }
    catch (IOException e){
      System.out.println(e.getMessage());
    }
  }
}
