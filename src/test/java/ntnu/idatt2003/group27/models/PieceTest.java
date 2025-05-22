package ntnu.idatt2003.group27.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Test class for the {@link Piece} class.
 * Testing the constructor,
 * testing the getName method,
 * testing the getIconFilePath method,
 * testing creating a piece with null values,
 * testing creating a piece with empty strings,
 * testing that two pieces with the same data are not the same object.
 */
public class PieceTest {

  /** The piece to be tested. */
  Piece piece;

  /**
   * Creates a new board game before each test.
   */
  @BeforeEach
  public void setup() {
    piece = new Piece("Piece_1", "path/to/icon");
  }

  /**
   * Test that the constructor of the Piece class works as expected.
   */
  @Test
  @DisplayName("Test that creating a new piece works and applies the correct parameters.")
  public void testCreatePiece() {
    assertNotNull(piece, "Piece should not be null");
    assertEquals("Piece_1", piece.getName(), "Piece name should match");
    assertEquals("path/to/icon", piece.getIconFilePath(), "Piece icon path should match");
  }

  /**
   * Test that the getName method of the Piece class works as expected.
   */
  @Test
  @DisplayName("Test that getName returns the correct name.")
  public void testGetName() {
    assertEquals("Piece_1", piece.getName(), "Piece name should match");
  }

  /**
   * Test that the getIconFilePath method of the Piece class works as expected.
   */
  @Test
  @DisplayName("Test that getIconFilePath returns the correct path.")
  public void testGetIconFilePath() {
    assertEquals("path/to/icon", piece.getIconFilePath(), "Piece icon path should match");
  }

  /**
   * Test that creating a piece with null values returns null values.
   */
  @Test
  @DisplayName("Test creating a piece with null values")
  public void testCreatePieceWithNullValues() {
    Piece nullPiece = new Piece(null, null);
    assertNull(nullPiece.getName(), "Name should be null");
    assertNull(nullPiece.getIconFilePath(), "Icon path should be null");
  }

  /**
   * Test that creating a piece with empty strings returns empty strings.
   */
  @Test
  @DisplayName("Test creating a piece with empty strings")
  public void testCreatePieceWithEmptyStrings() {
    Piece emptyPiece = new Piece("", "");
    assertEquals("", emptyPiece.getName(), "Name should be empty");
    assertEquals("", emptyPiece.getIconFilePath(), "Icon path should be empty");
  }

  /**
   * Test that duplicate pieces are not the same object.
   */
  @Test
  @DisplayName("Test that two pieces with the same data are not the same object")
  public void testPieceIdentity() {
    Piece duplicatePiece = new Piece("Piece_1", "path/to/icon1");
    assertNotSame(piece, duplicatePiece, "Pieces should not be the same object");
  }
}