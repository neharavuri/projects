package controller.command;

import model.EnhancedModel;
import view.IView;

/**
 * represents the flip vertically command for the image processor program that flips the
 * image vertically.
 */
public class FlipV extends controller.command.ACommand {
  /**
   * represents the constructor that takes in the name of the image
   * we are flipping, where the flipped image is going, as well
   * as an instance of the view we are working with.
   *
   * @param name        the image we are flipping.
   * @param destination where the flipped image is going.
   * @param view        an instance of the view we are working with.
   */
  public FlipV(String name, String destination, IView view) {
    super(name, destination, view);
  }

  @Override
  public void runCommand(EnhancedModel model) {
    model.flipVertically(name, destination);
    this.sendMessage("image flipped vertically!");
  }

}
