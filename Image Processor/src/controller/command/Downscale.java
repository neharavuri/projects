package controller.command;

import model.EnhancedModel;
import view.IView;

/**
 * Represents the downscale command for the image processing program.
 * This command takes in a custom height and width and shrinks the image
 * to fit the specified dimensions.
 */
public class Downscale extends controller.command.ACommand {
  private int width;
  private int height;

  /**
   * represents the public constructor for downscale that takes in the height and width
   * we are downsizing to, as well as the name of the image we are downsizing and the
   * destination name. Finally, it also takes in a view to render anything that needs to be
   * put out to the user.
   * @param width the width to which we are downsizing.
   * @param height the height to which we are downsizing.
   * @param name the name of the image we are downsizing.
   * @param destination the new name of the downsized image.
   * @param view the view for this image processor.
   * @throws IllegalArgumentException when any of the arguments
   *         are empty/null or if the width/height are not positive integer values.
   */
  public Downscale(String width, String height, String name, String destination, IView view)
          throws IllegalArgumentException {
    super(name, destination,view);
    try {
      this.width = Integer.parseInt(width);
      this.height = Integer.parseInt(height);
    } catch (NumberFormatException e) {
      this.sendMessage("width and height must be positive integers!");
    }
    if (this.width <= 0 || this.height <= 0) {
      throw new IllegalArgumentException("width and height must be positive integers!");
    }
  }

  @Override
  public void runCommand(EnhancedModel model) {
    try {
      model.downsize(name, destination, height, width);
      this.sendMessage("image downsized!");
    } catch (IllegalArgumentException e) {
      this.sendMessage("width and height must be less than the original image!");
    }
  }


}
