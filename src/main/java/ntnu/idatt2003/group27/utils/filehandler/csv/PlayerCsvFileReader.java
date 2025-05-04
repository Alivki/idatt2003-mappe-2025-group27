package ntnu.idatt2003.group27.utils.filehandler.csv;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;
import ntnu.idatt2003.group27.models.Piece;
import ntnu.idatt2003.group27.models.Player;
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
  /** A list of pieces to be used when reading files */
  private Piece[] pieces;

  /**
   * Constructor to set pieces
   * @param pieces
   */
  public PlayerCsvFileReader(Piece[] pieces) {
    this.pieces = pieces;
  }

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
      System.out.println(contents.size());
      Player[] players = new Player[contents.size()];
      for (int i = 0; i < contents.size(); i++) {
        String playerName = contents.get(i)[0];
        if (!playerName.isBlank()) {
          if (contents.get(i).length > 1) {
            String pieceName = contents.get(i)[1];
            Piece piece = Arrays.stream(pieces)
                .filter(p -> p.getName().equals(pieceName))
                .findFirst()
                .orElse(null);
            if (piece == null) {
              piece = new Piece(pieceName, null);
              System.out.println("Piece instance not found, created new piece for: " + pieceName);
            }
            players[i] = new Player(playerName, piece, null);
          } else {
            players[i] = new Player(playerName);
          }
        }
      }

      reader.close();
      System.out.println("Successfully read players csv file from " + filePath);

      //Removes potential null elements from player array
      players = Arrays.stream(players)
          .filter(s -> s != null)
          .toArray(Player[]::new);

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
