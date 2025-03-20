package ntnu.idatt2003.group27.models;

import java.io.IOException;
import ntnu.idatt2003.group27.filehandler.PlayerCsvFileReader;
import org.junit.jupiter.api.Test;

public class PlayerCsvFileReaderTest {
  /**
   * A test to read the Players.csv file.
   */
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

  /**
   * A test to read the Players_Test.csv file from the test directory.
   */
  @Test
  public void testReadPlayerTestCsvFile(){
    PlayerCsvFileReader reader = new PlayerCsvFileReader();
    try {
      Player[] players =
          reader.readFile("src/test/java/ntnu/idatt2003/group27/models/Players_Test.csv");
      for(int i = 0; i < players.length; i++){
        System.out.printf(players[i].getName() + "\n");
      }
    }
    catch (IOException e){
      System.out.println(e.getMessage());
    }
  }
}
