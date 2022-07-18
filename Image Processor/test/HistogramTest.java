import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.assertEquals;

import model.EnhancedModel;
import model.EnhancedModelImpl;
import model.IModel;
import model.Pixel;

/**
 * Represents the tests for getHistogram method.
 */
public class HistogramTest {
  Pixel[][] threeByThree;
  EnhancedModel model;

  @Before
  public void init() {
    this.threeByThree = new Pixel[][]{
            {new Pixel(100, 150, 200),
            new Pixel(100, 50, 100),
            new Pixel(255, 255, 255)},
            {new Pixel(150, 150, 150),
            new Pixel(100, 30, 60),
            new Pixel(40, 78, 92)},
            {new Pixel(254, 103, 202),
            new Pixel(6, 70, 139),
            new Pixel(79, 42, 100)}};
    model = new EnhancedModelImpl();
    model.addImage("image", threeByThree);
  }

  // tests a histogram using the red component
  @Test
  public void testMakingRedHistogram() {

    HashMap<Integer, Integer> red = model.getHistogram("image", IModel.Component.Red);
    for (int i = 0; i < 256; i++) {
      if (i == 100) {
        assertEquals(3, (int) red.get(i));
      } else if (i == 255 || i == 150 || i == 40 || i == 254 || i == 6 || i == 79) {
        assertEquals(1, (int) red.get(i));
      } else {
        assertEquals(0, (int) red.get(i));
      }
    }
  }

  // tests a histogram using the blue component
  @Test
  public void testMakingBlueHistogram() {
    HashMap<Integer, Integer> blue = model.getHistogram("image", IModel.Component.Blue);
    for (int i = 0; i < 256; i++) {
      if (i == 255 || i == 150 || i == 200 || i == 60 || i == 92 || i == 202
              || i == 139) {
        assertEquals(1, (int) blue.get(i));
      } else if (i == 100) {
        assertEquals(2, (int) blue.get(i));
      } else {
        assertEquals(0, (int) blue.get(i));
      }
    }
  }

  // tests a histogram using the green component
  @Test
  public void testMakingGreenHistogram() {
    HashMap<Integer, Integer> green = model.getHistogram("image", IModel.Component.Green);
    for (int i = 0; i < 256; i++) {
      if (i == 50 || i == 255 || i == 30 || i == 78 || i == 103 || i == 70
              || i == 42) {
        assertEquals(1, (int) green.get(i));
      } else if (i == 150) {
        assertEquals(2, (int) green.get(i));
      } else {
        assertEquals(0, (int) green.get(i));
      }
    }
  }

  // tests a histogram using the intensity component
  @Test
  public void testMakingIntensityHistogram() {
    HashMap<Integer, Integer> intense = model.getHistogram("image", IModel.Component.Intensity);
    for (int i = 0; i < 256; i++) {
      if (i == 83 || i == 255 || i == 63 || i == 70 || i == 186 || i == 71
              || i == 73) {
        assertEquals(1, (int) intense.get(i));
      } else if (i == 150) {
        assertEquals(2, (int) intense.get(i));
      } else {
        assertEquals(0, (int) intense.get(i));
      }
    }
  }

  // tests that the red histogram is right after brightening the image
  @Test
  public void testBrightenRed() {
    model.changeBrightness(100, "image", "bright");
    System.out.println(model.getImage("bright").convertToText());
    HashMap<Integer, Integer> red = model.getHistogram("bright", IModel.Component.Red);
    System.out.println(red.toString());
    for (int i = 0; i < 256; i++) {
      if (i == 200) {
        assertEquals(3, (int) red.get(i));
      } else if (i == 255) {
        assertEquals(2, (int) red.get(i));
      } else if (i == 250 || i == 140 || i == 106 || i == 179) {
        assertEquals(1, (int) red.get(i));
      } else {
        assertEquals(0, (int) red.get(i));
      }
    }
  }

  // tests that the blue histogram is right after brightening the image
  @Test
  public void testBrightenBlue() {
    model.changeBrightness(100, "image", "bright");
    HashMap<Integer, Integer> blue = model.getHistogram("bright", IModel.Component.Blue);
    for (int i = 0; i < 256; i++) {
      if (i == 250 || i == 160 || i == 192 || i == 239) {
        assertEquals(1, (int) blue.get(i));
      } else if (i == 200) {
        assertEquals(2, (int) blue.get(i));
      } else if (i == 255) {
        assertEquals(3, (int) blue.get(i));
      } else {
        assertEquals(0, (int) blue.get(i));
      }
    }
  }

