import org.junit.Before;
import org.junit.Test;

import controller.command.FlipV;
import model.EnhancedModel;
import view.IView;
import view.ImageTextView;

import static org.junit.Assert.assertEquals;

/**
 * Represents the tests needed for the FlipV class.
 */
public class FlipVTest {
  IView view;
  Appendable log;
  EnhancedModel model;
  FlipV invalid;
  Appendable corruptAppendable;
  IView corruptView;
  FlipV valid;


  @Before
  public void init() {
    log = new StringBuilder();
    view = new ImageTextView(log);
    model = new MockModel(log);
    corruptAppendable = new CorruptAppendable();
    corruptView = new ImageTextView(corruptAppendable);
    valid = new FlipV("water", "rawr", view);

  }


  // tests that when a null string is given for the name an illegal argument exception is thrown
  @Test(expected = IllegalArgumentException.class)
  public void testNullName() {
    invalid = new FlipV(null, "kate", view);
  }

  // tests that when an empty string is given for the name an illegal argument exception is thrown
  @Test(expected = IllegalArgumentException.class)
  public void testEmptyName() {
    invalid = new FlipV("", "kate", view);
  }

  // tests that when a null string is given for the destination an illegal argument exception
  // is thrown
  @Test(expected = IllegalArgumentException.class)
  public void testNullDestination() {
    invalid = new FlipV("kate", null, view);
  }

  // tests that when an empty string is given for the destination an illegal argument exception
  // is thrown
  @Test(expected = IllegalArgumentException.class)
  public void testEmptyDestination() {
    invalid = new FlipV("kate", "", view);
  }

  // tests that when a null is given for the view an illegal argument exception is thrown
  @Test(expected = IllegalArgumentException.class)
  public void testNullView() {
    invalid = new FlipV("kate", "hey", null);
  }

  // tests that when a corrupt appendable is given to the view (always throws an IO exception), that
  // an IllegalState exception is thrown when a message is attempted to be sent (which happens when
  // the runCommand method is called).
  @Test(expected = IllegalStateException.class)
  public void testCorruptSendMessage() {
    invalid = new FlipV("lol", "happy", corruptView);
    invalid.runCommand(model);
  }

  // checks that the correct methods are called and the correct message is sent to the user when
  // the image is flipped vertically
  @Test
  public void testWorkingGo() {
    valid.runCommand(model);
    assertEquals("flip vertically: water rawr\n" +
            "image flipped vertically!\n", log.toString());

  }

}