package ntnu.idatt2003.group27.models.actions;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import ntnu.idatt2003.group27.models.Player;
import ntnu.idatt2003.group27.models.Tile;
import ntnu.idatt2003.group27.models.exceptions.WrongMathAnswerException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Unit test for the class {@link MediumMathQuestion}.
 *
 * <p>Verifies that EasyMathQuestion generate questions correctly</p>
 */
class MediumMathQuestionTest {
  private MediumMathQuestion question;
  private Player player;

  /**
   * Sets up the test environment before each test.
   */
  @BeforeEach
  void setUp() {
    question = new MediumMathQuestion();
    player = new Player("TestPlayer");
    Tile tile1 = new Tile(1);
    Tile tile2 = new Tile(2);
    tile2.setPreviousTile(tile1);
    player.placeOnTile(tile2);
  }

  /**
   * Verifies that the question is generated correctly.
   */
  @Test
  @DisplayName("test question generation")
  void testQuestionFormat() {
    String answer = question.getQuestion();
    assertTrue(answer.matches("\\d+ [\\+\\-\\*] \\d+ = \\?"), "Question format is incorrect");
  }

  /**
   * Verifies that the correct answer does not throw an exception.
   */
  @Test
  @DisplayName("test correct answer does not throw exception")
  void testCorrectAnswer() {
    int answer = extractAnswer(question.getQuestion());
    System.out.println(answer);
    System.out.println(question.getQuestion());

    assertDoesNotThrow(() -> question.isCorrect(player, String.valueOf(answer)),
        "Expected no exception for correct answer");
  }

  /**
   * Verifies that the wrong answer throws exception and moves player back one tile.
   */
  @Test
  @DisplayName("test wrong answer throws exception")
  void testWrongAnswer() {
    int wrongAnswer = extractAnswer(question.getQuestion()) + 1;
    int originalPosition = player.getCurrentTile().getTileId();

    assertThrows(WrongMathAnswerException.class,
        () -> question.isCorrect(player, String.valueOf(wrongAnswer)),
        "Expected WrongMathAnswerException for wrong answer");

    assertEquals(originalPosition - 1, player.getCurrentTile().getTileId());
  }

  /**
   * test that exception is thrown when input is a non-integer
   */
  @Test
  @DisplayName("test throws exception when answer is wrong")
  void testInvalidInput() {
    assertThrows(NumberFormatException.class, () -> question.isCorrect(player, "invalid"),
        "Expected NumberFormatException for invalid input");
  }

  /**
   * Utility method to extract the correct answer from the question string.
   *
   * @param question The question string in the format "num1 + num2 = ?"
   * @return the expected answer
   */
  private int extractAnswer(String question) {
    String[] parts = question.split(" ");
    int a = Integer.parseInt(parts[0]);
    int b = Integer.parseInt(parts[2]);
    return switch (parts[1]) {
      case "+" -> a + b;
      case "-" -> a - b;
      case "*" -> a * b;
      default -> throw new IllegalArgumentException("Invalid operator: " + parts[1]);
    };
  }
}