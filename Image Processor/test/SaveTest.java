import org.junit.Before;
import org.junit.Test;

import controller.command.Load;
import controller.command.Save;
import model.EnhancedModel;
import model.EnhancedModelImpl;
import model.Image;
import model.Pixel;
import view.IView;
import view.ImageTextView;

import static org.junit.Assert.assertEquals;

/**
 * Represents the tests needed for the Save class.
 */
public class SaveTest {
  IView view;
  Appendable log;
  EnhancedModel model;
  Save invalid;
  Appendable corruptAppendable;
  IView corruptView;
  Save valid;
  EnhancedModel ppm;
  Pixel[][] smallImage;


  @Before
  public void init() {
    log = new StringBuilder();
    view = new ImageTextView(log);
    model = new MockModel(log);
    corruptAppendable = new CorruptAppendable();
    corruptView = new ImageTextView(corruptAppendable);
    valid = new Save("res/test.ppm", "test", view);
    ppm = new EnhancedModelImpl();
    smallImage = new Pixel[][]{{new Pixel(2, 2, 2), new Pixel(4, 4, 4)},
      {new Pixel(3, 3, 3), new Pixel(5, 5, 5)}};

  }


  // tests that when a null string is given for the filepath an illegal argument exception is thrown
  @Test(expected = IllegalArgumentException.class)
  public void testNullName() {
    invalid = new Save(null, "kate", view);
  }

  // tests that when an empty string is given for the filepath an illegal argument exception is
  // thrown
  @Test(expected = IllegalArgumentException.class)
  public void testEmptyName() {
    invalid = new Save("", "kate", view);
  }

  // tests that when a null string is given for the name an illegal argument exception
  // is thrown
  @Test(expected = IllegalArgumentException.class)
  public void testNullDestination() {
    invalid = new Save("res/Kate.ppm", null, view);
  }

  // tests that when an empty string is given for the name an illegal argument exception
  // is thrown
  @Test(expected = IllegalArgumentException.class)
  public void testEmptyDestination() {
    invalid = new Save("res/kate.ppm", "", view);
  }

  // tests that when a null is given for the view an illegal argument exception is thrown
  @Test(expected = IllegalArgumentException.class)
  public void testNullView() {
    invalid = new Save("res/Kate.ppm", "hey", null);
  }

  // tests that when a corrupt appendable is given to the view (always throws an IO exception), that
  // an IllegalState exception is thrown when a message is attempted to be sent (which happens when
  // the runCommand method is called).
  @Test(expected = IllegalStateException.class)
  public void testCorruptSendMessage() {
    invalid = new Save("res/wow.ppm", "wow", corruptView);
    invalid.runCommand(model);
  }

  // checks that the correct methods are called and the correct message is sent to the user when
  // the image is saved using a mock
  @Test
  public void testWorkingGoMock() {
    valid.runCommand(model);
    assertEquals("get image: test\n" +
            "image saved :)\n", log.toString());

  }

  // tests that the image is saved properly when using a real model instead of a mock as a PPM
  @Test
  public void testWorkingGoRealModelPPM() {
    ppm.addImage("small", smallImage);
    Save save = new Save("res/small.ppm", "small", view);
    save.runCommand(ppm);
    Load load = new Load("res/small.ppm", "loaded", view);
    load.runCommand(ppm);
    Image img = ppm.getImage("loaded");
    assertEquals("P3\n" + "# created by kate and neha :)\n" + "2 2\n" + "5\n" +
            "2 2 2\n" + "4 4 4\n" +
            "3 3 3\n" + "5 5 5", img.convertToText());
  }

