package ntnu.idatt2003.group27.models.exceptions;

import java.io.IOException;

/**
 * An exception thrown when there are over five players.
 *
 * @author Iver Lindholm, Amadeus Berg
 * @version 1.0
 * @since 2.0
 */
public class IllegalColorException extends IOException {

  /**
   * Constructs a {@link IllegalColorException} with the specified detail message.
   *
   * @param message The detail message explaining the reason for the exception.
   */
  public IllegalColorException(String message) {
    super(message);
  }
}
