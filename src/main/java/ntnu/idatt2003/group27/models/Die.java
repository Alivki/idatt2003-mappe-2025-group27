package ntnu.idatt2003.group27.models;

import java.util.Random;

/**
 * A class representing a die with an interchangeable number of sides.
 */
public class Die {
  private int lastRollValue;
  private final int numberOfSides;
  private final Random random;

  /**
   * Constructor for the Die class.
   *
   * @param numberOfSides The number of sides the die should have
   */
  public Die(int numberOfSides) {
    if (numberOfSides <= 0) {
      throw new IllegalArgumentException("Number of sides must be greater than 0");
    }

    this.numberOfSides = numberOfSides;
    this.lastRollValue = 0;
    this.random = new Random();
  }

  /**
   * Rolls the die and returns the value of the roll.
   *
   * @return The value of the roll
   */
  public int roll() {
    lastRollValue = random.nextInt(numberOfSides) + 1;
    return lastRollValue;
  }

  /**
   * Returns the value of the last roll.
   *
   * @return The value of the last roll
   */
  public int getRoll() {
    return lastRollValue;
  }
}