  // tests that an image imported as a PPM can be saved as a PPM
  @Test
  public void testPPMtoPPM() {
    Load loadOriginal = new Load("test/sample.ppm", "sample", view);
    loadOriginal.runCommand(ppm);
    assertEquals("P3\n" + "# created by kate and neha :)\n" + "4 4\n" + "253\n" +
            "80 40 40\n" + "4 2 0\n" + "69 69 69\n" + "60 60 60\n" +
            "49 59 69\n" + "56 68 10\n" + "39 150 11\n" + "96 243 206\n" +
            "0 111 222\n" + "22 33 66\n" + "121 77 99\n" + "84 96 108\n" +
            "68 70 63\n" + "163 33 235\n" + "92 59 65\n" + "112 195 253",
            ppm.getImage("sample").convertToText());
    Save save = new Save("test/sampleSave.ppm", "sample", view);
    save.runCommand(ppm);
    Load loadNew = new Load("test/sampleSave.ppm", "loaded", view);
    loadNew.runCommand(ppm);
    assertEquals("P3\n" + "# created by kate and neha :)\n" + "4 4\n" + "253\n" +
                    "80 40 40\n" + "4 2 0\n" + "69 69 69\n" + "60 60 60\n" +
                    "49 59 69\n" + "56 68 10\n" + "39 150 11\n" + "96 243 206\n" +
                    "0 111 222\n" + "22 33 66\n" + "121 77 99\n" + "84 96 108\n" +
                    "68 70 63\n" + "163 33 235\n" + "92 59 65\n" + "112 195 253",
            ppm.getImage("loaded").convertToText());
  }


  //tests converting a PPM to a JPG
  //checks length of the convert to text --> jpg uses lossy compression so we cant
  //check each pixel itself --> just make sure we retain the same size array of pixels
  @Test
  public void testPPMtoJPG() {
    Load loadOriginal = new Load("test/sample.ppm", "sample", view);
    loadOriginal.runCommand(ppm);
    assertEquals(20,
            ppm.getImage("sample").convertToText().split("\n").length);
    Save save = new Save("test/sampleSave.jpg", "sample", view);
    save.runCommand(ppm);
    Load loadNew = new Load("test/sampleSave.jpg", "loaded", view);
    loadNew.runCommand(ppm);
    assertEquals(20,
            ppm.getImage("loaded").convertToText().split("\n").length);
  }

  // tests converting a PPM to a PNG
  @Test
  public void testPPMtoPNG() {
    Load loadOriginal = new Load("test/sample.ppm", "sample", view);
    loadOriginal.runCommand(ppm);
    assertEquals("P3\n" + "# created by kate and neha :)\n" + "4 4\n" + "253\n" +
                    "80 40 40\n" + "4 2 0\n" + "69 69 69\n" + "60 60 60\n" +
                    "49 59 69\n" + "56 68 10\n" + "39 150 11\n" + "96 243 206\n" +
                    "0 111 222\n" + "22 33 66\n" + "121 77 99\n" + "84 96 108\n" +
                    "68 70 63\n" + "163 33 235\n" + "92 59 65\n" + "112 195 253",
            ppm.getImage("sample").convertToText());
    Save save = new Save("test/sample.png", "sample", view);
    save.runCommand(ppm);
    Load loadNew = new Load("test/sample.png", "loaded", view);
    loadNew.runCommand(ppm);
    assertEquals("P3\n" + "# created by kate and neha :)\n" + "4 4\n" + "253\n" +
                    "80 40 40\n" + "4 2 0\n" + "69 69 69\n" + "60 60 60\n" +
                    "49 59 69\n" + "56 68 10\n" + "39 150 11\n" + "96 243 206\n" +
                    "0 111 222\n" + "22 33 66\n" + "121 77 99\n" + "84 96 108\n" +
                    "68 70 63\n" + "163 33 235\n" + "92 59 65\n" + "112 195 253",
            ppm.getImage("loaded").convertToText());
  }

