package ntnu.idatt2003.group27.models;

import java.util.ArrayList;
import java.util.List;

public class BoardGame {
    private Board board;
    private Player currentPlayer;
    private ArrayList<Player> players = new ArrayList<Player>();
    private Dice dice;

    public void addPlayer(Player player){
        players.add(player);
    }

    public void createBoard(){
        
    }

    public void createDice(){
        dice = new Dice(1, 6);

    }

    public void play(){

    }

    public Player getWinner(){
        throw new UnsupportedOperationException("Not implemented yet.");
    }
}
