package ntnu.idatt2003.group27.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for the {@link Dice} class.
 *
 * <p>Verifies roll method, constructor, and getDie method.</p>
 *
 * @author Iver Lindholm
 */
class DiceTest {
  Dice dice;

  /**
   * Set up a new Dice object before each test.
   */
  @BeforeEach
  public void setUpDice() {
    dice = new Dice(3, 6);
  }

  @Test
  @DisplayName("Test that the roll method returns the correct sum of rolls")
  void testRoll() {
    int rollSum = dice.roll();
    assertTrue(rollSum >= 3 && rollSum <= 18, "Roll sum should be between 3 and 18");
    assertFalse(rollSum < 3 || rollSum > 18, "Roll sum should be between 3 and 18");
  }

  @Test
  @DisplayName("Test that the die handles zero sides correctly")
  void testDiceWithZeroInputs() {
    assertThrows(IllegalArgumentException.class, () -> new Dice(0, 0),
        "Number of die and sides must be greater than 0");
  }

  @Test
  @DisplayName("Test that getDie returns the correct value for a specific die")
  void testGetDie() {
    dice.roll();
    int dieValue = dice.getDie(0);
    assertTrue(dieValue >= 1 && dieValue <= 6, "Die value should be between 1 and 6");
    assertFalse(dieValue < 1 || dieValue > 6, "Die value should be between 1 and 6");
  }

  @Test
  @DisplayName("Test that getNumberOfDice returns the correct number of dice")
  public void testGetNumberOfDice() {
    int expectedNumberOfDice = 2;
    int numberOfSides = 6;

    Dice dice = new Dice(expectedNumberOfDice, numberOfSides);

    assertEquals(expectedNumberOfDice, dice.getNumberOfDice(),
        "getNumberOfDice should return the correct number of dice");
  }


  @Test
  @DisplayName("Test that getDie throws an exception for an invalid die number")
  void testGetDieThrowsException() {
    assertThrows(IndexOutOfBoundsException.class, () -> {
      dice.getDie(3);
    }, "Expected getDie to throw IndexOutOfBoundsException for invalid die number");
  }
}