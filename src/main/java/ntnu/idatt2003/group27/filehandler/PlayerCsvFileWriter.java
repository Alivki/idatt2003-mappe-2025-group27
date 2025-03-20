package ntnu.idatt2003.group27.filehandler;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.IntStream;
import ntnu.idatt2003.group27.models.Player;

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
      File playerCsvFile = createPlayerCsvFile(filePath);
      CSVWriter csvWriter = new CSVWriter(new FileWriter(playerCsvFile, true));
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
   * Creates a default players.csv file in the correct format.
   * @param filePath
   * @throws IOException
   */
  public File createPlayerCsvFile(String filePath) throws IOException {
    try{
      CSVWriter csvWriter = new CSVWriter(new FileWriter(filePath));
      String[] header = {"Player_Name", "Player_Piece"};
      csvWriter.writeNext(header);
      csvWriter.close();
      return new File(filePath);
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
      if (!playerCsvFile.exists()) {
        playerCsvFile = createPlayerCsvFile(filepath);
      }
      CSVWriter csvWriter = new CSVWriter(new FileWriter(playerCsvFile, true));
      String[] playerInfo = {player.getName(), player.getPiece()};
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
      PlayerCsvFileReader playerCsvFileReader = new PlayerCsvFileReader();
      Player[] players = playerCsvFileReader.readFile(filepath);
      ArrayList<Player> playerList = new ArrayList<>(Arrays.asList(players));
      playerList.remove(player);
      players = playerList.toArray(new Player[0]);
      writeFile(filepath, players);
    }
    catch (Exception e) {
      throw new IOException(e);
    }
  }
}

