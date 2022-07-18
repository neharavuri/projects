package model;

import java.awt.image.BufferedImage;

/**
 * IModel represents the operations needed to properly processed images in the image library. One
 * object of IModel represents a way to modify one type of image.
 */
public interface IModel {
  /**
   * Represents either a color component of a pixel or a way to visualize the intensity of a
   * pixel. Red, blue and green are the color components of the pixel. Value, intensity and luma are
   * all different ways to visualize the brightness/ intensity of the image. Value is the maximum
   * value of the three color components for the pixel. Intensity is the average of the three color
   * components for the pixel. Luma is the weighted sum of the three components
   * (Luma = 0.2126r + 0.7152g + 0.0722b).
   */
  enum Component { Red, Green, Blue, Value, Intensity, Luma }


  /**
   * visualizeComponent inspects the components of one image pixels by converting the image to
   * greyscale where for each pixel, the RGB values are all the same and their value is equal to the
   * magnitude of the component being inspected currently. This method creates a new image that is
   * this method visualized in the way wanted by the user and adds it to the collection of images
   * being stored currently.
   *
   * @param name        the name of the Image that the user wants to alter.
   * @param comp        the component that the user wants to visualize here. It can be one of:
   *                    Red, Green,
   *                    Blue, Intensity, Value or Luma.
   * @param destination the name that they want the new changed image to have in the image library.
   * @throws IllegalArgumentException if the name given for Image that the user want to be
   *                                  visualized different is not in the image library or if
   *                                  the destination name given
   *                                  is an empty string.
   */
  void visualizeComponent(String name, Component comp, String destination)
          throws IllegalArgumentException;

  /**
   * flipHorizontally flips the image horizontally. The size of the image does not change. This
   * method creates a new image that is flipped horizontally and adds it to the image library with
   * the name that the user gave to be the new name for this image.
   *
   * @param name        the name of the Image that the user wants to alter.
   * @param destination the name that they want the new changed image to have in the image library.
   * @throws IllegalArgumentException if the name given for Image that the user want to be
   *                                  visualized different is not in the image library or if
   *                                  the destination name
   *                                  given is an empty string.
   */
  void flipHorizontally(String name, String destination) throws IllegalArgumentException;

  /**
   * flipVertically flips the image vertically. The size of the image does not change. This
   * method creates a new image that is flipped vertically and adds it to the image library with
   * the name that the user gave to be the new name for this image.
   *
   * @param name        the name of the Image that the user wants to alter.
   * @param destination the name that they want the new changed image to have in the image library.
   * @throws IllegalArgumentException if the name given for Image that the user want to be
   *                                  visualized different is not in the image library or if the
   *                                  destination name given
   *                                  is an empty string.
   */
  void flipVertically(String name, String destination) throws IllegalArgumentException;

  /**
   * changeBrightness alters an image by an increment that the user gives. When the user gives a
   * positive number, the picture gets brighter by the amount given, when the user gives a negative
   * number the picture gets darker by the amount given. If the amount given for the increment is 0,
   * we made a design decision that an error will be thrown since it will not change the image
   * at all. This method creates a new image that has changed in brightness by some amount and adds
   * it to the image library with the name that the user gave to be the new name for this image. If
   * when the increment is added, one of the RGB components has a value greater than 255, the number
   * will be capped at 255. If the RGB component has a value less than 0, the number will be stopped
   * at 0.
   *
   * @param increment   the amount that the brightness should change by. If the number is positive
   *                    , it
   *                    will get brighter. If the number is negative, the image will get darker.
   * @param name        the name of the Image that the user wants to change.
   * @param destination the name that the user wants the new changed item to have in the image
   *                    library.
   * @throws IllegalArgumentException if the name given for Image that the user want to be
   *                                  visualized different is not in the image library or the
   *                                  increment to change by is 0
   *                                  or if the destination name given is an empty string.
   */
  void changeBrightness(int increment, String name, String destination)
          throws IllegalArgumentException;

  /**
   * getImage returns the Image associated with the name given in the Image library.
   *
   * @param name the name of the image that the user is looking for.
   * @return the image associate with the name given.
   * @throws IllegalArgumentException if the name given is not a name of the image in the library
   *                                  or if the destination name given is an empty string.
   */
  Image getImage(String name) throws IllegalArgumentException;

  /**
   * addImage takes a 2d pixel array and puts it into the image library with the name that the user
   * gives.
   *
   * @param name  the same that the user wants the pixel to be referred to as.
   * @param image a 2d pixel array in which all the pixels make up an image.
   * @throws IllegalArgumentException if the pixel array given is null or if the name
   *                                  given is an empty string.
   */
  void addImage(String name, Pixel[][] image) throws IllegalArgumentException;

  /**
   * converts the specified image to a bufferedImage format in order to make
   * saving and loading more seamless. This conversion is based on the name of
   * the image we are converting as well as the fileFormat we are converting to.
   * @param name name of the image we are converting.
   * @param fileFormat the file format to which we are saving.
   * @return a new buffered image with the same contents as the image
   *         the user specified.
   */
  BufferedImage convertToBufferedImage(String name, String fileFormat);


}
