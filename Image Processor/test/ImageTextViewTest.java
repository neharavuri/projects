import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import view.IView;
import view.ImageTextView;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Represents the tests needed to test ImageTextView.
 */
public class ImageTextViewTest {
  IView invalid;
  IView corruptView;
  IView valid;
  Appendable corrupt;
  Appendable log;


  @Before
  public void init() {
    corrupt = new CorruptAppendable();
    corruptView = new ImageTextView(corrupt);
    log = new StringBuilder();
    valid = new ImageTextView(log);
  }

  // tests that an exception is thrown when a null is given for the Appendable in the constructor
  @Test(expected = IllegalArgumentException.class)
  public void testExceptionConstructor() {
    invalid = new ImageTextView(null);
  }

  // tests the valid construction of an ImageTextView (with an appendable)
  @Test
  public void testValidConstruction() {
    try {
      valid.renderMessage("whats up");
    } catch (IOException ex) {
      fail();
    }
    assertEquals("whats up", log.toString());
    try {
      valid.renderMessage(" long day");
    } catch (IOException ex) {
      fail();
    }
    assertEquals("whats up long day", log.toString());
  }

  // tests that an exception is thrown in renderMessage when a corrupt appendable
  // (that always throws an IO exception) is given as the appendable
  @Test
  public void testCorruptAppendableRenderMessage() {
    try {
      corruptView.renderMessage("HEYYY");
      fail();
    } catch (IOException ex) {
      // if it makes it here the test passes
    }
  }

  // tests the renderMessage works properly with a non-corrupt appendable
  @Test
  public void testRenderMessage() {
    try {
      valid.renderMessage("monday");
    } catch (IOException ex) {
      fail();
    }
    assertEquals("monday", log.toString());
    try {
      valid.renderMessage(" tuesday");
    } catch (IOException ex) {
      fail();
    }
    assertEquals("monday tuesday", log.toString());
    try {
      valid.renderMessage(" wednesday");
    } catch (IOException ex) {
      fail();
    }
    assertEquals("monday tuesday wednesday", log.toString());
    try {
      valid.renderMessage(" thursday");
    } catch (IOException ex) {
      fail();
    }
    assertEquals("monday tuesday wednesday thursday", log.toString());
    try {
      valid.renderMessage(" friday");
    } catch (IOException ex) {
      fail();
    }
    assertEquals("monday tuesday wednesday thursday friday", log.toString());
  }

}