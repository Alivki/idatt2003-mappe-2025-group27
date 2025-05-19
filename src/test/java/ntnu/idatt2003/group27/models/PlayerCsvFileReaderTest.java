package ntnu.idatt2003.group27.models;

import java.io.IOException;
import ntnu.idatt2003.group27.utils.filehandler.csv.PlayerCsvFileReader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PlayerCsvFileReaderTest {
  private String filePath = "src/test/java/ntnu/idatt2003/group27/models/testFiles/Players_Read_Test.csv";
  private Piece[] pieces;

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
  }

  /**
   * A test to read the Players.csv file.
   */
  @Test
  public void testReadPlayerCsvFile(){

    PlayerCsvFileReader reader = new PlayerCsvFileReader(pieces);
    try {
      Player[] players =
          reader.readFile(filePath);
      for(int i = 0; i < players.length; i++){
        System.out.printf(players[i].getName() + ", " + players[i].getPiece().getName() + "\n");
      }
    }
    catch (IOException e){
      System.out.println(e.getMessage());
    }
    catch (Exception e){
      System.out.println(e.getMessage());
    }
  }

  /**
   * A test to read player csv files.
   */
  @Test
  public void testReadPlayerTestCsvFile(){

    PlayerCsvFileReader reader = new PlayerCsvFileReader(pieces);
    try {
      Player[] players =
          reader.readFile(filePath);
      for(int i = 0; i < players.length; i++){
        System.out.printf(players[i].getName() + "\n");
      }
    }
    catch (IOException e){
      System.out.println(e.getMessage());
    }
  }
}
