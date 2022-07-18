import org.junit.Before;
import org.junit.Test;

import model.IModel;
import model.Pixel;

import static org.junit.Assert.assertEquals;

/**
 * Represents the tests needed to test the Pixel class.
 */
public class PixelTest {
  Pixel fullRed;
  Pixel fullBlue;
  Pixel fullGreen;
  Pixel invalid;
  Pixel evenMix;
  Pixel moreRed;
  Pixel moreBlue;
  Pixel moreGreen;
  Pixel noColor;
  Pixel allColor;
  Pixel withAlpha;

  @Before
  public void init() {
    fullRed = new Pixel(255, 0, 0);
    fullGreen = new Pixel(0, 255, 0);
    fullBlue = new Pixel(0, 0, 255);
    evenMix = new Pixel(100, 100, 100);
    moreRed = new Pixel(205, 8, 12);
    moreBlue = new Pixel(105, 89, 201);
    moreGreen = new Pixel(5, 90, 60);
    noColor = new Pixel(0, 0, 0);
    allColor = new Pixel(255, 255, 255);
    withAlpha = new Pixel(20, 40, 60, 80);
  }

  // tests that the constructor for the Pixel class works. To test the constructor, the
  // visualizeComponent is used and since the visualizeComponent version returns an integer
  // for all the different values (in order to use it to change the pixels).This means that the
  // intensity and the luma will be floored.
  @Test
  public void testValidConstructor() {
    // checks that the constructor works for even mix
    assertEquals(100, evenMix.getRed());
    assertEquals(100, evenMix.getGreen());
    assertEquals(100, evenMix.getBlue());
    assertEquals(255, evenMix.getAlpha());
    assertEquals(100, evenMix.getVisualize(IModel.Component.Intensity));
    assertEquals(100, evenMix.getVisualize(IModel.Component.Value));
    assertEquals(100, evenMix.getVisualize(IModel.Component.Luma));

    // checks that the constructor works for moreBlue
    assertEquals(105, moreBlue.getRed());
    assertEquals(89, moreBlue.getGreen());
    assertEquals(201, moreBlue.getBlue());
    assertEquals(255, moreBlue.getAlpha());
    assertEquals(100, moreBlue.getVisualize(IModel.Component.Luma));
    assertEquals(201, moreBlue.getVisualize(IModel.Component.Value));
    assertEquals(131, moreBlue.getVisualize(IModel.Component.Intensity));

    // checks that the constructor works for noColor
    assertEquals(0, noColor.getRed());
    assertEquals(0, noColor.getGreen());
    assertEquals(0, noColor.getBlue());
    assertEquals(255, noColor.getAlpha());
    assertEquals(0, noColor.getVisualize(IModel.Component.Luma));
    assertEquals(0, noColor.getVisualize(IModel.Component.Value));
    assertEquals(0, noColor.getVisualize(IModel.Component.Intensity));

    // checks that the constructor works for allColor
    assertEquals(255, allColor.getRed());
    assertEquals(255, allColor.getGreen());
    assertEquals(255, allColor.getBlue());
    assertEquals(255, allColor.getAlpha());
    assertEquals(254, allColor.getVisualize(IModel.Component.Luma));
    assertEquals(255, allColor.getVisualize(IModel.Component.Value));
    assertEquals(255, allColor.getVisualize(IModel.Component.Intensity));

    // checks that the constructor works for withAlpha
    assertEquals(20, withAlpha.getRed());
    assertEquals(40, withAlpha.getGreen());
    assertEquals(60, withAlpha.getBlue());
    assertEquals(80, withAlpha.getAlpha());
    assertEquals(37, withAlpha.getVisualize(IModel.Component.Luma));
    assertEquals(60, withAlpha.getVisualize(IModel.Component.Value));
    assertEquals(40, withAlpha.getVisualize(IModel.Component.Intensity));
  }

  // tests that an exception is thrown in the constructor when the value of red is less 0
  @Test(expected = IllegalArgumentException.class)
  public void testNegRed() {
    invalid = new Pixel(-1, 2, 2);
  }

  // tests that an exception is thrown in the constructor when the value of red is greater than 255
  @Test(expected = IllegalArgumentException.class)
  public void testTooMuchRed() {
    invalid = new Pixel(256, 2, 2);
  }

