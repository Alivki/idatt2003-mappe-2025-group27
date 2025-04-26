package ntnu.idatt2003.group27.models.exceptions;

/**
 * An exception thrown when an unknown or unsupported ladder game type is encountered.
 *
 * @author Iver Lindholm
 * @version 1.0
 * @since 1.0
 */
public class UnknownLadderGameTypeExceptions extends Exception {

  /**
   * Constructs an {@link UnknownLadderGameTypeExceptions} with the specified detail message.
   *
   * @param message The detail message explaining the reason for the exception.
   */
  public UnknownLadderGameTypeExceptions(String message) {
    super(message);
  }
}
