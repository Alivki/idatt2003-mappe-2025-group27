package ntnu.idatt2003.group27.models;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

import ntnu.idatt2003.group27.models.actions.ThrowNewDiceAction;
import ntnu.idatt2003.group27.models.enums.LadderGameType;
import ntnu.idatt2003.group27.models.interfaces.GameConfiguration;
import ntnu.idatt2003.group27.utils.filehandler.json.JsonFileReader;
import ntnu.idatt2003.group27.models.actions.BackToStartAction;
import ntnu.idatt2003.group27.models.actions.LadderAction;
import ntnu.idatt2003.group27.models.interfaces.TileAction;
import ntnu.idatt2003.group27.models.exceptions.UnknownLadderGameTypeExceptions;

/**
 * A factory class responsible for creating {@link BoardGame} instances with predefined
 * configurations or file-based configurations loaded from JSON files. Utilizes the Factory
 * Pattern to encapsulate game creation logic and promote extensibility.
 *
 * @author Iver Lindholm
 * @version 1.2
 * @since 1.0
 */
public class BoardGameFactory {
  /** The {@link BoardFactory} used to create {@link Board} instances for the games. */
  private final BoardFactory boardFactory;

  /**
   * Constructs a {@code BoardGameFactory} with the specified {@link BoardFactory}.
   *
   * @param boardFactory The {@link BoardFactory} used to create game boards.
   */
  public BoardGameFactory(BoardFactory boardFactory) {
    this.boardFactory = boardFactory;
  }

  /**
   * Craetes a {@link BoardGame} instace based on the specified {@link LadderGameType}. The game is
   * configured with a board and dice as defined by the associated {@link GameConfiguration}.
   *
   * @param ladderGameType The {@link LadderGameType} defining the game configuration.
   * @return A new {@link BoardGame} instance configured with the specified game type.
   * @throws IllegalArgumentException if the {@code LadderGameType} is unknown.
   */
  public BoardGame createLadderGame(LadderGameType ladderGameType) {
    GameConfiguration config = null;
    if (ladderGameType.equals(LadderGameType.JSON)){
      try {
        config = new JsonLadderGameConfiguration("src/main/resources/boards/Board.Json");
      }
      catch (IOException e){
        System.out.println("Error creating JSON LadderGameConfiguration: " + e.getMessage());
      }
    }
    else{
      config = new LadderGameConfiguration(ladderGameType);
    }
    Board board = boardFactory.createBoard(config.getTotalTiles(), config.getTileActions());
    return new BoardGame(board, config.getNumberOfDice(), config.getNumberOfDieSides());
  }

  /**
   * Creates a {@link BoardGame} instace based on a configuration loaded from a JSON file. The game
   * is configured with a board and dice as defined by the associated {@link GameConfiguration}.
   *
   * @param jsonPath The file path to the JSON configuration file.
   * @return A new {@link BoardGame} instance configured with the specified JSON file.
   * @throws IOException if an error occurs while reading the JSON file.
   */
  public BoardGame createLadderGameFromJson(String jsonPath) throws IOException {
    JsonLadderGameConfiguration config = null;

    try {
      config = new JsonLadderGameConfiguration(jsonPath);
    } catch (IOException e) {
      throw new IOException(e.getMessage());
    }

    return new BoardGame(config.getBoard(), config.getNumberOfDice(), config.getNumberOfDieSides());
  }
}