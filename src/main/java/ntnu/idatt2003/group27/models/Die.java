package ntnu.idatt2003.group27.models;

import java.util.Random;

/**
 * Represents a single die with configurable number of sides. This class simulates rolling the die
 * and tracks the most recent roll.
 *
 * @author Iver Lindholm
 * @version 1.0
 * @since 0.0
 */
public class Die {
  /** The value of the most recent roll. */
  private int lastRollValue;

  /** The number of sides on this die. */
  private final int numberOfSides;

  /** The random number generator used for rolling the die. */
  private final Random random;

  /**
   * Constructs a die with the specified number of sides.
   *
   * @param numberOfSides The number of sides the die should have.
   * @throws IllegalArgumentException If {@code numberOfSides} is less than or equal to 0.
   */
  public Die(int numberOfSides) throws IllegalArgumentException {
    if (numberOfSides <= 0) {
      throw new IllegalArgumentException("Number of sides must be greater than 0");
    }

    this.numberOfSides = numberOfSides;
    this.lastRollValue = 0;
    this.random = new Random();
  }

  /**
   * Rolls the die, generating a random value between 1 and the number of sides, and updates
   * the last roll value.
   *
   * @return The value of the roll
   */
  public int roll() {
    lastRollValue = random.nextInt(numberOfSides) + 1;
    return lastRollValue;
  }

  /**
   * Retrieves the value of the most recent roll.
   *
   * @return The value of the roll, or 0 if the die has not been rolled yet.
   */
  public int getRoll() {
    return lastRollValue;
  }
}
