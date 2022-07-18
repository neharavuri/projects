package model;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

/**
 * A ModelImpl is a version of the IModel for an Image Processing software. This supports the basic
 * implementations of image processing tools. This includes, flipping the image, visualizing the
 * components, and brightening the image. You can also add images to the library and retrieve
 * images from the library.
 */
public class ModelImpl implements IModel {
  // A Hashmap full in which all the images in the photo library are stored. The String
  // represents the name of the image and the image is the actual image itself.
  private final Map<String, Image> images;

  /**
   * The constructor for the ModelImpl. A new object of ModelImpl takes in no arguments and
   * initializes the hashmap of images to just be an empty Hashmap.
   */
  public ModelImpl() {
    this.images = new HashMap<>();
  }


  @Override
  public void addImage(String name, Pixel[][] image) throws IllegalArgumentException {
    // checks if the image is null
    if (image == null) {
      throw new IllegalArgumentException("The image given cannot be null");
    }
    checkNonEmpty(name);
    Image newImage;
    newImage = new ImageImpl(image.length, image[0].length, image);
    this.images.put(name, newImage);
  }

  @Override
  public BufferedImage convertToBufferedImage(String name, String fileFormat) {
    return this.getImage(name).convertToBufferedImage(fileFormat);
  }

  @Override
  public void visualizeComponent(String name, Component comp, String destination)
          throws IllegalArgumentException {
    this.functionHelper(destination, this.getImage(name).visualize(comp));
  }

  @Override
  public void flipHorizontally(String name, String destination) throws IllegalArgumentException {
    this.functionHelper(destination, this.getImage(name).flipH());
  }

  @Override
  public void flipVertically(String name, String destination) throws IllegalArgumentException {
    this.functionHelper(destination, this.getImage(name).flipV());
  }

  @Override
  public void changeBrightness(int increment, String name, String destination)
          throws IllegalArgumentException {
    if (increment == 0) {
      throw new IllegalArgumentException("You cannot change the brightness of an " +
              "image by an increment of 0");
    }
    this.functionHelper(destination, this.getImage(name).brightenOrDarken(increment));
  }

  @Override
  public Image getImage(String name) throws IllegalArgumentException {
    if (this.images.containsKey(name)) {
      return this.images.get(name);
    } else {
      throw new IllegalArgumentException("There is no image with this name");
    }
  }

  /**
   * This method helps abstract all the functions by checking to ensure that the destination
   * name is not empty and adds the changed Image to the Hashmap of Images.
   *
   * @param destination the name that the new image should have.
   * @param changed     the version of the image that is an altered version of an original image.
   */
  protected void functionHelper(String destination, Image changed) {
    checkNonEmpty(destination);
    this.images.put(destination, changed);
  }

  /**
   * Checks to ensure that this String is not empty.
   *
   * @param name the String that is being checked.
   * @throws IllegalArgumentException if the String is empty.
   */
  protected void checkNonEmpty(String name) throws IllegalArgumentException {
    if (name.equals("")) {
      throw new IllegalArgumentException("Nothing can be referred to as an empty string");
    }
  }

  /**
   * ImageImpl is a private class that represents an Image. We made a design decision for
   * it to be a private class within the ModelImpl class so that the images themselves cannot be
   * altered without it going through the model itself. An image is made up of a 2d array of
   * pixels.
   */

  private static class ImageImpl implements Image {
    private final HashMap<String, Integer> imageType;
    // The pixels included in this PPM
    private final Pixel[][] pixels;
    // the width of the image in pixels.
    private int width;
    // the height of the image in pixels.
    private int height;

    private HashMap<Integer, Integer> redHistogram;
    private HashMap<Integer, Integer> greenHistogram;
    private HashMap<Integer, Integer> blueHistogram;
    private HashMap<Integer, Integer> intensityHistogram;


