import org.junit.Before;
import org.junit.Test;

import java.awt.Color;

import controller.command.Load;
import model.EnhancedModel;
import model.EnhancedModelImpl;
import model.Image;
import view.IView;
import view.ImageTextView;

import static org.junit.Assert.assertEquals;

/**
 * Represents the tests needed for the Load class.
 */
public class LoadTest {
  IView view;
  Appendable log;
  EnhancedModel model;
  Load invalid;
  Appendable corruptAppendable;
  IView corruptView;
  Load valid;
  EnhancedModel ppm;


  @Before
  public void init() {
    log = new StringBuilder();
    view = new ImageTextView(log);
    model = new MockModel(log);
    corruptAppendable = new CorruptAppendable();
    corruptView = new ImageTextView(corruptAppendable);
    valid = new Load("test/sample.ppm", "test", view);
    ppm = new EnhancedModelImpl();

  }


  // tests that when a null filePath is given for the name an illegal argument exception is thrown
  @Test(expected = IllegalArgumentException.class)
  public void testNullName() {
    invalid = new Load(null, "kate", view);
  }

  // tests that when an empty string is given for the filePath an illegal argument exception
  // is thrown
  @Test(expected = IllegalArgumentException.class)
  public void testEmptyName() {
    invalid = new Load("", "kate", view);
  }

  // tests that when a null string is given for the name an illegal argument exception
  // is thrown
  @Test(expected = IllegalArgumentException.class)
  public void testNullDestination() {
    invalid = new Load("res/flower.ppm", null, view);
  }

  // tests that when an empty string is given for the name an illegal argument exception
  // is thrown
  @Test(expected = IllegalArgumentException.class)
  public void testEmptyDestination() {
    invalid = new Load("res/flower.ppm", "", view);
  }

  // tests that when a null is given for the view an illegal argument exception is thrown
  @Test(expected = IllegalArgumentException.class)
  public void testNullView() {
    invalid = new Load("kate", "hey", null);
  }

  // tests that when a corrupt appendable is given to the view (always throws an IO exception), that
  // an IllegalState exception is thrown when a message is attempted to be sent (which happens when
  // the runCommand method is called).
  @Test(expected = IllegalStateException.class)
  public void testCorruptSendMessage() {
    invalid = new Load("not a real file path", "wow", corruptView);
    invalid.runCommand(model);
  }

  // checks that the correct methods are called and the correct message is sent to the user when
  // the image is loaded using a mock
  @Test
  public void testWorkingGoMock() {
    valid.runCommand(model);
    assertEquals("add image: test 4\n" +
            "image loaded!\n", log.toString());

  }

  // tests that a PPM image is loaded properly when using a real model instead of a mock
  @Test
  public void testWorkingGoRealModelPPM() {
    valid.runCommand(ppm);
    Image img = ppm.getImage("test");
    assertEquals("P3\n" + "# created by kate and neha :)\n" + "4 4\n" + "253\n" +
            "80 40 40\n" + "4 2 0\n" + "69 69 69\n" + "60 60 60\n" +
            "49 59 69\n" + "56 68 10\n" + "39 150 11\n" + "96 243 206\n" +
            "0 111 222\n" + "22 33 66\n" + "121 77 99\n" + "84 96 108\n" +
            "68 70 63\n" + "163 33 235\n" + "92 59 65\n" + "112 195 253", img.convertToText());
  }

