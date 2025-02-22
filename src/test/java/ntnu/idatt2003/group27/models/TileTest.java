package ntnu.idatt2003.group27.models;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TileTest {
  @Test
  @DisplayName("Test that tile creation functions properly.")
  public void testCreateTile() {
    Tile tile = new Tile(0);
    Assertions.assertNotNull(tile);
  }

/*
    @Test
    @DisplayName("Test that creating a negative tile is invalid and handles exception.")
    public void testCreateTileWithNegativeTileId(){
        
    }
*/
}
