package ntnu.idatt2003.group27.filehandler;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import java.io.IOException;
import java.util.List;
import ntnu.idatt2003.group27.models.Player;

public class PlayerCsvFileReader implements CustomFileReader<Player[]> {
  /**
   * Reads the csv file from filepath and returns a player array containing all players.
   * @param filePath .
   * @return Returns a Player array containing all players found in the csv file.
   * @throws IOException
   */
  @Override
  public Player[] readFile(String filePath) throws IOException {
    try (CSVReader reader = new CSVReader(new java.io.FileReader(filePath))) {
      List<String[]> contents = reader.readAll();

      Player[] players = new Player[contents.size()];
      //Loop starts at i = 1 to skip the titles for each column in the csv file.
      for (int i = 1; i < contents.size()-1; i++) {
        String[] row = contents.get(i);
        Player newPlayer = new Player(row[0], null);
        players[i-1] = newPlayer;
      }
      return players;
    }

    catch (IOException e){
      throw new IOException(e.getMessage());
    }

    catch (CsvException e) {
      throw new RuntimeException(e);
    }
  }
}
