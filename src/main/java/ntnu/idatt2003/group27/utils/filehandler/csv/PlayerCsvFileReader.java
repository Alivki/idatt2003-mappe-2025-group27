package ntnu.idatt2003.group27.utils.filehandler.csv;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;
import javafx.scene.paint.Color;
import ntnu.idatt2003.group27.models.Piece;
import ntnu.idatt2003.group27.models.Player;
import ntnu.idatt2003.group27.models.exceptions.ExceededMaxPlayersException;
import ntnu.idatt2003.group27.models.exceptions.MissingPlayerException;
import ntnu.idatt2003.group27.utils.filehandler.RandomColor;
import ntnu.idatt2003.group27.utils.filehandler.interfaces.CustomFileReader;

/**
 * A class for reading Players.csv files.
 * Uses OpenCsv for parsing of csv file data.
 *
 * @author Iver Lindholm, Amadeus Berg
 * @since 0.0
 * @version 1.0
 */
public class PlayerCsvFileReader implements CustomFileReader<Player[]> {

  /** Logger instance for the {@code PlayerCsvFileReader} class. */
  private static final Logger logger = Logger.getLogger(PlayerCsvFileReader.class.getName());

  /** A list of pieces to be used when reading files */
  private Piece[] pieces;

  /**
   * Constructor to set pieces
   * @param pieces The array of {@link Piece} objects available for assignment to players.
   */
  public PlayerCsvFileReader(Piece[] pieces) {
    this.pieces = pieces;
  }

  /**
   * Reads the csv file from filepath and returns a player array containing all players.
   *
   * @param filePath The path to the CSV file.
   * @return Returns a Player array containing all players found in the csv file.
   * @throws IOException If an I/O error occurs while reading the file.
   */
  @Override
  public Player[] readFile(String filePath) throws IOException {
    logger.info("Reading player CSV file from: " + filePath);

    try (CSVReader reader = new CSVReader(new FileReader(filePath))) {
      List<String[]> contents = reader.readAll();
      logger.fine("CSV file read successfully. Number of entries: " + contents.size());

      if (contents.isEmpty()) {
        logger.warning("CSV file is empty.");
        throw new MissingPlayerException("No players found in the csv file");
      }

      List<Player> playerList = contents.stream().map(content -> {
            String playerName = content[0];

            if (playerName.isBlank()) {
              logger.warning("Encountered blank player name.");
              return null;
            }

            if (content.length < 2) {
              logger.severe("Player " + playerName + " does not have a piece.");
              throw new IllegalArgumentException("Player " + playerName + " does not have a piece");
            }

            String pieceName = content[1];
            Color color;

            if (content.length < 3 || content[2].isEmpty()) {
              color = RandomColor.generateRandomColor().getColor();
              logger.fine("Generated random color for player: " + playerName);
            } else {
              try {
                color = Color.web(content[2]);
              } catch (IllegalArgumentException e) {
                logger.severe("Invalid color format for player " + playerName + ": " + content[2]);
                throw new IllegalArgumentException("Invalid color format for player " + playerName + ": " + content[2]);
              }
            }

            Piece piece = Arrays.stream(pieces)
                .filter(p -> p.getName().equals(pieceName))
                .findFirst()
                .orElse(null);

            if (piece == null) {
              logger.severe("Invalid piece name for player " + playerName);
              throw new IllegalArgumentException("Invalid piece name for player " + playerName);
            }

            logger.fine("Created player: " + playerName);
            return new Player(playerName, piece, color);
          })
          .filter(Objects::nonNull)
          .toList();

      Player[] players = playerList.toArray(new Player[0]);

      if (players.length > 5) {
        logger.warning("Exceeded maximum number of players: " + players.length);
        throw new ExceededMaxPlayersException("There are more than the allowed number of players in the csv file!");
      }

      logger.info("Successfully parsed " + players.length + " players from CSV.");
      return players;

    } catch (IOException e) {
      logger.severe("IOException occurred while reading file: " + e.getMessage());
      throw new IOException(e.getMessage());
    } catch (CsvException | ExceededMaxPlayersException e) {
      logger.severe("Error parsing CSV file: " + e.getMessage());
      throw new RuntimeException(e.getMessage());
    }
  }
}
