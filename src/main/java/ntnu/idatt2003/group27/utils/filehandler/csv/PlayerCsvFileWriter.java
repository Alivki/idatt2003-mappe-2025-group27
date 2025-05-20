package ntnu.idatt2003.group27.utils.filehandler.csv;

import com.opencsv.CSVWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Logger;
import ntnu.idatt2003.group27.models.Player;
import ntnu.idatt2003.group27.utils.filehandler.interfaces.CustomFileWriter;

/**
 * A class for writing Players.csv files.
 * Uses OpenCsv for parsing of csv file data.
 *
 * @author Iver Lindholm, Amadeus Berg
 * @since 0.0
 * @version 1.0
 */
public class PlayerCsvFileWriter implements CustomFileWriter<Player[]> {

  /** Logger instance for the {@link PlayerCsvFileWriter} class. */
  private static final Logger logger = Logger.getLogger(PlayerCsvFileWriter.class.getName());

  /**
   * Writes data of all players from given Player array to file at given path.
   *
   * @param filePath The path to the file where data should be written.
   * @param data The array of {@link Player} objects to write.
   * @throws IOException If an I/O error occurs during writing.
   */
  @Override
  public void writeFile(String filePath, Player[] data) throws IOException {
    logger.info("Attempting to write player data to CSV file at: " + filePath);

    try {
      File playerCsvFile = new File(filePath);
      CSVWriter csvWriter = new CSVWriter(new FileWriter(playerCsvFile, false));

      for (Player player : data) {
        String pieceName = player.getPiece() != null ? player.getPiece().getName() : "";
        String colorName = player.getColor() != null ? player.getColor().toString() : "#FFFFF";
        String[] playerInfo = {player.getName(), pieceName, colorName};
        csvWriter.writeNext(playerInfo);
        logger.fine("Wrote player to CSV: " + player.getName());
      }

      csvWriter.close();
      logger.info("Successfully wrote CSV file to: " + playerCsvFile.getAbsolutePath());

    } catch (Exception e) {
      logger.severe("Failed to write CSV file: " + e.getMessage());
      throw new IOException(e);
    }
  }
}
