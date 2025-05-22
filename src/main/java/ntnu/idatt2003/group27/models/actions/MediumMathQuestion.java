package ntnu.idatt2003.group27.models.actions;

import java.util.List;
import java.util.Random;
import java.util.logging.Logger;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import ntnu.idatt2003.group27.models.Player;
import ntnu.idatt2003.group27.models.exceptions.WrongMathAnswerException;
import ntnu.idatt2003.group27.models.interfaces.MathTileAction;
import ntnu.idatt2003.group27.models.interfaces.TileAction;
import ntnu.idatt2003.group27.view.components.LadderCanvas;

/**
 * Generates a medium math question. This class implements the
 * {@link TileAction} interface to define an action that moves a player to a specified
 * destination tile when triggered.
 *
 * @author Iver Lindholm
 * @version 1.1
 * @since 1.0
 */
public class MediumMathQuestion implements MathTileAction {
  /**
   * Logger instance for the {@link MediumMathQuestion} class.
   * Used for logging informational messages and errors related to class operations.
   */
  private static final Logger logger = Logger.getLogger(MediumMathQuestion.class.getName());

  /**
   * The first number in the math question.
   */
  private final int num1;

  /**
   * The second number in the math question.
   */
  private final int num2;

  /**
   * The answer to the math question.
   */
  private final int answer;

  /**
   * The operator used in the math question.
   */
  private final String operator;

  /**
   * Random number generator used to generate the math question.
   */
  private final Random random = new Random();

  /**
   * Constructs a math question with two random numbers between 1 and 50.
   * The operator is randomly chosen from addition, subtraction, or multiplication.
   */
  public MediumMathQuestion() {
    logger.fine("Initializing MediumMathQuestion.");
    this.num1 = random.nextInt(50) + 1;
    this.num2 = random.nextInt(50) + 1;

    int op = random.nextInt(3);
    switch (op) {
      case 0:
        this.operator = "+";
        this.answer = num1 + num2;
        break;
      case 1:
        this.operator = "-";
        this.answer = num1 - num2;
        break;
      case 2:
        this.operator = "*";
        this.answer = num1 * num2;
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
