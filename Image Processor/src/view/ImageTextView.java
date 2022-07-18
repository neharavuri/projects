package view;

import java.io.IOException;

/**
 * Represents an implementation of the test view in which the messages are sent out to the user.
 */
public class ImageTextView implements IView {
  // where the messages that will be sent to the user are sent to.
  private final Appendable destination;

  /**
   * The constructor for an ImageTextView that takes in an appendable for the outputs to
   * runCommand to.
   *
   * @param destination where the outputs from the program should be stored.
   * @throws IllegalArgumentException if the appendable given is null.
   */
  public ImageTextView(Appendable destination) throws IllegalArgumentException {
    if (destination == null) {
      throw new IllegalArgumentException("appendable cannot be null");
    }
    this.destination = destination;
  }

  /**
   * The default constructor for ImageTextView where the automatic destination is System.out (so the
   * user receives feedback in the console).
   */
  public ImageTextView() {
    this.destination = System.out;
  }

  @Override
  public void renderMessage(String message) throws IOException {
    this.destination.append(message);
  }
}
