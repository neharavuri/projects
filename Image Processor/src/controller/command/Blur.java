package controller.command;

import model.EnhancedModel;
import view.IView;

/**
 * represents the blur command for the image processor program that blurs the image.
 */
public class Blur extends controller.command.ACommand {
  /**
   * represents the constructor that takes in the name of the image
   * we are blurring, where the blurred image is going, as well
   * as an instance of the view we are working with.
   *
   * @param name        the image we are blurring.
   * @param destination where the blurred image is going.
   * @param view        an instance of the view we are working with.
   */
  public Blur(String name, String destination, IView view) {
    super(name, destination, view);
  }

  @Override
  public void runCommand(EnhancedModel model) {
    model.blur(this.name, this.destination);
    this.sendMessage("image blurred!");
  }

}
