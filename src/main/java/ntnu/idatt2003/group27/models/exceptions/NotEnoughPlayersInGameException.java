package ntnu.idatt2003.group27.models.exceptions;

/**
 * An exception thrown when a game does not have enough players to start or continue.
 *
 * @author Iver Lindholm
 * @version 1.0
 * @since 2.0
 */
public class NotEnoughPlayersInGameException extends Exception {

  /**
   * Constructs a {@link NotEnoughPlayersInGameException} with the specified detail message.
   *
   * @param message The detail message explaining the reason for the exception.
   */
  public NotEnoughPlayersInGameException(String message) {
    super(message);
  }
}
