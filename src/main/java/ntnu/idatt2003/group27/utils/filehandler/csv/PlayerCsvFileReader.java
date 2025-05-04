package ntnu.idatt2003.group27.utils.filehandler.csv;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import javafx.scene.paint.Color;
import ntnu.idatt2003.group27.models.Piece;
import ntnu.idatt2003.group27.models.Player;
import ntnu.idatt2003.group27.models.exceptions.ToManyPlayersInGameException;
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
   *
   * @param filePath .
   * @return Returns a Player array containing all players found in the csv file.
   * @throws IOException
   */
  @Override
  public Player[] readFile(String filePath) throws IOException {
    try (CSVReader reader = new CSVReader(new FileReader(filePath))) {
      List<String[]> contents = reader.readAll();

      //Loop starts at i = 1 to skip the titles for each column in the csv file.
      System.out.println(contents.size());

      if (contents.size() == 1) {
        //TODO: custom error
        throw new IllegalArgumentException("No players found in the csv file");
      }

      if (contents.size() > 5) {
        //TODO: custom error
        throw new ToManyPlayersInGameException("There are over 5 players in the game");
      }

      List<Player> playerList = contents.stream().map(content -> {
            String playerName = content[0];

            if (playerName.isBlank()) {
              return null;
            }

            if (content.length < 2) {
              //TODO: throw error for no piece for player
              System.out.println("Player does not have a piece");
              throw new IllegalArgumentException("Player " + playerName + " does not have a piece");
            }

            String pieceName = content[1];
            Color color = null;

            if (content.length < 3 || content[2].isEmpty()) {
              color = RandomColor.generateRandomColor().getColor();
            } else {
              try {
                color = Color.web(content[2]);
              } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Invalid color format for player " + playerName + ": " + content[2], e);
              }
            }

            Piece piece = Arrays.stream(pieces)
                .filter(p -> p.getName().equals(pieceName))
                .findFirst()
                .orElse(null);

            if (piece == null) {
              // TODO: trow custom exception and log it. maybe do not create a new piece?
              throw new IllegalArgumentException("Invalid piece name for player " + playerName);
            }

            return new Player(playerName, piece, color);
          })
          .filter(Objects::nonNull)
          .toList();

      Player[] players = playerList.toArray(new Player[0]);

      reader.close();
      System.out.println("Successfully read players csv file from " + filePath);

      return players;
    }

    catch (IOException e){
      throw new IOException(e.getMessage());
    }

    catch (CsvException | ToManyPlayersInGameException e) {
      throw new RuntimeException(e);
    }
  }
}
