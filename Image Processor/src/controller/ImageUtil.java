

package controller;

import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.FileInputStream;

import model.Pixel;


/**
 * This class contains utility methods to read a PPM image from file and create a 2d array of
 * pixels representing it.
 */
public class ImageUtil {

  /**
   * Read an image file in the PPM format and returns the image as a 2d array of pixels.
   *
   * @param filename the path of the file.
   * @return a 2d array of Pixels representing the image.
   * @throws IllegalArgumentException when the file does not exist or if the file is not a PPM file.
   */
  public static Pixel[][] readPPM(String filename) throws IllegalArgumentException {
    Scanner sc;

    try {
      sc = new Scanner(new FileInputStream(filename));
    } catch (FileNotFoundException e) {
      throw new IllegalArgumentException("File " + filename + " not found!");
    }
    StringBuilder builder = new StringBuilder();
    //read the file line by line, and populate a string. This will throw away any comment lines
    while (sc.hasNextLine()) {
      String s = sc.nextLine();
      if (s.charAt(0) != '#') {
        builder.append(s + System.lineSeparator());
      }
    }

    //now set up the scanner to read from the string we just built
    sc = new Scanner(builder.toString());

    String token;

    token = sc.next();
    if (!token.equals("P3")) {
      throw new IllegalArgumentException("Invalid PPM file: plain RAW file should begin with P3");
    }
    int width = sc.nextInt();
    int height = sc.nextInt();
    int maxValue = sc.nextInt();

    Pixel[][] pixels = new Pixel[height][width];

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        int r = sc.nextInt();
        int g = sc.nextInt();
        int b = sc.nextInt();
        Pixel color = new Pixel(r, g, b);
        pixels[i][j] = color;
      }
    }
    return pixels;
  }

}