    /**
     * constructor for ImageImpl that takes in the height/width of the image
     * as well as the pixel array that represents the pixels in the image. The imageType
     * is initialized to an empty hashmap and the respective integers that correspond
     * with the file formats are put into the map.
     *
     * @param height height of the image.
     * @param width  width of the image.
     * @param pixels 2d array of pixels representing the pixels in the image.
     * @throws IllegalArgumentException height/width is not positive
     *                                  or the pixel array is null.
     */
    private ImageImpl(int height, int width, Pixel[][] pixels)
            throws IllegalArgumentException {
      if (height <= 0 || width <= 0) {
        throw new IllegalArgumentException("Given values are out of range of allotted values");
      }
      if (pixels == null) {
        throw new IllegalArgumentException("There must be pixels for this to be an image");
      }
      this.width = width;
      this.height = height;
      this.pixels = pixels;
      this.imageType = new HashMap<>();
      imageType.put("png", 6);
      imageType.put("jpg", 5);
      imageType.put("bmp", 5);
      createHistograms();
    }

    /**
     * constructor for ImageImpl that takes in the height/width of the image. The pixel
     * array is initialized to an empty 2d array of pixels.
     * The imageType is initialized to an empty hashmap and the respective integers that correspond
     * with the file formats are put into the map.
     *
     * @param height height of the image.
     * @param width  width of the image.
     * @throws IllegalArgumentException height/width is not positive
     *                                  or the pixel array is null.
     */
    private ImageImpl(int height, int width) {
      this(height, width, new Pixel[height][width]);
    }

    @Override
    public Image visualize(IModel.Component c) {
      Pixel[][] updatedPixels = new Pixel[height][width];
      for (int i = 0; i < height; i++) {
        for (int j = 0; j < width; j++) {
          int num = pixels[i][j].getVisualize(c);
          updatedPixels[i][j] = new Pixel(num, num, num);
        }
      }
      return new ImageImpl(height, width, updatedPixels);
    }

    @Override
    public void updatePixel(int x, int y, Pixel newColor) throws IllegalArgumentException {
      if (x < 0 || x >= height || y < 0 || y > width - 1) {
        throw new IllegalArgumentException(
                "The pixel called is not within the range of this board");
      }
      if (newColor == null) {
        throw new IllegalArgumentException("You need to give a non null value for the new pixel");
      }
      if (pixels[x][y] != null) {
        Pixel current = pixels[x][y];
        redHistogram.put(current.getRed(), redHistogram.get(current.getRed()) - 1);
        blueHistogram.put(current.getBlue(), blueHistogram.get(current.getBlue()) - 1);
        greenHistogram.put(current.getGreen(), greenHistogram.get(current.getGreen()) - 1);
        intensityHistogram.put(current.getVisualize(Component.Intensity),
                intensityHistogram.get(current.getVisualize(Component.Intensity)) - 1);
      }
      updateHistogram(Component.Red, newColor);
      updateHistogram(Component.Green, newColor);
      updateHistogram(Component.Blue, newColor);
      updateHistogram(Component.Intensity, newColor);
      pixels[x][y] = newColor;
    }


    @Override
    public Image flipH() {
      Image flipped = new ImageImpl(height, width, new Pixel[height][width]);
      for (int i = 0; i < height; i++) {
        for (int j = 0; j < width / 2; j++) {
          Pixel left = pixels[i][j];
          Pixel right = pixels[i][width - j - 1];
          flipped.updatePixel(i, j, new Pixel(right.getRed(), right.getGreen(), right.getBlue()));
          flipped.updatePixel(i, width - j - 1, new Pixel(left.getRed(),
                  left.getGreen(), left.getBlue()));
        }
      }
      if (width % 2 == 1) {
        for (int k = 0; k < height; k++) {
          Pixel old = pixels[k][(int) width / 2];
          flipped.updatePixel(k, (int) width / 2, new Pixel(old.getRed(), old.getGreen(),
                  old.getBlue()));
        }
      }
      return flipped;
    }

    @Override
    public Image flipV() {
      Image flipped = new ImageImpl(height, width, new Pixel[height][width]);
      for (int i = 0; i < height / 2; i++) {
        for (int j = 0; j < width; j++) {
          Pixel up = pixels[i][j];
          Pixel down = pixels[height - i - 1][j];
          flipped.updatePixel(i, j, new Pixel(down.getRed(), down.getGreen(), down.getBlue()));
          flipped.updatePixel(height - i - 1, j, new Pixel(up.getRed(), up.getGreen(),
                  up.getBlue()));
        }
      }
      if (height % 2 == 1) {
        for (int k = 0; k < width; k++) {
          Pixel old = pixels[(int) width / 2][k];
          flipped.updatePixel((int) width / 2, k, new Pixel(old.getRed(), old.getGreen(),
                  old.getBlue()));
        }
      }
      return flipped;
    }


