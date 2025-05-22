package ntnu.idatt2003.group27.models.interfaces;

import ntnu.idatt2003.group27.models.Player;
import ntnu.idatt2003.group27.models.exceptions.WrongMathAnswerException;

/**
 * Defines a contract for custom actions that can be executed when a player interacts with a
 * tile on the game board. Implementations of this interface specify behavior triggered by
 * a player's presence on a tile in the math game.
 *
 * @author Iver Lindholm
 * @version 1.0
 * @since 2.0
 */
public interface MathTileAction extends TileAction {
  /**
   * Generates a math question for the player to answer.
   */
  void generateQuestion();

  /**
   * Checks if the player's answer to the math question is correct.
   *
   * @param player The player whose answer is being checked.
   * @param answer The player's answer to the math question.
   * @throws WrongMathAnswerException If the answer is incorrect.
   */
  void isCorrect(Player player, String answer) throws WrongMathAnswerException;

  /**
   * Retrieves the math question that was generated.
   *
   * @return The math question as a string.
   */
  String getQuestion();
}
