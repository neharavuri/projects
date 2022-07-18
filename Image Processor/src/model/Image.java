package model;

import java.awt.image.BufferedImage;
import java.util.HashMap;

/**
 * The Image interface represents a type of Image. Each implementation of Image is a different
 * version of Image.
 */
public interface Image {

  /**
   * visualize creates a new Image that takes this Image and converts it to only visualize 1 of its
   * components. This means that it will create a greyscale image in which the value of the
   * component given for each pixel is assigned to the red, green and blue channels for that pixel.
   *
   * @param c The component that one wants to be visualized as greyscale.
   * @return a new image that is the greyscale version of this image.
   */
  Image visualize(IModel.Component c);

  /**
   * updatePixel changes the Pixel at one particular spot in the Image to be the Pixel that is
   * given.
   *
   * @param x        the row of the position of the pixel that will be changed.
   * @param y        the column of the position of the pixel that will be changed.
   * @param newColor the pixel that should replace the old pixel.
   * @throws IllegalArgumentException when the new Pixel is null or if the row or column
   *                                  is less than 0 or greater than the size of the board.
   */
  void updatePixel(int x, int y, Pixel newColor) throws IllegalArgumentException;

  /**
   * flipH takes this Image and returns a new one that is the same image but flipped horizontally.
   *
   * @return the image that is flipped horizontally.
   */
  Image flipH();

  /**
   * flipV takes this Image and returns a new one that is the same image but flipped vertically.
   *
   * @return the image that is flipped vertically.
   */
  Image flipV();

  /**
   * brightenOrDarken takes this image and returns a new one that is either brightened or
   * darkened by the amount given.
   *
   * @param num the amount that the Image should be brightened or darkened by. If the number is
   *            positive, the image will brighten. If the number is negative, the image will
   *            darken.
   * @return the new image that is either brighter or darker than the original image.
   * @throws IllegalArgumentException if the num to change the brightness by is 0.
   */
  Image brightenOrDarken(int num) throws IllegalArgumentException;

  /**
   * convertToText takes this image and creates a String that when written into a File, it will be
   * able to be read as a file of the correct type.
   *
   * @return the text version of this image.
   */
  String convertToText();

  /**
   * convertToBufferedImage takes this image and creates a BufferedImage version of the pixels so
   * that it can be saved in a file. Any array of pixels (which is what the image is) can be saved
   * as any of the supported image types (PNG, JPG, BMP).
   *
   * @param format the image type that pixels should become.
   * @return a BufferedImage of the image type wanted that represents this image.
   * @throws IllegalArgumentException if the format is not a supported type.
   */
  BufferedImage convertToBufferedImage(String format) throws IllegalArgumentException;

  /**
   * blur takes this image and returns a new version of it that has been blurred.
   *
   * @return the blurred image.
   */
  Image blur();

  /**
   * sharpen takes this image and returns a new version of it that has been sharpened.
   *
   * @return the sharpened image.
   */
  Image sharpen();

  /**
   * filterImage takes this image and returns a new version of it that has undergone a linear color
   * transformation. to determine how to transform each pixel, a 3 by 3 matrix is given. matrix
   * multiplication is used to figure out the red, green and blue components for each pixel.
   *
   * @param matrix the matrix used to determine the color transformation.
   * @return a new image transformed.
   */
  Image transformImage(double[][] matrix);

  /**
   * downsize takes this image and returns a new version of it that has been downsized
   * to the specified height and width. To downsize the image, the original image is scaled
   * to the new dimensions and to determine the color of each new pixel, we perform an
   * algorithm to account for floating point mappings of the original image.
   * @param width the width of the new image.
   * @param height the height of the new image.
   * @return a new downscaled image.
   */
  Image downsize(int width, int height);

  /**
   * computes a hashmap with the frequencies of each value from 0-255.
   * This hashmap is created based on the frequencies of the component
   * that has been specified.
   * @param component the component we are creating a hashmap of.
   * @return the hashmap with the values as the key and the frequencies
   *         as the values.
   */
  HashMap<Integer,Integer> getHistogram(IModel.Component component);

}