    @Override
    public Image brightenOrDarken(int num) throws IllegalArgumentException {
      if (num == 0) {
        throw new IllegalArgumentException(
                "The image will not change if the increment to change is 0");
      }
      Image updated = new ImageImpl(height, width, new Pixel[height][width]);
      for (int i = 0; i < height; i++) {
        for (int j = 0; j < width; j++) {
          Pixel current = pixels[i][j];
          int red = this.process(current.getRed() + num);
          int green = this.process(current.getGreen() + num);
          int blue = this.process(current.getBlue() + num);
          updated.updatePixel(i, j, new Pixel(red, green, blue));
        }
      }
      return updated;
    }


    /**
     * This method ensures that the number in the RGB is not negative nor it is above 255. If it
     * is negative, the number will be returned as 0 and if it is over 255, the number will be
     * returned as 255.
     *
     * @param num the number for one of the components for a pixel.
     * @return the number for that component in the pixel. It will not be less than 0 or greater
     *         than 255.
     */
    private int process(int num) {
      if (num < 0) {
        return 0;
      } else if (num > 255) {
        return 255;
      }
      return num;
    }

    @Override
    public String convertToText() {
      StringBuilder result = new StringBuilder();
      result.append("P3");
      result.append("\n" + "# created by kate and neha :)");
      result.append("\n" + width + " " + height);
      result.append("\n" + determineMaxVal(pixels));
      for (int i = 0; i < height; i++) {
        for (int j = 0; j < width; j++) {
          result.append(String.format("\n%d %d %d",
                  pixels[i][j].getRed(),
                  pixels[i][j].getGreen(),
                  pixels[i][j].getBlue()));
        }
      }
      return result.toString();
    }

    @Override
    public BufferedImage convertToBufferedImage(String format) throws IllegalArgumentException {
      if (imageType.get(format) == null) {
        throw new IllegalArgumentException("unsupported image type");
      }
      BufferedImage result = new BufferedImage(this.width, this.height, imageType.get(format));
      for (int i = 0; i < this.height; i++) {
        for (int j = 0; j < this.width; j++) {
          int red = pixels[i][j].getRed();
          int green = pixels[i][j].getGreen();
          int blue = pixels[i][j].getBlue();
          int alpha = pixels[i][j].getAlpha();
          int rgb = new Color(red, green, blue, alpha).getRGB();
          result.setRGB(j, i, rgb);
        }
      }
      return result;
    }

    @Override
    public Image blur() {
      Double[][] vals = new Double[][]{{0.0625, 0.125, 0.0625},
          {0.125, 0.25, 0.125}, {0.0625, 0.125, 0.0625}};

      Image updated = new ImageImpl(height, width, new Pixel[height][width]);

      for (int i = 0; i < height; i++) {
        for (int j = 0; j < width; j++) {
          updated.updatePixel(i, j, this.filterHelper(vals, i, j, 3));
        }
      }
      return updated;
    }

    @Override
    public Image sharpen() {
      Double[][] vals = new Double[][]{{-0.125, -0.125, -0.125, -0.125, -0.125},
          {-0.125, 0.25, 0.25, 0.25, -0.125},
          {-0.125, 0.25, 1.0, 0.25, -0.125},
          {-0.125, 0.25, 0.25, 0.25, -0.125},
          {-0.125, -0.125, -0.125, -0.125, -0.125}};

      Image updated = new ImageImpl(height, width, new Pixel[height][width]);

      for (int i = 0; i < height; i++) {
        for (int j = 0; j < width; j++) {
          updated.updatePixel(i, j, this.filterHelper(vals, i, j, 5));
        }
      }
      return updated;
    }

