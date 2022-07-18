package controller.command;

import model.EnhancedModel;
import view.IView;

/**
 * represents the brighten command for the image processor.
 */
public class Brighten extends controller.command.ACommand {
  private int scale;

  /**
   * constructor that takes in the scale we are brightening by, the name of
   * the image we are brightening, where this new image is being stashed,
   * as well as instance of the view.
   *
   * @param scale       the scale by which we are brightening. If this scale is negative,
   *                    we are darkening by that much.
   * @param name        the name of the image we are brightening.
   * @param destination where the brightened/darkened image is going.
   * @param view        an instance of the view.
   */
  public Brighten(String scale, String name, String destination, IView view) {
    super(name, destination, view);
    try {
      this.scale = Integer.parseInt(scale);
      if (this.scale == 0) {
        this.sendMessage("brightness constant must be a non zero integer");
        this.scale = 0;
      }
    } catch (NumberFormatException e) {
      this.sendMessage("brightness constant must be a non zero integer");
      this.scale = 0;
    }
  }

  @Override
  public void runCommand(EnhancedModel model) {
    if (this.scale != 0) {
      model.changeBrightness(scale, name, destination);
      this.sendMessage("image brightness changed!");
    }
  }


}
