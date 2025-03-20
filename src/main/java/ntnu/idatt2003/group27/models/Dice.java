package ntnu.idatt2003.group27.models;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Representing a set of dice, each with a configurable number of sides. This class manages multiple
 * {@link Die} instances and provides functionality to roll them and retrieve their values.
 *
 * @author Iver Lindholm
 * @version 1.0
 * @since 0.0
 */
public class Dice {
  /** The list of dice in this set. */
  private final List<Die> dice;

  /**
   * Constructs a set of dice with the specified number of dice and sides per die.
   *
   * @param numberOfDice  The number of dice in the set.
   * @param numberOfSides The number of sides each die should have.
   * @throws IllegalArgumentException if either {@code numberOfDice} or {@code numberOfSides} is
   *     less than or equal to zero.
   */
  public Dice(int numberOfDice, int numberOfSides) throws IllegalArgumentException {
    if (numberOfDice <= 0 || numberOfSides <= 0) {
      throw new IllegalArgumentException("Number of dice and sides must be greater than 0");
    }

    this.dice = IntStream.range(0, numberOfDice)
        .mapToObj(i -> new Die(numberOfSides))
        .collect(Collectors.toList());
  }

  /**
   * Rolls all the dice in the set and returns the sum of their values.
   *
   * @return The total sum of the rolls from all dice.
   */
  public int roll() {
    return dice.stream()
        .mapToInt(Die::roll)
        .sum();
  }

  /**
   * Returns the value of a specific die in the set. This method is package-private to facilitate
   * unit testing.
   *
   * @param dieNumber The index of the die whose value is the be retrieved.
   * @return The value of the specified die.
   * @throws IndexOutOfBoundsException if {@code dieNumber} is less than 0 or greater than or equal
   *      to the number of dice in the set.
   */
  int getDie(int dieNumber) throws IndexOutOfBoundsException {
    if (dieNumber >= 0 && dieNumber < dice.size()) {
      return dice.get(dieNumber).getRoll();
    } else {
      throw new IndexOutOfBoundsException("Die number out of bounds");
    }
  }
}
