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

    public void placeOnTile(Tile tile){
        
    }

    public void move(int steps){
        
    }
}