  // tests converting a PPM to a BMP
  @Test
  public void testPPMtoBMP() {
    Load loadOriginal = new Load("test/sample.ppm", "sample", view);
    loadOriginal.runCommand(ppm);
    assertEquals("P3\n" + "# created by kate and neha :)\n" + "4 4\n" + "253\n" +
                    "80 40 40\n" + "4 2 0\n" + "69 69 69\n" + "60 60 60\n" +
                    "49 59 69\n" + "56 68 10\n" + "39 150 11\n" + "96 243 206\n" +
                    "0 111 222\n" + "22 33 66\n" + "121 77 99\n" + "84 96 108\n" +
                    "68 70 63\n" + "163 33 235\n" + "92 59 65\n" + "112 195 253",
            ppm.getImage("sample").convertToText());
    Save save = new Save("test/sample.bmp", "sample", view);
    save.runCommand(ppm);
    Load loadNew = new Load("test/sample.bmp", "loaded", view);
    loadNew.runCommand(ppm);
    assertEquals("P3\n" + "# created by kate and neha :)\n" + "4 4\n" + "253\n" +
                    "80 40 40\n" + "4 2 0\n" + "69 69 69\n" + "60 60 60\n" +
                    "49 59 69\n" + "56 68 10\n" + "39 150 11\n" + "96 243 206\n" +
                    "0 111 222\n" + "22 33 66\n" + "121 77 99\n" + "84 96 108\n" +
                    "68 70 63\n" + "163 33 235\n" + "92 59 65\n" + "112 195 253",
            ppm.getImage("loaded").convertToText());
  }

  // tests converting a PNG to a PNG
  @Test
  public void testPNGtoPNG() {
    Load loadOriginal = new Load("test/blueYellow.png", "sample", view);
    loadOriginal.runCommand(ppm);
    assertEquals("P3\n" + "# created by kate and neha :)\n" + "5 5\n" + "255\n" +
                    "0 0 255\n" + "0 0 255\n" + "0 0 255\n" + "0 0 255\n" + "0 0 255\n" +
                    "0 0 255\n" + "255 255 0\n" + "255 255 0\n" + "255 255 0\n" + "0 0 255\n" +
                    "0 0 255\n" + "255 255 0\n" + "255 255 0\n" + "255 255 0\n" + "0 0 255\n" +
                    "0 0 255\n" + "255 255 0\n" + "255 255 0\n" + "255 255 0\n" + "0 0 255\n" +
                    "0 0 255\n" + "0 0 255\n" + "0 0 255\n" + "0 0 255\n" + "0 0 255",
            ppm.getImage("sample").convertToText());
    Save save = new Save("test/blueYellow2.png", "sample", view);
    save.runCommand(ppm);
    Load loadNew = new Load("test/blueYellow2.png", "loaded", view);
    loadNew.runCommand(ppm);
    assertEquals("P3\n" + "# created by kate and neha :)\n" + "5 5\n" + "255\n" +
                    "0 0 255\n" + "0 0 255\n" + "0 0 255\n" + "0 0 255\n" + "0 0 255\n" +
                    "0 0 255\n" + "255 255 0\n" + "255 255 0\n" + "255 255 0\n" + "0 0 255\n" +
                    "0 0 255\n" + "255 255 0\n" + "255 255 0\n" + "255 255 0\n" + "0 0 255\n" +
                    "0 0 255\n" + "255 255 0\n" + "255 255 0\n" + "255 255 0\n" + "0 0 255\n" +
                    "0 0 255\n" + "0 0 255\n" + "0 0 255\n" + "0 0 255\n" + "0 0 255",
            ppm.getImage("loaded").convertToText());
  }

  //tests converting a PNG to a JPG
  //checks length of the convert to text --> jpg uses lossy compression so we cant
  //check each pixel itself --> just make sure we retain the same size array of pixels
  @Test
  public void testPNGtoJPG() {
    Load loadOriginal = new Load("test/blueYellow.png", "sample", view);
    loadOriginal.runCommand(ppm);
    assertEquals(29,
            ppm.getImage("sample").convertToText().split("\n").length);
    Save save = new Save("test/blueYellow2.jpg", "sample", view);
    save.runCommand(ppm);
    Load loadNew = new Load("test/blueYellow2.jpg", "loaded", view);
    loadNew.runCommand(ppm);
    assertEquals(29,
            ppm.getImage("loaded").convertToText().split("\n").length);
  }

