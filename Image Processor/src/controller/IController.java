package controller;

/**
 * represents the controller interface. The controller interface
 * is responsible for having methods that help the controller
 * read input from the user and delegate responsibilities to the
 * model and the view.
 */
public interface IController {
  /**
   * method that runs the program. Individual javadocs for the ways
   * that startProgram works for each implementation of the controller exist
   * in the respective classes.
   */
  void startProgram();
}
