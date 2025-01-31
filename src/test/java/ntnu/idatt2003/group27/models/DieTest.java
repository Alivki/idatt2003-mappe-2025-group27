package ntnu.idatt2003.group27.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class DieTest {
  @Test
  @DisplayName("Test that the dies rolls and returns a correct value with a given number of sides")
  void testRollWithSides() {
    Die dieOne = new Die(6);
    Die dieTwo = new Die(1);
    Die dieThree = new Die(4);

    int rollOne = dieOne.roll();
    int rollThree = dieThree.roll();

    assertTrue(rollOne >= 1 && rollOne <= 6, "Roll value should be between 1 and 6");
    assertEquals(1, dieTwo.roll(), "Roll value should be 1");
    assertTrue(rollThree >= 1 && rollThree <= 4, "Roll value should be between 1 and 4");

    assertFalse(rollOne < 1 || rollOne > 6, "Roll value should be between 1 and 6");
    assertNotEquals(2, dieTwo.roll(), "Roll value should be 1");
    assertFalse(rollThree < 1 || rollThree > 4, "Roll value should be between 1 and 4");
  }

  @Test
  @DisplayName("Test that the die handles zero sides correctly")
  void testRollWithZeroSides() {
    assertThrows(IllegalArgumentException.class, () -> new Die(0),
        "Number of sides must be greater than 0");
  }

  @Test
  @DisplayName("Test that getRoll returns the last roll value")
  void getRoll() {
    Die die = new Die(6);
    int roll = die.roll();
    assertEquals(roll, die.getRoll(), "getRoll should return the last roll value");
  }
}