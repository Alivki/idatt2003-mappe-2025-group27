package ntnu.idatt2003.group27.models;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * A class representing a set of dice with an interchangeable number of sides.
 */
public class Dice {
  private final List<Die> dice;

  /**
   * Constructor for the Dice class.
   *
   * @param numberOfDice  The number of dice in the set
   * @param numberOfSides The number of sides each die should have
   */
  public Dice(int numberOfDice, int numberOfSides) {
    if (numberOfDice <= 0 || numberOfSides <= 0) {
      throw new IllegalArgumentException("Number of dice and sides must be greater than 0");
    }

    this.dice = IntStream.range(0, numberOfDice)
        .mapToObj(i -> new Die(numberOfSides))
        .collect(Collectors.toList());
  }

  /**
   * Rolls all the dice in the set and returns the sum of the rolls.
   *
   * @return The sum of the rolls
   */
  public int roll() {
    return dice.stream()
        .mapToInt(Die::roll)
        .sum();
  }

  /**
   * Returns the value of a specific die in the set. No modifier to make it package-private for
   * unit testing.
   *
   * @param dieNumber The number of the die to get the value of
   * @return The value of the die
   */
  int getDie(int dieNumber) {
    if (dieNumber >= 0 && dieNumber < dice.size()) {
      return dice.get(dieNumber).getRoll();
    } else {
      throw new IndexOutOfBoundsException("Die number out of bounds");
    }
  }
}
