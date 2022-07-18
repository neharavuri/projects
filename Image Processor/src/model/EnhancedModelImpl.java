package model;

import java.util.HashMap;

/**
 * Represents the implementation of the methods in the enhanced model. This includes the ability
 * to blur, and sharpen the image, as well as produce greyscale and sepia versions of the images.
 */
public class EnhancedModelImpl extends ModelImpl implements EnhancedModel {
  @Override
  public void blur(String name, String destination) throws IllegalArgumentException {
    this.functionHelper(destination, this.getImage(name).blur());
  }

  @Override
  public void downsize(String name, String destination, int height, int width)
          throws IllegalArgumentException {
    this.functionHelper(destination, this.getImage(name).downsize(width,height));
  }

  @Override
  public void sharpen(String name, String destination) throws IllegalArgumentException {
    this.functionHelper(destination, this.getImage(name).sharpen());
  }

  @Override
  public void greyscale(String name, String destination) throws IllegalArgumentException {
    double[][] matrix = new double[][]{{0.2126, 0.7152, 0.0722},
        {0.2126, 0.7152, 0.0722}, {0.2126, 0.7152, 0.0722}};
    this.functionHelper(destination, this.getImage(name).transformImage(matrix));
  }

  @Override
  public void sepia(String name, String destination) throws IllegalArgumentException {
    double[][] matrix = new double[][]{{0.393, 0.769, 0.189},
        {0.349, 0.686, 0.168}, {0.272, 0.534, 0.131}};
    this.functionHelper(destination, this.getImage(name).transformImage(matrix));
  }

  @Override
  public HashMap<Integer, Integer> getHistogram(String name, Component c) throws
          IllegalArgumentException {
    if (c == Component.Luma || c == Component.Value) {
      throw new IllegalArgumentException("The component cannot be visualized as a histogram");
    }
    return this.getImage(name).getHistogram(c);
  }
}