    /**
     * helps the filter methods by filtering an individual pixel of the image.
     *
     * @param vals       the kernel used to filter the pixel.
     * @param row        the row of the pixel to be filtered.
     * @param col        the col of the pixel to be filtered.
     * @param kernelSize how big the kernel used to filter the image is.
     * @return a filtered pixel.
     */
    private Pixel filterHelper(Double[][] vals, int row, int col, int kernelSize) {
      double redSum = 0;
      double blueSum = 0;
      double greenSum = 0;
      for (int i = row - (kernelSize / 2); i <= row + (kernelSize / 2); i++) {
        if (i < 0 || i >= this.height) {
          //do nothing
        } else {
          for (int j = col - (kernelSize / 2); j <= col + (kernelSize / 2); j++) {
            if (j < 0 || j >= this.width) {
              // do nothing
            } else {
              redSum = redSum + (pixels[i][j].getRed()
                      * vals[i - (row - (kernelSize / 2))][j - (col - (kernelSize / 2))]);
              blueSum = blueSum + (pixels[i][j].getBlue() * vals[i - (row - (kernelSize / 2))]
                      [j - (col - (kernelSize / 2))]);
              greenSum = greenSum + (pixels[i][j].getGreen() * vals[i - (row - (kernelSize / 2))]
                      [j - (col - (kernelSize / 2))]);
            }
          }
        }
      }
      return new Pixel(this.process((int) redSum), this.process((int) greenSum),
              this.process((int) blueSum));
    }

    public Image transformImage(double[][] matrix) {
      Image updated = new ImageImpl(height, width, new Pixel[height][width]);
      for (int i = 0; i < this.height; i++) {
        for (int j = 0; j < this.width; j++) {
          updated.updatePixel(i, j, this.colorFilter(i, j, matrix));
        }
      }
      return updated;
    }

    @Override
    public Image downsize(int width, int height) {
      if (width > this.width || height > this.height) {
        throw new IllegalArgumentException("width and height must be less than the original " +
                "picture");
      }
      Image updated = new ImageImpl(height, width, new Pixel[height][width]);
      float ratioW = ((float) this.width) / ((float) width);
      float ratioH = ((float) this.height) / ((float) height);
      for (int i = 0; i < height; i++) {
        for (int j = 0; j < width; j++) {
          //columns
          float x = ratioW * j;
          //rows
          float y = ratioH * i;
          int floorY = (int) y;
          int ceilY = (int) Math.floor(y + 1);
          int floorX = (int) x;
          int ceilX = (int) Math.floor(x + 1);
          int aRed = 1;
          int aGreen = 1;
          int aBlue = 1;
          int bRed = 1;
          int bGreen = 1;
          int bBlue = 1;
          int cRed = 1;
          int cGreen = 1;
          int cBlue = 1;
          int dRed = 1;
          int dGreen = 1;
          int dBlue = 1;

          if (this.isValid(floorX, this.width) && this.isValid(floorY, this.height)) {
            aRed = pixels[floorY][floorX].getRed();
            aGreen = pixels[floorY][floorX].getGreen();
            aBlue = pixels[floorY][floorX].getBlue();
          }
          if (this.isValid(ceilX, this.width) && this.isValid(floorY, this.height)) {
            bRed = pixels[floorY][ceilX].getRed();
            bGreen = pixels[floorY][ceilX].getGreen();
            bBlue = pixels[floorY][ceilX].getBlue();
          }
          if (this.isValid(floorX, this.width) && this.isValid(ceilY, this.height)) {
            cRed = pixels[ceilY][floorX].getRed();
            cGreen = pixels[ceilY][floorX].getGreen();
            cBlue = pixels[ceilY][floorX].getBlue();
          }
          if (this.isValid(ceilX, this.width) && this.isValid(ceilY, this.height)) {
            dRed = pixels[ceilY][ceilX].getRed();
            dGreen = pixels[ceilY][ceilX].getGreen();
            dBlue = pixels[ceilY][ceilX].getBlue();
          }
          float mRed = (bRed * (x - floorX) + aRed * (ceilX - x));
          float mGreen = (bGreen * (x - floorX) + aGreen * (ceilX - x));
          float mBlue = (bBlue * (x - floorX) + aBlue * (ceilX - x));
          float nRed = ((dRed * (x - floorX)) + (cRed * (ceilX - x)));
          float nGreen = (dGreen * (x - floorX) + cGreen * (ceilX - x));
          float nBlue = (dBlue * (x - floorX) + cBlue * (ceilX - x));
          int pRed = (int) (nRed * (y - floorY) + mRed * (ceilY - y));
          int pGreen = (int) (nGreen * (y - floorY) + mGreen * (ceilY - y));
          int pBlue = (int) (nBlue * (y - floorY) + mBlue * (ceilY - y));
          updated.updatePixel(i, j, new Pixel(pRed, pGreen, pBlue));
        }
      }
      this.changeSize(width, height);
      return updated;
    }

