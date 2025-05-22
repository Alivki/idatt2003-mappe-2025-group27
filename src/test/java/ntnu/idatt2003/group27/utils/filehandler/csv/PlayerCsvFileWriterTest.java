package ntnu.idatt2003.group27.utils.filehandler.csv;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.opencsv.CSVWriter;
import java.io.IOException;
import java.io.StringWriter;
import javafx.scene.paint.Color;
import ntnu.idatt2003.group27.models.Piece;
import ntnu.idatt2003.group27.models.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Unit test for the {@link PlayerCsvFileWriter} class.
 *
 * <p>Verifies CSV serialization of player arrays</p>
 */
public class PlayerCsvFileWriterTest {
  private PlayerCsvFileWriter writer;
  private StringWriter stringWriter;
  private Player[] players;

  /**
   * Sets up test environment with a writer and sample player data.
   */
  @BeforeEach
  public void setUp() {
    writer = new PlayerCsvFileWriter() {
      @Override
      public void writeFile(String filePath, Player[] data) throws IOException {
        CSVWriter csvWriter = new CSVWriter(stringWriter);
        for (Player player : data) {
          String pieceName = player.getPiece() != null ? player.getPiece().getName() : "";
          String colorName = player.getColor() != null ? player.getColor().toString() : "#FFFFF";
          String[] playerInfo = {player.getName(), pieceName, colorName};
          csvWriter.writeNext(playerInfo);
        }
        csvWriter.close();
      }
    };
    stringWriter = new StringWriter();
    players = new Player[2];
    players[0] = new Player("Player1", new Piece("Hat", "path"), Color.RED);
    players[1] = new Player("Player2", new Piece("Car", "path"), Color.BLUE);
  }

  /**
   * Test to verify that the CSV writer correctly serializes player data for valid array.
   */
  @Test
  @DisplayName("Test CSV serialization of valid player array")
  public void testWriteValidPlayerArray() throws IOException {
    writer.writeFile("path", players);

    String csvOutput = stringWriter.toString();
    assertTrue(csvOutput.contains("\"Player1\",\"Hat\",\"0xff0000ff\""), "CSV output should contain Player1 data");
    assertTrue(csvOutput.contains("\"Player2\",\"Car\",\"0x0000ffff\""), "CSV output should contain Player2 data");
    String[] lines = csvOutput.split("\n");
    assertEquals(2, lines.length, "CSV should have two lines");
  }

  /**
   * Verifies that CSV file writer handles empty player array correctly.
   */
  @Test
  @DisplayName("should handle empty player array")
  public void testWriteEmptyPlayerArray() throws IOException {
    Player[] emptyPlayers = new Player[0];

    writer.writeFile("path", emptyPlayers);

    String csvOutput = stringWriter.toString();
    assertTrue(csvOutput.isEmpty(), "CSV output should be empty for empty player array");
  }

  /**
   * Verifies that CSV file writer handles null player array correctly.
   */
  @Test
  @DisplayName("should throw exception when writing null player array")
  public void testWriteNullPlayerArray() {
    Player[] nullPlayers = null;

    assertThrows(NullPointerException.class, () -> {
      writer.writeFile("path", nullPlayers);
    }, "Writing null player array should throw IOException");
  }
}
