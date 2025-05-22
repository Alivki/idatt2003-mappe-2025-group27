package ntnu.idatt2003.group27.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import java.io.IOException;
import java.io.StringReader;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;
import javafx.scene.paint.Color;
import ntnu.idatt2003.group27.models.exceptions.ExceededMaxPlayersException;
import ntnu.idatt2003.group27.models.exceptions.MissingPlayerException;
import ntnu.idatt2003.group27.utils.RandomColor;
import ntnu.idatt2003.group27.utils.filehandler.csv.PlayerCsvFileReader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Unit test for the {@link PlayerCsvFileReader} class.
 *
 * <p>Verifies CSV deserialization of player arrays.</p>
 */
public class PlayerCsvFileReaderTest {
  private PlayerCsvFileReader reader;
  private Piece[] pieces;

  /**
   * Sets up test environment with reader and sample peices.
   */
  @BeforeEach
  void setUp() {
    pieces =
        new Piece[] {
            new Piece("Hat", "path"),
            new Piece("Car", "path"),
            new Piece("Dog", "path")
    };

    reader = new PlayerCsvFileReader(pieces) {
      @Override
      public Player[] readFile(String filePath) throws IOException {
        try (CSVReader csvReader = new CSVReader(new StringReader(filePath))) {
          List<String[]> contents = csvReader.readAll();
          if (contents.isEmpty()) {
            throw new MissingPlayerException("No players found in the csv file");
          }
          List<Player> playerList = contents.stream().map(content -> {
            String playerName = content[0];
            if (playerName.isBlank()) {
              return null;
            }
            if (content.length < 2) {
              throw new IllegalArgumentException("Player " + playerName + " does not have a piece");
            }
            String pieceName = content[1];
            Color color = content.length < 3 || content[2].isEmpty() ?
                RandomColor.generateRandomColor().getColor() :
                Color.web(content[2]);
            Piece piece = Arrays.stream(pieces)
                .filter(p -> p.getName().equals(pieceName))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid piece name for player " + playerName));
            return new Player(playerName, piece, color);
          }).filter(Objects::nonNull).toList();
          Player[] players = playerList.toArray(new Player[0]);
          if (players.length > 5) {
            throw new ExceededMaxPlayersException("There are more than the allowed number of players in the csv file!");
          }
          return players;
        } catch (CsvException e) {
          throw new RuntimeException(e.getMessage());
        } catch (ExceededMaxPlayersException e) {
          throw new RuntimeException(e);
        }
      }
    };
  }

  /**
   * Verifies that a valid CSV with players is correctly deserialized.
   */
  @Test
  @DisplayName("test reading valid player CSV")
  public void testReadValidPlayerCsv() throws IOException {
    String csv = "Alice,Hat,RED\nBob,Car,BLUE";

    Player[] players = reader.readFile(csv);

    assertEquals(2, players.length, "Should return two players");
    assertEquals("Alice", players[0].getName(), "First player name should be Alice");
    assertEquals("Hat", players[0].getPiece().getName(), "First player piece should be Hat");
    assertEquals(Color.RED, players[0].getColor(), "First player color should be RED");
    assertEquals("Bob", players[1].getName(), "Second player name should be Bob");
    assertEquals("Car", players[1].getPiece().getName(), "Second player piece should be Car");
    assertEquals(Color.BLUE, players[1].getColor(), "Second player color should be BLUE");
  }

  /**
   * Verifies that an empty CSV throws MissingPlayerException.
   */
  @Test
  @DisplayName("should throw exception for empty CSV")
  public void testReadEmptyCsv() {
    String csv = "";

    assertThrows(MissingPlayerException.class, () -> reader.readFile(csv), "Should throw MissingPlayerException for empty CSV");
  }

  /**
   * Verifies that a CSV with a blank player name skips the player.
   */
  @Test
  @DisplayName("should skip player with blank name")
  public void testReadCsvWithBlankPlayerName() throws IOException {
    String csv = ",Hat,RED\nBob,Car,BLUE";

    Player[] players = reader.readFile(csv);

    assertEquals(1, players.length, "Should skip blank player name and return one player");
    assertEquals("Bob", players[0].getName(), "Player name should be Bob");
  }

  /**
   * Verifies that a CSV with missing piece throws IllegalArgumentException.
   */
  @Test
  @DisplayName("should throw exception for missing piece")
  public void testReadCsvWithMissingPiece() {
    String csv = "Alice,,";

    assertThrows(IllegalArgumentException.class, () -> reader.readFile(csv), "Should throw IllegalArgumentException for missing piece");
  }

  /**
   * Verifies that a CSV with invalid piece name throws IllegalArgumentException.
   */
  @Test
  @DisplayName("should throw exception for invalid piece name")
  public void testReadCsvWithInvalidPiece() {
    String csv = "Alice,Boat,RED";

    assertThrows(IllegalArgumentException.class, () -> reader.readFile(csv), "Should throw IllegalArgumentException for invalid piece name");
  }

  /**
   * Verifies that a CSV with invalid color format throws IllegalArgumentException.
   */
  @Test
  @DisplayName("should throw exception for invalid color format")
  public void testReadCsvWithInvalidColor() {
    String csv = "Alice,Hat,INVALID_COLOR";

    assertThrows(IllegalArgumentException.class, () -> reader.readFile(csv), "Should throw IllegalArgumentException for invalid color format");
  }

  /**
   * Verifies that a CSV with missing color assigns a random color.
   */
  @Test
  @DisplayName("test assigning random color for missing color")
  public void testReadCsvWithMissingColor() throws IOException {
    String csv = "Alice,Hat,";

    Player[] players = reader.readFile(csv);

    assertEquals(1, players.length, "Should return one player");
    assertEquals("Alice", players[0].getName(), "Player name should be Alice");
    assertEquals("Hat", players[0].getPiece().getName(), "Player piece should be Hat");
    assertNotNull(players[0].getColor(), "Player should have a random color");
  }
}
