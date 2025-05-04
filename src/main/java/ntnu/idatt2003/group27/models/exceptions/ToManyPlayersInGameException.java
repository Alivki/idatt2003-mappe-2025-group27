package ntnu.idatt2003.group27.models.exceptions;

/**
 * An exception thrown when there are over five players.
 *
 * @author Iver Lindholm
 * @version 1.0
 * @since 2.0
 */
public class ToManyPlayersInGameException extends Exception {

  /**
   * Constructs a {@link ToManyPlayersInGameException} with the specified detail message.
   *
   * @param message The detail message explaining the reason for the exception.
   */
  public ToManyPlayersInGameException(String message) {
    super(message);
  }
}
