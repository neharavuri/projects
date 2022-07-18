package view;

import java.io.IOException;

/**
 * Represents the operations needed for a text version of the view for the image processor.
 */
public interface IView {
  /**
   * renderMessages sends a message from the processor to the user.
   *
   * @param message the message to be transmitted.
   * @throws IOException if transmission of the board to the provided data destination fails.
   */
  void renderMessage(String message) throws IOException;

}
