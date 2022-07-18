import org.junit.Before;
import org.junit.Test;

import controller.command.Downscale;
import model.EnhancedModel;
import view.IView;
import view.ImageTextView;

import static org.junit.Assert.assertEquals;

/**
 * Represents the tests to test the Downsize command.
 */
public class DownscaleTest {

  IView view;
  Appendable log;
  EnhancedModel model;
  Downscale invalid;
  Appendable corruptAppendable;
  IView corruptView;
  Downscale valid;

  @Before
  public void init() {
    log = new StringBuilder();
    view = new ImageTextView(log);
    model = new MockModel(log);
    corruptAppendable = new CorruptAppendable();
    corruptView = new ImageTextView(corruptAppendable);
    valid = new Downscale("100", "100", "smile", "happy", view);
  }

  // tests that when a null string is given for the name an illegal argument exception is thrown
  @Test(expected = IllegalArgumentException.class)
  public void testNullName() {
    invalid = new Downscale(null, null, null, "kate", view);
  }

  // tests that when an empty string is given for the name an illegal argument exception is thrown
  @Test(expected = IllegalArgumentException.class)
  public void testEmptyName() {
    invalid = new Downscale("100", "100", "", "yuh", view);
  }

  // tests that when a null string is given for the destination an illegal argument exception
  // is thrown
  @Test(expected = IllegalArgumentException.class)
  public void testNullDestination() {
    invalid = new Downscale("100", "100", "weeee", null, view);
  }

  // tests that when an empty string is given for the destination an illegal argument exception
  // is thrown
  @Test(expected = IllegalArgumentException.class)
  public void testEmptyDestination() {
    invalid = new Downscale("100", "100", "kate", "", view);
  }

  // tests that when a null is given for the view an illegal argument exception is thrown
  @Test(expected = IllegalArgumentException.class)
  public void testNullView() {
    invalid = new Downscale("100", "100", "kate", "hey", null);
  }

  // tests that when a corrupt appendable is given to the view (always throws an IO exception), that
  // an IllegalState exception is thrown when a message is attempted to be sent (which happens when
  // the runCommand method is called).
  @Test(expected = IllegalStateException.class)
  public void testCorruptSendMessage() {
    invalid = new Downscale("100", "100", "neha", "moreNeha", corruptView);
    invalid.runCommand(model);
  }

  // checks that the correct methods are called and the correct message is sent to the user when
  // the image is downscaled
  @Test
  public void testWorkingGo() {
    valid.runCommand(model);
    assertEquals("downsize: smile happy  100 100 image downsized!\n", log.toString());

  }
}