package ntnu.idatt2003.group27.filehandler;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import java.io.IOException;
import java.util.List;
import ntnu.idatt2003.group27.models.Player;

public class PlayerCsvFileReader implements FileReader {
  /**
   * Reads the csv file from filepath and returns a player array containing all players.
   * The returned 2-dimensional string array is on the format [Player_name][Player_piece].
   * @param filePath .
   * @return Returns a Player array containing all players found in the csv file.
   * @throws IOException
   */
  @Override
  public String[][] readFile(String filePath) throws IOException {
    try (CSVReader reader = new CSVReader(new java.io.FileReader(filePath))) {
      List<String[]> contents = reader.readAll();

      String[][] playerData = new String[contents.size()][2];
      //Loop starts at i = 1 to skip the titles for each column in the csv file.
      for (int i = 1; i < contents.size(); i++) {
        String[] row = contents.get(i);
        playerData[i-1][0] = row[0];
        playerData[i-1][1] = row[1];
      }
      return playerData;
    }

    catch (IOException e){
      throw new IOException(e.getMessage());
    }

    catch (CsvException e) {
      throw new RuntimeException(e);
    }
  }
}
