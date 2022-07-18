package controller.command;

import model.EnhancedModel;
import view.IView;

/**
 * represents the sepia command for the image processor program that creates a version of the
 * given image, filtered to be sepia.
 */
public class Sepia extends controller.command.ACommand {

  /**
   * represents the constructor that takes in the name of the image
   * we are filtering to make it sepia, where the filtered image is going, as well
   * as an instance of the view we are working with.
   *
   * @param name        the image we are filtering.
   * @param destination where the filtered image is going.
   * @param view        an instance of the view we are working with.
   */
  public Sepia(String name, String destination, IView view) {
    super(name, destination, view);
  }

  @Override
  public void runCommand(EnhancedModel model) {
    model.sepia(this.name, this.destination);
    this.sendMessage("image has a sepia filter now!");
  }

}
