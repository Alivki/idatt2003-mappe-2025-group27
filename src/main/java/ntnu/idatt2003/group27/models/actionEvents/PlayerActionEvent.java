package ntnu.idatt2003.group27.models.actionEvents;

import javafx.event.ActionEvent;
import javafx.event.EventType;
import ntnu.idatt2003.group27.models.Player;

public class PlayerActionEvent extends ActionEvent {
  public static final EventType<PlayerActionEvent>
      PLAYER_ACTION = new EventType<>(ActionEvent.ACTION, "PLAYER_ACTION");
  private final Player player;

  public PlayerActionEvent(Player player) {
    super();
    this.player = player;
  }

  public Player getPlayer() {
    return player;
  }
}

