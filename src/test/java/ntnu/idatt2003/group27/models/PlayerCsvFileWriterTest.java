package ntnu.idatt2003.group27.models;

import java.io.IOException;
import java.util.stream.IntStream;
import ntnu.idatt2003.group27.utils.filehandler.csv.PlayerCsvFileWriter;
import org.junit.jupiter.api.Test;


public class PlayerCsvFileWriterTest {
  /**
   * A test for writing csv files.
   */
  @Test
  public void testPlayerCsvWriteFile(){
    PlayerCsvFileWriter playerCsvFileWriter = new PlayerCsvFileWriter();
    Player[] players = IntStream.range(1, 6)
            .mapToObj(i -> new Player("Player " + i))
            .toArray(Player[]::new);

    System.out.println(players.length);
    for (Player player : players) {
      System.out.println(player.getName());
    }

    try {
      playerCsvFileWriter.writeFile("src/test/java/ntnu/idatt2003/group27/models/Players_Test.csv",
          players);
    }
    catch (IOException e){
      System.out.println(e.getMessage());
    }
  }

  @Test
  public void testWritePlayerToCsvFile(){
    PlayerCsvFileWriter playerCsvFileWriter = new PlayerCsvFileWriter();
    Player player = new Player("WritePlayerToCsvFile_TestPlayer");
    try {
      playerCsvFileWriter.writePlayerToFile(
          "src/test/java/ntnu/idatt2003/group27/models/Players_Test.csv", player);
    }
    catch (IOException e){
      System.out.println(e.getMessage());
    }
  }

  @Test
  public void testRemovePlayerFromCsvFile(){
    PlayerCsvFileWriter playerCsvFileWriter = new PlayerCsvFileWriter();
    Player player = new Player("WritePlayerToCsvFile_TestPlayer");
    try{
      playerCsvFileWriter.removePlayerFromFile("src/test/java/ntnu/idatt2003/group27/models/Players_Test.csv", player);
    }
    catch (Exception e){
      System.out.println(e.getMessage());
    }
  }
}
