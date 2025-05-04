package ntnu.idatt2003.group27.models.exceptions;

/**
 * An exception thrown when trying to upload a piece with an invalid name.
 *
 * @author Iver Lindholm
 * @version 1.0
 * @since 2.0
 */
public class WrongPieceName extends Exception {

  /**
   * Constructs a {@link WrongPieceName} with the specified detail message.
   *
   * @param message The detail message explaining the reason for the exception.
   */
  public WrongPieceName(String message) {
    super(message);
  }
}