  // test that the alpha values are correct when loading a jpg
  @Test
  public void testAlphaPPM() {
    valid.runCommand(ppm);
    Image test = ppm.getImage("test");
    Color zeroZero = new Color(test.convertToBufferedImage("png").getRGB(0, 0), true);
    assertEquals(255, zeroZero.getAlpha());
    Color zeroOne = new Color(test.convertToBufferedImage("png").getRGB(0, 1), true);
    assertEquals(255, zeroOne.getAlpha());
    Color zeroTwo = new Color(test.convertToBufferedImage("png").getRGB(0, 2), true);
    assertEquals(255, zeroTwo.getAlpha());
    Color zeroThree = new Color(test.convertToBufferedImage("png").getRGB(0, 3), true);
    assertEquals(255, zeroThree.getAlpha());


    Color oneZero = new Color(test.convertToBufferedImage("png").getRGB(1, 0), true);
    assertEquals(255, oneZero.getAlpha());
    Color oneOne = new Color(test.convertToBufferedImage("png").getRGB(1, 1), true);
    assertEquals(255, oneOne.getAlpha());
    Color oneTwo = new Color(test.convertToBufferedImage("png").getRGB(1, 2), true);
    assertEquals(255, oneTwo.getAlpha());
    Color oneThree = new Color(test.convertToBufferedImage("png").getRGB(1, 3), true);
    assertEquals(255, oneThree.getAlpha());

    Color twoZero = new Color(test.convertToBufferedImage("png").getRGB(2, 0), true);
    assertEquals(255, twoZero.getAlpha());
    Color twoOne = new Color(test.convertToBufferedImage("png").getRGB(2, 1), true);
    assertEquals(255, twoOne.getAlpha());
    Color twoTwo = new Color(test.convertToBufferedImage("png").getRGB(2, 2), true);
    assertEquals(255, twoTwo.getAlpha());
    Color twoThree = new Color(test.convertToBufferedImage("png").getRGB(2, 3), true);
    assertEquals(255, twoThree.getAlpha());

    Color threeZero = new Color(test.convertToBufferedImage("png").getRGB(3, 0), true);
    assertEquals(255, threeZero.getAlpha());
    Color threeOne = new Color(test.convertToBufferedImage("png").getRGB(3, 1), true);
    assertEquals(255, threeOne.getAlpha());
    Color threeTwo = new Color(test.convertToBufferedImage("png").getRGB(3, 2), true);
    assertEquals(255, threeTwo.getAlpha());
    Color threeThree = new Color(test.convertToBufferedImage("png").getRGB(3, 3), true);
    assertEquals(255, threeThree.getAlpha());
  }


  // tests that when a not real file path is given, the correct message is sent to the user
  @Test
  public void testNotRealFilePath() {
    invalid = new Load("fake.ppm", "slime", view);
    invalid.runCommand(model);
    assertEquals("error loading file\n", log.toString());
  }

  // tests that when a file that does not start with P3 is given as the file to load, the correct
  // message is sent to the user
  @Test
  public void testNotPPM() {
    invalid = new Load("test/fakePPM.ppm", "heyyy", view);
    invalid.runCommand(model);
    assertEquals("error loading file\n", log.toString());
  }

  // test that when loading a jpg, the right image is created
  @Test
  public void testJPG() {
    Load valid2 = new Load("test/red.jpg", "red", view);
    valid2.runCommand(ppm);
    Image red = ppm.getImage("red");
    assertEquals("P3\n" + "# created by kate and neha :)\n" + "5 5\n" + "254\n" +
            "254 0 0\n" + "254 0 0\n" + "254 0 0\n" + "254 0 0\n" + "254 0 0\n" +
            "254 0 0\n" + "254 0 0\n" + "254 0 0\n" + "254 0 0\n" + "254 0 0\n" +
            "254 0 0\n" + "254 0 0\n" + "254 0 0\n" + "254 0 0\n" + "254 0 0\n" +
            "254 0 0\n" + "254 0 0\n" + "254 0 0\n" + "254 0 0\n" + "254 0 0\n" +
            "254 0 0\n" + "254 0 0\n" + "254 0 0\n" + "254 0 0\n" + "254 0 0", red.convertToText());
  }

