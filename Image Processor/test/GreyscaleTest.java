import org.junit.Before;
import org.junit.Test;

import controller.command.Greyscale;
import model.EnhancedModel;
import view.IView;
import view.ImageTextView;

import static org.junit.Assert.assertEquals;

/**
 * Represents the tests to test the Greyscale command.
 */
public class GreyscaleTest {

  IView view;
  Appendable log;
  EnhancedModel model;
  Greyscale invalid;
  Appendable corruptAppendable;
  IView corruptView;
  Greyscale valid;

  @Before
  public void init() {
    log = new StringBuilder();
    view = new ImageTextView(log);
    model = new MockModel(log);
    corruptAppendable = new CorruptAppendable();
    corruptView = new ImageTextView(corruptAppendable);
    valid = new Greyscale("messages", "intellij", view);
  }

  // tests that when a null string is given for the name an illegal argument exception is thrown
  @Test(expected = IllegalArgumentException.class)
  public void testNullName() {
    invalid = new Greyscale(null, "finder", view);
  }

  // tests that when an empty string is given for the name an illegal argument exception is thrown
  @Test(expected = IllegalArgumentException.class)
  public void testEmptyName() {
    invalid = new Greyscale("", "photos", view);
  }

  // tests that when a null string is given for the destination an illegal argument exception
  // is thrown
  @Test(expected = IllegalArgumentException.class)
  public void testNullDestination() {
    invalid = new Greyscale("powerpoint", null, view);
  }

  // tests that when an empty string is given for the destination an illegal argument exception
  // is thrown
  @Test(expected = IllegalArgumentException.class)
  public void testEmptyDestination() {
    invalid = new Greyscale("calendar", "", view);
  }

  // tests that when a null is given for the view an illegal argument exception is thrown
  @Test(expected = IllegalArgumentException.class)
  public void testNullView() {
    invalid = new Greyscale("notes", "chrome", null);
  }

  // tests that when a corrupt appendable is given to the view (always throws an IO exception), that
  // an IllegalState exception is thrown when a message is attempted to be sent (which happens when
  // the runCommand method is called).
  @Test(expected = IllegalStateException.class)
  public void testCorruptSendMessage() {
    invalid = new Greyscale("zoom", "outlook", corruptView);
    invalid.runCommand(model);
  }

  // checks that the correct methods are called and the correct message is sent to the user when
  // the image is in a greyscale version
  @Test
  public void testWorkingGo() {
    valid.runCommand(model);
    assertEquals("greyscale: messages intellij\n" +
            "image converted to greyscale!\n", log.toString());

  }
}