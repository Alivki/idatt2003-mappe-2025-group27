package ntnu.idatt2003.group27.models;

import java.io.IOException;
import java.util.stream.IntStream;
import ntnu.idatt2003.group27.utils.filehandler.csv.PlayerCsvFileWriter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class PlayerCsvFileWriterTest {
  private String filePath = "src/test/java/ntnu/idatt2003/group27/models/testFiles/Players_Write_Test.csv";
  private Piece[] pieces;
  private Player[] players;

  /**
   * Sets up piece data before running tests
   */
  @BeforeEach
  void setUp() {
    pieces = new Piece[5];
    pieces[0] = (new Piece("Car", "/icons/player_icons/jeep.png"));
    pieces[1] = (new Piece("Chicken", "/icons/player_icons/chicken.png"));
    pieces[2] = (new Piece("Frisbee", "/icons/player_icons/frisbee.png"));
    pieces[3] = (new Piece("Pawn", "/icons/player_icons/chess-pawn.png"));
    pieces[4] = (new Piece("Tophat", "/icons/player_icons/top-hat.png"));

    players = IntStream.range(0, 5)
        .mapToObj(i -> new Player("Player " + i, pieces[i], null))
        .toArray(Player[]::new);
  }

  @Test
  public void testWriteAllPlayersToCsvFile(){
    PlayerCsvFileWriter playerCsvFileWriter = new PlayerCsvFileWriter();

    System.out.println(players.length);
    for (Player player : players) {
      System.out.println(player.getName());
    }

    try {
      playerCsvFileWriter.writeFile(filePath,
          players);
    }
    catch (IOException e){
      System.out.println(e.getMessage());
    }
  }
}
