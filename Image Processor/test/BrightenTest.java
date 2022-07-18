import org.junit.Before;
import org.junit.Test;

import controller.command.Brighten;
import model.EnhancedModel;
import view.IView;
import view.ImageTextView;

import static org.junit.Assert.assertEquals;

/**
 * Represents the tests needed for the Brighten class.
 */
public class BrightenTest {
  IView view;
  Appendable log;
  EnhancedModel model;
  Brighten invalid;
  Appendable corruptAppendable;
  IView corruptView;
  Brighten valid;


  @Before
  public void init() {
    log = new StringBuilder();
    view = new ImageTextView(log);
    model = new MockModel(log);
    corruptAppendable = new CorruptAppendable();
    corruptView = new ImageTextView(corruptAppendable);
    valid = new Brighten("5", "kate", "hey", view);

  }

  // tests that when a string that is not an integer is given for the increment at which the
  // brightness is changed, the proper message is sent to the user
  @Test
  public void testInvalidScaleString() {
    invalid = new Brighten("kate", "ur mom", "ur dad", view);
    assertEquals("brightness constant must be a non zero integer\n", log.toString());
  }

  // tests that when "0" is given as the increment for the brightness to be changed by, the proper
  // message is sent to the user
  @Test
  public void testInvalidScaleZero() {
    invalid = new Brighten("0", "bleh", "boo", view);
    assertEquals("brightness constant must be a non zero integer\n", log.toString());
  }

  // tests that when a null string is given for the name an illegal argument exception is thrown
  @Test(expected = IllegalArgumentException.class)
  public void testNullName() {
    invalid = new Brighten("2", null, "kate", view);
  }

  // tests that when an empty string is given for the name an illegal argument exception is thrown
  @Test(expected = IllegalArgumentException.class)
  public void testEmptyName() {
    invalid = new Brighten("2", "", "kate", view);
  }

  // tests that when a null string is given for the destination an illegal argument exception
  // is thrown
  @Test(expected = IllegalArgumentException.class)
  public void testNullDestination() {
    invalid = new Brighten("2", "kate", null, view);
  }

  // tests that when an empty string is given for the destination an illegal argument exception
  // is thrown
  @Test(expected = IllegalArgumentException.class)
  public void testEmptyDestination() {
    invalid = new Brighten("2", "kate", "", view);
  }

  // tests that when a null is given for the view an illegal argument exception is thrown
  @Test(expected = IllegalArgumentException.class)
  public void testNullView() {
    invalid = new Brighten("2", "kate", "hey", null);
  }

  // tests that when a corrupt appendable is given to the view (always throws an IO exception), that
  // an IllegalState exception is thrown when a message is attempted to be sent (which happens when
  // a non zero integer is given for the string for the scale).
  @Test(expected = IllegalStateException.class)
  public void testCorruptSendMessage() {
    invalid = new Brighten("0", "neha", "wow", corruptView);
  }

  // checks that the correct methods are called and the correct message is sent to the user when
  // the image brightness is changed
  @Test
  public void testWorkingGo() {
    valid.runCommand(model);
    assertEquals("change brightness: 5 kate hey\n" +
            "image brightness changed!\n", log.toString());

  }

}