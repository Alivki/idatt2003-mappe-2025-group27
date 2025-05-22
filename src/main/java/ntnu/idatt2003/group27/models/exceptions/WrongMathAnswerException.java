package ntnu.idatt2003.group27.models.exceptions;

import java.io.IOException;

/**
 * An exception thrown when the player answer the wrong in math game.
 *
 * @author Iver Lindholm, Amadeus Berg
 * @version 1.0
 * @since 2.0
 */
public class WrongMathAnswerException extends IOException {

  /**
   * Constructs a {@link WrongMathAnswerException} with the specified detail message.
   *
   * @param message The detail message explaining the reason for the exception.
   */
  public WrongMathAnswerException(String message) {
    super(message);
  }
}