  // tests converting a PNG to a PPM
  @Test
  public void testPNGtoPPM() {
    Load loadOriginal = new Load("test/blueYellow.png", "sample", view);
    loadOriginal.runCommand(ppm);
    assertEquals("P3\n" + "# created by kate and neha :)\n" + "5 5\n" + "255\n" +
                    "0 0 255\n" + "0 0 255\n" + "0 0 255\n" + "0 0 255\n" + "0 0 255\n" +
                    "0 0 255\n" + "255 255 0\n" + "255 255 0\n" + "255 255 0\n" + "0 0 255\n" +
                    "0 0 255\n" + "255 255 0\n" + "255 255 0\n" + "255 255 0\n" + "0 0 255\n" +
                    "0 0 255\n" + "255 255 0\n" + "255 255 0\n" + "255 255 0\n" + "0 0 255\n" +
                    "0 0 255\n" + "0 0 255\n" + "0 0 255\n" + "0 0 255\n" + "0 0 255",
            ppm.getImage("sample").convertToText());
    Save save = new Save("test/blueYellow.ppm", "sample", view);
    save.runCommand(ppm);
    Load loadNew = new Load("test/blueYellow.ppm", "loaded", view);
    loadNew.runCommand(ppm);
    assertEquals("P3\n" + "# created by kate and neha :)\n" + "5 5\n" + "255\n" +
                    "0 0 255\n" + "0 0 255\n" + "0 0 255\n" + "0 0 255\n" + "0 0 255\n" +
                    "0 0 255\n" + "255 255 0\n" + "255 255 0\n" + "255 255 0\n" + "0 0 255\n" +
                    "0 0 255\n" + "255 255 0\n" + "255 255 0\n" + "255 255 0\n" + "0 0 255\n" +
                    "0 0 255\n" + "255 255 0\n" + "255 255 0\n" + "255 255 0\n" + "0 0 255\n" +
                    "0 0 255\n" + "0 0 255\n" + "0 0 255\n" + "0 0 255\n" + "0 0 255",
            ppm.getImage("loaded").convertToText());
  }

  // tests converting a PNG to a BMP
  @Test
  public void testPNGtoBMP() {
    Load loadOriginal = new Load("test/blueYellow.png", "sample", view);
    loadOriginal.runCommand(ppm);
    assertEquals("P3\n" + "# created by kate and neha :)\n" + "5 5\n" + "255\n" +
                    "0 0 255\n" + "0 0 255\n" + "0 0 255\n" + "0 0 255\n" + "0 0 255\n" +
                    "0 0 255\n" + "255 255 0\n" + "255 255 0\n" + "255 255 0\n" + "0 0 255\n" +
                    "0 0 255\n" + "255 255 0\n" + "255 255 0\n" + "255 255 0\n" + "0 0 255\n" +
                    "0 0 255\n" + "255 255 0\n" + "255 255 0\n" + "255 255 0\n" + "0 0 255\n" +
                    "0 0 255\n" + "0 0 255\n" + "0 0 255\n" + "0 0 255\n" + "0 0 255",
            ppm.getImage("sample").convertToText());
    Save save = new Save("test/blueYellow.bmp", "sample", view);
    save.runCommand(ppm);
    Load loadNew = new Load("test/blueYellow.bmp", "loaded", view);
    loadNew.runCommand(ppm);
    assertEquals("P3\n" + "# created by kate and neha :)\n" + "5 5\n" + "255\n" +
                    "0 0 255\n" + "0 0 255\n" + "0 0 255\n" + "0 0 255\n" + "0 0 255\n" +
                    "0 0 255\n" + "255 255 0\n" + "255 255 0\n" + "255 255 0\n" + "0 0 255\n" +
                    "0 0 255\n" + "255 255 0\n" + "255 255 0\n" + "255 255 0\n" + "0 0 255\n" +
                    "0 0 255\n" + "255 255 0\n" + "255 255 0\n" + "255 255 0\n" + "0 0 255\n" +
                    "0 0 255\n" + "0 0 255\n" + "0 0 255\n" + "0 0 255\n" + "0 0 255",
            ppm.getImage("loaded").convertToText());
  }

