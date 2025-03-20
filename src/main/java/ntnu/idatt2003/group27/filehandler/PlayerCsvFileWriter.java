package ntnu.idatt2003.group27.filehandler;

import com.opencsv.CSVWriter;
import com.opencsv.bean.CsvToBean;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Optional;
import ntnu.idatt2003.group27.models.Player;

public class PlayerCsvFileWriter implements CustomFileWriter<Player[]> {
  @Override
  public Player[] writeFile(String filePath, Object data) throws IOException {
    if (data instanceof Player[]) {
      Player[] players = (Player[]) data;
      try{
        CSVWriter csvWriter = new CSVWriter(new FileWriter(filePath));
        String[] header = {"Player_Name", "Player_Piece"};
        csvWriter.writeNext(header);
        for (Player player : players) {
          String[] playerInfo = {player.getName(), player.getPiece()};
          csvWriter.writeNext(playerInfo);
        }
        csvWriter.close();
        return players;
      } catch (Exception e) {
        throw new IOException(e);
      }
    }

    return null;
  }
}
