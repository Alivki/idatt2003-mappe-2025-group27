package ntnu.idatt2003.group27.models.exceptions;

import ntnu.idatt2003.group27.models.Player;

/**
 * An exception thrown when a {@link Player} move is invalid
 *
 * @author Amadeus Berg
 */
public class InvalidPlayerMoveException extends RuntimeException {

  /**
   * Constructs a {@link InvalidPlayerMoveException} with the specified detail message.
   *
   * @param message The detail message explaining the reason for the exception.
   */
  public InvalidPlayerMoveException(String message) {
    super(message);
  }
}
