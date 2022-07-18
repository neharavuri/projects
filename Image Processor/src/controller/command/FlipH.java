package controller.command;

import model.EnhancedModel;
import view.IView;

/**
 * represents the flip horizontally command for the image processor program
 * that flips the image horizontally.
 */
public class FlipH extends controller.command.ACommand {
  /**
   * constructor that takes the name of the image we are flipping, where
   * the flipped image is going and an instance of the view.
   *
   * @param name        the image we are flipping.
   * @param destination where the flipped image is going to runCommand.
   * @param view        an instance of the view we are working with.
   */
  public FlipH(String name, String destination, IView view) {
    super(name, destination, view);
  }

  @Override
  public void runCommand(EnhancedModel model) {
    model.flipHorizontally(name, destination);
    this.sendMessage("flipped horizontally!!!!!!");
  }
}