  // test that the alpha values are correct when loading a jpg
  @Test
  public void testAlphaJPG() {
    Load valid2 = new Load("test/red.jpg", "red", view);
    valid2.runCommand(ppm);
    Image red = ppm.getImage("red");
    Color zeroZero = new Color(red.convertToBufferedImage("png").getRGB(0, 0), true);
    assertEquals(255, zeroZero.getAlpha());
    Color zeroOne = new Color(red.convertToBufferedImage("png").getRGB(0, 1), true);
    assertEquals(255, zeroOne.getAlpha());
    Color zeroTwo = new Color(red.convertToBufferedImage("png").getRGB(0, 2), true);
    assertEquals(255, zeroTwo.getAlpha());
    Color zeroThree = new Color(red.convertToBufferedImage("png").getRGB(0, 3), true);
    assertEquals(255, zeroThree.getAlpha());
    Color zeroFour = new Color(red.convertToBufferedImage("png").getRGB(0, 4), true);
    assertEquals(255, zeroFour.getAlpha());

    Color oneZero = new Color(red.convertToBufferedImage("png").getRGB(1, 0), true);
    assertEquals(255, oneZero.getAlpha());
    Color oneOne = new Color(red.convertToBufferedImage("png").getRGB(1, 1), true);
    assertEquals(255, oneOne.getAlpha());
    Color oneTwo = new Color(red.convertToBufferedImage("png").getRGB(1, 2), true);
    assertEquals(255, oneTwo.getAlpha());
    Color oneThree = new Color(red.convertToBufferedImage("png").getRGB(1, 3), true);
    assertEquals(255, oneThree.getAlpha());
    Color oneFour = new Color(red.convertToBufferedImage("png").getRGB(1, 4), true);
    assertEquals(255, oneFour.getAlpha());

    Color twoZero = new Color(red.convertToBufferedImage("png").getRGB(2, 0), true);
    assertEquals(255, twoZero.getAlpha());
    Color twoOne = new Color(red.convertToBufferedImage("png").getRGB(2, 1), true);
    assertEquals(255, twoOne.getAlpha());
    Color twoTwo = new Color(red.convertToBufferedImage("png").getRGB(2, 2), true);
    assertEquals(255, twoTwo.getAlpha());
    Color twoThree = new Color(red.convertToBufferedImage("png").getRGB(2, 3), true);
    assertEquals(255, twoThree.getAlpha());
    Color twoFour = new Color(red.convertToBufferedImage("png").getRGB(2, 4), true);
    assertEquals(255, twoFour.getAlpha());

    Color threeZero = new Color(red.convertToBufferedImage("png").getRGB(3, 0), true);
    assertEquals(255, threeZero.getAlpha());
    Color threeOne = new Color(red.convertToBufferedImage("png").getRGB(3, 1), true);
    assertEquals(255, threeOne.getAlpha());
    Color threeTwo = new Color(red.convertToBufferedImage("png").getRGB(3, 2), true);
    assertEquals(255, threeTwo.getAlpha());
    Color threeThree = new Color(red.convertToBufferedImage("png").getRGB(3, 3), true);
    assertEquals(255, threeThree.getAlpha());
    Color threeFour = new Color(red.convertToBufferedImage("png").getRGB(3, 4), true);
    assertEquals(255, threeFour.getAlpha());

    Color fourZero = new Color(red.convertToBufferedImage("png").getRGB(4, 0), true);
    assertEquals(255, fourZero.getAlpha());
    Color fourOne = new Color(red.convertToBufferedImage("png").getRGB(4, 1), true);
    assertEquals(255, fourOne.getAlpha());
    Color fourTwo = new Color(red.convertToBufferedImage("png").getRGB(4, 2), true);
    assertEquals(255, fourTwo.getAlpha());
    Color fourThree = new Color(red.convertToBufferedImage("png").getRGB(4, 3), true);
    assertEquals(255, fourThree.getAlpha());
    Color fourFour = new Color(red.convertToBufferedImage("png").getRGB(4, 4), true);
    assertEquals(255, fourFour.getAlpha());
  }


  // test that when loading a bmp, the right image is created
  @Test
  public void testBMP() {
    Load valid2 = new Load("test/redBlue.bmp", "rb", view);
    valid2.runCommand(ppm);
    Image rb = ppm.getImage("rb");
    assertEquals("P3\n" + "# created by kate and neha :)\n" + "5 5\n" + "255\n" +
            "0 0 255\n" + "0 0 255\n" + "0 0 255\n" + "255 0 0\n" + "255 0 0\n" +
            "0 0 255\n" + "0 0 255\n" + "0 0 255\n" + "255 0 0\n" + "255 0 0\n" +
            "0 0 255\n" + "0 0 255\n" + "0 0 255\n" + "255 0 0\n" + "255 0 0\n" +
            "255 0 0\n" + "255 0 0\n" + "255 0 0\n" + "255 0 0\n" + "255 0 0\n" +
            "255 0 0\n" + "255 0 0\n" + "255 0 0\n" + "255 0 0\n" + "255 0 0", rb.convertToText());
  }

