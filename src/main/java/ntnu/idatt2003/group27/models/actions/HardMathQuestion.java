package ntnu.idatt2003.group27.models.actions;

import java.util.List;
import java.util.Random;
import java.util.logging.Logger;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import ntnu.idatt2003.group27.models.Player;
import ntnu.idatt2003.group27.models.exceptions.WrongMathAnswerException;
import ntnu.idatt2003.group27.models.interfaces.MathTileAction;
import ntnu.idatt2003.group27.view.components.LadderCanvas;

public class HardMathQuestion implements MathTileAction {
  /**
   * Logger instance for the {@link HardMathQuestion} class.
   * Used for logging informational messages and errors related to class operations.
   */
  private static final Logger logger = Logger.getLogger(HardMathQuestion.class.getName());
  private final int num1;
  private final int num2;
  private final int answer;
  private final String operator;
  private final Random random = new Random();

  public HardMathQuestion() {
    logger.fine("Initializing HardMathQuestion.");
    int op = random.nextInt(5);
    switch (op) {
      case 0:
        this.num1 = random.nextInt(100) + 1;
        this.num2 = random.nextInt(100) + 1;
        this.operator = "+";
        this.answer = num1 + num2;
        break;
      case 1:
        this.num1 = random.nextInt(100) + 1;
        this.num2 = random.nextInt(num1) + 1;
        this.operator = "-";
        this.answer = num1 - num2;
        break;
      case 2:
        this.num1 = random.nextInt(20) + 1;
        this.num2 = random.nextInt(20) + 1;
        this.operator = "*";
        this.answer = num1 * num2;
        break;
      case 3:
        this.answer = random.nextInt(20) + 1;
        this.num2 = random.nextInt(20) + 1;
        this.num1 = this.answer * this.num2;
        this.operator = "/";
        break;
      case 4:
        this.num1 = random.nextInt(10) + 1;
        this.num2 = 2;
        this.operator = "^";
        this.answer = num1 * num1;
        break;
      default:
        logger.warning("Invalid operator: " + op);
        throw new IllegalStateException("Invalid operator");
    }
  }

  @Override
  public void perform(Player player) {
    // Not needed for this action
  }

  @Override
  public void isCorrect(Player player, String answer) throws WrongMathAnswerException {
    int userAnswer = 0;

    try {
      userAnswer = Integer.parseInt(answer);
    } catch (NumberFormatException e) {
      logger.warning("Error parsing answer from player " + player.getName() + ", answer: " + answer);
      throw new NumberFormatException();
    }

    if (userAnswer != this.answer) {
      player.move(-1);
      throw new WrongMathAnswerException("Wrong answer, try again next round.");
    }
  }

  @Override
  public void generateQuestion() {
    // No need to implement this method for EasyMathQuestion
  }

  @Override
  public List<Integer> getAnimationPath(int startTileId, int actionTileId) {
    // not needed for this action
    return null;
  }

  @Override
  public Color getTileColor(int tileId) {
    // not needed for this action
    return null;
  }

  @Override
  public String getIconPath() {
    // not needed for this action
    return "";
  }

  @Override
  public void drawCustom(GraphicsContext gc, int tileId, LadderCanvas ladderCanvas) {
    // Custom drawing logic for the action can be implemented here
  }

  @Override
  public void drawDestinationTile(GraphicsContext gc, int tileId, LadderCanvas ladderCanvas) {
    // Custom drawing logic for the destination tile can be implemented here
  }

  @Override
  public String getQuestion() {
    logger.fine("Getting question.");
    if (operator.equals("^")) {
      return num1 + " ^ " + num2 + " = ?";
    }
    return num1 + " " + operator + " " + num2 + " = ?";
  }
}
