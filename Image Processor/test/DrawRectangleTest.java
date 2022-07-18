import org.junit.Test;

import java.awt.Dimension;

import view.DrawRectangle;

import static org.junit.Assert.assertEquals;

/**
 * Represents the test for the DrawRectangle class.
 */
public class DrawRectangleTest {
  DrawRectangle invalid;

  // tests when the red height is less than 0 that an exception is thrown
  @Test(expected = IllegalArgumentException.class)
  public void testInvalidRed() {
    invalid = new DrawRectangle(-3,7,7,7, 1.4);
  }

  // tests when the green height is less than 0 that an exception is thrown
  @Test(expected = IllegalArgumentException.class)
  public void testInvalidGreen() {
    invalid = new DrawRectangle(3,-7,7,7, 1.4);
  }

  // tests when the blue height is less than 0 that an exception is thrown
  @Test(expected = IllegalArgumentException.class)
  public void testInvalidBlue() {
    invalid = new DrawRectangle(7,7,-7,7, 1.4);
  }

  // tests when the intensity height is less than 0 that an exception is thrown
  @Test(expected = IllegalArgumentException.class)
  public void testInvalidIntensity() {
    invalid = new DrawRectangle(3,7,7,-7, 1.4);
  }

  // tests when the scale is less than 0 that an exception is thrown
  @Test(expected = IllegalArgumentException.class)
  public void testNegativeScale() {
    invalid = new DrawRectangle(-3,7,7,7, -1.4);
  }

  // tests when the scale is 0 that an exception is thrown
  @Test(expected = IllegalArgumentException.class)
  public void testZeroScale() {
    invalid = new DrawRectangle(-3,7,7,7, 0);
  }

  // tests that the getPreferredSize is correct
  @Test
  public void testGetPreferredSize() {
    DrawRectangle rect = new DrawRectangle(100, 200, 300, 400, 0.7);
    Dimension d =  rect.getPreferredSize();
    assertEquals(280, d.getHeight(), 0.01);
    assertEquals(1, d.getWidth(), 0.01);
  }

}