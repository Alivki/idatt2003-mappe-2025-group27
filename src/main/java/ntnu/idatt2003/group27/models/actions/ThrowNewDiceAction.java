package ntnu.idatt2003.group27.models.actions;

import ntnu.idatt2003.group27.models.Dice;
import ntnu.idatt2003.group27.models.Player;
import ntnu.idatt2003.group27.models.interfaces.TileAction;

/**
 * Represents the behavior of a "throw an extra dice" tile on the game board. This class implements the
 * {@link TileAction} interface to define an action with custom behaviour.
 *
 * @author Amadeus Berg
 * @version 1.0
 * @since 1.0
 */
public class ThrowNewDiceAction implements TileAction {
  public String description;
  //Variables for dice to throw
  public int numberOfDice, numberOfDieSides;

  /**
   * Constructs a ladder action with the specified destination tile and description.
   *
   * @param description A textual description of the ladder action.
   */
  public ThrowNewDiceAction(String description, int numberOfDice, int numberOfDieSides) {
    this.description = description;
    this.numberOfDice = numberOfDice;
    this.numberOfDieSides = numberOfDieSides;
  }

  /**
   * Retrieves the tile id of the destination tile for this ladder action.
   *
   * @return the ID of the destination tile.
   */
  public int getNumberOfSides() {
    return numberOfDieSides;
  }

  /**
   * Retrieves the number of dice to throw for this ladder action.
   *
   * @return the number of dice to throw.
   */
  public int getNumberOfDice() {
    return numberOfDice;
  }

  /**
   * Retrieves the description of this ladder action.
   *
   * @return the description of the ladder action.
   */
  public String getDescription() {
    return description;
  }

  /**
   * Executes the ladder action by moving the specified player to the destination tile.
   *
   * @param player The {@link Player} affected by this ladder action.
   */
  @Override
  public void perform(Player player) {
    Dice dice = new Dice(numberOfDice, numberOfDieSides);
    int steps = dice.roll();
    player.move(steps);
    System.out.println(player.getName() + " threw a new dice and rolled a " + steps);
  }
}