  // tests converting a BMP to a BMP
  @Test
  public void testBMPtoBMP() {
    Load loadOriginal = new Load("test/redBlue.bmp", "sample", view);
    loadOriginal.runCommand(ppm);
    assertEquals("P3\n" + "# created by kate and neha :)\n" + "5 5\n" + "255\n" +
                    "0 0 255\n" + "0 0 255\n" + "0 0 255\n" + "255 0 0\n" + "255 0 0\n" +
                    "0 0 255\n" + "0 0 255\n" + "0 0 255\n" + "255 0 0\n" + "255 0 0\n" +
                    "0 0 255\n" + "0 0 255\n" + "0 0 255\n" + "255 0 0\n" + "255 0 0\n" +
                    "255 0 0\n" + "255 0 0\n" + "255 0 0\n" + "255 0 0\n" + "255 0 0\n" +
                    "255 0 0\n" + "255 0 0\n" + "255 0 0\n" + "255 0 0\n" + "255 0 0",
            ppm.getImage("sample").convertToText());
    Save save = new Save("test/redBlue2.bmp", "sample", view);
    save.runCommand(ppm);
    Load loadNew = new Load("test/redBlue2.bmp", "loaded", view);
    loadNew.runCommand(ppm);
    assertEquals("P3\n" + "# created by kate and neha :)\n" + "5 5\n" + "255\n" +
                    "0 0 255\n" + "0 0 255\n" + "0 0 255\n" + "255 0 0\n" + "255 0 0\n" +
                    "0 0 255\n" + "0 0 255\n" + "0 0 255\n" + "255 0 0\n" + "255 0 0\n" +
                    "0 0 255\n" + "0 0 255\n" + "0 0 255\n" + "255 0 0\n" + "255 0 0\n" +
                    "255 0 0\n" + "255 0 0\n" + "255 0 0\n" + "255 0 0\n" + "255 0 0\n" +
                    "255 0 0\n" + "255 0 0\n" + "255 0 0\n" + "255 0 0\n" + "255 0 0",
            ppm.getImage("loaded").convertToText());
  }

  //tests converting a BMP to a JPG
  //checks length of the convert to text --> jpg uses lossy compression so we cant
  //check each pixel itself --> just make sure we retain the same size array of pixels
  @Test
  public void testBMPtoJPG() {
    Load loadOriginal = new Load("test/redBlue.bmp", "sample", view);
    loadOriginal.runCommand(ppm);
    assertEquals(29,
            ppm.getImage("sample").convertToText().split("\n").length);
    Save save = new Save("test/redBlue2.jpg", "sample", view);
    save.runCommand(ppm);
    Load loadNew = new Load("test/redBlue2.jpg", "loaded", view);
    loadNew.runCommand(ppm);
    assertEquals(29,
            ppm.getImage("loaded").convertToText().split("\n").length);
  }

