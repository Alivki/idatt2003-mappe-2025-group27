package ntnu.idatt2003.group27.models;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import ntnu.idatt2003.group27.models.enums.LadderGameType;
import ntnu.idatt2003.group27.models.enums.MathGameType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for the {@link BoardGameFactory} class.
 *
 * <p>Verifies creation of LadderBoardGame and MathBoardGame instances using various configurations.</p>
 *
 * @author Amadeus Berg, Iver Lindholm
 */
public class BoardGameFactoryTest {

  private BoardFactory boardFactory;
  private BoardGameFactory boardGameFactory;

  /**
   * Initializes the BoardGameFactory with a BoardFactory before each test.
   */
  @BeforeEach
  public void setUp() {
    boardFactory = new BoardFactory();
    boardGameFactory = new BoardGameFactory(boardFactory);
  }

  /**
   * Verifies that a LadderBoardGame is created correctly for LadderGameType NORMAL.
   */
  @Test
  @DisplayName("should create LadderBoardGame from LadderGameType NORMAL")
  public void testCreateLadderGameFromLadderGameTypeNormal() throws IOException {
    LadderBoardGame game = boardGameFactory.createLadderGame(LadderGameType.NORMAL);
    assertNotNull(game, "Game should not be null");
    assertNotNull(game.getBoards(), "Game boards should not be null");
    assertNotNull(game.getDice(), "Game dice should not be null");
  }

  /**
   * Verifies that a LadderBoardGame is created correctly for LadderGameType CRAZY.
   */
  @Test
  @DisplayName("should create LadderBoardGame from LadderGameType CRAZY")
  public void testCreateLadderGameFromLadderGameTypeCrazy() throws IOException {
    LadderBoardGame game = boardGameFactory.createLadderGame(LadderGameType.CRAZY);
    assertNotNull(game, "Game should not be null");
    assertNotNull(game.getBoards(), "Game boards should not be null");
    assertNotNull(game.getDice(), "Game dice should not be null");
  }

  /**
   * Verifies that a LadderBoardGame is created correctly for LadderGameType IMPOSSIBLE.
   */
  @Test
  @DisplayName("should create LadderBoardGame from LadderGameType IMPOSSIBLE")
  public void testCreateLadderGameFromLadderGameTypeImpossible() throws IOException {
    LadderBoardGame game = boardGameFactory.createLadderGame(LadderGameType.IMPOSSIBLE);
    assertNotNull(game, "Game should not be null");
    assertNotNull(game.getBoards(), "Game boards should not be null");
    assertNotNull(game.getDice(), "Game dice should not be null");
  }

  /**
   * Verifies that a LadderBoardGame is created correctly from a JSON configuration file.
   */
  @Test
  @DisplayName("should create LadderBoardGame from JSON file")
  public void testCreateLadderGameFromJson() throws IOException {
    LadderBoardGame game = boardGameFactory.createLadderGameFromJson("src/main/resources/boards/Board.json");

    assertNotNull(game, "Game should not be null");
    assertNotNull(game.getBoards(), "Game boards should not be null");
    assertNotNull(game.getDice(), "Game dice should not be null");
  }

  /**
   * Verifies that a LadderBoardGame throws an exception when created from an invalid JSON file path.
   */
  @Test
  @DisplayName("should throw exception for invalid JSON file path")
  public void testCreateLadderGameFromInvalidJson() {
    assertThrows(IOException.class, () -> boardGameFactory.createLadderGameFromJson("incorrect/path/to/Board.json"));
  }

  /**
   * Verifies that a MathBoardGame is created correctly for multiple players.
   */
  @Test
  @DisplayName("should create MathBoardGame with one board per player")
  public void testCreateMathGame() {
    Player[] players = new Player[] {
        new Player("Alice"),
        new Player("Bob")
    };

    MathBoardGame game = boardGameFactory.createMathGame(MathGameType.EASY, players);

    assertNotNull(game, "Game should not be null");
    assertEquals(2, game.getBoards().size(), "Each player should have their own board");
    assertEquals(2, game.getPlayers().size(), "Game should have 2 players");
  }

  /**
   * Verifies that creating a LadderBoardGame with a null LadderGameType throws an exception.
   */
  @Test
  @DisplayName("should throw exception for null LadderGameType")
  public void testCreateLadderGameWithNullType() {
    assertThrows(NullPointerException.class, () -> boardGameFactory.createLadderGame(null));
  }

  /**
   * Verifies that creating a LadderBoardGame with an unsupported LadderGameType throws an exception.
   */
  @Test
  @DisplayName("should throw exception for unsupported LadderGameType")
  public void testUnsupportedLadderGameType() {
    assertThrows(IllegalArgumentException.class, () -> boardGameFactory.createLadderGame(LadderGameType.valueOf("UNSUPPORTED")));
  }

}
