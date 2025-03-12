package ntnu.idatt2003.group27.filehandler;

import com.opencsv.exceptions.CsvException;
import java.io.FileReader;
import com.opencsv.CSVReader;
import java.io.IOException;
import java.util.List;

public class CsvFileReader {
  public void ReadFile(String filePath){
    try (CSVReader reader = new CSVReader(new FileReader(filePath))) {
      List<String[]> contents = reader.readAll();
      for (String[] row : contents) {
        for (int i = 0; i < row.length; i++) {
          System.out.println(row[i]);
        }
      }
    }

    catch (IOException e){
      System.out.println(e.getMessage());
    }

    catch (CsvException e) {
      throw new RuntimeException(e);
    }
  }
}
