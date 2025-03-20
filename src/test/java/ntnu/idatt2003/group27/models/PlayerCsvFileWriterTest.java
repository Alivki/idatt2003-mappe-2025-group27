package ntnu.idatt2003.group27.models;

import java.io.IOException;
import java.util.stream.IntStream;
import ntnu.idatt2003.group27.filehandler.PlayerCsvFileWriter;
import org.junit.jupiter.api.Test;

public class PlayerCsvFileWriterTest {
  @Test
  public void testPlayerCsvWriteFile(){
    PlayerCsvFileWriter playerCsvFileWriter = new PlayerCsvFileWriter();
    Player[] players = IntStream.range(1, 11)
            .mapToObj(i -> new Player("Player " + i, null))
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
}
