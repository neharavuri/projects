package controller.command;

import model.EnhancedModel;
import view.IView;

/**
 * represents the sharpen command for the image processor program that sharpens the image.
 */
public class Sharpen extends controller.command.ACommand {

  /**
   * represents the constructor that takes in the name of the image
   * we are sharpening, where the sharpened image is going, as well
   * as an instance of the view we are working with.
   *
   * @param name        the image we are sharpening.
   * @param destination where the sharpened image is going.
   * @param view        an instance of the view we are working with.
   */
  public Sharpen(String name, String destination, IView view) {
    super(name, destination, view);
  }

  @Override
  public void runCommand(EnhancedModel model) {
    model.sharpen(this.name, this.destination);
    this.sendMessage("image sharpened!");
  }

}
