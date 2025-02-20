package ntnu.idatt2003.group27.models;

/**
 * A class representing a player.
 */
public class Player {
    public String name;
    public Tile currentTile;
    private BoardGame game;

    public Player(String name, BoardGame game){
        this.name = name;
        this.game = game;
    }

    public String getName(){
        return name;
    }

    public Tile getCurrentTile(){
        return currentTile;
    }

    public void placeOnTile(Tile tile){
        if (currentTile == tile) {
            throw new IllegalArgumentException("Player already on this tile");
        }

        currentTile = tile;
    }

    public void move(int steps){
        
    }
}
