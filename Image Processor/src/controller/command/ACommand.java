package controller.command;

import java.io.IOException;

import view.IView;

/**
 * represents an abstract class for the different sets of commands
 * available in the image processor. This class holds the fields and the methods
 * that all the commands utilize in some form.
 */
public abstract class ACommand implements ICommand {
  protected String name;
  protected String destination;
  protected IView view;

  /**
   * abstract constructor that takes in the name of the image,
   * the destination of where the image is going and an instance
   * of the view class.
   *
   * @param name        the name of the image we are accessing.
   * @param destination the new reference for the image.
   * @param view        an object of the image processor view interface.
   */
  protected ACommand(String name, String destination, IView view) {
    if (name == null || name.equals("") ||
            destination == null || destination.equals("") ||
            view == null) {
      throw new IllegalArgumentException("arguments cannot be null or empty");
    }
    this.name = name;
    this.destination = destination;
    this.view = view;
  }

  /**
   * renders the message we are trying to send while catching
   * any IOExceptions that may occur as a result of this transmission.
   *
   * @param message the message we are trying to send.
   */
  protected void sendMessage(String message) throws IllegalStateException {
    try {
      this.view.renderMessage(message + "\n");
    } catch (IOException e) {
      throw new IllegalStateException("transmission failed");
    }
  }
}