  // tests that an exception is thrown in the constructor when the value of green is less 0
  @Test(expected = IllegalArgumentException.class)
  public void testNegGreen() {
    invalid = new Pixel(12, -92, 2);
  }

  // tests that an exception is thrown in the constructor when the value of green is greater
  // than 255
  @Test(expected = IllegalArgumentException.class)
  public void testTooMuchGreen() {
    invalid = new Pixel(56, 300, 2);
  }

  // tests that an exception is thrown in the constructor when the value of blue is less 0
  @Test(expected = IllegalArgumentException.class)
  public void testNegBlue() {
    invalid = new Pixel(11, 2, -32);
  }

  // tests that an exception is thrown in the constructor when the value of blue is greater than 255
  @Test(expected = IllegalArgumentException.class)
  public void testTooMuchBlue() {
    invalid = new Pixel(45, 2, 502);
  }

  // tests that the getRed method works
  @Test
  public void testGetRed() {
    assertEquals(255, fullRed.getRed());
    assertEquals(0, fullBlue.getRed());
    assertEquals(0, fullGreen.getRed());
    assertEquals(100, evenMix.getRed());
    assertEquals(0, noColor.getRed());
    assertEquals(255, allColor.getRed());
    assertEquals(205, moreRed.getRed());
    assertEquals(105, moreBlue.getRed());
    assertEquals(5, moreGreen.getRed());
  }

  // tests that the getGreen method works
  @Test
  public void testGetGreen() {
    assertEquals(0, fullRed.getGreen());
    assertEquals(0, fullBlue.getGreen());
    assertEquals(255, fullGreen.getGreen());
    assertEquals(100, evenMix.getGreen());
    assertEquals(0, noColor.getGreen());
    assertEquals(255, allColor.getGreen());
    assertEquals(8, moreRed.getGreen());
    assertEquals(89, moreBlue.getGreen());
    assertEquals(90, moreGreen.getGreen());
  }

  // tests that the getBlue method works
  @Test
  public void testGetBlue() {
    assertEquals(0, fullRed.getBlue());
    assertEquals(255, fullBlue.getBlue());
    assertEquals(0, fullGreen.getBlue());
    assertEquals(100, evenMix.getBlue());
    assertEquals(0, noColor.getBlue());
    assertEquals(255, allColor.getBlue());
    assertEquals(12, moreRed.getBlue());
    assertEquals(201, moreBlue.getBlue());
    assertEquals(60, moreGreen.getBlue());
  }

  // tests that getAlpha works
  @Test
  public void testGetAlpha() {
    assertEquals(255, fullRed.getAlpha());
    assertEquals(255, fullBlue.getAlpha());
    assertEquals(255, fullGreen.getAlpha());
    assertEquals(255, evenMix.getAlpha());
    assertEquals(255, noColor.getAlpha());
    assertEquals(255, allColor.getAlpha());
    assertEquals(255, moreRed.getAlpha());
    assertEquals(255, moreBlue.getAlpha());
    assertEquals(255, moreGreen.getAlpha());
    assertEquals(80, withAlpha.getAlpha());
  }

  // tests that the getVisualize method works with every component
  @Test
  public void testGetVisualize() {
    // tests with the fullRed object
    assertEquals(255, fullRed.getVisualize(IModel.Component.Red));
    assertEquals(0, fullRed.getVisualize(IModel.Component.Green));
    assertEquals(0, fullRed.getVisualize(IModel.Component.Blue));
    assertEquals(85, fullRed.getVisualize(IModel.Component.Intensity));
    assertEquals(255, fullRed.getVisualize(IModel.Component.Value));
    assertEquals(54, fullRed.getVisualize(IModel.Component.Luma));

    // tests with the evenMix object
    assertEquals(100, evenMix.getVisualize(IModel.Component.Red));
    assertEquals(100, evenMix.getVisualize(IModel.Component.Green));
    assertEquals(100, evenMix.getVisualize(IModel.Component.Blue));
    assertEquals(100, evenMix.getVisualize(IModel.Component.Intensity));
    assertEquals(100, evenMix.getVisualize(IModel.Component.Value));
    assertEquals(100, evenMix.getVisualize(IModel.Component.Luma));
  }

}
