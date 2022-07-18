package model;

/**
 * Represents one Pixel that is a part of an Image. For a pixel, the red, green and blue components
 * is recorded, as well as different measures of brightness/intensity for the pixel. It was decided
 * for this to be a public class without an interface since pixels make up every image and there
 * is not another way to visualize one singular section of an Image.
 */
public class Pixel {
  // represents how much red is in this pixel
  private final int red;
  // represents how much green is in this pixel
  private final int green;
  // represents how much blue is in this pixel
  private final int blue;
  // represents the value of this pixel which is the max value of the red, green and blue
  // components for this pixel.
  private final int value;
  // represents the intensity of this pixel which is the average of the three red, blue and green
  // components for this pixel.
  private final double intensity;
  // represents the luma of this pixel which is the weighted sum of the three red, green and blue
  // components for this image. luma = 0.2126r + 0.7152g + 0.0722b
  private final double luma;

  //represents the alpha component of this pixel
  //corresponds to transparency values
  private final int alpha;

  /**
   * Represents the constructor for the pixel which takes in the red, green, and blue components
   * and assigns the luma, intensity and value based off of that.
   *
   * @param red   how much red is in this pixel.
   * @param green how much green is in this pixel.
   * @param blue  how much blue is in this pixel.
   * @throws IllegalArgumentException if any of the red, green or blue components given are less
   *                                  than 0 or greater than 255.
   */
  public Pixel(int red, int green, int blue, int alpha) throws IllegalArgumentException {
    if (!(checkColor(red) && checkColor(green) && checkColor(blue))) {
      throw new IllegalArgumentException("This is not a valid color");
    }
    this.red = red;
    this.green = green;
    this.blue = blue;
    this.alpha = alpha;
    this.value = Math.max(Math.max(red, green), blue);
    this.intensity = (double) (red + green + blue) / 3.0;
    this.luma = 0.2126 * (double) red + 0.7152 * (double) green + 0.0722 * (double) blue;
  }

  /**
   * Represents the constructor for the pixel which takes in the red, green, and blue components
   * and assigns the luma, intensity and value based off of that.
   *
   * @param red   how much red is in this pixel.
   * @param green how much green is in this pixel.
   * @param blue  how much blue is in this pixel.
   * @throws IllegalArgumentException if any of the red, green or blue components given are less
   *                                  than 0 or greater than 255.
   */
  public Pixel(int red, int green, int blue) throws IllegalArgumentException {
    if (!(checkColor(red) && checkColor(green) && checkColor(blue))) {
      throw new IllegalArgumentException("This is not a valid color");
    }
    this.red = red;
    this.green = green;
    this.blue = blue;
    this.alpha = 255;
    this.value = Math.max(Math.max(red, green), blue);
    this.intensity = (double) (red + green + blue) / 3.0;
    this.luma = 0.2126 * (double) red + 0.7152 * (double) green + 0.0722 * (double) blue;
  }

  /**
   * Checks to ensure that the magnitude of color component is valid.
   *
   * @param color the magnitude of the color component that is being checked.
   * @return true if the color component is within the 0-255 range, inclusive. false otherwise
   */
  private boolean checkColor(int color) {
    return color >= 0 && color <= 255;
  }

  /**
   * Retrieves the amount of red in this pixel.
   *
   * @return how much red is in this pixel.
   */
  public int getRed() {
    return this.red;
  }

  /**
   * Retrieves the amount of green in this pixel.
   *
   * @return how much green is in this pixel.
   */
  public int getGreen() {
    return this.green;
  }

  /**
   * Retrieves the amount of blue in this pixel.
   *
   * @return how much blue is in this pixel.
   */
  public int getBlue() {
    return this.blue;
  }

  public int getAlpha() {
    return this.alpha;
  }

  /**
   * Takes in a component (one of the colors or the way to visualize intensity/brightness) and
   * returns that value for this Pixel.
   *
   * @param c the component that you want the amount of.
   * @return the amount of that component that is in the image. If the number is a double (luma or
   *         intensity), the number is floored to be an int.
   */
  public int getVisualize(IModel.Component c) {
    switch (c) {
      case Blue:
        return this.blue;
      case Red:
        return this.red;
      case Green:
        return this.green;
      case Luma:
        return (int) this.luma;
      case Value:
        return this.value;
      case Intensity:
        return (int) this.intensity;
      default:
        throw new IllegalArgumentException("This is impossible to reach since every possible" +
                "component is listed");
    }
  }
}
