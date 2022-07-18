package controller.command;

import model.EnhancedModel;
import view.IView;

/**
 * represents the greyscale command for the image processor program that produces a greyscale
 * version of the image.
 */
public class Greyscale extends controller.command.ACommand {
  /**
   * represents the constructor that takes in the name of the image
   * we are grey scaling, where the grey scaled image is going, as well
   * as an instance of the view we are working with.
   *
   * @param name        the image we are grey scaling.
   * @param destination where the grey scaled image is going.
   * @param view        an instance of the view we are working with.
   */
  public Greyscale(String name, String destination, IView view) {
    super(name, destination, view);
  }

  @Override
  public void runCommand(EnhancedModel model) {
    model.greyscale(this.name, this.destination);
    this.sendMessage("image converted to greyscale!");
  }

}
