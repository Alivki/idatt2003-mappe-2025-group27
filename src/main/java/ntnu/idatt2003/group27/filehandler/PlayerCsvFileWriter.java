package ntnu.idatt2003.group27.filehandler;

import com.opencsv.CSVWriter;
import com.opencsv.bean.CsvToBean;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Optional;
import ntnu.idatt2003.group27.models.Player;

/**
 * A class for writing Players.csv files.
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
      CSVWriter csvWriter = new CSVWriter(new FileWriter(filePath));
      String[] header = {"Player_Name", "Player_Piece"};
      csvWriter.writeNext(header);
      for (Player player : data) {
        String[] playerInfo = {player.getName(), player.getPiece()};
        csvWriter.writeNext(playerInfo);
      }
      csvWriter.close();
    } catch (Exception e) {
      throw new IOException(e);
    }
  }

  /**
   * Write the specified player to the csv file.
   * @param filepath
   * @param player
   * @throws IOException
   */
  public void writePlayerToFile(String filepath, Player player) throws IOException{
    try{
      CSVWriter csvWriter = new CSVWriter(new FileWriter(filepath));
      String[] playerInfo = {player.getName(), player.getPiece()};
      csvWriter.writeNext(playerInfo);
      csvWriter.close();
    }
    catch (Exception e) {
      throw new IOException(e);
    }
  }
}

