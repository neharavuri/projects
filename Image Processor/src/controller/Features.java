package controller;

/**
 * Represents the operations needed for the GUI to interact with the rest of the program. These
 * are the event specific applications necessary for the program to run properly.
 */
public interface Features {
  /**
   * Represents the action to load an image into the program.
   * @param filepath the filepath of the image to be loaded.
   */
  void load(String filepath);

  /**l
   * Represents the action to save the current image to the filepath given.
   * @param filepath the filepath of the image to be saved to.
   */
  void save(String filepath);

  /**
   * Represents the action to perform one of the commands that is not downsize, brighten, save or
   * load on this current image.
   * @param action the command that should be performed on this image as a String.
   */
  void modifyImage(String action);

  /**
   * Represents the action to brighten the current image by the amount given.
   * @param scale the amount to brighten the current image by.
   */
  void brighten(String scale);

  /**
   * Represents the action to downsize the current image to the height and width given.
   * @param height the height the downsized image should have.
   * @param width the width the downsized image should have.
   */
  void downsize(String height, String width);

}
