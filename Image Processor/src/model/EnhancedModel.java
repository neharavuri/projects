package model;

import java.util.HashMap;

/**
 * Represents the operations needed to have an Enhanced model. In this enhanced version, the images
 * can be blurred, and sharpened. It is also possible to produce sepia and greyscale versions of
 * the images.
 */
public interface EnhancedModel extends IModel {

  /**
   * blur creates a version of the image in which the image is blurred. This method creates a
   * new image that is a blurred version of the given image and adds it to the image library with
   * the name that the user gives.
   *
   * @param name        the name of the Image that the user wants to blur.
   * @param destination the name the user wants the new blurred image to have in the library.
   * @throws IllegalArgumentException if the name given for Image that the user want to be
   *                                  blurred is not in the image library or if the destination
   *                                  name given is an empty string.
   */
  void blur(String name, String destination) throws IllegalArgumentException;


  /**
   * sharpen creates a version of the image in which the image is sharpened. This method creates a
   * new image that is a sharpened version of the given image and adds it to the image library with
   * the name that the user gives.
   *
   * @param name        the name of the Image that the user wants to sharpen.
   * @param destination the name the user wants the new sharpened image to have in the library.
   * @throws IllegalArgumentException if the name given for Image that the user want to be
   *                                  sharpened is not in the image library or if the destination
   *                                  name given is an empty string.
   */
  void sharpen(String name, String destination) throws IllegalArgumentException;

  /**
   * greyscale creates a version of the image in which the image is fully in greyscale. This method
   * creates a new image that is a grey scaled version of the given image and adds it to the image
   * library with the name that the user gives.
   *
   * @param name        the name of the Image that the user wants to greyscale.
   * @param destination the name the user wants the new grey scaled image to have in the library.
   * @throws IllegalArgumentException if the name given for Image that the user want to be
   *                                  grey scaled is not in the image library or if the destination
   *                                  name given is an empty string.
   */
  void greyscale(String name, String destination) throws IllegalArgumentException;

  /**
   * sepia creates a version of the image in which the image is filtered to be sepia. This method
   * creates a new image that is a sepia version of the given image and adds it to the image library
   * with the name that the user gives.
   *
   * @param name        the name of the Image that the user wants to produce a sepia version of.
   * @param destination the name the user wants the new version of the image to have in the library.
   * @throws IllegalArgumentException if the name given for Image that the user want to have the
   *                                  sepia version of is not in the image library or if the
   *                                  destination name given is an empty string.
   */
  void sepia(String name, String destination) throws IllegalArgumentException;

  /**
   * Represents a method to return the histogram for a certain component of an image.
   *
   * @param name the name of the image in which the histogram should be made for.
   * @param c    the component in which the image should be visualized for.
   * @return a HashMap that represents the histogram. In the Hashmap, the key represents the value
   *         of a component and the value represents the frequency of that number in this image.
   * @throws IllegalArgumentException if the name does not exist or if the component given is not
   *                                  a component that should be visualized in a hashmap
   *                                  (aka value or luma)
   */
  HashMap<Integer, Integer> getHistogram(String name, Component c) throws IllegalArgumentException;


  /**
   * downsize creates a version of the image that retains the pixels/pixel colors of the original
   * image but scales it down to the specified height and width using an algorithm that
   * takes into account the 4 pixels around a floating point location in the original picture.
   * @param name the name of the image the user wants to downsize.
   * @param destination the name the user wants the new version of the image to have in the library.
   * @param height the height the user wants to downsize the image to.
   * @param width the width the user wants to downsize the image to.
   * @throws IllegalArgumentException if the width/height are bigger than that of the original
   *         image or if they are not positive integers.
   */
  void downsize(String name, String destination, int height, int width)
          throws IllegalArgumentException;
}