    private boolean isValid(int number, int upperLimit) {
      return (number >= 0 && number < upperLimit);
    }

    @Override
    public HashMap<Integer, Integer> getHistogram(IModel.Component component) {
      HashMap<Integer, Integer> current;
      HashMap<Integer, Integer> output = new HashMap<>();
      switch (component) {
        case Red:
          current = this.redHistogram;
          break;
        case Green:
          current = this.greenHistogram;
          break;
        case Blue:
          current = this.blueHistogram;
          break;
        case Intensity:
          current = this.intensityHistogram;
          break;
        default:
          // this will never be reached since this is checked in the method calling
          // this one
          throw new IllegalArgumentException("This is not a valid component to " +
                  "make a histogram from");
      }
      output.putAll(current);
      return output;
    }

    public void changeSize(int width, int height) {
      this.width = width;
      this.height = height;
    }


    /**
     * applies the given color filter to the specific pixel at the coordinates
     * given.
     *
     * @param row    the row of the pixel to be filtered.
     * @param col    the col of the pixel to be filtered.
     * @param matrix the matrix used to filter the pixel.
     * @return a pixel filtered.
     */
    protected Pixel colorFilter(int row, int col, double[][] matrix) {
      Pixel current = pixels[row][col];
      int newRed = (int) ((current.getRed() * matrix[0][0])
              + (current.getGreen() * matrix[0][1])
              + (current.getBlue() * matrix[0][2]));
      int newGreen = (int) ((current.getRed() * matrix[1][0])
              + (current.getGreen() * matrix[1][1])
              + (current.getBlue() * matrix[1][2]));
      int newBlue = (int) ((current.getRed() * matrix[2][0])
              + (current.getGreen() * matrix[2][1])
              + (current.getBlue() * matrix[2][2]));
      return new Pixel(this.process(newRed), this.process(newGreen), this.process(newBlue));
    }

    /**
     * Goes through an array of pixels to determine what the max value is for a PPM. The max value
     * is the highest number of red, blue, or green that is found anywhere in the 2d pixel array.
     *
     * @param pixels The 2d array of pixels that will make up the image.
     * @return whatever the max value is for this array.
     */
    private int determineMaxVal(Pixel[][] pixels) {
      int max = 0;
      for (int i = 0; i < pixels.length; i++) {
        for (int j = 0; j < pixels[0].length; j++) {
          if (pixels[i][j].getRed() > max) {
            max = pixels[i][j].getRed();
          }
          if (pixels[i][j].getGreen() > max) {
            max = pixels[i][j].getGreen();
          }
          if (pixels[i][j].getBlue() > max) {
            max = pixels[i][j].getBlue();
          }
        }
      }
      return max;
    }

    private void createHistograms() {
      redHistogram = new HashMap<>();
      blueHistogram = new HashMap<>();
      greenHistogram = new HashMap<>();
      intensityHistogram = new HashMap<>();
      for (int i = 0; i < 256; i++) {
        redHistogram.put(i, 0);
        blueHistogram.put(i, 0);
        greenHistogram.put(i, 0);
        intensityHistogram.put(i, 0);
      }
      for (int h = 0; h < height; h++) {
        for (int w = 0; w < width; w++) {
          if (pixels[h][w] != null) {
            Pixel pixel = pixels[h][w];
            updateHistogram(Component.Red, pixel);
            updateHistogram(Component.Green, pixel);
            updateHistogram(Component.Blue, pixel);
            updateHistogram(Component.Intensity, pixel);
          }
        }
      }
    }

    private void updateHistogram(Component comp, Pixel pixel) throws IllegalArgumentException {
      int val = pixel.getVisualize(comp);
      switch (comp) {
        case Red:
          redHistogram.put(val, redHistogram.get(val) + 1);
          break;
        case Green:
          greenHistogram.put(val, greenHistogram.get(val) + 1);
          break;
        case Blue:
          blueHistogram.put(val, blueHistogram.get(val) + 1);
          break;
        case Intensity:
          intensityHistogram.put(val, intensityHistogram.get(val) + 1);
          break;
        default:
          throw new IllegalArgumentException("This is not a component that can be visualized");
      }
    }
  }
}




