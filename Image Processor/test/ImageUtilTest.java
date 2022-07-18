import org.junit.Test;

import controller.ImageUtil;
import model.IModel;
import model.Image;
import model.ModelImpl;
import model.Pixel;

import static org.junit.Assert.assertEquals;

/**
 * Represents the tests needed for the ImageUtil class.
 */
public class ImageUtilTest {

  // tests that an IllegalArgumentException is thrown when an invalid filename is given
  @Test(expected = IllegalArgumentException.class)
  public void testInvalidFilePath() {
    ImageUtil.readPPM("res/urmom.ppm");
  }

  // tests that an IllegalArgumentException is thrown when an invalid PPM file is given
  @Test(expected = IllegalArgumentException.class)
  public void testInvalidPPM() {
    ImageUtil.readPPM("test/fakePPM.ppm");
  }

  // tests that the proper PixelArray is returned when readPPM is called on a PPM file
  @Test
  public void testWorkingRead() {
    Pixel[][] arr = ImageUtil.readPPM("test/sample.ppm");
    IModel mod = new ModelImpl();
    mod.addImage("kate", arr);
    Image img = mod.getImage("kate");
    assertEquals("P3\n" + "# created by kate and neha :)\n" + "4 4\n" + "253\n" +
            "80 40 40\n" + "4 2 0\n" + "69 69 69\n" + "60 60 60\n" +
            "49 59 69\n" + "56 68 10\n" + "39 150 11\n" + "96 243 206\n" +
            "0 111 222\n" + "22 33 66\n" + "121 77 99\n" + "84 96 108\n" +
            "68 70 63\n" + "163 33 235\n" + "92 59 65\n" + "112 195 253", img.convertToText());


  }


}