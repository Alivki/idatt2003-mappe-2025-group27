package ntnu.idatt2003.group27.utils.filehandler;

import java.util.logging.Logger;
import javafx.scene.paint.Color;
import java.util.Random;

/**
 * A utility class for generating random colors. This class encapsulates a color and provides a
 * static method to generate a random color.
 *
 * @author Iver Lindholm
 * @version 1.0
 * @since 2.0
 */
public class RandomColor {
  /**
   * Logger instance for the {@link RandomColor} class.
   * Used for logging informational messages and errors related to class operations.
   */
  private static final Logger logger = Logger.getLogger(RandomColor.class.getName());
  /** The color that will be used. */
  private final Color color;
  /** The instance of the random object that will generate random doubles. */
  private static final Random random = new Random();

  /**
   * Private constructor to create a RandomColor with a specific Color.
   *
   * @param color The Color object to store.
   */
  private RandomColor(Color color) {
    this.color = color;
  }

  /**
   * Generates a random color with RGB values between 0 and 1. The alpha value is set to 1.0.
   *
   * @return A new instance of {@link RandomColor} with a randomly generated color.
   */
  public static RandomColor generateRandomColor() {
    double red = random.nextDouble();
    double green = random.nextDouble();
    double blue = random.nextDouble();
    return new RandomColor(new Color(red, green, blue, 1.0));
  }

  /**
   * Retrieves the color encapsulated in this instance.
   *
   * @return The {@link Color} object representing the color.
   */
  public Color getColor() {
    return color;
  }
}
