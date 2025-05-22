package ntnu.idatt2003.group27.utils.filehandler;

import static org.junit.jupiter.api.Assertions.*;

import javafx.scene.paint.Color;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Unit test for the {@link RandomColor} class.
 *
 * <p>Verifies random color generation and color retrieval</p>
 */
class RandomColorTest {
  private RandomColor randomColor;

  /**
   * Sets up the test environment with a RandomColor instance.
   */
  @BeforeEach
  void setUp(){
    randomColor = RandomColor.generateRandomColor();
  }

  /**
   * Verifies that the generateRandomColor creates a valid color with random RGB values.
   */
  @Test
  @DisplayName("test random color generation")
  void testGenerateRandomColor() {
    Color color = randomColor.getColor();

    assertNotNull(color, "Generated color should not be null");
    assertTrue(color.getRed() >= 0 && color.getRed() <= 1, "Red value should be between 0 and 1");
    assertTrue(color.getGreen() >= 0 && color.getGreen() <= 1, "Green value should be between 0 and 1");
    assertTrue(color.getBlue() >= 0 && color.getBlue() <= 1, "Blue value should be between 0 and 1");
    assertEquals(1.0, color.getOpacity(), "Alpha value should be 1.0");
  }

  /**
   * Verifies that multiple calls to generateRandomColor produce different colors.
   */
  @Test
  @DisplayName("Verifies that multiple calls to generateRandomColor produce different colors")
  void getColor() {
    RandomColor randomColor2 = RandomColor.generateRandomColor();

    Color color1 = randomColor.getColor();
    Color color2 = randomColor2.getColor();

    assertNotEquals(color1, color2, "Generated colors should be different");
  }


  /**
   * Verifies that the getColor method returns the same color instance.
   */
  @Test
  @DisplayName("test getColor returns consistent colors")
  void testGetColorConsistency() {
    Color color1 = randomColor.getColor();
    Color color2 = randomColor.getColor();

    assertSame(color1, color2, "getColor should return the same color instance");
  }
}