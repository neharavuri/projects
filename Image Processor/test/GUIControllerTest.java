import org.junit.Before;
import org.junit.Test;

import controller.GUIController;
import model.EnhancedModel;
import model.EnhancedModelImpl;
import view.EnhancedView;

import static org.junit.Assert.assertEquals;


/**
 * Represents the tests for the GUIController.
 */
public class GUIControllerTest {
  StringBuilder log;
  EnhancedModel model;
  EnhancedModel notMock;
  EnhancedView view;
  GUIController controller;
  GUIController real;

  @Before
  public void init() {
    log = new StringBuilder();
    model = new MockModel(log);
    view = new MockGUIView(log);
    controller = new GUIController(model, view);
    notMock = new EnhancedModelImpl();
    real = new GUIController(notMock, view);
  }

  // tests that an exception is thrown when the model is null
  @Test(expected = IllegalArgumentException.class)
  public void testNullModel() {
    GUIController invalid = new GUIController(null, view);
  }

  // tests that an exception is thrown when the view is null
  @Test(expected = IllegalArgumentException.class)
  public void testNullView() {
    GUIController invalid = new GUIController(model, null);
  }

  // tests that the correct methods are called in the view when the program is started
  @Test
  public void testStartProgram() {
    controller.startProgram();
    assertEquals("makeVisible\naddFeatures\n", log.toString());
  }

  // tests that the correct methods are called when the load method is called with a valid
  // file path
  @Test
  public void testLoadValidPath() {
    controller.load("res/flower.jpg");
    String[] result = log.toString().split("\n");
    assertEquals("add image: 1 90", result[0]);
    assertEquals("renderMessage: image loaded!", result[1]);
    assertEquals("", result[2]);
    assertEquals("get image: 1", result[3]);
    assertEquals("updateImage", result[4]);
    assertEquals("drawHistogram", result[5]);
  }

  // tests that the correct methods are called when the load method is called with a file
  // path that does not exist, a real model has to be used here so the proper exceptions are thrown
  @Test
  public void testLoadInalidPath() {
    real.load("res/hey.jpg");
    String[] result = log.toString().split("\n");
    assertEquals("renderMessage: this file does not exist", result[0]);
    assertEquals("", result[1]);
    assertEquals("renderMessage: Failure loading image.", result[2]);
  }

  // tests that the correct methods are called when the load method is called for a unsupported
  // file type, a real model has to be used here so the proper exceptions are thrown
  @Test
  public void testLoadUnsupportedType() {
    real.load("earth.gif");
    String[] result = log.toString().split("\n");
    assertEquals("renderMessage: this is an unsupported file type.", result[0]);
    assertEquals("", result[1]);
    assertEquals("renderMessage: Failure loading image.", result[2]);
  }

  // tests that the correct methods are called when the save method is called for a valid
  // filepath but there is no image currently on the screen
  @Test
  public void testInvalidSave() {
    controller.save("res/kate.jpg");
    String[] result = log.toString().split("\n");
    assertEquals("renderMessage: Failure saving image.", result[0]);
  }

  // tests that the correct methods are called when the save method is called when there is an image
  // on screen and a valid filepath is given. A real model must be used here to load the image
  @Test
  public void testValidSave() {
    real.load("res/flower.jpg");
    real.save("test/guiSave.png");
    String[] result = log.toString().split("\n");
    assertEquals("renderMessage: image loaded!", result[0]);
    assertEquals("", result[1]);
    assertEquals("updateImage", result[2]);
    assertEquals("drawHistogram", result[3]);
    assertEquals("renderMessage: image saved :)", result[4]);
  }

  // tests that the correct methods are called when the save method is called with an unsupported
  // file type
  @Test
  public void testSaveUnsupportedFileType() {
    real.load("res/flower.jpg");
    real.save("test/guiSave.gif");
    String[] result = log.toString().split("\n");
    assertEquals("renderMessage: image loaded!", result[0]);
    assertEquals("", result[1]);
    assertEquals("updateImage", result[2]);
    assertEquals("drawHistogram", result[3]);
    assertEquals("renderMessage: Failure saving image.", result[4]);
  }

  // tests that the correct methods are called when one tries to modify an image but there is
  // no image on screen
  @Test
  public void testNoImageModify() {
    real.modifyImage("Blur");
    String[] result = log.toString().split("\n");
    assertEquals("renderMessage: Failure altering image", result[0]);
  }

