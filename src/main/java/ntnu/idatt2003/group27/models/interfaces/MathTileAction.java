package ntnu.idatt2003.group27.models.interfaces;

import ntnu.idatt2003.group27.models.Player;
import ntnu.idatt2003.group27.models.exceptions.WrongMathAnswerException;

public interface MathTileAction extends TileAction {
  void generateQuestion();
  void isCorrect(Player player, String answer) throws WrongMathAnswerException;
  String getQuestion();
}
