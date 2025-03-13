package ntnu.idatt2003.group27.models;

import java.io.IOException;
import ntnu.idatt2003.group27.filehandler.PlayerCsvFileReader;
import org.junit.jupiter.api.Test;

public class PlayerCsvFileReaderTest {
  @Test
  public void testReadPlayerCsvFile(){
    PlayerCsvFileReader reader = new PlayerCsvFileReader();
    try {
      String[][] playerData =
          reader.readFile("src/main/java/ntnu/idatt2003/group27/filehandler/Players.csv");
      for(int i = 0; i < playerData.length; i++){
        System.out.printf("%s %s\n",playerData[i][0], playerData[i][1]);
      }
    }
    catch (IOException e){
      System.out.println(e.getMessage());
    }
  }
}