  // tests that the correct methods are called when one puts an action that does not exist for
  // modify image
  @Test
  public void testFakeCommandModify() {
    controller.modifyImage("heyyy");
    String[] result = log.toString().split("\n");
    assertEquals("renderMessage: Invalid command", result[0]);
  }

  // tests that the correct methods are called when modify image works successfully for Flip
  // vertically
  @Test
  public void testWorkingModifyFlipV() {
    real.load("res/flower.jpg");
    real.modifyImage("Flip vertically");
    String[] result = log.toString().split("\n");
    assertEquals("renderMessage: image loaded!", result[0]);
    assertEquals("", result[1]);
    assertEquals("updateImage", result[2]);
    assertEquals("drawHistogram", result[3]);
    assertEquals("renderMessage: image flipped vertically!", result[4]);
    assertEquals("", result[5]);
    assertEquals("updateImage", result[6]);
    assertEquals("drawHistogram", result[7]);
  }

  // tests that the correct methods are called when modify image works successfully for Flip
  // horizontally
  @Test
  public void testWorkingModifyFlipH() {
    real.load("res/flower.jpg");
    real.modifyImage("Flip horizontally");
    String[] result = log.toString().split("\n");
    assertEquals("renderMessage: image loaded!", result[0]);
    assertEquals("", result[1]);
    assertEquals("updateImage", result[2]);
    assertEquals("drawHistogram", result[3]);
    assertEquals("renderMessage: flipped horizontally!!!!!!", result[4]);
    assertEquals("", result[5]);
    assertEquals("updateImage", result[6]);
    assertEquals("drawHistogram", result[7]);
  }


  // tests that the correct methods are called when modify image works successfully for
  // visualizing value
  @Test
  public void testWorkingModifyValue() {
    real.load("res/flower.jpg");
    real.modifyImage("Visualize value");
    String[] result = log.toString().split("\n");
    assertEquals("renderMessage: image loaded!", result[0]);
    assertEquals("", result[1]);
    assertEquals("updateImage", result[2]);
    assertEquals("drawHistogram", result[3]);
    assertEquals("renderMessage: Value component visualized!", result[4]);
    assertEquals("", result[5]);
    assertEquals("updateImage", result[6]);
    assertEquals("drawHistogram", result[7]);
  }

  // tests that the correct methods are called when modify image works successfully for
  // visualizing luma
  @Test
  public void testWorkingModifyLuma() {
    real.load("res/flower.jpg");
    real.modifyImage("Visualize luma");
    String[] result = log.toString().split("\n");
    assertEquals("renderMessage: image loaded!", result[0]);
    assertEquals("", result[1]);
    assertEquals("updateImage", result[2]);
    assertEquals("drawHistogram", result[3]);
    assertEquals("renderMessage: Luma component visualized!", result[4]);
    assertEquals("", result[5]);
    assertEquals("updateImage", result[6]);
    assertEquals("drawHistogram", result[7]);
  }

  // tests that the correct methods are called when modify image works successfully for
  // visualizing intensity
  @Test
  public void testWorkingModifyIntensity() {
    real.load("res/flower.jpg");
    real.modifyImage("Visualize intensity");
    String[] result = log.toString().split("\n");
    assertEquals("renderMessage: image loaded!", result[0]);
    assertEquals("", result[1]);
    assertEquals("updateImage", result[2]);
    assertEquals("drawHistogram", result[3]);
    assertEquals("renderMessage: Intensity component visualized!", result[4]);
    assertEquals("", result[5]);
    assertEquals("updateImage", result[6]);
    assertEquals("drawHistogram", result[7]);
  }

  // tests that the correct methods are called when modify image works successfully for
  // visualizing green
  @Test
  public void testWorkingModifyGreen() {
    real.load("res/flower.jpg");
    real.modifyImage("Visualize green");
    String[] result = log.toString().split("\n");
    assertEquals("renderMessage: image loaded!", result[0]);
    assertEquals("", result[1]);
    assertEquals("updateImage", result[2]);
    assertEquals("drawHistogram", result[3]);
    assertEquals("renderMessage: Green component visualized!", result[4]);
    assertEquals("", result[5]);
    assertEquals("updateImage", result[6]);
    assertEquals("drawHistogram", result[7]);
  }

