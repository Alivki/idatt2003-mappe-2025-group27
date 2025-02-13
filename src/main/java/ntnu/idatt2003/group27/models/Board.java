package ntnu.idatt2003.group27.models;

import java.util.Map;

/*
 * A class representing a game board with a number of tiles.
 */
public class Board {
    public Map<Integer, Tile> tiles;

    public void addTile(){

    }

    public Tile getTile(int tileId){
        return tiles.get(tileId);
    }
}
