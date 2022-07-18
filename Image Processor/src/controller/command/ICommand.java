package controller.command;

import model.EnhancedModel;

/**
 * represents an interface of all the methods that a command
 * in the image processor program should be able to perform.
 */
public interface ICommand {
  /**
   * runCommand method for all the possible commands for the image processer program.
   * This method takes the model that it is fed through the argument
   * and tells it to perform the command we want based on which command the user
   * chose.
   *
   * @param model an instance of the model we are working with.
   */
  void runCommand(EnhancedModel model);
}