  // tests converting a BMP to a PPM
  @Test
  public void testBMPtoPPM() {
    Load loadOriginal = new Load("test/redBlue.bmp", "sample", view);
    loadOriginal.runCommand(ppm);
    assertEquals("P3\n" + "# created by kate and neha :)\n" + "5 5\n" + "255\n" +
                    "0 0 255\n" + "0 0 255\n" + "0 0 255\n" + "255 0 0\n" + "255 0 0\n" +
                    "0 0 255\n" + "0 0 255\n" + "0 0 255\n" + "255 0 0\n" + "255 0 0\n" +
                    "0 0 255\n" + "0 0 255\n" + "0 0 255\n" + "255 0 0\n" + "255 0 0\n" +
                    "255 0 0\n" + "255 0 0\n" + "255 0 0\n" + "255 0 0\n" + "255 0 0\n" +
                    "255 0 0\n" + "255 0 0\n" + "255 0 0\n" + "255 0 0\n" + "255 0 0",
            ppm.getImage("sample").convertToText());
    Save save = new Save("test/redBlue.ppm", "sample", view);
    save.runCommand(ppm);
    Load loadNew = new Load("test/redBlue.ppm", "loaded", view);
    loadNew.runCommand(ppm);
    assertEquals("P3\n" + "# created by kate and neha :)\n" + "5 5\n" + "255\n" +
                    "0 0 255\n" + "0 0 255\n" + "0 0 255\n" + "255 0 0\n" + "255 0 0\n" +
                    "0 0 255\n" + "0 0 255\n" + "0 0 255\n" + "255 0 0\n" + "255 0 0\n" +
                    "0 0 255\n" + "0 0 255\n" + "0 0 255\n" + "255 0 0\n" + "255 0 0\n" +
                    "255 0 0\n" + "255 0 0\n" + "255 0 0\n" + "255 0 0\n" + "255 0 0\n" +
                    "255 0 0\n" + "255 0 0\n" + "255 0 0\n" + "255 0 0\n" + "255 0 0",
            ppm.getImage("loaded").convertToText());
  }

  // tests converting a BMP to a PNG
  @Test
  public void testBMPtoPNG() {
    Load loadOriginal = new Load("test/redBlue.bmp", "sample", view);
    loadOriginal.runCommand(ppm);
    assertEquals("P3\n" + "# created by kate and neha :)\n" + "5 5\n" + "255\n" +
                    "0 0 255\n" + "0 0 255\n" + "0 0 255\n" + "255 0 0\n" + "255 0 0\n" +
                    "0 0 255\n" + "0 0 255\n" + "0 0 255\n" + "255 0 0\n" + "255 0 0\n" +
                    "0 0 255\n" + "0 0 255\n" + "0 0 255\n" + "255 0 0\n" + "255 0 0\n" +
                    "255 0 0\n" + "255 0 0\n" + "255 0 0\n" + "255 0 0\n" + "255 0 0\n" +
                    "255 0 0\n" + "255 0 0\n" + "255 0 0\n" + "255 0 0\n" + "255 0 0",
            ppm.getImage("sample").convertToText());
    Save save = new Save("test/redBlue.png", "sample", view);
    save.runCommand(ppm);
    Load loadNew = new Load("test/redBlue.png", "loaded", view);
    loadNew.runCommand(ppm);
    assertEquals("P3\n" + "# created by kate and neha :)\n" + "5 5\n" + "255\n" +
                    "0 0 255\n" + "0 0 255\n" + "0 0 255\n" + "255 0 0\n" + "255 0 0\n" +
                    "0 0 255\n" + "0 0 255\n" + "0 0 255\n" + "255 0 0\n" + "255 0 0\n" +
                    "0 0 255\n" + "0 0 255\n" + "0 0 255\n" + "255 0 0\n" + "255 0 0\n" +
                    "255 0 0\n" + "255 0 0\n" + "255 0 0\n" + "255 0 0\n" + "255 0 0\n" +
                    "255 0 0\n" + "255 0 0\n" + "255 0 0\n" + "255 0 0\n" + "255 0 0",
            ppm.getImage("loaded").convertToText());
  }

