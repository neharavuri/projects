import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;

import model.EnhancedModel;
import model.Image;
import model.Pixel;

/**
 * Represents a Mock version of the model (and the Image within it) to be used for testing.
 */
public class MockModel implements EnhancedModel {
  Appendable log;

  MockModel(Appendable log) {
    this.log = log;
  }

  @Override
  public void visualizeComponent(String name, Component comp, String destination)
          throws IllegalArgumentException {
    try {
      this.log.append("visualize component: ").append(name).append(" ").append(String.valueOf(comp))
              .append(" ").append(destination).append("\n");
    } catch (IOException e) {
      throw new IllegalArgumentException();
    }
  }

  @Override
  public void flipHorizontally(String name, String destination) throws IllegalArgumentException {
    try {
      this.log.append("flip horizontally: ").append(name).append(" ").append(destination)
              .append("\n");
    } catch (IOException e) {
      throw new IllegalArgumentException();
    }
  }

  @Override
  public void flipVertically(String name, String destination) throws IllegalArgumentException {
    try {
      this.log.append("flip vertically: ").append(name).append(" ").append(destination).append(
              "\n");
    } catch (IOException e) {
      throw new IllegalArgumentException();
    }
  }

  @Override
  public void changeBrightness(int increment, String name, String destination)
          throws IllegalArgumentException {
    try {
      this.log.append("change brightness: ").append(increment + "")
              .append(" ").append(name).append(" ").append(destination).append("\n");
    } catch (IOException e) {
      throw new IllegalArgumentException();
    }
  }

  @Override
  public Image getImage(String name) throws IllegalArgumentException {
    try {
      this.log.append("get image: ").append(name).append("\n");
    } catch (IOException e) {
      throw new IllegalArgumentException();
    }
    return new MockImage(this.log);
  }

  @Override
  public void addImage(String name, Pixel[][] image) throws IllegalArgumentException {
    try {
      this.log.append("add image: ").append(name)
              .append(" ").append(image.length + "\n");
    } catch (IOException e) {
      throw new IllegalArgumentException();
    }
  }

  @Override
  public BufferedImage convertToBufferedImage(String name, String fileFormat) {
    return null;
  }

  @Override
  public void blur(String name, String destination) throws IllegalArgumentException {
    try {
      this.log.append("blur: ").append(name).append(" ").append(destination).append("\n");
    } catch (IOException e) {
      throw new IllegalArgumentException();
    }
  }


  @Override
  public void sharpen(String name, String destination) throws IllegalArgumentException {
    try {
      this.log.append("sharpen: ").append(name).append(" ").append(destination).append("\n");
    } catch (IOException e) {
      throw new IllegalArgumentException();
    }
  }

  @Override
  public void greyscale(String name, String destination) throws IllegalArgumentException {
    try {
      this.log.append("greyscale: ").append(name).append(" ").append(destination).append("\n");
    } catch (IOException e) {
      throw new IllegalArgumentException();
    }
  }

  @Override
  public void sepia(String name, String destination) throws IllegalArgumentException {
    try {
      this.log.append("sepia: ").append(name).append(" ").append(destination).append("\n");
    } catch (IOException e) {
      throw new IllegalArgumentException();
    }
  }

  @Override
  public void downsize(String name, String destination, int height, int width) {
    try {
      this.log.append("downsize: ")
              .append(name).append(" ")
              .append(destination).append(" ")
              .append(" " + height).append(" " +
                      width).append(" ");
    } catch (IOException e) {
      throw new IllegalArgumentException();
    }
  }

  @Override
  public HashMap<Integer, Integer> getHistogram(String name, Component c) {
    return null;
  }


  class MockImage implements Image {
    Appendable log;

    private MockImage(Appendable log) {
      this.log = log;
    }

    @Override
    public Image visualize(Component c) {
      try {
        log.append("visualize: " + c.toString() + "\n");
      } catch (IOException e) {
        System.out.println("transmission failed");
      }
      return this;
    }

    @Override
    public void updatePixel(int x, int y, Pixel newColor) throws IllegalArgumentException {
      try {
        log.append("updatePixel: " + x + " " + y + " " + newColor.toString() + "\n");
      } catch (IOException e) {
        System.out.println("transmission failed");
      }
    }

    @Override
    public Image flipH() {
      return this;
    }

    @Override
    public Image flipV() {
      return this;
    }

    @Override
    public Image brightenOrDarken(int num) throws IllegalArgumentException {
      try {
        log.append("brighten/darken: " + num + "\n");
      } catch (IOException e) {
        System.out.println("transmission failed");
      }
      return this;
    }

    @Override
    public String convertToText() {
      return "";
    }

    @Override
    public BufferedImage convertToBufferedImage(String format) throws IllegalArgumentException {
      return null;
    }

    @Override
    public Image blur() {
      return null;
    }

    @Override
    public Image sharpen() {
      return null;
    }

    @Override
    public Image transformImage(double[][] matrix) {
      return null;
    }

    @Override
    public Image downsize(int width, int height) {
      return null;
    }

    @Override
    public HashMap<Integer, Integer> getHistogram(Component component) {
      return null;
    }

  }
}