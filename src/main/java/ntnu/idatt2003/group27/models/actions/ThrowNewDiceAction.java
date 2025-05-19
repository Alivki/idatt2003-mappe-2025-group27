package ntnu.idatt2003.group27.models.actions;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import ntnu.idatt2003.group27.models.Dice;
import ntnu.idatt2003.group27.models.Player;
import ntnu.idatt2003.group27.models.interfaces.LadderTileAction;
import ntnu.idatt2003.group27.view.components.Canvas;

/**
 * Represents the behavior of a "throw an extra dice" tile on the game board. This class implements the
 * {@link LadderTileAction} interface to define an action with custom behaviour.
 *
 * @author Amadeus Berg
 * @version 1.0
 * @since 1.0
 */
public class ThrowNewDiceAction implements LadderTileAction {
  public String description;
  //Variables for dice to throw
  public int numberOfDice, numberOfDieSides;
  private int roll = 0;

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
   * Executes the ladder action by moving the specified player to the destination tile.
   *
   * @param player The {@link Player} affected by this ladder action.
   */
  @Override
  public void perform(Player player) {
    Dice dice = new Dice(numberOfDice, numberOfDieSides);
    int steps = dice.roll();
    roll += steps;
    player.move(steps);
  }

  @Override
  public List<Integer> getAnimationPath(int startTileId, int actionTileId) {
    List<Integer> path = new ArrayList<>();
    IntStream.range(startTileId, actionTileId + roll).forEach(path::add);
    return path;
  }

  @Override
  public Color getTileColor(int tileId) {
    return Color.BLUE;
  }

  @Override
  public String getIconPath() {
    return "/icons/reroll-white.png";
  }

  @Override
  public void drawCustom(GraphicsContext gc, int tileId, Canvas canvas) {
    // Custom drawing logic for the action can be implemented here
  }

  @Override
  public void drawDestinationTile(GraphicsContext gc, int tileId, Canvas canvas) {
    // Custom drawing logic for the destination tile can be implemented here
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
}