  // tests converting a JPG to a JPG
  @Test
  public void testJPGtoJPG() {
    Load loadOriginal = new Load("test/red.jpg", "sample", view);
    loadOriginal.runCommand(ppm);
    assertEquals("P3\n" + "# created by kate and neha :)\n" + "5 5\n" + "254\n" +
                    "254 0 0\n" + "254 0 0\n" + "254 0 0\n" + "254 0 0\n" + "254 0 0\n" +
                    "254 0 0\n" + "254 0 0\n" + "254 0 0\n" + "254 0 0\n" + "254 0 0\n" +
                    "254 0 0\n" + "254 0 0\n" + "254 0 0\n" + "254 0 0\n" + "254 0 0\n" +
                    "254 0 0\n" + "254 0 0\n" + "254 0 0\n" + "254 0 0\n" + "254 0 0\n" +
                    "254 0 0\n" + "254 0 0\n" + "254 0 0\n" + "254 0 0\n" + "254 0 0",
            ppm.getImage("sample").convertToText());
    Save save = new Save("test/red2.jpg", "sample", view);
    save.runCommand(ppm);
    Load loadNew = new Load("test/red2.jpg", "loaded", view);
    loadNew.runCommand(ppm);
    assertEquals("P3\n" + "# created by kate and neha :)\n" + "5 5\n" + "254\n" +
                    "254 0 0\n" + "254 0 0\n" + "254 0 0\n" + "254 0 0\n" + "254 0 0\n" +
                    "254 0 0\n" + "254 0 0\n" + "254 0 0\n" + "254 0 0\n" + "254 0 0\n" +
                    "254 0 0\n" + "254 0 0\n" + "254 0 0\n" + "254 0 0\n" + "254 0 0\n" +
                    "254 0 0\n" + "254 0 0\n" + "254 0 0\n" + "254 0 0\n" + "254 0 0\n" +
                    "254 0 0\n" + "254 0 0\n" + "254 0 0\n" + "254 0 0\n" + "254 0 0",
            ppm.getImage("loaded").convertToText());
  }

  // tests converting a JPG to a PPM
  @Test
  public void testJPGtoPPM() {
    Load loadOriginal = new Load("test/red.jpg", "sample", view);
    loadOriginal.runCommand(ppm);
    assertEquals("P3\n" + "# created by kate and neha :)\n" + "5 5\n" + "254\n" +
                    "254 0 0\n" + "254 0 0\n" + "254 0 0\n" + "254 0 0\n" + "254 0 0\n" +
                    "254 0 0\n" + "254 0 0\n" + "254 0 0\n" + "254 0 0\n" + "254 0 0\n" +
                    "254 0 0\n" + "254 0 0\n" + "254 0 0\n" + "254 0 0\n" + "254 0 0\n" +
                    "254 0 0\n" + "254 0 0\n" + "254 0 0\n" + "254 0 0\n" + "254 0 0\n" +
                    "254 0 0\n" + "254 0 0\n" + "254 0 0\n" + "254 0 0\n" + "254 0 0",
            ppm.getImage("sample").convertToText());
    Save save = new Save("test/red.ppm", "sample", view);
    save.runCommand(ppm);
    Load loadNew = new Load("test/red.ppm", "loaded", view);
    loadNew.runCommand(ppm);
    assertEquals("P3\n" + "# created by kate and neha :)\n" + "5 5\n" + "254\n" +
                    "254 0 0\n" + "254 0 0\n" + "254 0 0\n" + "254 0 0\n" + "254 0 0\n" +
                    "254 0 0\n" + "254 0 0\n" + "254 0 0\n" + "254 0 0\n" + "254 0 0\n" +
                    "254 0 0\n" + "254 0 0\n" + "254 0 0\n" + "254 0 0\n" + "254 0 0\n" +
                    "254 0 0\n" + "254 0 0\n" + "254 0 0\n" + "254 0 0\n" + "254 0 0\n" +
                    "254 0 0\n" + "254 0 0\n" + "254 0 0\n" + "254 0 0\n" + "254 0 0",
            ppm.getImage("loaded").convertToText());
  }

