package ntnu.idatt2003.group27.utils.filehandler.csv;

import com.opencsv.CSVWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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

  /**
   * Writes data of all players from given Player array to file at given path.
   * @param filePath .
   * @param data
   * @return Player[] The player array that was written. Null if none.
   * @throws IOException
   */
  @Override
  public void writeFile(String filePath, Player[] data) throws IOException {
    try{
      File playerCsvFile = new File(filePath);
      CSVWriter csvWriter = new CSVWriter(new FileWriter(playerCsvFile, false));
      for (Player player : data) {
        String pieceName = player.getPiece() != null? player.getPiece().getName() : "";
        String colorName = player.getColor() != null? player.getColor().toString() : "#FFFFF";
        String[] playerInfo = {player.getName(), pieceName, colorName};
        csvWriter.writeNext(playerInfo);
      }
      csvWriter.close();
      System.out.println("Wrote CSV file to " + playerCsvFile.getAbsolutePath());
    } catch (Exception e) {
      throw new IOException(e);
    }
  }
}

