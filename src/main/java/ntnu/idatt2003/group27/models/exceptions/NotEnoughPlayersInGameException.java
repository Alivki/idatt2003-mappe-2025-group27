package ntnu.idatt2003.group27.models.exceptions;

public class NotEnoughPlayersInGameException extends Exception {
  public NotEnoughPlayersInGameException(String message) {
    super(message);
  }
}
