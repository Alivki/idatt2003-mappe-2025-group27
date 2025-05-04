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
        String[] playerInfo = {player.getName(), player.getPiece().getName()};
        csvWriter.writeNext(playerInfo);
      }
      csvWriter.close();
      System.out.println("Wrote CSV file to " + playerCsvFile.getAbsolutePath());
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
      File playerCsvFile = new File(filepath);
      CSVWriter csvWriter = new CSVWriter(new FileWriter(playerCsvFile, true));
      String[] playerInfo = {player.getName(), player.getPiece().getName()};
      csvWriter.writeNext(playerInfo);
      csvWriter.close();
    }
    catch (Exception e) {
      throw new IOException(e);
    }
  }

  /**
   * Removes the specified player from the specified file.
   * @param filepath
   * @param player
   * @throws IOException
   */
  public void removePlayerFromFile(String filepath, Player player) throws IOException, FileNotFoundException {
    File playerCsvFile = new File(filepath);
    if (!playerCsvFile.exists()) {
      throw new FileNotFoundException("File not found");
    }

    try{
      PlayerCsvFileReader playerCsvFileReader = new PlayerCsvFileReader(null);
      Player[] players = playerCsvFileReader.readFile(filepath);
      List<Player> updatedPlayersList = Arrays.stream(players)
          .filter(p -> !p.getName().equals(player.getName()))
          .collect(Collectors.toList());

      Player[] updatedPlayers = updatedPlayersList.toArray(new Player[0]);
      for(Player p : updatedPlayers) {
        System.out.println(p.getName());
      }
      writeFile(filepath, updatedPlayers);
    }
    catch (Exception e) {
      throw new IOException(e);
    }
  }
}

