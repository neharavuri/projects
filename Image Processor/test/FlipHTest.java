import org.junit.Before;
import org.junit.Test;

import controller.command.FlipH;
import model.EnhancedModel;
import view.IView;
import view.ImageTextView;

import static org.junit.Assert.assertEquals;

/**
 * Represents the tests needed for the FlipH class.
 */
public class FlipHTest {
  IView view;
  Appendable log;
  EnhancedModel model;
  FlipH invalid;
  Appendable corruptAppendable;
  IView corruptView;
  FlipH valid;


  @Before
  public void init() {
    log = new StringBuilder();
    view = new ImageTextView(log);
    model = new MockModel(log);
    corruptAppendable = new CorruptAppendable();
    corruptView = new ImageTextView(corruptAppendable);
    valid = new FlipH("kate", "bye", view);

  }


  // tests that when a null string is given for the name an illegal argument exception is thrown
  @Test(expected = IllegalArgumentException.class)
  public void testNullName() {
    invalid = new FlipH(null, "kate", view);
  }

  // tests that when an empty string is given for the name an illegal argument exception is thrown
  @Test(expected = IllegalArgumentException.class)
  public void testEmptyName() {
    invalid = new FlipH("", "kate", view);
  }

  // tests that when a null string is given for the destination an illegal argument exception
  // is thrown
  @Test(expected = IllegalArgumentException.class)
  public void testNullDestination() {
    invalid = new FlipH("kate", null, view);
  }

  // tests that when an empty string is given for the destination an illegal argument exception
  // is thrown
  @Test(expected = IllegalArgumentException.class)
  public void testEmptyDestination() {
    invalid = new FlipH("kate", "", view);
  }

  // tests that when a null is given for the view an illegal argument exception is thrown
  @Test(expected = IllegalArgumentException.class)
  public void testNullView() {
    invalid = new FlipH("kate", "hey", null);
  }

  // tests that when a corrupt appendable is given to the view (always throws an IO exception), that
  // an IllegalState exception is thrown when a message is attempted to be sent (which happens when
  // the runCommand method is called).
  @Test(expected = IllegalStateException.class)
  public void testCorruptSendMessage() {
    invalid = new FlipH("neha", "wow", corruptView);
    invalid.runCommand(model);
  }

  // checks that the correct methods are called and the correct message is sent to the user when
  // the image is flipped horizontally
  @Test
  public void testWorkingGo() {
    valid.runCommand(model);
    assertEquals("flip horizontally: kate bye\n" +
            "flipped horizontally!!!!!!\n", log.toString());

  }

}