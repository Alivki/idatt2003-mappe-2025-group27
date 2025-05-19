package ntnu.idatt2003.group27.models.interfaces;

import ntnu.idatt2003.group27.models.Player;

public interface MathTileAction extends TileAction {
  void generateQuestion();
  void isCorrect(Player player, String answer);
  String getQuestion();
}
