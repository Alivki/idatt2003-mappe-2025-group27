package ntnu.idatt2003.group27.models;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class PlayerTest {

    @Test
    @DisplayName("Test that creating a new player works.")
    public void testCreatePlayer(){
        BoardGame game = new BoardGame(90, 1, 6);
        Player player = new Player("Player_1", game);
        Assertions.assertTrue(player != null);
    }

    @Test
    @DisplayName("Test that placeOnTile method functions properly.")
    public void testPlaceOnTile(){
        BoardGame game = new BoardGame(90, 1, 6);
        Player player = new Player("Player_1", game);
        Tile tile = new Tile(0);
        player.placeOnTile(tile);
        Assertions.assertTrue(player.getCurrentTile() == tile);

    }

    @Test
    @DisplayName("Test that player Move method function properly.")
    public void testMove(){
        Board board = new Board(90);
        BoardGame game = new BoardGame(90, 1, 6);
        Player player = new Player("Player_1", game);
        game.addPlayer(player);
        game.setUpGame();
        player.move(2);
    }
}