  // tests converting a JPG to a PNG
  @Test
  public void testJPGtoPNG() {
    Load loadOriginal = new Load("test/red.jpg", "sample", view);
    loadOriginal.runCommand(ppm);
    assertEquals("P3\n" + "# created by kate and neha :)\n" + "5 5\n" + "254\n" +
                    "254 0 0\n" + "254 0 0\n" + "254 0 0\n" + "254 0 0\n" + "254 0 0\n" +
                    "254 0 0\n" + "254 0 0\n" + "254 0 0\n" + "254 0 0\n" + "254 0 0\n" +
                    "254 0 0\n" + "254 0 0\n" + "254 0 0\n" + "254 0 0\n" + "254 0 0\n" +
                    "254 0 0\n" + "254 0 0\n" + "254 0 0\n" + "254 0 0\n" + "254 0 0\n" +
                    "254 0 0\n" + "254 0 0\n" + "254 0 0\n" + "254 0 0\n" + "254 0 0",
            ppm.getImage("sample").convertToText());
    Save save = new Save("test/red.png", "sample", view);
    save.runCommand(ppm);
    Load loadNew = new Load("test/red.png", "loaded", view);
    loadNew.runCommand(ppm);
    assertEquals("P3\n" + "# created by kate and neha :)\n" + "5 5\n" + "254\n" +
                    "254 0 0\n" + "254 0 0\n" + "254 0 0\n" + "254 0 0\n" + "254 0 0\n" +
                    "254 0 0\n" + "254 0 0\n" + "254 0 0\n" + "254 0 0\n" + "254 0 0\n" +
                    "254 0 0\n" + "254 0 0\n" + "254 0 0\n" + "254 0 0\n" + "254 0 0\n" +
                    "254 0 0\n" + "254 0 0\n" + "254 0 0\n" + "254 0 0\n" + "254 0 0\n" +
                    "254 0 0\n" + "254 0 0\n" + "254 0 0\n" + "254 0 0\n" + "254 0 0",
            ppm.getImage("loaded").convertToText());
  }

  // tests converting a JPG to a BMP
  @Test
  public void testJPGtoBMP() {
    Load loadOriginal = new Load("test/red.jpg", "sample", view);
    loadOriginal.runCommand(ppm);
    assertEquals("P3\n" + "# created by kate and neha :)\n" + "5 5\n" + "254\n" +
                    "254 0 0\n" + "254 0 0\n" + "254 0 0\n" + "254 0 0\n" + "254 0 0\n" +
                    "254 0 0\n" + "254 0 0\n" + "254 0 0\n" + "254 0 0\n" + "254 0 0\n" +
                    "254 0 0\n" + "254 0 0\n" + "254 0 0\n" + "254 0 0\n" + "254 0 0\n" +
                    "254 0 0\n" + "254 0 0\n" + "254 0 0\n" + "254 0 0\n" + "254 0 0\n" +
                    "254 0 0\n" + "254 0 0\n" + "254 0 0\n" + "254 0 0\n" + "254 0 0",
            ppm.getImage("sample").convertToText());
    Save save = new Save("test/red.bmp", "sample", view);
    save.runCommand(ppm);
    Load loadNew = new Load("test/red.bmp", "loaded", view);
    loadNew.runCommand(ppm);
    assertEquals("P3\n" + "# created by kate and neha :)\n" + "5 5\n" + "254\n" +
                    "254 0 0\n" + "254 0 0\n" + "254 0 0\n" + "254 0 0\n" + "254 0 0\n" +
                    "254 0 0\n" + "254 0 0\n" + "254 0 0\n" + "254 0 0\n" + "254 0 0\n" +
                    "254 0 0\n" + "254 0 0\n" + "254 0 0\n" + "254 0 0\n" + "254 0 0\n" +
                    "254 0 0\n" + "254 0 0\n" + "254 0 0\n" + "254 0 0\n" + "254 0 0\n" +
                    "254 0 0\n" + "254 0 0\n" + "254 0 0\n" + "254 0 0\n" + "254 0 0",
            ppm.getImage("loaded").convertToText());
  }
}