import org.junit.Before;
import org.junit.Test;

import controller.command.Sepia;
import model.EnhancedModel;
import view.IView;
import view.ImageTextView;

import static org.junit.Assert.assertEquals;

/**
 * Represents the tests to test the Sepia command.
 */
public class SepiaTest {

  IView view;
  Appendable log;
  EnhancedModel model;
  Sepia invalid;
  Appendable corruptAppendable;
  IView corruptView;
  Sepia valid;

  @Before
  public void init() {
    log = new StringBuilder();
    view = new ImageTextView(log);
    model = new MockModel(log);
    corruptAppendable = new CorruptAppendable();
    corruptView = new ImageTextView(corruptAppendable);
    valid = new Sepia("hungry", "stomach", view);
  }

  // tests that when a null string is given for the name an illegal argument exception is thrown
  @Test(expected = IllegalArgumentException.class)
  public void testNullName() {
    invalid = new Sepia(null,"eye", view);
  }

  // tests that when an empty string is given for the name an illegal argument exception is thrown
  @Test (expected = IllegalArgumentException.class)
  public void testEmptyName() {
    invalid = new Sepia( "", "ear", view);
  }

  // tests that when a null string is given for the destination an illegal argument exception
  // is thrown
  @Test (expected = IllegalArgumentException.class)
  public void testNullDestination() {
    invalid = new Sepia("mouth", null, view);
  }

  // tests that when an empty string is given for the destination an illegal argument exception
  // is thrown
  @Test (expected = IllegalArgumentException.class)
  public void testEmptyDestination() {
    invalid = new Sepia("nose", "", view);
  }

  // tests that when a null is given for the view an illegal argument exception is thrown
  @Test (expected = IllegalArgumentException.class)
  public void testNullView() {
    invalid = new Sepia("finger", "arm", null);
  }

  // tests that when a corrupt appendable is given to the view (always throws an IO exception), that
  // an IllegalState exception is thrown when a message is attempted to be sent (which happens when
  // the runCommand method is called).
  @Test (expected = IllegalStateException.class)
  public void testCorruptSendMessage() {
    invalid = new Sepia("fibia", "hamstring", corruptView);
    invalid.runCommand(model);
  }

  // checks that the correct methods are called and the correct message is sent to the user when
  // the image is in a sepia version
  @Test
  public void testWorkingGo() {
    valid.runCommand(model);
    assertEquals("sepia: hungry stomach\n" +
            "image has a sepia filter now!\n", log.toString());

  }
}