  // tests that the correct methods are called when modify image works successfully for
  // visualizing red
  @Test
  public void testWorkingModifyRed() {
    real.load("res/flower.jpg");
    real.modifyImage("Visualize red");
    String[] result = log.toString().split("\n");
    assertEquals("renderMessage: image loaded!", result[0]);
    assertEquals("", result[1]);
    assertEquals("updateImage", result[2]);
    assertEquals("drawHistogram", result[3]);
    assertEquals("renderMessage: Red component visualized!", result[4]);
    assertEquals("", result[5]);
    assertEquals("updateImage", result[6]);
    assertEquals("drawHistogram", result[7]);
  }

  // tests that the correct methods are called when modify image works successfully for
  // visualizing blue
  @Test
  public void testWorkingModifyBlue() {
    real.load("res/flower.jpg");
    real.modifyImage("Visualize blue");
    String[] result = log.toString().split("\n");
    assertEquals("renderMessage: image loaded!", result[0]);
    assertEquals("", result[1]);
    assertEquals("updateImage", result[2]);
    assertEquals("drawHistogram", result[3]);
    assertEquals("renderMessage: Blue component visualized!", result[4]);
    assertEquals("", result[5]);
    assertEquals("updateImage", result[6]);
    assertEquals("drawHistogram", result[7]);
  }

  // tests that the correct methods are called when modify image works successfully for
  // blurring
  @Test
  public void testWorkingModifyBlur() {
    real.load("res/flower.jpg");
    real.modifyImage("Blur");
    String[] result = log.toString().split("\n");
    assertEquals("renderMessage: image loaded!", result[0]);
    assertEquals("", result[1]);
    assertEquals("updateImage", result[2]);
    assertEquals("drawHistogram", result[3]);
    assertEquals("renderMessage: image blurred!", result[4]);
    assertEquals("", result[5]);
    assertEquals("updateImage", result[6]);
    assertEquals("drawHistogram", result[7]);
  }

  // tests that the correct methods are called when modify image works successfully for
  // sharpening
  @Test
  public void testWorkingModifySharpen() {
    real.load("res/flower.jpg");
    real.modifyImage("Sharpen");
    String[] result = log.toString().split("\n");
    assertEquals("renderMessage: image loaded!", result[0]);
    assertEquals("", result[1]);
    assertEquals("updateImage", result[2]);
    assertEquals("drawHistogram", result[3]);
    assertEquals("renderMessage: image sharpened!", result[4]);
    assertEquals("", result[5]);
    assertEquals("updateImage", result[6]);
    assertEquals("drawHistogram", result[7]);
  }

  // tests that the correct methods are called when modify image works successfully for
  // filtering greyscale
  @Test
  public void testWorkingModifyGreyscale() {
    real.load("res/flower.jpg");
    real.modifyImage("Apply greyscale filter");
    String[] result = log.toString().split("\n");
    assertEquals("renderMessage: image loaded!", result[0]);
    assertEquals("", result[1]);
    assertEquals("updateImage", result[2]);
    assertEquals("drawHistogram", result[3]);
    assertEquals("renderMessage: image converted to greyscale!", result[4]);
    assertEquals("", result[5]);
    assertEquals("updateImage", result[6]);
    assertEquals("drawHistogram", result[7]);
  }

  // tests that the correct methods are called when modify image works successfully for
  // filtering sepia
  @Test
  public void testWorkingModifySepia() {
    real.load("res/flower.jpg");
    real.modifyImage("Apply sepia filter");
    String[] result = log.toString().split("\n");
    assertEquals("renderMessage: image loaded!", result[0]);
    assertEquals("", result[1]);
    assertEquals("updateImage", result[2]);
    assertEquals("drawHistogram", result[3]);
    assertEquals("renderMessage: image has a sepia filter now!", result[4]);
    assertEquals("", result[5]);
    assertEquals("updateImage", result[6]);
    assertEquals("drawHistogram", result[7]);
  }

  // tests that the correct methods are called when the image is brightened by a valid number
  @Test
  public void testWorkingBrighten() {
    real.load("res/flower.jpg");
    real.brighten("100");
    String[] result = log.toString().split("\n");
    assertEquals("renderMessage: image loaded!", result[0]);
    assertEquals("", result[1]);
    assertEquals("updateImage", result[2]);
    assertEquals("drawHistogram", result[3]);
    assertEquals("renderMessage: image brightness changed!", result[4]);
    assertEquals("", result[5]);
    assertEquals("updateImage", result[6]);
    assertEquals("drawHistogram", result[7]);
  }

