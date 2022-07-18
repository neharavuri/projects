import org.junit.Before;
import org.junit.Test;

import controller.command.VisualizeComp;
import model.EnhancedModel;
import model.IModel;
import view.IView;
import view.ImageTextView;

import static org.junit.Assert.assertEquals;


/**
 * Represents the tests needed for the VisualizeComp class.
 */
public class VisualizeCompTest {
  IView view;
  Appendable log;
  EnhancedModel model;
  VisualizeComp invalid;
  Appendable corruptAppendable;
  IView corruptView;
  VisualizeComp valid;


  @Before
  public void init() {
    log = new StringBuilder();
    view = new ImageTextView(log);
    model = new MockModel(log);
    corruptAppendable = new CorruptAppendable();
    corruptView = new ImageTextView(corruptAppendable);
    valid = new VisualizeComp("kate", IModel.Component.Blue, "bye", view);
  }


  // tests that when a null string is given for the name an illegal argument exception is thrown
  @Test (expected = IllegalArgumentException.class)
  public void testNullName() {
    invalid = new VisualizeComp(null, IModel.Component.Red, "kate", view);
  }

  // tests that when an empty string is given for the name an illegal argument exception is thrown
  @Test (expected = IllegalArgumentException.class)
  public void testEmptyName() {
    invalid = new VisualizeComp( "", IModel.Component.Red,"kate", view);
  }

  // tests that when a null string is given for the destination an illegal argument exception
  // is thrown
  @Test (expected = IllegalArgumentException.class)
  public void testNullDestination() {
    invalid = new VisualizeComp("kate", IModel.Component.Red, null, view);
  }

  // tests that when an empty string is given for the destination an illegal argument exception
  // is thrown
  @Test (expected = IllegalArgumentException.class)
  public void testEmptyDestination() {
    invalid = new VisualizeComp("kate", IModel.Component.Red, "", view);
  }

  // tests that when a null is given for the view an illegal argument exception is thrown
  @Test (expected = IllegalArgumentException.class)
  public void testNullView() {
    invalid = new VisualizeComp("kate", IModel.Component.Green,"hey", null);
  }

  // tests that when a corrupt appendable is given to the view (always throws an IO exception), that
  // an IllegalState exception is thrown when a message is attempted to be sent (which happens when
  // the runCommand method is called).
  @Test (expected = IllegalStateException.class)
  public void testCorruptSendMessage() {
    invalid = new VisualizeComp("neha", IModel.Component.Luma,"wow", corruptView);
    invalid.runCommand(model);
  }

  // checks that the correct methods are called and the correct message is sent to the user when
  // the image is visualized by component
  @Test
  public void testWorkingGo() {
    valid.runCommand(model);
    assertEquals("visualize component: kate Blue bye\n" +
            "Blue component visualized!\n", log.toString());

  }

}