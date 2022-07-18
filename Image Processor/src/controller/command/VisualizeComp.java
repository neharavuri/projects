package controller.command;

import model.EnhancedModel;
import model.IModel;
import view.IView;

/**
 * represents the visualize command for the image processor program.
 * This command visualizes either the red, green, blue components of the image
 * or properties of the image such as the value, intensity and luma in the form
 * of a greyscale.
 */
public class VisualizeComp extends controller.command.ACommand {
  private final IModel.Component comp;

  /**
   * constructor that takes in the name of the image we are visualizing,
   * the component we are visualizing, where this new image is going as well as
   * an instance of the view we are working with.
   *
   * @param name        the image we are modifying.
   * @param comp        the component we are visualizing.
   * @param destination where this new image is being stored.
   * @param view        an instance of the view we are working with.
   */
  public VisualizeComp(String name, IModel.Component comp, String destination, IView view) {
    super(name, destination, view);
    this.comp = comp;
  }

  @Override
  public void runCommand(EnhancedModel model) {
    model.visualizeComponent(name, comp, destination);
    this.sendMessage(comp.toString() + " component visualized!");
  }

}