  // tests that the correct methods are called when one tries to attempt to brighten by 0
  @Test
  public void testZeroBrighten() {
    real.load("res/flower.jpg");
    real.brighten("0");
    String[] result = log.toString().split("\n");
    assertEquals("renderMessage: image loaded!", result[0]);
    assertEquals("", result[1]);
    assertEquals("updateImage", result[2]);
    assertEquals("drawHistogram", result[3]);
    assertEquals("renderMessage: brightness constant must be a non zero " +
            "integer", result[4]);
  }

  // tests that the correct methods are called when one tries to attempt to brighten by a random
  // string
  @Test
  public void testStringBrighten() {
    real.load("res/flower.jpg");
    real.brighten("random");
    String[] result = log.toString().split("\n");
    assertEquals("renderMessage: image loaded!", result[0]);
    assertEquals("", result[1]);
    assertEquals("updateImage", result[2]);
    assertEquals("drawHistogram", result[3]);
    assertEquals("renderMessage: brightness constant must be a non zero " +
            "integer", result[4]);
  }

  // tests that the correct methods are called when one tries to attempt to brighten by a random
  // double
  @Test
  public void testDoubleBrighten() {
    real.load("res/flower.jpg");
    real.brighten("0.49494");
    String[] result = log.toString().split("\n");
    assertEquals("renderMessage: image loaded!", result[0]);
    assertEquals("", result[1]);
    assertEquals("updateImage", result[2]);
    assertEquals("drawHistogram", result[3]);
    assertEquals("renderMessage: brightness constant must be a non zero " +
            "integer", result[4]);
  }

  // checks that the right methods are called when downscale with proper numbers
  // is called
  @Test
  public void testWorkingDownsize() {
    real.load("res/flower.jpg");
    real.downsize("10", "10");
    String[] result = log.toString().split("\n");
    assertEquals("renderMessage: image loaded!", result[0]);
    assertEquals("", result[1]);
    assertEquals("updateImage", result[2]);
    assertEquals("drawHistogram", result[3]);
    assertEquals("renderMessage: image downsized!", result[4]);
    assertEquals("", result[5]);
    assertEquals("updateImage", result[6]);
    assertEquals("drawHistogram", result[7]);
  }

  // checks that the right methods are called when downscale with numbers less than 0
  // are called
  @Test
  public void testTooSmallNumbersDownsize() {
    real.load("res/flower.jpg");
    real.downsize("-9", "-10");
    String[] result = log.toString().split("\n");
    assertEquals("renderMessage: image loaded!", result[0]);
    assertEquals("", result[1]);
    assertEquals("updateImage", result[2]);
    assertEquals("drawHistogram", result[3]);
    assertEquals("renderMessage: Downsizing failed. " +
            "Width and height must be positive integers", result[4]);
  }

  // checks that the right methods are called when downscale with numbers bigger than the
  // image is
  @Test
  public void testTooBigNumbersDownsize() {
    real.load("res/flower.jpg");
    real.downsize("1000000", "10000");
    String[] result = log.toString().split("\n");
    assertEquals("renderMessage: image loaded!", result[0]);
    assertEquals("", result[1]);
    assertEquals("updateImage", result[2]);
    assertEquals("drawHistogram", result[3]);
    assertEquals("renderMessage: width and height must be less than the " +
            "original image!", result[4]);
  }

  // checks that the right methods are called when downscale with non integer strings
  // as the width and height
  @Test
  public void testRandomStringsDownsize() {
    real.load("res/flower.jpg");
    real.downsize("oooo", "bbabbby");
    String[] result = log.toString().split("\n");
    assertEquals("renderMessage: image loaded!", result[0]);
    assertEquals("", result[1]);
    assertEquals("updateImage", result[2]);
    assertEquals("drawHistogram", result[3]);
    assertEquals("renderMessage: width and height must be positive integers!", result[4]);
  }

  // checks that the right methods are called when downscale with doubles
  // as the width and height
  @Test
  public void testDoublesDownsize() {
    real.load("res/flower.jpg");
    real.downsize("10.24", "20.22");
    String[] result = log.toString().split("\n");
    assertEquals("renderMessage: image loaded!", result[0]);
    assertEquals("", result[1]);
    assertEquals("updateImage", result[2]);
    assertEquals("drawHistogram", result[3]);
    assertEquals("renderMessage: width and height must be positive integers!", result[4]);
  }

}