  // tests that the green histogram is right after brightening the image
  @Test
  public void testBrightenGreen() {
    model.changeBrightness(100, "image", "bright");
    HashMap<Integer, Integer> green = model.getHistogram("bright", IModel.Component.Green);
    for (int i = 0; i < 256; i++) {
      if (i == 150 || i == 255 || i == 130 || i == 178 || i == 203 || i == 170
              || i == 142) {
        assertEquals(1, (int) green.get(i));
      } else if (i == 250) {
        assertEquals(2, (int) green.get(i));
      } else {
        assertEquals(0, (int) green.get(i));
      }
    }
  }

  // tests that the histogram is right after flipping the image (aka it should be same as it
  // was originally
  @Test
  public void testMakingFlippedHistogram() {
    model.flipVertically("image", "flipped");
    HashMap<Integer, Integer> red = model.getHistogram("flipped", IModel.Component.Red);
    for (int i = 0; i < 256; i++) {
      if (i == 100) {
        assertEquals(3, (int) red.get(i));
      } else if (i == 255 || i == 150 || i == 40 || i == 254 || i == 6 || i == 79) {
        assertEquals(1, (int) red.get(i));
      } else {
        assertEquals(0, (int) red.get(i));
      }
    }

    HashMap<Integer, Integer> blue = model.getHistogram("image", IModel.Component.Blue);
    for (int i = 0; i < 256; i++) {
      if (i == 255 || i == 150 || i == 200 || i == 60 || i == 92 || i == 202
              || i == 139) {
        assertEquals(1, (int) blue.get(i));
      } else if (i == 100) {
        assertEquals(2, (int) blue.get(i));
      } else {
        assertEquals(0, (int) blue.get(i));
      }
    }

    HashMap<Integer, Integer> green = model.getHistogram("image", IModel.Component.Green);
    for (int i = 0; i < 256; i++) {
      if (i == 50 || i == 255 || i == 30 || i == 78 || i == 103 || i == 70
              || i == 42) {
        assertEquals(1, (int) green.get(i));
      } else if (i == 150) {
        assertEquals(2, (int) green.get(i));
      } else {
        assertEquals(0, (int) green.get(i));
      }
    }

    HashMap<Integer, Integer> intense = model.getHistogram("image", IModel.Component.Intensity);
    for (int i = 0; i < 256; i++) {
      if (i == 83 || i == 255 || i == 63 || i == 70 || i == 186 || i == 71
              || i == 73) {
        assertEquals(1, (int) intense.get(i));
      } else if (i == 150) {
        assertEquals(2, (int) intense.get(i));
      } else {
        assertEquals(0, (int) intense.get(i));
      }
    }
  }

  // tests that the histograms are correct after visualizing a component of the image
  @Test
  public void visualizeComponentHistogram() {
    model.visualizeComponent("image", IModel.Component.Blue, "BLUE");
    HashMap<Integer, Integer> blue = model.getHistogram("BLUE", IModel.Component.Blue);
    HashMap<Integer, Integer> red = model.getHistogram("BLUE", IModel.Component.Red);
    HashMap<Integer, Integer> green = model.getHistogram("BLUE", IModel.Component.Green);
    HashMap<Integer, Integer> intense = model.getHistogram("BLUE", IModel.Component.Intensity);

    for (int i = 0; i < 256; i++) {
      if (i == 255 || i == 150 || i == 200 || i == 60 || i == 92 || i == 202
              || i == 139) {
        assertEquals(1, (int) blue.get(i));
        assertEquals(1, (int) red.get(i));
        assertEquals(1, (int) green.get(i));
        assertEquals(1, (int) intense.get(i));
      } else if (i == 100) {
        assertEquals(2, (int) blue.get(i));
        assertEquals(2, (int) red.get(i));
        assertEquals(2, (int) green.get(i));
        assertEquals(2, (int) intense.get(i));
      } else {
        assertEquals(0, (int) blue.get(i));
        assertEquals(0, (int) red.get(i));
        assertEquals(0, (int) green.get(i));
        assertEquals(0, (int) intense.get(i));
      }
    }
  }

  // checks that an error is thrown when you try to create a histogram with value
  @Test(expected = IllegalArgumentException.class)
  public void testValueHistogram() {
    HashMap<Integer, Integer> value = model.getHistogram("image", IModel.Component.Value);
  }

  // checks that an error is thrown when you try to create a histogram with luma
  @Test(expected = IllegalArgumentException.class)
  public void testLumaHistogram() {
    HashMap<Integer, Integer> value = model.getHistogram("image", IModel.Component.Luma);
  }

  // checks that an error is thrown when you try to create a histogram with an empty name
  @Test(expected = IllegalArgumentException.class)
  public void testNoNameHistogram() {
    HashMap<Integer, Integer> nameless = model.getHistogram("", IModel.Component.Blue);
  }

  // checks that an error is thrown when you try to create a histogram with a name that is not in
  // the image library
  @Test(expected = IllegalArgumentException.class)
  public void testNotRealNameHistogram() {
    HashMap<Integer, Integer> notHere = model.getHistogram("no names here", IModel.Component.Blue);
  }

}
