package ntnu.idatt2003.group27.models;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import java.util.logging.Logger;
import ntnu.idatt2003.group27.models.enums.LadderGameType;
import ntnu.idatt2003.group27.models.enums.MathGameType;
import ntnu.idatt2003.group27.models.interfaces.GameConfiguration;

/**
 * A factory class responsible for creating {@link LadderBoardGame} instances with predefined
 * configurations or file-based configurations loaded from JSON files. Utilizes the Factory
 * Pattern to encapsulate game creation logic and promote extensibility.
 *
 * @author Iver Lindholm
 * @version 1.2
 * @since 1.0
 */
public class BoardGameFactory {
  /**
   * Logger instance for the {@link BoardGameFactory} class.
   * Used for logging informational messages and errors related to class operations.
   */
  private static final Logger logger = Logger.getLogger(BoardGameFactory.class.getName());

  /**
   * The {@link BoardFactory} used to create {@link Board} instances for the games.
   */
  private final BoardFactory boardFactory;

  /**
   * Constructs a {@code BoardGameFactory} with the specified {@link BoardFactory}.
   *
   * @param boardFactory The {@link BoardFactory} used to create game boards.
   */
  public BoardGameFactory(BoardFactory boardFactory) {
    logger.finer("Initializing BoardGameFactory");
    this.boardFactory = boardFactory;
  }

  /**
   * Creates a {@link LadderBoardGame} instance based on the specified {@link LadderGameType}. The game is
   * configured with a board and dice as defined by the associated {@link GameConfiguration}.
   *
   * @param ladderGameType The {@link LadderGameType} defining the game configuration.
   * @return A new {@link LadderBoardGame} instance configured with the specified game type.
   * @throws IOException if the {@code LadderGameType} is unknown.
   */
  public LadderBoardGame createLadderGame(LadderGameType ladderGameType) throws IOException {
    logger.fine("Creating LadderGame of type " + ladderGameType);
    if (ladderGameType.equals(LadderGameType.JSON)) {
      return createLadderGameFromJson("src/main/resources/boards/Board.Json");
    }

    GameConfiguration config = new LadderGameConfiguration(ladderGameType);
    Board board = boardFactory.createBoard(config.getTotalTiles(), config.getTileActions());
    return new LadderBoardGame(board, config.getNumberOfDice(), config.getNumberOfDieSides());
  }

  /**
   * Creates a {@link LadderBoardGame} instance based on a configuration loaded from a JSON file. The game
   * is configured with a board and dice as defined by the associated {@link GameConfiguration}.
   *
   * @param jsonPath The file path to the JSON configuration file.
   * @return A new {@link LadderBoardGame} instance configured with the specified JSON file.
   * @throws IOException if an error occurs while reading the JSON file.
   */
  public LadderBoardGame createLadderGameFromJson(String jsonPath) throws IOException {
    JsonLadderGameConfiguration config = null;

    try {
      config = new JsonLadderGameConfiguration(jsonPath);
    } catch (IOException e) {
      logger.severe("Error creating JSON LadderGameConfiguration: " + e.getMessage());
      throw new IOException(e.getMessage());
    }

    return new LadderBoardGame(config.getBoard(), config.getNumberOfDice(),
        config.getNumberOfDieSides());
  }

  /**
   * Creates a {@link MathBoardGame} instance based on the specified {@link MathGameType}. The game is
   *
   * @param mathGameType The {@link MathGameType} defining the game configuration.
   * @param players      The players participating in the game.
   * @return A new {@link MathBoardGame} instance configured with the specified game type and players.
   */
  public MathBoardGame createMathGame(MathGameType mathGameType, Player[] players) {
    GameConfiguration config = new MathGameConfiguration(mathGameType);

    List<Board> boards = new ArrayList<>();
    IntStream.range(0, players.length).forEach(i -> {
      Board board = boardFactory.createBoard(config.getTotalTiles(), config.getTileActions());
      boards.add(board);
    });
    return new MathBoardGame(boards, Arrays.stream(players).toList());
  }
}