  // test that the alpha values are correct when loading a bmp
  @Test
  public void testAlphaBMP() {
    Load valid2 = new Load("test/redBlue.bmp", "rb", view);
    valid2.runCommand(ppm);
    Image rb = ppm.getImage("rb");
    Color zeroZero = new Color(rb.convertToBufferedImage("png").getRGB(0, 0), true);
    assertEquals(255, zeroZero.getAlpha());
    Color zeroOne = new Color(rb.convertToBufferedImage("png").getRGB(0, 1), true);
    assertEquals(255, zeroOne.getAlpha());
    Color zeroTwo = new Color(rb.convertToBufferedImage("png").getRGB(0, 2), true);
    assertEquals(255, zeroTwo.getAlpha());
    Color zeroThree = new Color(rb.convertToBufferedImage("png").getRGB(0, 3), true);
    assertEquals(255, zeroThree.getAlpha());
    Color zeroFour = new Color(rb.convertToBufferedImage("png").getRGB(0, 4), true);
    assertEquals(255, zeroFour.getAlpha());

    Color oneZero = new Color(rb.convertToBufferedImage("png").getRGB(1, 0), true);
    assertEquals(255, oneZero.getAlpha());
    Color oneOne = new Color(rb.convertToBufferedImage("png").getRGB(1, 1), true);
    assertEquals(255, oneOne.getAlpha());
    Color oneTwo = new Color(rb.convertToBufferedImage("png").getRGB(1, 2), true);
    assertEquals(255, oneTwo.getAlpha());
    Color oneThree = new Color(rb.convertToBufferedImage("png").getRGB(1, 3), true);
    assertEquals(255, oneThree.getAlpha());
    Color oneFour = new Color(rb.convertToBufferedImage("png").getRGB(1, 4), true);
    assertEquals(255, oneFour.getAlpha());

    Color twoZero = new Color(rb.convertToBufferedImage("png").getRGB(2, 0), true);
    assertEquals(255, twoZero.getAlpha());
    Color twoOne = new Color(rb.convertToBufferedImage("png").getRGB(2, 1), true);
    assertEquals(255, twoOne.getAlpha());
    Color twoTwo = new Color(rb.convertToBufferedImage("png").getRGB(2, 2), true);
    assertEquals(255, twoTwo.getAlpha());
    Color twoThree = new Color(rb.convertToBufferedImage("png").getRGB(2, 3), true);
    assertEquals(255, twoThree.getAlpha());
    Color twoFour = new Color(rb.convertToBufferedImage("png").getRGB(2, 4), true);
    assertEquals(255, twoFour.getAlpha());

    Color threeZero = new Color(rb.convertToBufferedImage("png").getRGB(3, 0), true);
    assertEquals(255, threeZero.getAlpha());
    Color threeOne = new Color(rb.convertToBufferedImage("png").getRGB(3, 1), true);
    assertEquals(255, threeOne.getAlpha());
    Color threeTwo = new Color(rb.convertToBufferedImage("png").getRGB(3, 2), true);
    assertEquals(255, threeTwo.getAlpha());
    Color threeThree = new Color(rb.convertToBufferedImage("png").getRGB(3, 3), true);
    assertEquals(255, threeThree.getAlpha());
    Color threeFour = new Color(rb.convertToBufferedImage("png").getRGB(3, 4), true);
    assertEquals(255, threeFour.getAlpha());

    Color fourZero = new Color(rb.convertToBufferedImage("png").getRGB(4, 0), true);
    assertEquals(255, fourZero.getAlpha());
    Color fourOne = new Color(rb.convertToBufferedImage("png").getRGB(4, 1), true);
    assertEquals(255, fourOne.getAlpha());
    Color fourTwo = new Color(rb.convertToBufferedImage("png").getRGB(4, 2), true);
    assertEquals(255, fourTwo.getAlpha());
    Color fourThree = new Color(rb.convertToBufferedImage("png").getRGB(4, 3), true);
    assertEquals(255, fourThree.getAlpha());
    Color fourFour = new Color(rb.convertToBufferedImage("png").getRGB(4, 4), true);
    assertEquals(255, fourFour.getAlpha());
  }

  // test that when loading a png, the right image is created
  @Test
  public void testPNG() {
    Load valid2 = new Load("test/blueYellow.png", "by", view);
    valid2.runCommand(ppm);
    Image by = ppm.getImage("by");
    assertEquals("P3\n" + "# created by kate and neha :)\n" + "5 5\n" + "255\n" +
            "0 0 255\n" + "0 0 255\n" + "0 0 255\n" + "0 0 255\n" + "0 0 255\n" +
            "0 0 255\n" + "255 255 0\n" + "255 255 0\n" + "255 255 0\n" + "0 0 255\n" +
            "0 0 255\n" + "255 255 0\n" + "255 255 0\n" + "255 255 0\n" + "0 0 255\n" +
            "0 0 255\n" + "255 255 0\n" + "255 255 0\n" + "255 255 0\n" + "0 0 255\n" +
            "0 0 255\n" + "0 0 255\n" + "0 0 255\n" + "0 0 255\n" + "0 0 255", by.convertToText());
  }

