package ntnu.idatt2003.group27.models;

import java.io.IOException;
import ntnu.idatt2003.group27.filehandler.CsvFileReader;
import org.junit.jupiter.api.Test;

public class CsvFileReaderTest {
  @Test
  public void testReadCsvFile(){
    CsvFileReader reader = new CsvFileReader();
    reader.ReadFile("src/main/java/ntnu/idatt2003/group27/filehandler/Players.csv");

  }
}
