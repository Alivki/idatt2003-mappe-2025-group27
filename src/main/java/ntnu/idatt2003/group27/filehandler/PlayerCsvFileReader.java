package ntnu.idatt2003.group27.filehandler;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import java.io.IOException;
import java.util.List;
import java.util.stream.IntStream;
import ntnu.idatt2003.group27.models.Player;

/**
 * A class for reading Players.csv files.
 *
 * @author Iver Lindholm, Amadeus Berg
 * @since 0.0
 * @version 1.0
 */
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

      //Loop starts at i = 1 to skip the titles for each column in the csv file.
      Player[] players = IntStream.range(1, contents.size())
          .mapToObj(i -> new Player(contents.get(i)[0]))
          .toArray(Player[]::new);
      reader.close();
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