  // test that the alpha values are correct when loading a png
  @Test
  public void testAlphaPNG() {
    Load valid2 = new Load("test/blueYellow.png", "by", view);
    valid2.runCommand(ppm);
    Image by = ppm.getImage("by");
    Color zeroZero = new Color(by.convertToBufferedImage("png").getRGB(0, 0), true);
    assertEquals(128, zeroZero.getAlpha());
    Color zeroOne = new Color(by.convertToBufferedImage("png").getRGB(0, 1), true);
    assertEquals(128, zeroOne.getAlpha());
    Color zeroTwo = new Color(by.convertToBufferedImage("png").getRGB(0, 2), true);
    assertEquals(128, zeroTwo.getAlpha());
    Color zeroThree = new Color(by.convertToBufferedImage("png").getRGB(0, 3), true);
    assertEquals(128, zeroThree.getAlpha());
    Color zeroFour = new Color(by.convertToBufferedImage("png").getRGB(0, 4), true);
    assertEquals(128, zeroFour.getAlpha());

    Color oneZero = new Color(by.convertToBufferedImage("png").getRGB(1, 0), true);
    assertEquals(128, oneZero.getAlpha());
    Color oneOne = new Color(by.convertToBufferedImage("png").getRGB(1, 1), true);
    assertEquals(255, oneOne.getAlpha());
    Color oneTwo = new Color(by.convertToBufferedImage("png").getRGB(1, 2), true);
    assertEquals(255, oneTwo.getAlpha());
    Color oneThree = new Color(by.convertToBufferedImage("png").getRGB(1, 3), true);
    assertEquals(255, oneThree.getAlpha());
    Color oneFour = new Color(by.convertToBufferedImage("png").getRGB(1, 4), true);
    assertEquals(128, oneFour.getAlpha());

    Color twoZero = new Color(by.convertToBufferedImage("png").getRGB(2, 0), true);
    assertEquals(128, twoZero.getAlpha());
    Color twoOne = new Color(by.convertToBufferedImage("png").getRGB(2, 1), true);
    assertEquals(255, twoOne.getAlpha());
    Color twoTwo = new Color(by.convertToBufferedImage("png").getRGB(2, 2), true);
    assertEquals(255, twoTwo.getAlpha());
    Color twoThree = new Color(by.convertToBufferedImage("png").getRGB(2, 3), true);
    assertEquals(255, twoThree.getAlpha());
    Color twoFour = new Color(by.convertToBufferedImage("png").getRGB(2, 4), true);
    assertEquals(128, twoFour.getAlpha());

    Color threeZero = new Color(by.convertToBufferedImage("png").getRGB(3, 0), true);
    assertEquals(128, threeZero.getAlpha());
    Color threeOne = new Color(by.convertToBufferedImage("png").getRGB(3, 1), true);
    assertEquals(255, threeOne.getAlpha());
    Color threeTwo = new Color(by.convertToBufferedImage("png").getRGB(3, 2), true);
    assertEquals(255, threeTwo.getAlpha());
    Color threeThree = new Color(by.convertToBufferedImage("png").getRGB(3, 3), true);
    assertEquals(255, threeThree.getAlpha());
    Color threeFour = new Color(by.convertToBufferedImage("png").getRGB(3, 4), true);
    assertEquals(128, threeFour.getAlpha());

    Color fourZero = new Color(by.convertToBufferedImage("png").getRGB(4, 0), true);
    assertEquals(128, fourZero.getAlpha());
    Color fourOne = new Color(by.convertToBufferedImage("png").getRGB(4, 1), true);
    assertEquals(128, fourOne.getAlpha());
    Color fourTwo = new Color(by.convertToBufferedImage("png").getRGB(4, 2), true);
    assertEquals(128, fourTwo.getAlpha());
    Color fourThree = new Color(by.convertToBufferedImage("png").getRGB(4, 3), true);
    assertEquals(128, fourThree.getAlpha());
    Color fourFour = new Color(by.convertToBufferedImage("png").getRGB(4, 4), true);
    assertEquals(128, fourFour.getAlpha());
  }


  // checks that the proper message is sent when the user gives an unsupported file type
  @Test
  public void testUnsupportedFileType() {
    invalid = new Load("res/file.gif", "wont work", view);
    invalid.runCommand(model);
    assertEquals("this is an unsupported file type.\n", log.toString());
  }

  // checks when the user gives a jpg file that does not exist
  @Test
  public void testNotRealJPG() {
    invalid = new Load("res/file.jpg", "not here", view);
    invalid.runCommand(model);
    assertEquals("this file does not exist\n", log.toString());
  }


}