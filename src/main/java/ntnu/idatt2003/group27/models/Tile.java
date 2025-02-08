package ntnu.idatt2003.group27.models;

import scala.tools.nsc.interactive.Replayer;

public class Tile {
    public Tile nextTile;
    public int tileId;
    public TileAction landAction;

    public Tile(int tileId){
        this.tileId = tileId;
    }

    public void landPlayer(Replayer player) {

    }

    public void leavePlayer(Player player){
        
    }

    public void setNextTile(Tile nextTile){

    }
}
